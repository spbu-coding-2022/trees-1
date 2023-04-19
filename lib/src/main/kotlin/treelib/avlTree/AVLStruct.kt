package treelib.avlTree

import treelib.abstractTree.balanced.BalancedTreeStruct
import treelib.singleObjects.exceptions.IncorrectUsage

class AVLStruct<Pack : Comparable<Pack>> :
    BalancedTreeStruct<Pack, AVLNode<Pack>, AVLStateContainer<Pack>, AVLVertex<Pack>, AVLBalancer<Pack>>() {

    override var root: AVLNode<Pack>? = null
    override val balancer = AVLBalancer(root)

    override fun generateStateDelete(
        deletedNode: AVLNode<Pack>?,
        contentNode: AVLNode<Pack>?,
    ): AVLStateContainer<Pack> = AVLStateContainer(contentNode, root)

    override fun generateStateInsert(
        insertNode: AVLNode<Pack>?,
        contentNode: AVLNode<Pack>?
    ): AVLStateContainer<Pack> = AVLStateContainer(contentNode, root)

    override fun generateStateFind(
        findNode: AVLNode<Pack>?,
        contentNode: AVLNode<Pack>?,
    ): AVLStateContainer<Pack> = AVLStateContainer(contentNode, root)

    override fun connectUnlinkedSubTreeWithParent(
        node: AVLNode<Pack>,
        parent: AVLNode<Pack>?,
        childForLink: AVLNode<Pack>?
    ) {
        if (root == null) return

        if (parent != null) {
            when {
                (node.value < parent.value) -> parent.left = childForLink
                (node.value > parent.value) -> parent.right = childForLink
            }
        } else root?.let {
            root = childForLink
        }
    }

    override fun toVertex(node: AVLNode<Pack>): AVLVertex<Pack> = AVLVertex(node.value, node.height)

    fun toNode(vertex: AVLVertex<Pack>): AVLNode<Pack> = AVLNode(value = vertex.value, height = vertex.height)

    override fun createNode(item: Pack): AVLNode<Pack> = AVLNode(item)

    override fun getNodeKernel(node: AVLNode<Pack>): AVLNode<Pack> = AVLNode(node.value, height = node.height)

    override fun linkNewNode(node: AVLNode<Pack>, parent: AVLNode<Pack>?): AVLNode<Pack> {
        if (parent == null) root = node
        else {
            if (node.value > parent.value) parent.right = node
            else parent.left = node
        }
        return node
    }
    fun <AVLVertexType: AVLVertex<Pack>> restoreStruct(preOrder: List<AVLVertexType>){
        if (root != null) throw IncorrectUsage("The tree already exists")
        for (vertex in preOrder){
            val currentNode = toNode(vertex)
            val leaf = getLeafForInsert(currentNode.value)
            linkNewNode(currentNode, leaf)
        }
    }
}
