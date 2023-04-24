package databaseManage

import databaseSave.sqlite.DrawableAVLVertex
import databaseSave.sqlite.SQLiteRepositoryExposed
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import treelib.avlTree.AVLStruct
import treelib.avlTree.AVLVertex
import treelib.commonObjects.Container
import java.io.File

const val AVL_DEFAULT_NAME = "NewTreeAVL"
const val DB_DEFAULT_NAME = "avlDB.db"

class AVLTreeManager: TreeManager<Container<Int, String>, DrawableAVLVertex<Container<Int, String>>> {
    private val db = SQLiteRepositoryExposed()
    private var avlTree = AVLStruct<Container<Int, String>>()

    override var currentTreeName = AVL_DEFAULT_NAME
        private set

    private var dataBaseName = DB_DEFAULT_NAME
        private set

    private fun drawVertexToVertex(drawVertex: MutableList<DrawableAVLVertex<Container<Int, String>>>): MutableList<AVLVertex<Container<Int, String>>> {
        //TODO: Rewrite while working on GUI
        val ans = mutableListOf<AVLVertex<Container<Int, String>>>()
        for (el in drawVertex) ans.add(AVLVertex(value = el.value, height = el.height.toUInt()))
        return ans
    }

    private fun vertexToDrawVertex(drawVertex: List<AVLVertex<Container<Int, String>>>): MutableList<DrawableAVLVertex<Container<Int, String>>> {
        //TODO: Rewrite while working on GUI
        val ans = mutableListOf<DrawableAVLVertex<Container<Int, String>>>()
        for (el in drawVertex) ans.add(DrawableAVLVertex(value = el.value, height = el.height.toInt(), x = -0.0, y = -0.0))
        return ans
    }

    private fun serialization(data: Container<Int, String>): String = Json.encodeToString(data)

    private fun deserialization(data: String): Container<Int, String> =
        Json.decodeFromString<Container<Int, String>>(data)

    fun initDatabase(dbName: String) {
        db.initDataBase(dbName)
        dataBaseName = dbName
    }

    override fun initTree(treeName: String): MutableList<DrawableAVLVertex<Container<Int, String>>> {
        avlTree = AVLStruct()
        currentTreeName = treeName

        if (db.isTreeExist(treeName)) {
            val vertexes = db.getTree(treeName, ::deserialization)
            avlTree.restoreStruct(drawVertexToVertex(vertexes))
            return vertexes
        }
        return mutableListOf()
    }

    override fun getVertexesForDrawFromTree(): MutableList<DrawableAVLVertex<Container<Int, String>>> {
        return vertexToDrawVertex(avlTree.preOrder())
    }

    override fun getVertexesForDrawFromDB(): MutableList<DrawableAVLVertex<Container<Int, String>>> {
        return db.getTree(currentTreeName, ::deserialization)
    }

    override fun saveTree(
        preOrder: List<DrawableAVLVertex<Container<Int, String>>>,
        inOrder: List<DrawableAVLVertex<Container<Int, String>>>
    ) {
        db.deleteTree(currentTreeName)
        db.saveTree(currentTreeName, preOrder.toMutableList(), ::serialization)
    }

    override fun deleteTree() {
        db.deleteTree(currentTreeName)
    }

    override fun getSavedTreesNames(): List<String> {
        val treesNames = db.getAllSavedTrees()
        val dirPath = System.getProperty("user.dir") + "/saved-trees/AVL-trees"
        File(dirPath).mkdirs()
        if (treesNames.isNotEmpty()) {
            for (name in treesNames) {
                File(dirPath, name).run {
                    createNewFile()
                }
            }
        }

        return treesNames.subList(0, treesNames.size)
    }

    override fun insert(item: Container<Int, String>) = avlTree.insert(item)

    override fun delete(item: Container<Int, String>) {
        if (avlTree.find(item) != null) avlTree.delete(item)
    }
}
