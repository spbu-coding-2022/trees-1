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

        return if ((filesNames?.size ?: 0) > 3) filesNames?.subList(0, 3) ?: listOf() else filesNames ?: listOf()
    }

    override fun isTreeExist(): Boolean {
        TODO("Not yet implemented")
    }

    fun isTreeExist(treeName: String): Boolean {
        return File(dirPath, treeName).exists()
    }

    fun cleanDB() = jsonRep.clean()

}