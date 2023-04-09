package treelib.rbTree

import treelib.abstractTree.balanced.BalancedTreeStruct

class RBStruct <Pack : Comparable<Pack>> : BalancedTreeStruct<Pack, RBNode<Pack>, RBStateContainer<Pack>, RBBalancer<Pack>>(){
    override var root: RBNode<Pack>? = null
    override val balancer = RBBalancer<Pack>(root)

}