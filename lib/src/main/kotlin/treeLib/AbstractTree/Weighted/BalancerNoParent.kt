package treeLib.AbstractTree.Weighted

import treeLib.AbstractTree.Node

abstract class BalancerNoParent<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>>: Balancer<Pack, NodeType> {
    override fun rightRotate(currentNode: NodeType): NodeType {
        // вероятно можем полагаться на то, что сын не будет null
        val leftSon = currentNode.left ?: throw NullPointerException()
        currentNode.left = leftSon.right
        leftSon.right = currentNode
        return leftSon
    }

    override fun leftRotate(currentNode: NodeType): NodeType {
        val rightSon = currentNode.right ?: throw NullPointerException()
        currentNode.right = rightSon.left
        rightSon.left = currentNode
        return rightSon
    }
}