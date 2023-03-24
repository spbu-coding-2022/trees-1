package treelib.avlTree

import treelib.abstractTree.balanced.BalancerNoParent

class AVLBalancer<Pack : Comparable<Pack>>(root: AVLNode<Pack>?) : BalancerNoParent<Pack, AVLNode<Pack>>(root) {
    override fun balance(node: AVLNode<Pack>): AVLNode<Pack> {
        TODO("Not yet implemented")
    }
}