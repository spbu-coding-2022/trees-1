package AbstractTree.Weighted

import AbstractTree.Node
import AbstractTree.TreeStruct

abstract class WeightedTreeStruct<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>, BalancerType: Balancer<Pack, NodeType>>:
    TreeStruct<Pack, NodeType>() {

    protected abstract val balancer: BalancerType

    private fun insertItem(item: Pack) {
        TODO("Not yet implemented")
    }

    private fun deleteItem(item: Pack) {
        TODO("Not yet implemented")
    }

    override fun insert(item: Pack) {
        insertItem(item)
        if (root != null) {
            root = balancer.balance(root!!)
        }
    }

    override fun delete(item: Pack) {
        deleteItem(item)
        if (root != null) {
            root = balancer.balance(root!!)
        }
    }
}