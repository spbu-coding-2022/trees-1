package databaseManage

import com.google.gson.reflect.TypeToken
import databaseSave.jsonFormat.DrawableBINVertex
import databaseSave.jsonFormat.JsonRepository
import treelib.binTree.BINNode
import treelib.binTree.BINStateContainer
import treelib.binTree.BINStruct
import treelib.binTree.BINVertex
import treelib.commonObjects.Container
import java.io.File

class BINTreeManager : TreeManager<
        Container<Int, String>,
        DrawableBINVertex<Container<Int, String>>,
        BINNode<Container<Int, String>>,
        BINStateContainer<Container<Int, String>>,
        BINVertex<Container<Int, String>>,
        BINStruct<Container<Int, String>>
        > {

    /*** using json format files ***/

    private val dirPath = System.getProperty("user.dir") + "/saved-trees/BIN-trees"

    private val jsonRep = JsonRepository(dirPath)

    override fun initTree(
        name: String,
        tree: BINStruct<Container<Int, String>>
    ): List<DrawableBINVertex<Container<Int, String>>> {
        if (this.isTreeExist(name)) {
            val typeToken = object : TypeToken<Array<DrawableBINVertex<Container<Int, String>>>>() {}
            val preOrder = jsonRep.exportTree("$name.json", typeToken).toList()

            tree.restoreStruct(preOrder.toList())

            return preOrder
        }

        return listOf()
    }

    override fun saveTreeToDB(
        name: String,
        preOrder: List<DrawableBINVertex<Container<Int, String>>>,
        inOrder: List<DrawableBINVertex<Container<Int, String>>>
    ) {
        jsonRep.saveChanges(preOrder.toTypedArray(), "$name.json")
    }

    override fun saveTreeToDB(name: String, tree: BINStruct<Container<Int, String>>) {
        val info = vertexToDrawVertex(tree.preOrder())
        jsonRep.saveChanges(info.toTypedArray(), "$name.json")
    }

    private fun drawVertexToVertex(drawVertex: MutableList<DrawableBINVertex<Container<Int, String>>>): MutableList<BINVertex<Container<Int, String>>> {
        val ans = mutableListOf<BINVertex<Container<Int, String>>>()
        for (el in drawVertex) ans.add(BINVertex(value = el.value))
        return ans
    }

    private fun vertexToDrawVertex(drawVertex: List<BINVertex<Container<Int, String>>>): MutableList<DrawableBINVertex<Container<Int, String>>> {
        val ans = mutableListOf<DrawableBINVertex<Container<Int, String>>>()
        for (el in drawVertex) ans.add(
            DrawableBINVertex(
                value = el.value,
                x = -0.0,
                y = -0.0
            )
        )
        return ans
    }

    override fun deleteTreeFromDB(name: String) = jsonRep.removeTree(name)

    override fun getSavedTreesNames(): List<String> {
        val filesNames = File(dirPath).list()?.map { it.replace(".json", "") }

        return filesNames?.subList(0, filesNames.size) ?: listOf()
    }

    private fun isTreeExist(treeName: String): Boolean {
        return File(dirPath, "${treeName}.json").exists()
    }

    override fun getVertexesForDrawFromDB(name: String): List<DrawableBINVertex<Container<Int, String>>> {
        TODO("Not yet implemented")
    }

    override fun getVertexesForDrawFromTree(tree: BINStruct<Container<Int, String>>): List<DrawableBINVertex<Container<Int, String>>> =
        tree.preOrder().map { DrawableBINVertex(it.value) }

    fun cleanDB() = jsonRep.clean()

}
