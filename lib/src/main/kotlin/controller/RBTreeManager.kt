package controller

import dbSave.neo4j.DrawRBVertex
import dbSave.neo4j.Neo4jRepository
import treelib.rbTree.RBStruct
import treelib.singleObjects.Container

class RBTreeManager {

    /*** using neo4j ***/

    private val neo4jDB = Neo4jRepository()

    init {
        neo4jDB.open("bolt://localhost:7687", "neo4j", "password")
    }

    fun initTree(treeName: String): RBStruct<Container<String, Comparable<String>>> {
        /***    orders.first = preOrder, orders.second = inOrder   ***/
        val orders: Pair<List<DrawRBVertex<Container<String, Comparable<String>>>>, List<DrawRBVertex<Container<String, Comparable<String>>>>> =
            neo4jDB.exportRBtree(treeName)

        val RBtree = RBStruct<Container<String, Comparable<String>>>()
        RBtree.restoreStruct(orders.first, orders.second)
        neo4jDB.close()

        return RBtree
    }

    fun <Pack : Comparable<Pack>> saveTree(tree: RBStruct<Pack>, treeName: String) {

        val preOrder = tree.preOrder().map { DrawRBVertex(it.value, it.color) }
        val inOrder = tree.inOrder().map { DrawRBVertex(it.value, it.color) }

        neo4jDB.saveChanges(preOrder.toTypedArray(), inOrder.toTypedArray(), treeName)
        neo4jDB.close()
    }

    fun deleteTree(treeName: String) {

        neo4jDB.removeTree(treeName)

    }

    fun cleanDB() {
        neo4jDB.clean()
    }

}