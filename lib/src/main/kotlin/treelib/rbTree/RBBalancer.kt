package treelib.rbTree

import treelib.abstractTree.balanced.BalancerParent

class RBBalancer<Pack : Comparable<Pack>>(root: RBNode<Pack>?) :
    BalancerParent<Pack, RBNode<Pack>, RBStateContainer<Pack>>(root) {

    override fun balance(state: RBStateContainer<Pack>): RBNode<Pack> {
        TODO("Not yet implemented")
    }
}
