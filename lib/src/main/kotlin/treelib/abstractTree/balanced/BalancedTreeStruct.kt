package treelib.abstractTree.balanced

import treelib.abstractTree.Node
import treelib.abstractTree.StateContainer
import treelib.abstractTree.TreeStruct
import treelib.abstractTree.Vertex

abstract class BalancedTreeStruct<
        Pack : Comparable<Pack>,
        NodeType : Node<Pack, NodeType>,
        State : StateContainer<Pack, NodeType>,
        VertexType : Vertex<Pack>,
        BalancerType : Balancer<Pack, NodeType, State>,
        > : TreeStruct<Pack, NodeType, State, VertexType>() {

    protected abstract val balancer: BalancerType

    override fun insert(item: Pack) {
        val currentState = insertItem(item)
        if (currentState.contentNode != null) {
            root = balancer.balance(currentState)
        }
    }

    override fun delete(item: Pack) {
        val currentState = deleteItem(item)
        if (root == null) {
            return
        }
        if (currentState.contentNode != null) {
            root = balancer.balance(currentState)
            return
        }
    }
}
