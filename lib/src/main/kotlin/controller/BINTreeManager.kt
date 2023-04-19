package controller

import dbSave.jsonFormat.JsonRepository
import treelib.binTree.BINStruct

class BINTreeManager: TreeManager() {

    /*** using json format files ***/

    val jsonRep = JsonRepository(System.getProperty("user.dir"))


    /*** 1.7.6 ***/
    fun initTree(treeName: String) {
        TODO()
    }

    fun <Pack: Comparable<Pack>> saveTree(tree: BINStruct<Pack>) {
        TODO()
    }
}