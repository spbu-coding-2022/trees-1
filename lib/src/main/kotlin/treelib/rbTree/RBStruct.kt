package treelib.rbTree

import treelib.abstractTree.balanced.BalancedTreeStruct

class RBStruct<Pack : Comparable<Pack>> : BalancedTreeStruct<Pack, RBNode<Pack>, RBBalancer<Pack>>() {
    override var root: RBNode<Pack>? = null

    override val balancer = RBBalancer(root)

    override fun rebaseNode(node: RBNode<Pack>, parent: RBNode<Pack>?, replaceNode: RBNode<Pack>?): RBNode<Pack>? {
        TODO("Not yet implemented")
    }

    override fun unLink(node: RBNode<Pack>, parent: RBNode<Pack>?): RBNode<Pack> {
        TODO("Not yet implemented")
    }

    override fun deleteItem(item: Pack): RBNode<Pack>? {
        if (find(item) == null) {
            TODO("Not yet implemented")
        } else return null
    }

//    передавать того, от кого начинается балансировка
    override fun insertItem(item: Pack): RBNode<Pack>? {
        val parentNode: RBNode<Pack>?
        val currentNode: RBNode<Pack>
        val updateNode: RBNode<Pack>? = findItem(item)

        if (updateNode == null) {
            parentNode = getLeafForInsert(item)
            currentNode = RBNode(value = item, parent = parentNode)
            if (parentNode != null) {
                if (item > parentNode.value) parentNode.right = currentNode
                else parentNode.left = currentNode
            } else {
                root = RBNode(value = item)
                return root
            }
            return currentNode
        } else {
            updateNode.value = item
            return updateNode
        }
    }
}