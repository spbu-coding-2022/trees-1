package treelib.abstractTree.balanced

import treelib.abstractTree.Node
import treelib.abstractTree.StateContainer
import treelib.abstractTree.TreeStruct

abstract class BalancedTreeStruct<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>, StateContainerType: StateContainer<Pack, NodeType>, BalancerType: Balancer<Pack, NodeType, StateContainerType>>:
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
