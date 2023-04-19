package dbSave.neo4j


import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.TransactionContext
import org.neo4j.driver.exceptions.SessionExpiredException
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

    fun <Pack : Comparable<Pack>> saveChanges(preOrder: Array<DrawRBVertex<Pack>>, inOrder: Array<DrawRBVertex<Pack>>) {

        /*** сюда по ощущениям лучше всего добавлять именно то поддерево исходного дерева, которое было изменено ***/
        val session = driver?.session() ?: throw IOException()

        var inOrderIndex = 0
        var preOrderIndex = 0
        val set = HashSet<DrawRBVertex<Pack>>()
        val stack = LinkedList<DrawRBVertex<Pack>>()
        var id = 0

        while (preOrderIndex in preOrder.indices) {
            do {
                val currentNode = preOrder[preOrderIndex]
                //currentNode.value as Container<*, *>
                if (preOrderIndex == 0) {
                    session.executeWrite { tx ->
                        cleanDB(tx)
                        createRoot(tx, currentNode, id)
                    }
                    ++id
                }
                if (!stack.isEmpty()) {
                    if (set.contains(stack.peek())) {
                        set.remove(stack.peek())
                        val parentNode = stack.pop()
                        //parentNode.value as Container<*, *>
                        session.executeWrite { tx ->
                            createRightSon(tx, parentNode, currentNode, id)
                        }
                        ++id
                    } else {
                        val parentNode = stack.peek()
                        parentNode.value as Container<*, *>
                        session.executeWrite { tx ->
                            createLeftSon(tx, parentNode, currentNode, id)
                        }
                        ++id
                    }
                }
                stack.push(currentNode)
            } while (preOrder[preOrderIndex++].value != inOrder[inOrderIndex].value && preOrderIndex < preOrder.size)

            var currentNode: DrawRBVertex<Pack>? = null

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

    fun exportRBtree(): Pair<List<DrawRBVertex<Container<String, Comparable<String>>>>, List<DrawRBVertex<Container<String, Comparable<String>>>>> {

        /*** Плохо, что передаем Container с четко привязанными типами (K, V), потому что может быть так, что вместо контейнера будет просто инт  ***/

        val session = driver?.session() ?: throw IOException()
        var preOrder: List<DrawRBVertex<Container<String, Comparable<String>>>> = listOf()
        var inOrder: List<DrawRBVertex<Container<String, Comparable<String>>>> = listOf()

        session.executeRead { tx ->
            preOrder = tx.run(
                "MATCH (node: Node) " +
                        "RETURN node.value, node.key, node.color, node.x, node.y " +
                        "ORDER BY node.id"
            ).list()
                .map {
                    DrawRBVertex(
                        value = Container(Pair(it.values()[1].toString(), it.values()[0].toString())),
                        color = if (it.values()[2].toString() == """RED""") Markers.RED else Markers.BLACK,
                        x = it.values()[3].toString().toDouble(),
                        y = it.values()[4].toString().toDouble()
                    )
                }

            inOrder = tx.run(
                "MATCH (node: Node) " +
                        "RETURN node.value, node.key, node.color, node.x, node.y " +
                        "ORDER BY node.key"
            ).list()
                .map {
                    DrawRBVertex(
                        value = Container(Pair(it.values()[1].toString(), it.values()[0].toString())),
                        color = if (it.values()[2].toString() == """RED""") Markers.RED else Markers.BLACK,
                        x = it.values()[3].toString().toDouble(),
                        y = it.values()[4].toString().toDouble()
                    )
                }
        }

        session.close()

        return Pair(preOrder, inOrder)

    }

    private fun cleanDB(tx: TransactionContext) {
        tx.run("MATCH (n: Node) DETACH DELETE n")
    }

    private fun <Pack : Comparable<Pack>> createRoot(tx: TransactionContext, rootNode: DrawRBVertex<Pack>, id: Int) {
        rootNode.value as Container<*, *>
        tx.run(
            "MERGE(:Node {value: \$nodeValue, key: \$nodeKey, color: \$nodeColor, x: \$nodeX, y: \$nodeY, id: \$nodeID})",
            mutableMapOf(
                "nodeValue" to rootNode.value.pair.second,
                "nodeKey" to rootNode.value.pair.first,
                "nodeColor" to rootNode.color.toString(),
                "nodeX" to rootNode.x,
                "nodeY" to rootNode.y,
                "nodeID" to id
            )
        )
    }

    private fun <Pack : Comparable<Pack>> createRightSon(
        tx: TransactionContext, parentNode: DrawRBVertex<Pack>,
        currentNode: DrawRBVertex<Pack>, id: Int
    ) {
        parentNode.value as Container<*, *>
        currentNode.value as Container<*, *>
        tx.run(
            "MERGE(parent:Node {value: \$parentNodeValue, key: \$parentNodeKey, " +
                    "color: \$parentNodeColor, x: \$parentNodeX, y: \$parentNodeY}) " +
                    "MERGE(son:Node {value: \$nodeValue, key: \$nodeKey, color: \$nodeColor, x: \$nodeX, y: \$nodeY, id: \$nodeID}) " +
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
                "nodeY" to currentNode.y,
                "nodeID" to id,
            )
        )
    }

    private fun <Pack : Comparable<Pack>> createLeftSon(
        tx: TransactionContext, parentNode: DrawRBVertex<Pack>,
        currentNode: DrawRBVertex<Pack>, id: Int
    ) {
        parentNode.value as Container<*, *>
        currentNode.value as Container<*, *>
        tx.run(
            "MERGE(parent:Node {value: \$parentNodeValue, key: \$parentNodeKey, " +
                    "color: \$parentNodeColor, x: \$parentNodeX, y: \$parentNodeY}) " +
                    "MERGE(son:Node {value: \$nodeValue, key: \$nodeKey, color: \$nodeColor, x: \$nodeX, y: \$nodeY, id: \$nodeID}) " +
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
                "nodeY" to currentNode.y,
                "nodeID" to id,
            )
        )
    }

    override fun close() {
        driver?.close()
    }
}
