
import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.exceptions.SessionExpiredException
import treelib.DBNodeRB
import treelib.singleObjects.Markers
import java.io.Closeable
import java.io.IOException
import java.util.*

class Neo4jRepository: Closeable {

    private var driver: Driver? = null

    fun open(uri: String, username: String, password: String) {
        try {
            driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password))
        } catch(ex: IllegalArgumentException) {
             throw IOException()
        } catch(ex: SessionExpiredException) {
            throw IOException()
        }

    }

    // я наверное смогу получить рут, используя фильтр что-то вроде: на данный узел не указывает ни один другой узел с отношением [left_child] / [right_child]

    fun <K: Comparable<K>, V> saveChanges(preOrder: Array<DBNodeRB<K, V>>, inOrder: Array<DBNodeRB<K, V>>) {

        /*** сюда по ощущениям лучше всего добавлять именно то поддерево исходного дерева, которое было изменено ***/
        val session = driver?.session() ?: throw IOException()

        var inOrderIndex = 0
        var preOrderIndex = 0
        val set = HashSet<DBNodeRB<K, V>>()
        val stack = LinkedList<DBNodeRB<K, V>>()

        while (preOrderIndex in preOrder.indices) {
            do {
                val currentNode = preOrder[preOrderIndex]
                if (preOrderIndex == 0) {
                    session.executeWrite { tx ->
                        tx.run(
                            "MERGE(:Node {value: \$nodeValue, key: \$nodeKey, color: \$nodeColor, x: \$nodeX, y: \$nodeY})",
                            mutableMapOf(
                                "nodeValue" to currentNode.value,
                                "nodeKey" to currentNode.key,
                                "nodeColor" to currentNode.color.toString(),
                                "nodeX" to currentNode.x,
                                "nodeY" to currentNode.y
                            )
                        )
                    }
                }
                if (!stack.isEmpty()) {
                    if ( set.contains( stack.peek() ) ) {
                        set.remove(stack.peek())
                        val parentNode = stack.pop()
                        session.executeWrite {tx ->
                            tx.run("MERGE(parent:Node {value: \$parentNodeValue, key: \$parentNodeKey, " +
                                    "color: \$parentNodeColor, x: \$parentNodeX, y: \$parentNodeY}) " +
                                    "MERGE(son:Node {value: \$nodeValue, key: \$nodeKey, color: \$nodeColor, x: \$nodeX, y: \$nodeY}) " +
                                    "MERGE (parent)-[:RIGHT_SON]->(son)",
                                mutableMapOf(
                                    "parentNodeValue" to parentNode.value,
                                    "parentNodeKey" to parentNode.key,
                                    "parentNodeColor" to parentNode.color.toString(),
                                    "parentNodeX" to parentNode.x,
                                    "parentNodeY" to parentNode.y,
                                    "nodeValue" to currentNode.value,
                                    "nodeKey" to currentNode.key,
                                    "nodeColor" to currentNode.color.toString(),
                                    "nodeX" to currentNode.x,
                                    "nodeY" to currentNode.y
                                )
                            )
                        }
                    }
                    else {
                        val parentNode = stack.peek()
                        session.executeWrite {tx ->
                            tx.run("MERGE(parent:Node {value: \$parentNodeValue, key: \$parentNodeKey, " +
                                    "color: \$parentNodeColor, x: \$parentNodeX, y: \$parentNodeY}) " +
                                    "MERGE(son:Node {value: \$nodeValue, key: \$nodeKey, color: \$nodeColor, x: \$nodeX, y: \$nodeY}) " +
                                    "MERGE (parent)-[:LEFT_SON]->(son)",
                                mutableMapOf(
                                    "parentNodeValue" to parentNode.value,
                                    "parentNodeKey" to parentNode.key,
                                    "parentNodeColor" to parentNode.color.toString(),
                                    "parentNodeX" to parentNode.x,
                                    "parentNodeY" to parentNode.y,
                                    "nodeValue" to currentNode.value,
                                    "nodeKey" to currentNode.key,
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

            var currentNode: DBNodeRB<K, V>? = null

            while(!stack.isEmpty() && inOrderIndex < inOrder.size && stack.peek().key == inOrder[inOrderIndex].key) {
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

    // Это скорее всего будем запускать при открытии приложения, нужно будет сразу восстановить дерево

    fun exportRBtree() {

        val session = driver?.session() ?: throw IOException()
        var preOrder: List<DBNodeRB<String, Comparable<String>>> = listOf()
        var inOrder: List<DBNodeRB<String, Comparable<String>>> = listOf()

        session.executeRead {tx ->
            preOrder = tx.run("MATCH (node: Node) " +
                    "RETURN node.value, node.key, node.color, node.x, node.y ").list()
                .map{ DBNodeRB(
                    value = it.values().get(0).toString(),
                    key = it.values().get(1).toString(),
                    color = if (it.values().get(2).toString() == """RED""") Markers.RED else Markers.BLACK,
                    x = it.values().get(3).toString().toDouble(),
                    y = it.values().get(4).toString().toDouble())
                }

            inOrder = tx.run("MATCH (node: Node) " +
                    "RETURN node.value, node.key, node.color, node.x, node.y " +
                    "ORDER BY node.key").list()
                .map{ DBNodeRB(
                    value = it.values().get(0).toString(),
                    key = it.values().get(1).toString(),
                    color = if (it.values().get(2).toString() == """RED""") Markers.RED else Markers.BLACK,
                    x = it.values().get(3).toString().toDouble(),
                    y = it.values().get(4).toString().toDouble())
                }
        }
        
        session.close()

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
