package treelib.binTree

import treelib.abstractTree.TreeStruct

class BINStruct<Pack : Comparable<Pack>> :
    TreeStruct<Pack, BINNode<Pack>, BINStateContainer<Pack>>() {

    override var root: BINNode<Pack>? = null

    override fun generateStateDelete(
        deletedNode: BINNode<Pack>?,
        itsParent: BINNode<Pack>?
    ): BINStateContainer<Pack> = BINStateContainer(deletedNode)

    override fun generateStateInsert(
        insertedNode: BINNode<Pack>?,
        itsParent: BINNode<Pack>?
    ): BINStateContainer<Pack> = BINStateContainer(insertedNode)

    override fun generateStateFind(foundNode: BINNode<Pack>?): BINStateContainer<Pack> = BINStateContainer(foundNode)

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
        /*Behaviour: return - Node without children */
        /*TODO: unLink - test*/
        val unLinkedNode: BINNode<Pack> = node
        val childForLink: BINNode<Pack>?
        when {
            (node.right != null) && (node.left != null) -> throw Exception("unLink - method Shouldn't be used with node with both children")
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

    override fun delete(item: Pack): Pack? = deleteItem(item).contentNode?.value

    override fun insert(item: Pack): Pack? = insertItem(item).contentNode?.value
}
