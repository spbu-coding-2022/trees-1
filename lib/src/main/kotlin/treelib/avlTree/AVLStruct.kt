package treelib.avlTree

import treelib.abstractTree.balanced.BalancedTreeStruct

class AVLStruct<Pack : Comparable<Pack>> :
    BalancedTreeStruct<Pack, AVLNode<Pack>, AVLStateContainer<Pack>, AVLBalancer<Pack>>() {

    override var root: AVLNode<Pack>? = null

    override fun generateStateDelete(
        deletedNodeType: AVLNode<Pack>,
        itsParent: AVLNode<Pack>?
    ): AVLStateContainer<Pack> {
        TODO("Not yet implemented")
    }

    override fun generateStateInsert(
        insertedNodeType: AVLNode<Pack>,
        itsParent: AVLNode<Pack>?
    ): AVLStateContainer<Pack> {
        TODO("Not yet implemented")
    }

    override fun generateStateFind(foundNode: AVLNode<Pack>): AVLStateContainer<Pack> {
        TODO("Not yet implemented")
    }

    override val balancer = AVLBalancer(root)


    override fun createNode(item: Pack): AVLNode<Pack> {
        TODO("Not yet implemented")
    }

    override fun linkNewNode(node: AVLNode<Pack>, parent: AVLNode<Pack>?): AVLNode<Pack> {
        TODO("Not yet implemented")
    }

    override fun rebaseNode(node: AVLNode<Pack>, parent: AVLNode<Pack>?, replaceNode: AVLNode<Pack>?): AVLNode<Pack>? {
        TODO("Not yet implemented rebaseNode")
    }

    override fun unLink(node: AVLNode<Pack>, parent: AVLNode<Pack>?): AVLNode<Pack> {
        TODO("Not yet implemented unLink")
    }
}
