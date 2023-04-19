package treelib.binTree

import treelib.abstractTree.TreeStruct
import treelib.abstractTree.Vertex
import treelib.singleObjects.exceptions.IncorrectUsage

class BINStruct<Pack : Comparable<Pack>> :
    TreeStruct<Pack, BINNode<Pack>, BINStateContainer<Pack>, BINVertex<Pack>>() {

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
                (node.value < parent.value) -> parent.left = childForLink
                (node.value > parent.value) -> parent.right = childForLink
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

    override fun toVertex(node: BINNode<Pack>): BINVertex<Pack> {
        return BINVertex(node.value)
    }

    override fun createNode(item: Pack) = BINNode(item)

    override fun delete(item: Pack) {
        deleteItem(item).contentNode
    }

    override fun insert(item: Pack) {
        insertItem(item).contentNode
    }

    private fun toNode(vertex: BINVertex<Pack>): BINNode<Pack> = BINNode(value = vertex.value)

    fun <BINVertexType: BINVertex<Pack>> restoreStruct(preOrder: List<BINVertexType>){
        if (root != null) throw IncorrectUsage("The tree already exists")
        for (vertex in preOrder){
            val currentNode = toNode(vertex)
            val leaf = getLeafForInsert(currentNode.value)
            linkNewNode(currentNode, leaf)
        }
    }
}
