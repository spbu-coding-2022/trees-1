package databaseSave.neo4j


import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.TransactionContext
import org.neo4j.driver.exceptions.SessionExpiredException
import treelib.commonObjects.Container
import treelib.rbTree.Markers
import java.io.Closeable
import java.io.IOException
import java.util.LinkedList
import kotlin.collections.HashSet


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

    fun <Pack : Comparable<Pack>> saveChanges(
        preOrder: Array<DrawableRBVertex<Pack>>,
        inOrder: Array<DrawableRBVertex<Pack>>,
        treeName: String
    ) {

        val session = driver?.session() ?: throw IOException()

        var inOrderIndex = 0
        var preOrderIndex = 0
        val set = HashSet<DrawableRBVertex<Pack>>()
        val stack = LinkedList<DrawableRBVertex<Pack>>()
        var id = 0

        while (preOrderIndex in preOrder.indices) {
            do {
                val currentNode = preOrder[preOrderIndex]
                if (preOrderIndex == 0) {
                    session.executeWrite { tx ->
                        //cleanDB(tx)
                        createRoot(tx, currentNode, id, treeName)
                    }
                    ++id
                }
                if (!stack.isEmpty()) {
                    if (set.contains(stack.peek())) {
                        set.remove(stack.peek())
                        val parentNode = stack.pop()
                        session.executeWrite { tx ->
                            createRightSon(tx, parentNode, currentNode, id, treeName)
                        }
                        ++id
                    } else {
                        val parentNode = stack.peek()
                        parentNode.value as Container<*, *>
                        session.executeWrite { tx ->
                            createLeftSon(tx, parentNode, currentNode, id, treeName)
                        }
                        ++id
                    }
                }
                stack.push(currentNode)
            } while (preOrder[preOrderIndex++].value != inOrder[inOrderIndex].value && preOrderIndex < preOrder.size)

            var currentNode: DrawableRBVertex<Pack>? = null

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

    fun exportRBtree(treeName: String): Pair<List<DrawableRBVertex<Container<Int, String>>>, List<DrawableRBVertex<Container<Int, String>>>> {

        val session = driver?.session() ?: throw IOException()
        var preOrder: List<DrawableRBVertex<Container<Int, String>>> = listOf()
        var inOrder: List<DrawableRBVertex<Container<Int, String>>> = listOf()

        session.executeRead { tx ->
            preOrder = tx.run(
                "MATCH (node: Node {treeName: \$name}) " +
                        "RETURN node.value, node.key, node.color, node.x, node.y " +
                        "ORDER BY node.id",
                mutableMapOf("name" to treeName) as Map<String, Any>?
            ).list()
                .map {
                    DrawableRBVertex(
                        value = Container(Pair(it.values()[1].toString().toInt(), it.values()[0].toString().replace("\"", ""))),
                        color = if (it.values()[2].toString().replace("\"", "") == "RED") Markers.RED else Markers.BLACK,
                        x = it.values()[3].toString().toDouble(),
                        y = it.values()[4].toString().toDouble()
                    )
                }

            inOrder = tx.run(
                "MATCH (node: Node {treeName: \$name}) " +
                        "RETURN node.value, node.key, node.color, node.x, node.y " +
                        "ORDER BY node.key",
                mutableMapOf("name" to treeName) as Map<String, Any>?
            ).list()
                .map {
                    DrawableRBVertex(
                        value = Container(Pair(it.values()[1].toString().toInt(), it.values()[0].toString())),
                        color = if (it.values()[2].toString() == """RED""") Markers.RED else Markers.BLACK,
                        x = it.values()[3].toString().toDouble(),
                        y = it.values()[4].toString().toDouble()
                    )
                }
        }

        session.close()

        return Pair(preOrder, inOrder)

    }

    fun removeTree(treeName: String) {

        val session = driver?.session() ?: throw IOException()

        session.executeWrite { tx ->
            tx.run(
                "MATCH (n: Node {treeName: \$name}) DETACH DELETE n",
                mutableMapOf("name" to treeName) as Map<String, Any>?
            )
        }

    }

    fun findNamesTrees(): List<String> {
        val session = driver?.session() ?: throw IOException()
        var treesNames: List<String> = listOf()
        session.executeRead { tx ->
            treesNames = tx.run("MATCH (n: Node) WHERE NOT(:Node)-->(n) RETURN n.treeName")
                .list().map { it.values()[0].toString().replace("\"", "") }.filter { it != "null" }
        }

        return treesNames
    }

    fun clean() {
        val session = driver?.session() ?: throw IOException()

        session.executeWrite { tx ->
            tx.run("MATCH (n: Node) DETACH DELETE n")
        }
    }

    private fun <Pack : Comparable<Pack>> createRoot(
        tx: TransactionContext,
        rootNode: DrawableRBVertex<Pack>,
        id: Int,
        treeName: String
    ) {
        rootNode.value as Container<*, *>
        tx.run(
            "MERGE(:Node {value: \$nodeValue, key: \$nodeKey, color: \$nodeColor, x: \$nodeX, y: \$nodeY, id: \$nodeID, treeName: \$name})",
            mutableMapOf(
                "nodeValue" to rootNode.value.pair.second,
                "nodeKey" to rootNode.value.pair.first,
                "nodeColor" to rootNode.color.toString(),
                "nodeX" to rootNode.x,
                "nodeY" to rootNode.y,
                "nodeID" to id,
                "name" to treeName
            )
        )
    }

    private fun <Pack : Comparable<Pack>> createRightSon(
        tx: TransactionContext, parentNode: DrawableRBVertex<Pack>,
        currentNode: DrawableRBVertex<Pack>, id: Int, treeName: String
    ) {
        parentNode.value as Container<*, *>
        currentNode.value as Container<*, *>
        tx.run(
            "MERGE(parent:Node {value: \$parentNodeValue, key: \$parentNodeKey, " +
                    "color: \$parentNodeColor, x: \$parentNodeX, y: \$parentNodeY, treeName: \$name}) " +
                    "MERGE(son:Node {value: \$nodeValue, key: \$nodeKey, color: \$nodeColor, x: \$nodeX, y: \$nodeY, id: \$nodeID, treeName: \$name}) " +
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
                "name" to treeName
            )
        )
    }

    private fun <Pack : Comparable<Pack>> createLeftSon(
        tx: TransactionContext, parentNode: DrawableRBVertex<Pack>,
        currentNode: DrawableRBVertex<Pack>, id: Int, treeName: String
    ) {
        parentNode.value as Container<*, *>
        currentNode.value as Container<*, *>
        tx.run(
            "MERGE(parent:Node {value: \$parentNodeValue, key: \$parentNodeKey, " +
                    "color: \$parentNodeColor, x: \$parentNodeX, y: \$parentNodeY, treeName: \$name}) " +
                    "MERGE(son:Node {value: \$nodeValue, key: \$nodeKey, color: \$nodeColor, x: \$nodeX, y: \$nodeY, id: \$nodeID, treeName: \$name}) " +
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
                "name" to treeName
            )
        )
    }

    fun findTree(treeName: String): Boolean {
        val session = driver?.session() ?: throw IOException()
        var name = ""

        session.executeRead { tx ->
            name = tx.run(
                "MATCH (n: Node {treeName: \$treeName}) WHERE NOT (:Node)-->(n) RETURN n.treeName",
                mutableMapOf(
                    "treeName" to treeName
                ) as Map<String, Any>?
            ).list().singleOrNull()?.values()?.get(0)?.toString()?.replace("\"", "") ?: ""

        }
        return name == treeName

    }

    override fun close() {
        driver?.close()
    }
}
