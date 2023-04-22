package databaseManage

import databaseSave.neo4j.DrawableRBVertex
import databaseSave.neo4j.Neo4jRepository
import treelib.rbTree.RBStruct
import treelib.singleObjects.Container

class RBTreeManager {

    /*** using neo4j ***/

    private val neo4jDB = Neo4jRepository()

    init {
        neo4jDB.open("bolt://localhost:7687", "neo4j", "password")
    }

    fun initTree(treeName: String): RBStruct<Container<Int, String>> {
        /***    orders.first = preOrder, orders.second = inOrder   ***/
        val orders: Pair<List<DrawableRBVertex<Container<Int, String>>>, List<DrawableRBVertex<Container<Int, String>>>> =
            neo4jDB.exportRBtree(treeName)

        val RBtree = RBStruct<Container<Int, String>>()
        RBtree.restoreStruct(orders.first, orders.second)
        neo4jDB.close()

        return RBtree
    }

    fun <Pack : Comparable<Pack>> saveTree(preOrder: Array<DrawableRBVertex<Pack>>, inOrder: Array<DrawableRBVertex<Pack>>, treeName: String) {

        neo4jDB.saveChanges(preOrder, inOrder, treeName)
        neo4jDB.close()
    }

    fun deleteTree(treeName: String) {

        neo4jDB.removeTree(treeName)
        neo4jDB.close()

    }

    fun cleanDB() {

        neo4jDB.clean()
        neo4jDB.close()
    }

}