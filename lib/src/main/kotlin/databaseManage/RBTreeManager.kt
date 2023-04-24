package databaseManage

import databaseSave.neo4j.DrawableRBVertex
import databaseSave.neo4j.Neo4jRepository
import treelib.commonObjects.Container
import java.io.File

class RBTreeManager : TreeManager<Container<Int, String>, DrawableRBVertex<Container<Int, String>>> {

    /*** using neo4j ***/

    private val neo4jDB = Neo4jRepository()

    init {
        neo4jDB.open("bolt://localhost:7687", "neo4j", "password")
    }

    // добавить проверку на существование дерева
    override fun getTree(treeName: String): Pair<List<DrawableRBVertex<Container<Int, String>>>, List<DrawableRBVertex<Container<Int, String>>>> {
        /***    orders.first = preOrder, orders.second = inOrder   ***/
        val orders: Pair<List<DrawableRBVertex<Container<Int, String>>>, List<DrawableRBVertex<Container<Int, String>>>> =
            neo4jDB.exportRBtree(treeName)
        neo4jDB.close()

        return orders
    }

    override fun saveTree(
        preOrder: Array<DrawableRBVertex<Container<Int, String>>>,
        inOrder: Array<DrawableRBVertex<Container<Int, String>>>,
        treeName: String
    ) {

        neo4jDB.saveChanges(preOrder, inOrder, treeName)
        neo4jDB.close()
    }

    override fun deleteTree(treeName: String) {

        neo4jDB.removeTree(treeName)
        neo4jDB.close()

    }

    override fun getSavedTreesNames(): List<String> {
        val treesNames = neo4jDB.findNamesTrees()
        val dirPath = System.getProperty("user.dir") + "/neo4jDB/neo4jFormatFiles"
        File(dirPath).mkdirs()
        if (treesNames != null) {
            for (name in treesNames) {
                File(dirPath, name).run {
                    createNewFile()
                }
            }
        }
        return treesNames?.subList(0, 3) ?: listOf()
    }

    override fun isTreeExist(): Boolean {
        TODO()
    }

    fun isTreeExist(treeName: String): Boolean {
        return neo4jDB.findTree(treeName)
    }

    fun cleanDB() {

        neo4jDB.clean()
        neo4jDB.close()
    }

}
