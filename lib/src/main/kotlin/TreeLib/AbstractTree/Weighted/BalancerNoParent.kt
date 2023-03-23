package TreeLib.AbstractTree.Weighted

import TreeLib.AbstractTree.Node

abstract class BalancerNoParent<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>>: Balancer<Pack, NodeType> {
    override fun rightRotate(currentNode: NodeType): NodeType {
        // вероятно можем полагаться на то, что сын не будет null
        val leftSon = currentNode.left!!
        currentNode.left = leftSon.right
        leftSon.right = currentNode
        return leftSon
    }

    override fun leftRotate(currentNode: NodeType): NodeType {
        val rightSon = currentNode.right!!
        currentNode.right = rightSon.left
        rightSon.left = currentNode
        return rightSon
    }
}