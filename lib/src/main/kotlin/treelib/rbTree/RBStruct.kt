package treelib.rbTree

import treelib.abstractTree.balanced.BalancedTreeStruct
import treelib.singleObjects.Markers
import treelib.singleObjects.exceptions.ImpossibleCaseException
import treelib.singleObjects.exceptions.MultithreadingException
import java.util.*

class RBStruct<Pack : Comparable<Pack>> :
    BalancedTreeStruct<Pack, RBNode<Pack>, RBStateContainer<Pack>, RBVertex<Pack>, RBBalancer<Pack>>() {

    override var root: RBNode<Pack>? = null

    override val balancer = RBBalancer(root)

    override fun generateStateDelete(
        deletedNode: RBNode<Pack>?,
        contentNode: RBNode<Pack>?,
    ): RBStateContainer<Pack> = RBStateContainer(contentNode)

    override fun generateStateInsert(
        insertNode: RBNode<Pack>?,
        contentNode: RBNode<Pack>?,
    ): RBStateContainer<Pack> = RBStateContainer(insertNode)

    override fun generateStateFind(
        findNode: RBNode<Pack>?,
        contentNode: RBNode<Pack>?,
    ): RBStateContainer<Pack> = RBStateContainer(findNode)

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
            if (childForLink != null) {
                childForLink.parent = parent
            }
        } else root?.let {
            root = childForLink
            if (childForLink != null) childForLink.parent = null
        }
    }

    override fun getNodeKernel(node: RBNode<Pack>): RBNode<Pack> = RBNode(node.value, color = node.color)

    override fun toVertex(node: RBNode<Pack>): RBVertex<Pack> {
        return RBVertex(node.value, node.color)
    }

    override fun createNode(item: Pack): RBNode<Pack> = RBNode(item)

    override fun linkNewNode(node: RBNode<Pack>, parent: RBNode<Pack>?): RBNode<Pack> {
        if (parent == null) {
            root = node
            root?.let {
                it.color = Markers.BLACK
            } ?: throw MultithreadingException(ImpossibleCaseException())
        } else {
            if (node.value > parent.value) parent.right = node
            else parent.left = node
            node.parent = parent
        }
        return node
    }

    fun <RBVertexType : RBVertex<Pack>> restoreStruct(preOrder: List<RBVertexType>, inOrder: List<RBVertexType>) {
        var inOrderIndex = 0
        var preOrderIndex = 0
        val set = HashSet<RBNode<Pack>>()
        val stack = LinkedList<RBNode<Pack>>()

        while (preOrderIndex in preOrder.indices) {
            var currentNode: RBNode<Pack>?
            var drawNode: RBVertexType

            do {
                drawNode = preOrder[preOrderIndex]
                currentNode = createRBNode(drawNode)
                if (root == null) {
                    root = currentNode
                }
                if (!stack.isEmpty()) {
                    if (set.contains(stack.peek())) {
                        set.remove(stack.peek())
                        stack.pop().right = currentNode
                    } else {
                        stack.peek().left = currentNode
                        // связь с ролитилем
                    }
                }
                stack.push(currentNode)
            } while (preOrder[preOrderIndex++] != inOrder[inOrderIndex] && preOrderIndex < preOrder.size)

            currentNode = null
            while (stack.isEmpty() && inOrderIndex < inOrder.size &&
                stack.peek().value == inOrder[inOrderIndex].value
            ) {
                currentNode = stack.pop()
                ++inOrderIndex
            }

            if (currentNode != null) {
                set.add(currentNode)
                stack.push(currentNode)
            }
        }
    }

    private fun <RBVertexType : RBVertex<Pack>> createRBNode(drawNode: RBVertexType): RBNode<Pack> {
        val node = RBNode(value = drawNode.value, color = drawNode.color)
        return node
    }
}
