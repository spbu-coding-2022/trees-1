package treelib.abstractTree.balanced

import treelib.abstractTree.Node
import treelib.abstractTree.StateContainer

abstract class BalancerNoParent<
        Pack : Comparable<Pack>,
        NodeType : Node<Pack, NodeType>,
        State : StateContainer<Pack, NodeType>,
        >(val root: NodeType?) : Balancer<Pack, NodeType, State> {

    override fun rightRotate(currentNode: NodeType): NodeType {
        TODO("rightRotate - Not yet implemented")
    }

    override fun leftRotate(currentNode: NodeType): NodeType {
        TODO("leftRotate - Not yet implemented")
    }
}
