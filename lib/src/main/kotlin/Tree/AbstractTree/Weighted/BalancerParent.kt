package AbstractTree.Weighted

import AbstractTree.Node

abstract class BalancerParent<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>>: Balancer<Pack, NodeType> {
    override fun rightRotate(currentNode: NodeType): NodeType {
        TODO("Not yet implemented")
    }

    override fun leftRotate(currentNode: NodeType): NodeType {
        TODO("Not yet implemented")
    }
}