package treelib.abstractTree.balanced

import treelib.abstractTree.Node
import treelib.abstractTree.StateContainer
import treelib.commonObjects.exceptions.IllegalNodeStateException

abstract class BalancerNoParent<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>, StateContainerType : StateContainer<Pack, NodeType>> :
    Balancer<Pack, NodeType, StateContainerType> {
    override fun rightRotate(currentNode: NodeType): NodeType {
        val leftSon = currentNode.left ?: throw IllegalNodeStateException()
        currentNode.left = leftSon.right
        leftSon.right = currentNode
        return leftSon
    }

    override fun leftRotate(currentNode: NodeType): NodeType {
        val rightSon = currentNode.right ?: throw IllegalNodeStateException()
        currentNode.right = rightSon.left
        rightSon.left = currentNode
        return rightSon
    }
}
