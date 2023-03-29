//TODO нужны ли let? везде
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
            node.value = replaceNode.value
            return node
        } else return null
    }

    override fun unLink(
        node: BINNode<Pack>,
        parent: BINNode<Pack>?
    ): BINNode<Pack> {
        /*Behaviour:
        * 1) must be used with [one or two] children == null
        * 2) return - Node without children */
        /*TODO: unLink - test*/
        val unLinkedNode: BINNode<Pack> = node
        val childForLink: BINNode<Pack>?

        when {
            (node.right != null) && (node.left != null) -> return node //means-error (in correct node input)
            node.right != null -> childForLink = node.right
            node.left != null -> childForLink = node.left
            else -> childForLink = null
        }
        unLinkedNode.left = null
        unLinkedNode.right = null
        if (parent == null) return unLinkedNode
        when {
            (node.value < parent.value) -> parent.right = childForLink
            (node.value > parent.value) -> parent.left = childForLink
        }
        return unLinkedNode
    }

    override fun linkNewNode(
        //TODO make an abstract?
        node: BINNode<Pack>,
        parent: BINNode<Pack>?,
    ): BINNode<Pack> {
        if (parent == null) root = node
        else {
            if (node.value > parent.value) parent.right = node
            else parent.left = node
        }
        return node
    }

    override fun createNode(item: Pack) = BINNode(item)

    override fun delete(item: Pack): Pack? = deleteItem(item)?.value

    override fun insert(item: Pack): Pack = insertItem(item).value
}