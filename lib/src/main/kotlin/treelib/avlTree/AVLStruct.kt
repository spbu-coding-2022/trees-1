package treelib.avlTree

import treelib.abstractTree.balanced.BalancedTreeStruct

class AVLStruct<Pack : Comparable<Pack>> : BalancedTreeStruct<Pack, AVLNode<Pack>, AVLBalancer<Pack>>() {
    override var root: AVLNode<Pack>? = null

    override val balancer = AVLBalancer(root)

    override fun rebaseNode(node: AVLNode<Pack>, parent: AVLNode<Pack>?, replaceNode: AVLNode<Pack>?): AVLNode<Pack>? {
        TODO("Not yet implemented rebaseNode")
    }

    override fun unLink(node: AVLNode<Pack>, parent: AVLNode<Pack>?): AVLNode<Pack> {
        TODO("Not yet implemented unLink")
    }

    override fun deleteItem(item: Pack): AVLNode<Pack> {
        // возвращать того, от кого начинается балансировка.
        TODO("Not yet implemented")
    }

    override fun insertItem(item: Pack): AVLNode<Pack> {
        val parentNode: AVLNode<Pack>?
        val currentNode: AVLNode<Pack>
        val updateNode: AVLNode<Pack>? = findItem(item)

        if (updateNode == null) {
            parentNode = getLeafForInsert(item)
            currentNode = AVLNode(value = item)
            if (parentNode != null) {
                if (item > parentNode.value) parentNode.right = currentNode
                else parentNode.left = currentNode
            } else root = currentNode

            return currentNode
        } else {
            updateNode.value = item
            return updateNode
        }
    }
}