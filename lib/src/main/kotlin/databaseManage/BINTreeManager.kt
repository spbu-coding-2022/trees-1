package databaseManage

import com.google.gson.reflect.TypeToken
import databaseSave.jsonFormat.DrawableBINVertex
import databaseSave.jsonFormat.JsonRepository
import treelib.binTree.BINStruct
import treelib.singleObjects.Container

class BINTreeManager: TreeManager() {

    /*** using json format files ***/

    private val jsonRep = JsonRepository(System.getProperty("user.dir") + "/jsonFormatFiles")

    fun initTree(treeName: String): BINStruct<Container<Int, String>> {
        val typeToken = object : TypeToken<Array<DrawableBINVertex<Container<Int, String>>>>() {}
        val preOrder = jsonRep.exportTree(treeName, typeToken)
        val BINtree = BINStruct<Container<Int, String>>()
        BINtree.restoreStruct(preOrder.toList())

        return BINtree
    }

    fun <Pack: Comparable<Pack>> saveTree(preOrder: Array<DrawableBINVertex<Pack>>, treeName: String) {

        jsonRep.saveChanges(preOrder, treeName)

    }

    fun deleteTree(treeName: String) = jsonRep.removeTree(treeName)

    fun cleanDB() = jsonRep.clean()

}