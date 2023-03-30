package treelib.avlTree

import treelib.abstractTree.balanced.BalancerNoParent

class AVLBalancer<Pack : Comparable<Pack>>(root: AVLNode<Pack>?) :
    BalancerNoParent<Pack, AVLNode<Pack>, AVLStateContainer<Pack>>(root) {

    override fun balance(state: AVLStateContainer<Pack>): AVLNode<Pack> {
        TODO("Not yet implemented")
    }
}
