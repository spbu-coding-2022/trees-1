package TreeLib.AbstractTree.Weighted

import TreeLib.AbstractTree.Node

interface Balancer<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>> {
    fun rightRotate(currentNode: NodeType): NodeType

    fun leftRotate(currentNode: NodeType): NodeType

    fun balance(node: NodeType): NodeType
}