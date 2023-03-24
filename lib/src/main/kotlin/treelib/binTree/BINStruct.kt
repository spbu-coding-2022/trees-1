package treelib.binTree

import treelib.abstractTree.TreeStruct

class BINStruct<Pack : Comparable<Pack>> : TreeStruct<Pack, BINNode<Pack>>() {
    override var root: BINNode<Pack>? = null

    override fun delete(item: Pack): Pack? {
        TODO("Not yet implemented")
    }

    override fun deleteItem(item: Pack): BINNode<Pack>? {
        TODO("Not yet implemented")
    }

    override fun insert(item: Pack): Pack? {
        TODO("Not yet implemented")
    }

    override fun insertItem(item: Pack): BINNode<Pack>? {
        TODO("Not yet implemented")
    }
}