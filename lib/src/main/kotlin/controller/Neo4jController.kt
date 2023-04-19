package controller

import dbSave.neo4j.DrawRBVertex
import dbSave.neo4j.Neo4jRepository
import treelib.rbTree.RBStruct
import treelib.singleObjects.Container

class Neo4jController {

    fun initTree() {
        val neo4jDB = Neo4jRepository()
        neo4jDB.open("bolt://localhost:7687", "neo4j", "test-neo4j")

        /***    orders.first = preOrder, orders.second = inOrder   ***/
        val orders: Pair<List<DrawRBVertex<Container<String, Comparable<String>>>>, List<DrawRBVertex<Container<String, Comparable<String>>>>> =
            neo4jDB.exportRBtree()

        val RBtree = RBStruct<Container<String, Comparable<String>>>()
        RBtree.restoreStruct(orders.first, orders.second)
        neo4jDB.close()
    }

    fun <Pack : Comparable<Pack>> saveTree(tree: RBStruct<Pack>) {
        val neo4jDB = Neo4jRepository()
        neo4jDB.open("bolt://localhost:7687", "neo4j", "test-neo4j")

        // вот тут плохо, потому что тут надо получать не base nodes, а для рисовалки

        val preOrder = tree.preOrder().map { DrawRBVertex(it.value, it.color) }
        val inOrder = tree.inOrder().map { DrawRBVertex(it.value, it.color) }

        neo4jDB.saveChanges(preOrder.toTypedArray(), inOrder.toTypedArray())
        neo4jDB.close()

    }

}