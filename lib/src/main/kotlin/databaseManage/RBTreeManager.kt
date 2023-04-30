package databaseManage

import databaseSave.neo4j.DrawableRBVertex
import databaseSave.neo4j.Neo4jRepository
import treelib.commonObjects.Container
import treelib.rbTree.RBNode
import treelib.rbTree.RBStateContainer
import treelib.rbTree.RBStruct
import treelib.rbTree.RBVertex
import java.io.File

class RBTreeManager : TreeManager<
        Container<Int, String>,
        DrawableRBVertex<Container<Int, String>>,
        RBNode<Container<Int, String>>,
        RBStateContainer<Container<Int, String>>,
        RBVertex<Container<Int, String>>,
        RBStruct<Container<Int, String>>,
        > {

    private val neo4jDB = Neo4jRepository()
    
    init {
        neo4jDB.open("bolt://localhost:7687", "neo4j", "password")
    }

    override fun initTree(name: String, tree: RBStruct<Container<Int, String>>): List<DrawableRBVertex<Container<Int, String>>> {
        /***    orders.first = preOrder, orders.second = inOrder   ***/
        if (this.isTreeExist(name)) {
            val orders: Pair<List<DrawableRBVertex<Container<Int, String>>>, List<DrawableRBVertex<Container<Int, String>>>> =
                neo4jDB.exportRBtree(name)
            tree.restoreStruct(orders.first, orders.second)
            neo4jDB.close()
            return orders.first
        }

        return listOf()
    }

    override fun saveTreeToDB(
        name: String,
        preOrder: List<DrawableRBVertex<Container<Int, String>>>,
        inOrder: List<DrawableRBVertex<Container<Int, String>>>
    ) {
        neo4jDB.saveChanges(preOrder.toTypedArray(), inOrder.toTypedArray(), name)
        neo4jDB.close()
    }

    override fun saveTreeToDB(name: String, tree: RBStruct<Container<Int, String>>) {
        TODO()
    }


    override fun deleteTreeFromDB(name: String) {

        neo4jDB.removeTree(name)
        neo4jDB.close()

    }

    override fun getSavedTreesNames(): List<String> {
        val treesNames = neo4jDB.findNamesTrees()
        val dirPath = System.getProperty("user.dir") + "/saved-trees/RB-trees"
        File(dirPath).mkdirs()
        if (treesNames != null) {
            for (name in treesNames) {
                File(dirPath, name).run {
                    createNewFile()
                }
            }
        }
        return treesNames?.subList(0, treesNames.size) ?: listOf()
    }
/*

    override fun delete(item: Container<Int, String>) = rbTree.insert(item)

    override fun insert(item: Container<Int, String>) {
        if (rbTree.find(item) != null)
            rbTree.delete(item)
    }
*/

    override fun getVertexesForDrawFromDB(name: String): List<DrawableRBVertex<Container<Int, String>>> {
        return neo4jDB.exportRBtree(name).first.map { DrawableRBVertex(it.value, it.color) }
    }

    override fun getVertexesForDrawFromTree(tree: RBStruct<Container<Int, String>>): List<DrawableRBVertex<Container<Int, String>>> {
        return tree.preOrder().map { DrawableRBVertex(it.value, it.color) }
    }

    private fun isTreeExist(treeName: String): Boolean {
        return neo4jDB.findTree(treeName)
    }

    fun cleanDB() {
        neo4jDB.clean()
        neo4jDB.close()
    }
}
