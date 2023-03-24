package treeLib.BINtree

import treeLib.AbstractTree.TreeStruct

class BINStruct<Pack : Comparable<Pack>> : TreeStruct<Pack, BINNode<Pack>>() {
    override var root: BINNode<Pack>? = null

    override fun delete(item: Pack) {
        TODO("Not yet implemented")
    }

    override fun insert(item: Pack) {
        TODO("Not yet implemented")
    }
}