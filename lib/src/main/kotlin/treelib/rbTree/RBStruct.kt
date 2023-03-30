package treelib.rbTree

import treelib.abstractTree.balanced.BalancedTreeStruct

class RBStruct<Pack : Comparable<Pack>> :
    BalancedTreeStruct<Pack, RBNode<Pack>, RBStateContainer<Pack>, RBBalancer<Pack>>() {

    override var root: RBNode<Pack>? = null

    override fun generateStateDelete(
        deletedNodeType: RBNode<Pack>,
        itsParent: RBNode<Pack>?
    ): RBStateContainer<Pack> {
        TODO("Not yet implemented")
    }

    override fun generateStateInsert(
        insertedNodeType: RBNode<Pack>,
        itsParent: RBNode<Pack>?
    ): RBStateContainer<Pack> {
        TODO("Not yet implemented")
    }

    override fun generateStateFind(foundNode: RBNode<Pack>): RBStateContainer<Pack> {
        TODO("Not yet implemented")
    }

    override val balancer = RBBalancer(root)

    override fun createNode(item: Pack): RBNode<Pack> {
        TODO("Not yet implemented")
    }

    override fun linkNewNode(node: RBNode<Pack>, parent: RBNode<Pack>?): RBNode<Pack> {
        TODO("Not yet implemented")
    }

    override fun rebaseNode(node: RBNode<Pack>, parent: RBNode<Pack>?, replaceNode: RBNode<Pack>?): RBNode<Pack>? {
        TODO("Not yet implemented")
    }

    override fun unLink(node: RBNode<Pack>, parent: RBNode<Pack>?): RBNode<Pack> {
        TODO("Not yet implemented")
    }
}
