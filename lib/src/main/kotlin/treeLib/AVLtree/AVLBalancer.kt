package treeLib.AVLtree

import treeLib.AbstractTree.Weighted.BalancerNoParent
// gradle init
class AVLBalancer<Pack: Comparable<Pack>>(private var root: AVLNode<Pack>?): BalancerNoParent<Pack, AVLNode<Pack>>() {
    private fun updateBalance(node: AVLNode<Pack>?): Int {
        return (getHeight(node?.left) - getHeight(node?.right)).toInt()

    }

    private fun getHeight(currentNode: AVLNode<Pack>?): UInt {
        return currentNode?.height ?: 0u
    }

    private fun updateHeight(currentNode: AVLNode<Pack>?): UInt {
        return if (currentNode == null) 0u else ( maxOf(getHeight(currentNode.left), getHeight(currentNode.right)) + 1u)
    }

    override fun balance(node: AVLNode<Pack>): AVLNode<Pack> = balance(root, node.value)

    private fun balance(currentNode: AVLNode<Pack>?, value: Pack): AVLNode<Pack> {
        if (currentNode == null) {
            throw NullPointerException()
        }
        when {
            currentNode.value < value -> currentNode.right = balance(currentNode.right, value)
            currentNode.value > value -> currentNode.left = balance(currentNode.left, value)
            currentNode.value == value -> return currentNode
        }
        currentNode.height = updateHeight(currentNode)
        val balance = updateBalance(currentNode)
        if (balance == -2) {
            if (updateBalance(currentNode.right) == 1) {
                currentNode.right = currentNode.right?.let { rightRotate(it) } ?: throw NullPointerException()
                updateHeight(currentNode.right?.right)
            }
            val balancedNode = leftRotate(currentNode)
            updateHeight(balancedNode.left)
            updateHeight(balancedNode)
            return balancedNode
        }
        if (balance == 2) {
            if (updateBalance(currentNode.left) == -1) {
                currentNode.left = currentNode.left?.let { leftRotate(it) } ?: throw NullPointerException()
                updateHeight(currentNode.left?.left)
            }
            val balanceNode = rightRotate(currentNode)
            updateHeight(currentNode.right)
            updateHeight(currentNode)
            return balanceNode
        }
        return currentNode
    }

}
