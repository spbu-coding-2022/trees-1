package databaseManage

import databaseSave.sqlite.DrawableAVLVertex
import databaseSave.sqlite.SQLiteRepositoryExposed
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import treelib.avlTree.AVLNode
import treelib.avlTree.AVLStateContainer
import treelib.avlTree.AVLStruct
import treelib.avlTree.AVLVertex
import treelib.commonObjects.Container


class AVLTreeManager : TreeManager<
        Container<Int, String>,
        DrawableAVLVertex<Container<Int, String>>,
        AVLNode<Container<Int, String>>,
        AVLStateContainer<Container<Int, String>>,
        AVLVertex<Container<Int, String>>,
        AVLStruct<Container<Int, String>>,
        > {
    private val db = SQLiteRepositoryExposed()

    init {
        db.initDataBase(AVL_DB_DEFAULT_NAME)
    }

    private fun drawVertexToVertex(drawVertex: MutableList<DrawableAVLVertex<Container<Int, String>>>): MutableList<AVLVertex<Container<Int, String>>> {
        val ans = mutableListOf<AVLVertex<Container<Int, String>>>()
        for (el in drawVertex) ans.add(AVLVertex(value = el.value, height = el.height.toUInt()))
        return ans
    }

    private fun vertexToDrawVertex(drawVertex: List<AVLVertex<Container<Int, String>>>): MutableList<DrawableAVLVertex<Container<Int, String>>> {
        val ans = mutableListOf<DrawableAVLVertex<Container<Int, String>>>()
        for (el in drawVertex) ans.add(
            DrawableAVLVertex(
                value = el.value,
                height = el.height.toInt(),
                x = -0.0,
                y = -0.0
            )
        )
        return ans
    }

    private fun serialization(data: Container<Int, String>): String = Json.encodeToString(data)

    private fun deserialization(data: String): Container<Int, String> =
        Json.decodeFromString<Container<Int, String>>(data)

    override fun initTree(name: String, tree: AVLStruct<Container<Int, String>>): MutableList<DrawableAVLVertex<Container<Int, String>>> {
        if (db.isTreeExist(name)) {
            val vertexes = db.getTree(name, ::deserialization)
            tree.restoreStruct(drawVertexToVertex(vertexes))
            return vertexes
        }
        return mutableListOf()
    }

    override fun getVertexesForDrawFromTree(tree: AVLStruct<Container<Int, String>>): MutableList<DrawableAVLVertex<Container<Int, String>>> {
        return vertexToDrawVertex(tree.preOrder())
    }

    override fun getVertexesForDrawFromDB(name: String): MutableList<DrawableAVLVertex<Container<Int, String>>> {
        return db.getTree(name, ::deserialization)
    }

    override fun saveTreeToDB(
        name: String,
        preOrder: List<DrawableAVLVertex<Container<Int, String>>>,
        inOrder: List<DrawableAVLVertex<Container<Int, String>>>
    ) {
        db.deleteTree(name)
        db.saveTree(name, preOrder.toMutableList(), ::serialization)
    }

    override fun saveTreeToDB(name: String, tree: AVLStruct<Container<Int, String>>) {
        val info = vertexToDrawVertex(tree.preOrder())
        db.deleteTree(name)
        db.saveTree(name, info.toMutableList(), ::serialization)
    }

    override fun deleteTreeFromDB(name: String) {
        if (db.isTreeExist(name)) db.deleteTree(name)
    }

    override fun getSavedTreesNames(): List<String> = db.getAllSavedTrees()
/*    {
        val treesNames = db.getAllSavedTrees()

//        val dirPath = System.getProperty("user.dir") + "/saved-trees/AVL-trees"
//        File(dirPath).mkdirs()
//        if (treesNames.isNotEmpty()) {
//            for (name in treesNames) {
//                File(dirPath, name).run {
//                    createNewFile()
//                }
//            }
//        }

        return treesNames.subList(0, treesNames.size)
    }

    override fun insert(item: Container<Int, String>) = avlTree.insert(item)

    override fun delete(item: Container<Int, String>) {
        if (avlTree.find(item) != null) avlTree.delete(item)
    }
    */

    companion object {
        const val AVL_DB_DEFAULT_NAME = "avlDB.db"
    }
}
