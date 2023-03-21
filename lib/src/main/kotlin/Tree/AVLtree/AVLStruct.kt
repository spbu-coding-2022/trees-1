package AVLtree

import AbstractTree.Weighted.WeightedTreeStruct

class AVLStruct<Pack : Comparable<Pack>> : WeightedTreeStruct<Pack, AVLNode<Pack>, AVLBalancer<Pack>>() {
    override var root: AVLNode<Pack>? = null
    override val balancer = AVLBalancer<Pack>()
}