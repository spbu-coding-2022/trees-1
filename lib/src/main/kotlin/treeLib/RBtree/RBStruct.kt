package treeLib.RBtree

import treeLib.AbstractTree.Weighted.WeightedTreeStruct

class RBStruct <Pack : Comparable<Pack>> : WeightedTreeStruct<Pack, RBNode<Pack>, RBBalancer<Pack>>(){
    override var root: RBNode<Pack>? = null
    override val balancer = RBBalancer<Pack>(root)

}