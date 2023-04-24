package databaseManage

import com.google.gson.reflect.TypeToken
import databaseSave.jsonFormat.DrawableBINVertex
import databaseSave.jsonFormat.JsonRepository
import treelib.binTree.BINStruct
import treelib.commonObjects.Container
import java.io.File

class BINTreeManager : TreeManager<Container<Int, String>, DrawableBINVertex<Container<Int, String>>> {

    /*** using json format files ***/

    override var currentTreeName = BIN_DEFAULT_NAME
        private set

    private val dirPath = System.getProperty("user.dir") + "/saved-trees/BIN-trees"

    private val jsonRep = JsonRepository(dirPath)
    private var binTree = BINStruct<Container<Int, String>>()

    override fun initTree(treeName: String): List<DrawableBINVertex<Container<Int, String>>> {
        binTree = BINStruct()
        currentTreeName = treeName

        if (this.isTreeExist(treeName)) {
            val typeToken = object : TypeToken<Array<DrawableBINVertex<Container<Int, String>>>>() {}
            val preOrder = jsonRep.exportTree(treeName, typeToken).toList()

            binTree.restoreStruct(preOrder.toList())

            return preOrder
        }

        return listOf()
    }

    override fun saveTree(
        preOrder: List<DrawableBINVertex<Container<Int, String>>>,
        inOrder: List<DrawableBINVertex<Container<Int, String>>>
    ) {

        jsonRep.saveChanges(preOrder.toTypedArray(), currentTreeName)

    }

    override fun deleteTree() = jsonRep.removeTree(currentTreeName)

    override fun getSavedTreesNames(): List<String> {
        val filesNames = File(dirPath).list()?.map { it.replace(".json", "") }

        return filesNames?.subList(0, filesNames.size) ?: listOf()
    }

    private fun isTreeExist(treeName: String): Boolean {
        return File(dirPath, treeName).exists()
    }

    override fun delete(item: Container<Int, String>) {
        if (binTree.find(item) != null)
            binTree.delete(item)
    }

    override fun insert(item: Container<Int, String>) = binTree.insert(item)

    override fun getVertexesForDrawFromDB(): List<DrawableBINVertex<Container<Int, String>>> {
        TODO("Not yet implemented")
    }

    override fun getVertexesForDrawFromTree(): List<DrawableBINVertex<Container<Int, String>>> =
        binTree.preOrder().map { DrawableBINVertex(it.value) }

    fun cleanDB() = jsonRep.clean()

    companion object {
        private const val BIN_DEFAULT_NAME = "NewTreeBIN"
    }

}