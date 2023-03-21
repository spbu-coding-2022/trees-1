package RBtree

import AVLtree.AVLNode
import AbstractTree.Weighted.BalancerParent

class RBBalancer<Pack: Comparable<Pack>>: BalancerParent<Pack, RBNode<Pack>>() {
    override fun balance(node: RBNode<Pack>): RBNode<Pack> {
        TODO("Not yet implemented")
    }
}