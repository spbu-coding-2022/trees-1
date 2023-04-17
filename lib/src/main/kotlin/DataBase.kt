import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.exceptions.SessionExpiredException
import treelib.DBNodeRB
import treelib.singleObjects.Container
import treelib.singleObjects.Markers
import java.io.Closeable
import java.io.IOException
import java.util.*

class Neo4jRepository : Closeable {

    private var driver: Driver? = null

    fun open(uri: String, username: String, password: String) {
        try {
            driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password))
        } catch (ex: IllegalArgumentException) {
            throw IOException()
        } catch (ex: SessionExpiredException) {
            throw IOException()
        }
    }

    fun <Pack : Comparable<Pack>> saveChanges(preOrder: Array<DBNodeRB<Pack>>, inOrder: Array<DBNodeRB<Pack>>) {

        /*** сюда по ощущениям лучше всего добавлять именно то поддерево исходного дерева, которое было изменено ***/
        val session = driver?.session() ?: throw IOException()

        var inOrderIndex = 0
        var preOrderIndex = 0
        val set = HashSet<DBNodeRB<Pack>>()
        val stack = LinkedList<DBNodeRB<Pack>>()

        while (preOrderIndex in preOrder.indices) {
            do {
                val currentNode = preOrder[preOrderIndex]
                currentNode.value as Container<*, *>
                if (preOrderIndex == 0) {
                    session.executeWrite { tx ->
                        tx.run(
                            "MERGE(:Node {value: \$nodeValue, key: \$nodeKey, color: \$nodeColor, x: \$nodeX, y: \$nodeY})",
                            mutableMapOf(
                                "nodeValue" to currentNode.value.pair.second,
                                "nodeKey" to currentNode.value.pair.first,
                                "nodeColor" to currentNode.color.toString(),
                                "nodeX" to currentNode.x,
                                "nodeY" to currentNode.y
                            )
                        )
                    }
                }
                if (!stack.isEmpty()) {
                    if (set.contains(stack.peek())) {
                        set.remove(stack.peek())
                        val parentNode = stack.pop()
                        parentNode.value as Container<*, *>
                        session.executeWrite { tx ->
                            tx.run(
                                "MERGE(parent:Node {value: \$parentNodeValue, key: \$parentNodeKey, " +
                                        "color: \$parentNodeColor, x: \$parentNodeX, y: \$parentNodeY}) " +
                                        "MERGE(son:Node {value: \$nodeValue, key: \$nodeKey, color: \$nodeColor, x: \$nodeX, y: \$nodeY}) " +
                                        "MERGE (parent)-[:RIGHT_SON]->(son)",
                                mutableMapOf(
                                    "parentNodeValue" to parentNode.value.pair.second,
                                    "parentNodeKey" to parentNode.value.pair.first,
                                    "parentNodeColor" to parentNode.color.toString(),
                                    "parentNodeX" to parentNode.x,
                                    "parentNodeY" to parentNode.y,
                                    "nodeValue" to currentNode.value.pair.second,
                                    "nodeKey" to currentNode.value.pair.first,
                                    "nodeColor" to currentNode.color.toString(),
                                    "nodeX" to currentNode.x,
                                    "nodeY" to currentNode.y
                                )
                            )
                        }
                    } else {
                        val parentNode = stack.peek()
                        parentNode.value as Container<*, *>
                        session.executeWrite { tx ->
                            tx.run(
                                "MERGE(parent:Node {value: \$parentNodeValue, key: \$parentNodeKey, " +
                                        "color: \$parentNodeColor, x: \$parentNodeX, y: \$parentNodeY}) " +
                                        "MERGE(son:Node {value: \$nodeValue, key: \$nodeKey, color: \$nodeColor, x: \$nodeX, y: \$nodeY}) " +
                                        "MERGE (parent)-[:LEFT_SON]->(son)",
                                mutableMapOf(
                                    "parentNodeValue" to parentNode.value.pair.second,
                                    "parentNodeKey" to parentNode.value.pair.first,
                                    "parentNodeColor" to parentNode.color.toString(),
                                    "parentNodeX" to parentNode.x,
                                    "parentNodeY" to parentNode.y,
                                    "nodeValue" to currentNode.value.pair.second,
                                    "nodeKey" to currentNode.value.pair.first,
                                    "nodeColor" to currentNode.color.toString(),
                                    "nodeX" to currentNode.x,
                                    "nodeY" to currentNode.y
                                )
                            )
                        }
                    }
                }
                stack.push(currentNode)
            } while (preOrder[preOrderIndex++] != inOrder[inOrderIndex] && preOrderIndex < preOrder.size)

            var currentNode: DBNodeRB<Pack>? = null

            while (!stack.isEmpty() && inOrderIndex < inOrder.size && stack.peek().value == inOrder[inOrderIndex].value) {
                currentNode = stack.pop()
                ++inOrderIndex
            }

            if (currentNode != null) {
                set.add(currentNode)
                stack.push(currentNode)
            }

        }

        session.close()
    }

    fun exportRBtree(): Pair<List<DBNodeRB<Container<String, Comparable<String>>>>, List<DBNodeRB<Container<String, Comparable<String>>>>> {

        val session = driver?.session() ?: throw IOException()
        var preOrder: List<DBNodeRB<Container<String, Comparable<String>>>> = listOf()
        var inOrder: List<DBNodeRB<Container<String, Comparable<String>>>> = listOf()

        session.executeRead { tx ->
            preOrder = tx.run(
                "MATCH (node: Node) " +
                        "RETURN node.value, node.key, node.color, node.x, node.y "
            ).list()
                .map {
                    DBNodeRB(
                        value = Container(Pair(it.values().get(0).toString(), it.values().get(1).toString())),
                        color = if (it.values().get(2).toString() == """RED""") Markers.RED else Markers.BLACK,
                        x = it.values().get(3).toString().toDouble(),
                        y = it.values().get(4).toString().toDouble()
                    )
                }

            inOrder = tx.run(
                "MATCH (node: Node) " +
                        "RETURN node.value, node.key, node.color, node.x, node.y " +
                        "ORDER BY node.key"
            ).list()
                .map {
                    DBNodeRB(
                        value = Container(Pair(it.values().get(0).toString(), it.values().get(1).toString())),
                        color = if (it.values().get(2).toString() == """RED""") Markers.RED else Markers.BLACK,
                        x = it.values().get(3).toString().toDouble(),
                        y = it.values().get(4).toString().toDouble()
                    )
                }
        }

        session.close()

        return Pair(preOrder, inOrder)

    }

    override fun close() {
        driver?.close()
    }
}

// neo4j-admin, backup, restore
/*
у меня есть вершины и ребра между ними, надо уметь сохранять дерево в базе, но как?
вопрос: когда заносить изменения в бд и как это реализовывать?
надо еще подумать над оптимизацией, те чтобы не пересобирать дерево в бд каждый раз, когда мы добавили за сессию всего один узел

root = tx.run("MATCH (parent: Node)-->(son: Node) " +
                    "WHERE NOT ()-->(parent) " +
                    "RETURN parent.value, parent.key, parent.color, parent.x, parent.y").list().map {it.values()}.get(0)
 */
