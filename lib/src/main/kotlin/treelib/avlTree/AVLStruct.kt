package treelib.avlTree

import treelib.abstractTree.balanced.BalancedTreeStruct

class AVLStruct<Pack : Comparable<Pack>> :
    BalancedTreeStruct<Pack, AVLNode<Pack>, AVLStateContainer<Pack>, AVLBalancer<Pack>>() {

    override var root: AVLNode<Pack>? = null
    override val balancer = AVLBalancer(root)

    override fun generateStateDelete(
        deletedNode: AVLNode<Pack>?,
        contentNode: AVLNode<Pack>?,
    ): AVLStateContainer<Pack> {
        TODO("Not yet implemented")
    }

    override fun getNodeKernel(node: AVLNode<Pack>): AVLNode<Pack> {
        TODO("Not yet implemented")
    }

    override fun connectUnlinkedSubTreeWithParent(
        node: AVLNode<Pack>,
        parent: AVLNode<Pack>?,
        childForLink: AVLNode<Pack>?
    ) {
        TODO("Not yet implemented")
    }

    override fun generateStateInsert(
        insertNode: AVLNode<Pack>?,
        contentNode: AVLNode<Pack>?
    ): AVLStateContainer<Pack> {
        TODO("Not yet implemented")
    }

    override fun generateStateFind(
        findNode: AVLNode<Pack>?,
        contentNode: AVLNode<Pack>?,
        ): AVLStateContainer<Pack> {
        TODO("Not yet implemented")
    }

    override fun createNode(item: Pack): AVLNode<Pack> {
        TODO("Not yet implemented")
    }

    override fun linkNewNode(node: AVLNode<Pack>, parent: AVLNode<Pack>?): AVLNode<Pack> {
        TODO("Not yet implemented")
    }
}
