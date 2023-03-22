package TreeLib.AVLtree

import TreeLib.AbstractTree.Weighted.BalancerNoParent

class AVLBalancer<Pack: Comparable<Pack>>: BalancerNoParent<Pack, AVLNode<Pack>>() {
    override fun balance(node: AVLNode<Pack>): AVLNode<Pack> {
        TODO("Not yet implemented")
    }
}