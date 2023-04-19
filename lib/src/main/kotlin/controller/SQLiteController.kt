package controller

import dbSave.sqlite.DrawAVLVertex
import dbSave.sqlite.SQLiteRepository
import treelib.avlTree.AVLStruct
import treelib.avlTree.AVLVertex
import java.io.Closeable

class SQLiteController<Pack : Comparable<Pack>>(
    private var treeName: String,
    private val dbPath: String,
    private val serializeData: (input: Pack) -> String,
    private val deSerializeData: (input: String) -> Pack,
) : Closeable {
    private val db = SQLiteRepository(dbPath, serializeData, deSerializeData)
    private var avlTree = AVLStruct<Pack>()


    private fun drawVertexToVertex(drawVertex: MutableList<DrawAVLVertex<Pack>>): MutableList<AVLVertex<Pack>> {
        //TODO: Rewrite while working on GUI
        val ans = mutableListOf<AVLVertex<Pack>>()
        for (el in drawVertex) ans.add(AVLVertex(value = el.value, height = el.height.toUInt()))
        return ans
    }

    private fun vertexToDrawVertex(drawVertex: List<AVLVertex<Pack>>): MutableList<DrawAVLVertex<Pack>> {
        //TODO: Rewrite while working on GUI
        val ans = mutableListOf<DrawAVLVertex<Pack>>()
        for (el in drawVertex) ans.add(DrawAVLVertex(value = el.value, height = el.height.toInt(), x = 1.1, y = 1.1))
        return ans
    }

    fun initTree() {
        avlTree = AVLStruct()
        if (db.getTreeId(treeName) == 0) {
            db.addTree(treeName)
        } else {
            avlTree.restoreStruct(drawVertexToVertex(db.getAllVertexes(treeName)))
        }
        db.addTree(treeName)
    }

    fun saveTree() {
        db.addVertexes(vertexToDrawVertex(avlTree.preOrder()), treeName)
    }

    fun deleteTree() {
        if (db.getTreeId(treeName) != 0) {
            db.deleteTree(treeName)
        }
    }

    fun insert(item: Pack) = avlTree.insert(item)

    fun delete(item: Pack) = avlTree.delete(item)

    override fun close() = db.close()
}
