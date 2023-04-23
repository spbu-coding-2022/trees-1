package databaseManage

import com.google.gson.reflect.TypeToken
import databaseSave.jsonFormat.DrawableBINVertex
import databaseSave.jsonFormat.JsonRepository
import treelib.binTree.BINStruct
import treelib.commonObjects.Container
import java.io.File

class BINTreeManager {

    /*** using json format files ***/

    private val dirPath = System.getProperty("user.dir") + "/jsonFormatFiles"

    private val jsonRep = JsonRepository(dirPath)

    fun initTree(treeName: String): BINStruct<Container<Int, String>> {
        val typeToken = object : TypeToken<Array<DrawableBINVertex<Container<Int, String>>>>() {}
        val preOrder = jsonRep.exportTree(treeName, typeToken)
        val BINtree = BINStruct<Container<Int, String>>()
        BINtree.restoreStruct(preOrder.toList())

        return BINtree
    }

    fun <Pack : Comparable<Pack>> saveTree(preOrder: Array<DrawableBINVertex<Pack>>, treeName: String) {

        jsonRep.saveChanges(preOrder, treeName)

    }

    fun deleteTree(treeName: String) = jsonRep.removeTree(treeName)

    fun getNamesTrees(): List<String>? {
        val filesNames = File(dirPath).list()?.map { it.replace(".json", "") }

        return filesNames?.subList(0, 3)
    }

    fun cleanDB() = jsonRep.clean()

}