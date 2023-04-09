package treelib.avlTree

import treelib.abstractTree.balanced.BalancerNoParent

class AVLBalancer<Pack: Comparable<Pack>>(private var root: AVLNode<Pack>?): BalancerNoParent<Pack, AVLNode<Pack>, AVLStateContainer<Pack>>() {
    private fun updateBalance(node: AVLNode<Pack>?): Int {
        return (getHeight(node?.left) - getHeight(node?.right)).toInt()
    }

    private fun getHeight(currentNode: AVLNode<Pack>?): UInt {
        return currentNode?.height ?: 0u
    }

    private fun updateHeight(currentNode: AVLNode<Pack>?) {
        if (currentNode != null)
            currentNode.height = maxOf(getHeight(currentNode.left), getHeight(currentNode.right))+1u
    }

    override fun balance(stateContainer: AVLStateContainer<Pack>): AVLNode<Pack> {
        val node = stateContainer.contentNode
        root = stateContainer.root
        return balance(root, node?.value ?: throw NullPointerException())
    }
    // В баланс передаем родителя ноды, которую будем удалять
    private fun balance(currentNode: AVLNode<Pack>?, value: Pack): AVLNode<Pack> {
        if (currentNode == null) {
            throw NullPointerException()
        }
        when {
            currentNode.value < value -> currentNode.right = balance(currentNode.right, value)
            currentNode.value > value -> currentNode.left = balance(currentNode.left, value)
        }
        updateHeight(currentNode)
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
            updateHeight(balanceNode.right)
            updateHeight(balanceNode)
            return balanceNode
        }
        return currentNode
    }

}
