package databaseManage

import databaseSave.neo4j.DrawableRBVertex
import databaseSave.neo4j.Neo4jRepository
import treelib.commonObjects.Container
import treelib.rbTree.RBStruct
import java.io.File

class RBTreeManager : TreeManager<Container<Int, String>, DrawableRBVertex<Container<Int, String>>> {

    override var currentTreeName = RB_DEFAULT_NAME
        private set

    private val neo4jDB = Neo4jRepository()
    private var rbTree = RBStruct<Container<Int, String>>()

    init {
        neo4jDB.open("bolt://localhost:7687", "neo4j", "password")
    }

    override fun initTree(treeName: String): List<DrawableRBVertex<Container<Int, String>>> {
        /***    orders.first = preOrder, orders.second = inOrder   ***/
        rbTree = RBStruct()
        currentTreeName = treeName

        if (this.isTreeExist(treeName)) {
            val orders: Pair<List<DrawableRBVertex<Container<Int, String>>>, List<DrawableRBVertex<Container<Int, String>>>> =
                neo4jDB.exportRBtree(treeName)
            rbTree.restoreStruct(orders.first, orders.second)
            neo4jDB.close()
            return orders.first
        }

        return listOf()
    }

    override fun saveTree(
        preOrder: List<DrawableRBVertex<Container<Int, String>>>,
        inOrder: List<DrawableRBVertex<Container<Int, String>>>
    ) {

        neo4jDB.saveChanges(preOrder.toTypedArray(), inOrder.toTypedArray(), currentTreeName)
        neo4jDB.close()
    }

    override fun deleteTree() {

        neo4jDB.removeTree(currentTreeName)
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

    override fun delete(item: Container<Int, String>) = rbTree.insert(item)

    override fun insert(item: Container<Int, String>) {
        if (rbTree.find(item) != null)
            rbTree.delete(item)

    }

    override fun getVertexesForDrawFromDB(): List<DrawableRBVertex<Container<Int, String>>> {
        return neo4jDB.exportRBtree(currentTreeName).first.map { DrawableRBVertex(it.value, it.color) }
    }

    override fun getVertexesForDrawFromTree(): List<DrawableRBVertex<Container<Int, String>>> {
        return rbTree.preOrder().map { DrawableRBVertex(it.value, it.color) }
    }



    private fun isTreeExist(treeName: String): Boolean {
        return neo4jDB.findTree(treeName)
    }

    fun cleanDB() {

        neo4jDB.clean()
        neo4jDB.close()
    }

    companion object {
        private const val RB_DEFAULT_NAME = "NewTreeRB"
    }

}
