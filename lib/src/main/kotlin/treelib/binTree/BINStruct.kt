package treelib.binTree

import treelib.abstractTree.TreeStruct

class BINStruct<Pack : Comparable<Pack>> :
    TreeStruct<Pack, BINNode<Pack>, BINStateContainer<Pack>>() {

    override var root: BINNode<Pack>? = null

    override fun generateStateDelete(
        deletedNode: BINNode<Pack>?,
        contentNode: BINNode<Pack>?
    ): BINStateContainer<Pack> = BINStateContainer(deletedNode)

    override fun generateStateInsert(
        insertNode: BINNode<Pack>?,
        contentNode: BINNode<Pack>?,
    ): BINStateContainer<Pack> = BINStateContainer(insertNode)

    override fun generateStateFind(
        findNode: BINNode<Pack>?,
        contentNode: BINNode<Pack>?,
    ): BINStateContainer<Pack> = BINStateContainer(findNode)

    override fun getNodeKernel(node: BINNode<Pack>) = BINNode(node.value)

    override fun connectUnlinkedSubTreeWithParent(
        node: BINNode<Pack>,
        parent: BINNode<Pack>?,
        childForLink: BINNode<Pack>?,
    ) {
        if (root == null) return

        if (parent != null) {
            when {
                (node.value < parent.value) -> parent.right = childForLink
                (node.value > parent.value) -> parent.left = childForLink
            }
        } else root?.let {
            root = childForLink
        }
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

    override fun delete(item: Pack) {
        deleteItem(item).contentNode
    }

    override fun insert(item: Pack) {
        insertItem(item).contentNode
    }
}
