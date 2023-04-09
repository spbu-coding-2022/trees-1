package treelib.rbTree

import treelib.abstractTree.balanced.BalancedTreeStruct

class RBStruct<Pack : Comparable<Pack>> :
    BalancedTreeStruct<Pack, RBNode<Pack>, RBStateContainer<Pack>, RBBalancer<Pack>>() {

    override var root: RBNode<Pack>? = null

    override val balancer = RBBalancer(root)

    override fun generateStateDelete(
        deletedNode: RBNode<Pack>?,
        contentNode: RBNode<Pack>?,
        ): RBStateContainer<Pack> = RBStateContainer(contentNode)

    override fun generateStateInsert(
        insertNode: RBNode<Pack>?,
        contentNode: RBNode<Pack>?,
    ): RBStateContainer<Pack>  = RBStateContainer(contentNode)

    override fun generateStateFind(
        findNode: RBNode<Pack>?,
        contentNode: RBNode<Pack>?,
    ): RBStateContainer<Pack>  = RBStateContainer(contentNode)

    override fun connectUnlinkedSubTreeWithParent(
        node: RBNode<Pack>,
        parent: RBNode<Pack>?,
        childForLink: RBNode<Pack>?
    ) {
        if (root == null) return

        if (parent != null) {
            when {
                (node.value < parent.value) -> {
                    parent.left = childForLink
                }
                (node.value > parent.value) -> {
                    parent.right = childForLink
                }
            }
            if (childForLink != null){
                childForLink.parent = parent
            }
        } else root?.let {
            root = childForLink
        }
    }

    override fun getNodeKernel(node: RBNode<Pack>): RBNode<Pack> = RBNode(node.value, color = node.color)

    override fun createNode(item: Pack): RBNode<Pack> = RBNode(item)

    override fun linkNewNode(node: RBNode<Pack>, parent: RBNode<Pack>?): RBNode<Pack> {
        if (parent == null) root = node
        else {
            if (node.value > parent.value) parent.right = node
            else parent.left = node
            node.parent = parent
        }
        return node
    }
}
