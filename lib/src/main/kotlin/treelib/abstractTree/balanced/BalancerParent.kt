package treelib.abstractTree.balanced

import treelib.abstractTree.NodeParent
import treelib.abstractTree.StateContainer

abstract class BalancerParent<Pack : Comparable<Pack>, NodeType : NodeParent<Pack, NodeType>, StateContainerType : StateContainer<Pack, NodeType>> :
    Balancer<Pack, NodeType, StateContainerType> {
    fun rightRotate(currentNode: NodeType): NodeType {
        val leftChild = currentNode.left ?: throw InternalError()

        val parent = currentNode.parent
        leftChild.right?.parent = currentNode
        currentNode.left = leftChild.right
        leftChild.right = currentNode

        when {
            parent?.left == currentNode -> parent.left = leftChild
            parent?.right == currentNode -> parent.right = leftChild
        }
        currentNode.parent = leftChild
        leftChild.parent = parent
        return leftChild
    }

    fun leftRotate(currentNode: NodeType): NodeType {
        val rightChild = currentNode.right ?: throw InternalError()
        val parent = currentNode.parent

        rightChild.left?.parent = currentNode
        currentNode.right = rightChild.left
        rightChild.left = currentNode
        when {
            parent?.left == currentNode -> parent.left = rightChild
            parent?.right == currentNode -> parent.right = rightChild
        }
        currentNode.parent = rightChild
        rightChild.parent = parent
        return rightChild
    }

}
