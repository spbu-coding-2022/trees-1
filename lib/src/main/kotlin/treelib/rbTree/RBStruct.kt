package treelib.rbTree

import treelib.abstractTree.balanced.BalancedTreeStruct

class RBStruct<Pack : Comparable<Pack>> :
    BalancedTreeStruct<Pack, RBNode<Pack>, RBStateContainer<Pack>, RBBalancer<Pack>>() {

    override var root: RBNode<Pack>? = null

    override val balancer = RBBalancer(root)

    override fun generateStateDelete(
        deletedNode: RBNode<Pack>?,
        contentNode: RBNode<Pack>?,
        ): RBStateContainer<Pack> {
        TODO("Not yet implemented")
    }

    override fun getNodeKernel(node: RBNode<Pack>): RBNode<Pack> {
        TODO("Not yet implemented")
    }

    override fun connectUnlinkedSubTreeWithParent(
        node: RBNode<Pack>,
        parent: RBNode<Pack>?,
        childForLink: RBNode<Pack>?
    ) {
        TODO("Not yet implemented")
    }

    override fun generateStateInsert(
        insertNode: RBNode<Pack>?,
        contentNode: RBNode<Pack>?,
        ): RBStateContainer<Pack> {
        TODO("Not yet implemented")
    }

    override fun generateStateFind(
        findNode: RBNode<Pack>?,
        contentNode: RBNode<Pack>?,
        ): RBStateContainer<Pack> {
        TODO("Not yet implemented")
    }

    override fun createNode(item: Pack): RBNode<Pack> = RBNode(item)

    override fun linkNewNode(node: RBNode<Pack>, parent: RBNode<Pack>?): RBNode<Pack> {
        TODO("Not yet implemented")
    }
}
