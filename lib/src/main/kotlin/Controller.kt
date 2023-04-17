import treelib.DBNodeRB
import treelib.rbTree.RBStruct
import treelib.rbTree.RBTree
import treelib.singleObjects.Container

class Controller {

    fun initTree() {
        val neo4jDB = Neo4jRepository()
        neo4jDB.open("bolt://localhost:7687", "neo4j", "test-neo4j")
        /***    orders.first = preOrder, orders.second = inOrder   ***/
        val orders: Pair<List<DBNodeRB<Container<String, Comparable<String>>>>, List<DBNodeRB<Container<String, Comparable<String>>>>> =
            neo4jDB.exportRBtree()

        val RBtree = RBStruct<Container<String, Comparable<String>>>()
        RBtree.restoreTreeFromDatabase(orders.first, orders.second)
    }

    fun saveTree() {
        val tree = RBTree<Int, Int>()
        tree.putItem(Pair(25, 1))
        tree.putItem(Pair(15, 1))
        tree.putItem(Pair(50, 1))
        tree.putItem(Pair(10, 1))
        tree.putItem(Pair(22, 1))
        tree.putItem(Pair(35, 1))
        tree.putItem(Pair(70, 1))
        tree.putItem(Pair(4, 1))
        tree.putItem(Pair(12, 1))
        tree.putItem(Pair(18, 1))
        tree.putItem(Pair(24, 1))
        tree.putItem(Pair(31, 1))
        tree.putItem(Pair(44, 1))
        tree.putItem(Pair(66, 1))
        tree.putItem(Pair(90, 1))

        val neo4jDB = Neo4jRepository()
        neo4jDB.open("bolt://localhost:7687", "neo4j", "test-neo4j")

        val preOrder = tree.preOrder().map { DBNodeRB(it.value, it.color) }
        val inOrder = tree.inOrder().map { DBNodeRB(it.value, it.color) }

        neo4jDB.saveChanges(preOrder.toTypedArray(), inOrder.toTypedArray())

        neo4jDB.close()


    }

}