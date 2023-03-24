package treelib.abstractTree.balanced

import treelib.abstractTree.Node

abstract class BalancerParent<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>>(val root: NodeType?) :
    Balancer<Pack, NodeType> {
    override fun rightRotate(currentNode: NodeType): NodeType {
        TODO("Not yet implemented")
    }

    override fun leftRotate(currentNode: NodeType): NodeType {
        TODO("Not yet implemented")
    }
}