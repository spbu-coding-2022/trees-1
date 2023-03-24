package treeLib.AbstractTree.Weighted

import treeLib.AbstractTree.NodeParent

abstract class BalancerParent<Pack : Comparable<Pack>, NodeType : NodeParent<Pack, NodeType>>: Balancer<Pack, NodeType> {
    override fun rightRotate(currentNode: NodeType): NodeType {
        // пока верим в то, что currentNode не равно null
        val leftChild = currentNode.left ?: throw NullPointerException()
        val parent = currentNode.parent
        leftChild.right?.parent = currentNode
        leftChild.right = currentNode
        currentNode.left = leftChild.right

        when {
            parent?.left == currentNode -> parent.left = leftChild
            parent?.right == currentNode -> parent.right = leftChild
        }
        currentNode.parent = leftChild
        leftChild.parent = parent
        return leftChild
    }

    override fun leftRotate(currentNode: NodeType): NodeType {
        val rightChild = currentNode.right ?: throw NullPointerException()
        val parent = currentNode.parent

        rightChild.left?.parent = currentNode
        rightChild.left = currentNode
        currentNode.right = rightChild.left

        when {
            parent?.left == currentNode -> parent.left = rightChild
            parent?.right == currentNode -> parent.right = rightChild
        }
        currentNode.parent = rightChild
        rightChild.parent = parent
        return rightChild
    }

}
