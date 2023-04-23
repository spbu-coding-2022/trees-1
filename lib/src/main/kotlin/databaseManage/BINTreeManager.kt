package databaseManage

import com.google.gson.reflect.TypeToken
import databaseSave.jsonFormat.DrawableBINVertex
import databaseSave.jsonFormat.JsonRepository
import treelib.commonObjects.Container
import java.io.File

class BINTreeManager : TreeManager<Container<Int, String>, DrawableBINVertex<Container<Int, String>>> {

    /*** using json format files ***/

    private val dirPath = System.getProperty("user.dir") + "/jsonFormatFiles"

    private val jsonRep = JsonRepository(dirPath)

    override fun getTree(treeName: String): Pair<List<DrawableBINVertex<Container<Int, String>>>, List<DrawableBINVertex<Container<Int, String>>>> {
        val typeToken = object : TypeToken<Array<DrawableBINVertex<Container<Int, String>>>>() {}
        val preOrder = jsonRep.exportTree(treeName, typeToken).toList()

        return Pair(preOrder, listOf())
    }

    override fun saveTree(
        preOrder: Array<DrawableBINVertex<Container<Int, String>>>,
        inOrder: Array<DrawableBINVertex<Container<Int, String>>>,
        treeName: String
    ) {

        jsonRep.saveChanges(preOrder, treeName)

    }

    override fun deleteTree(treeName: String) = jsonRep.removeTree(treeName)

    override fun getSavedTreesNames(): List<String> {
        val filesNames = File(dirPath).list()?.map { it.replace(".json", "") }

        return filesNames?.subList(0, 3) ?: listOf()
    }

    override fun isTreeExist(): Boolean {
        TODO()
    }

    fun cleanDB() = jsonRep.clean()

}