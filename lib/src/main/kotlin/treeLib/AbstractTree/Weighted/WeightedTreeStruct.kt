package treeLib.AbstractTree.Weighted

import treeLib.AbstractTree.Node
import treeLib.AbstractTree.StateContainer
import treeLib.AbstractTree.TreeStruct

abstract class WeightedTreeStruct<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>, StateContainerType: StateContainer<Pack, NodeType>, BalancerType: Balancer<Pack, NodeType, StateContainerType>>:
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
            //root = balancer.balance(StateContainer(root!!))
            TODO()
        }
    }


    override fun delete(item: Pack) {
        deleteItem(item)
        if (root != null) {
            //root = balancer.balance(root!!)
            TODO()
        }
    }

}
