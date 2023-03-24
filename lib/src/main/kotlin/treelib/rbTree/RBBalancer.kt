package treelib.rbTree

import treelib.abstractTree.balanced.BalancerParent

class RBBalancer<Pack : Comparable<Pack>>(root: RBNode<Pack>?) : BalancerParent<Pack, RBNode<Pack>>(root) {
    override fun balance(node: RBNode<Pack>): RBNode<Pack> {
        TODO("Not yet implemented")
    }
}