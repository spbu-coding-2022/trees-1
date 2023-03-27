package treelib.binTree

import treelib.abstractTree.TreeStruct

class BINStruct<Pack : Comparable<Pack>> : TreeStruct<Pack, BINNode<Pack>>() {
    override var root: BINNode<Pack>? = null

    override fun rebaseNode(
        node: BINNode<Pack>,
        parent: BINNode<Pack>?,
        replaceNode: BINNode<Pack>?,
    ): BINNode<Pack>? {
        /*Behaviour: return - linked replaceNode*/
        //TODO: rebaseNode - test
        if (replaceNode != null) {
            replaceNode.left = node.left
            node.left = null
            replaceNode.right = node.right
            node.right = null
        }
        if (parent != null){
            when{
                node.value inRightOf parent -> parent.right = replaceNode
                node.value inLeftOf parent -> parent.left = replaceNode
                else -> return null
            }
        }
        return replaceNode
    }

    override fun unLink(
        node: BINNode<Pack>,
        parent: BINNode<Pack>?
    ): BINNode<Pack> {
        /*Behaviour:
        * 1) must be used with one or two children == null
        * 2) return - Node without children */
        /*TODO: unLink - test*/
        val unLinkedNode: BINNode<Pack> = node
        val childForLink: BINNode<Pack>?

        when {
            (node.right != null) && (node.left != null) -> return node //means-error (in correct node input)
            (node.right != null) -> childForLink = node.right
            (node.left != null) -> childForLink = node.left
            else -> childForLink = null
        }
        unLinkedNode.left = null
        unLinkedNode.right = null
        if (parent != null) {
            when {
                (node.value < parent.value) -> parent.right = childForLink
                (node.value > parent.value) -> parent.left = childForLink
            }
        }
        return unLinkedNode
    }

    override fun delete(item: Pack): Pack? = deleteItem(item)?.value

    override fun deleteItem(item: Pack): BINNode<Pack>? {
        /*return: null - error; value - deleted value*/
        /*TODO: test - deleteItem [невозможные ветки: - проверить, что они реально невозможны
           (->1 & 2) => что невозможно достичь deleteNode = parentNode?. со значением null (потому что inRightOf/inLeftOf)
           (->3) => странный кейс, когда удаляемое значение - null; такого вообще быть не должно
           ] */
        val parentNode: BINNode<Pack>? // parent node of the node for deleting
        var replaceNode: BINNode<Pack>?   // node for relink on the place deleted node
        var deleteNode: BINNode<Pack>? = null // node for deleting

        if (findItem(item) != null) {
            parentNode = getParentByValue(item)
            when {
                parentNode == null -> deleteNode = root
                item inRightOf parentNode -> deleteNode = parentNode.right //(->1)
                item inLeftOf parentNode -> deleteNode = parentNode.left   //(->2)
            }
            if (deleteNode != null) {
                replaceNode = getNodeForReplace(deleteNode)
                if (replaceNode != null) {
                    replaceNode = unLink(replaceNode, getParentByValue(replaceNode.value))
                } else replaceNode = null // if deleteNode doesn't have children

                if (parentNode == null) root = rebaseNode(deleteNode, parentNode, replaceNode)
                else rebaseNode(deleteNode, parentNode, replaceNode)

                return deleteNode
            } else return null // (->3)
        } else return null
    }

    override fun insert(item: Pack): Pack = insertItem(item).value

    override fun insertItem(item: Pack): BINNode<Pack> {
        val parentNode: BINNode<Pack>?
        val currentNode: BINNode<Pack>
        val updateNode: BINNode<Pack>? = findItem(item)

        if (updateNode == null) {
            parentNode = getLeafForInsert(item)
            currentNode = BINNode(value = item)
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