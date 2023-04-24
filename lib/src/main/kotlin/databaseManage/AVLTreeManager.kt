package databaseManage

import databaseSave.sqlite.DrawAVLVertex
import databaseSave.sqlite.SQLiteRepositoryExposed
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import treelib.avlTree.AVLStruct
import treelib.avlTree.AVLVertex
import treelib.commonObjects.Container

const val AVL_DEFAULT_NAME = "NewTreeAVL"
const val DB_DEFAULT_NAME = "avlDB.db"

class AVLTreeManager: TreeManager<Container<Int, String>, DrawAVLVertex<Container<Int, String>>> {
    private val db = SQLiteRepositoryExposed()
    private var avlTree = AVLStruct<Container<Int, String>>()

    override var currentTreeName = AVL_DEFAULT_NAME
        private set

    var dataBaseName = DB_DEFAULT_NAME
        private set

    private fun drawVertexToVertex(drawVertex: MutableList<DrawAVLVertex<Container<Int, String>>>): MutableList<AVLVertex<Container<Int, String>>> {
        //TODO: Rewrite while working on GUI
        val ans = mutableListOf<AVLVertex<Container<Int, String>>>()
        for (el in drawVertex) ans.add(AVLVertex(value = el.value, height = el.height.toUInt()))
        return ans
    }

    private fun vertexToDrawVertex(drawVertex: List<AVLVertex<Container<Int, String>>>): MutableList<DrawAVLVertex<Container<Int, String>>> {
        //TODO: Rewrite while working on GUI
        val ans = mutableListOf<DrawAVLVertex<Container<Int, String>>>()
        for (el in drawVertex) ans.add(DrawAVLVertex(value = el.value, height = el.height.toInt(), x = -0.0, y = -0.0))
        return ans
    }

    private fun serialization(data: Container<Int, String>): String = Json.encodeToString(data)

    private fun deserialization(data: String): Container<Int, String> =
        Json.decodeFromString<Container<Int, String>>(data)

    fun initDatabase(dbName: String) {
        db.initDataBase(dbName)
        dataBaseName = dbName
    }

    override fun initTree(treeName: String): MutableList<DrawAVLVertex<Container<Int, String>>> {
        avlTree = AVLStruct()
        currentTreeName = treeName

        if (db.isTreeExist(treeName)) {
            val vertexes = db.getTree(treeName, ::deserialization)
            avlTree.restoreStruct(drawVertexToVertex(vertexes))
            return vertexes
        }
        return mutableListOf()
    }

    override fun getVertexesForDrawFromTree(): MutableList<DrawAVLVertex<Container<Int, String>>> {
        return vertexToDrawVertex(avlTree.preOrder())
    }

    override fun getVertexesForDrawFromDB(): MutableList<DrawAVLVertex<Container<Int, String>>> {
        return db.getTree(currentTreeName, ::deserialization)
    }

    override fun saveTree(
        vertexes: MutableList<DrawAVLVertex<Container<Int, String>>>,
        inOrder: MutableList<DrawAVLVertex<Container<Int, String>>>
    ) {
        db.deleteTree(currentTreeName)
        db.saveTree(currentTreeName, vertexes, ::serialization)
    }

    override fun deleteTree() {
        db.deleteTree(currentTreeName)
    }

    override fun getSavedTreesNames() = db.getAllSavedTrees()

    override fun insert(item: Container<Int, String>) = avlTree.insert(item)

    override fun delete(item: Container<Int, String>) {
        if (avlTree.find(item) != null) avlTree.delete(item)
    }
}
