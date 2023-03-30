package treeLib.AbstractTree.Weighted

import treeLib.AbstractTree.Node
import treeLib.AbstractTree.StateContainer

interface Balancer <Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>, StateContainerType: StateContainer<Pack, NodeType>> {
    fun rightRotate(currentNode: NodeType): NodeType

    fun leftRotate(currentNode: NodeType): NodeType

    fun balance(node: StateContainerType): NodeType
}