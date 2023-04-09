package treelib.avlTree

import treelib.abstractTree.balanced.BalancedTreeStruct

class AVLStruct<Pack : Comparable<Pack>> : BalancedTreeStruct<Pack, AVLNode<Pack>, AVLStateContainer<Pack>, AVLBalancer<Pack>>() {
    override var root: AVLNode<Pack>? = null
    override val balancer = AVLBalancer<Pack>(root)
}