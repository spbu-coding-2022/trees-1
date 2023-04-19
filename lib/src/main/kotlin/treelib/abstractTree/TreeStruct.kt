package treelib.abstractTree

import treelib.singleObjects.exceptions.BugInImplementException
import treelib.singleObjects.exceptions.ImpossibleCaseException
import treelib.singleObjects.exceptions.MultithreadingException
import treelib.singleObjects.exceptions.NonExistentValueException


abstract class TreeStruct<
        Pack : Comparable<Pack>,
        NodeType : Node<Pack, NodeType>,
        State : StateContainer<Pack, NodeType>,
        VertexType : Vertex<Pack>
        > {

    protected abstract var root: NodeType?

    protected fun getLeafForInsert(item: Pack): NodeType? {
        var currentNode: NodeType? = root ?: return null

        while (true) {
            currentNode?.let {
                when {
                    item > it.value -> {
                        if (it.right == null) return@getLeafForInsert currentNode
                        currentNode = it.right
                    }

                    item < it.value -> {
                        if (it.left == null) return@getLeafForInsert currentNode
                        currentNode = it.left
                    }

                    else -> throw BugInImplementException("getLeafForInsert shouldn't be used with a value exists in Struct")
                }
            } ?: throw MultithreadingException(ImpossibleCaseException())
        }
    }

    private fun getParentByValue(item: Pack): NodeType? {
        var currentNode = root

        if ((currentNode != null) && (currentNode.value == item)) return null

        while (true) {
            currentNode?.let {
                when {
                    item inRightOf it -> return@getParentByValue currentNode
                    item inLeftOf it -> return@getParentByValue currentNode
                    else -> {
                        if (item > it.value) currentNode = it.right
                        else currentNode = it.left
                    }
                }
            }
                ?: throw BugInImplementException("getParentByValue shouldn't be used with value doesn't exist in tree")// (1)l ->
        }
    }

    private infix fun Pack.inRightOf(node: NodeType?): Boolean {
        if (node == null) return false
        node.right?.let {
            if (this == it.value) return@inRightOf true
        }
        return false
    }

    private infix fun Pack.inLeftOf(node: NodeType?): Boolean {
        if (node == null) return false
        node.left?.let {
            if (it.value == this) return@inLeftOf true
        }
        return false
    }

    private fun getRightMinNode(localRoot: NodeType): NodeType {
        var currentNode: NodeType?

        localRoot.right
            ?: throw BugInImplementException("Incorrect usage of the getRightMinNode: right node doesn't exist")

        currentNode = localRoot.right

        while (true) {
            currentNode?.let { curNode ->
                if (curNode.left == null) return@getRightMinNode curNode
                else currentNode = curNode.left
            } ?: throw MultithreadingException(ImpossibleCaseException())
        }
    }

    private fun unLink(
        node: NodeType,
        parent: NodeType?,
    ): NodeType {
        val unLinkedNode: NodeType = node
        val childForLink: NodeType?

        when {
            (node.right != null) && (node.left != null) -> throw BugInImplementException("unLink - method Shouldn't be used with node with both children")
            node.right != null -> childForLink = node.right
            node.left != null -> childForLink = node.left
            else -> childForLink = null
        }
        unLinkedNode.left = null
        unLinkedNode.right = null

        if (parent == null) return unLinkedNode
        connectUnlinkedSubTreeWithParent(node, parent, childForLink)

        return unLinkedNode
    }

    private fun rebaseNode(
        node: NodeType,
        parent: NodeType?,
        replaceNode: NodeType?,
    ) {
        when {
            (parent == null) && (replaceNode == null) -> root = null
            (parent != null) && (replaceNode == null) -> {
                when {
                    node.value inLeftOf parent.left -> parent.left = null
                    node.value inRightOf parent.right -> parent.right = null
                }
            }

            replaceNode != null -> node.value = replaceNode.value
        }
    }

    protected abstract fun generateStateFind(
        findNode: NodeType?,
        contentNode: NodeType? = null,
    ): State

    protected fun findItem(obj: Pack): State {
        var currentNode = root
        if (root == null) return generateStateFind(null, null)

        while (true) {
            if (obj == currentNode?.value) return generateStateFind(currentNode, currentNode)
            else {
                currentNode?.let {
                    if (obj > it.value) currentNode = it.right
                    else currentNode = it.left
                }
                if (currentNode == null) return generateStateFind(null, null)
            }
        }
    }

    protected abstract fun generateStateInsert(
        insertNode: NodeType?,
        contentNode: NodeType? = null,
    ): State

    protected fun insertItem(item: Pack): State {
        val parentNode: NodeType?
        val currentNode: NodeType
        val updateNode: NodeType? = findItem(item).contentNode

        if (updateNode == null) {
            parentNode = getLeafForInsert(item)
            currentNode = createNode(item)

            linkNewNode(currentNode, parentNode)

            if (parentNode != null) return generateStateInsert(currentNode, parentNode)
            else return generateStateInsert(currentNode, currentNode)
        }

        updateNode.value = item
        return generateStateInsert(null, null)
    }

    protected abstract fun generateStateDelete(
        deletedNode: NodeType?,
        contentNode: NodeType? = null,
    ): State

    protected fun deleteItem(item: Pack): State {
        val parentDeleteNode: NodeType?
        val deleteNode: NodeType?

        if (findItem(item).contentNode == null) throw NonExistentValueException()

        parentDeleteNode = getParentByValue(item)
        if (parentDeleteNode != null) {
            deleteNode = when {
                item inRightOf parentDeleteNode -> parentDeleteNode.right
                item inLeftOf parentDeleteNode -> parentDeleteNode.left
                else -> throw ImpossibleCaseException()
            }
        } else deleteNode = root

        if (deleteNode == null) throw ImpossibleCaseException()

        val nodeForReplace: NodeType?
        val parentNodeForReplace: NodeType?
        val deletedNodeWithoutLinks = getNodeKernel(deleteNode)

        when {
            (deleteNode.left != null) && (deleteNode.right != null) -> {
                val rightMinNode = getRightMinNode(deleteNode)

                parentNodeForReplace = getParentByValue(rightMinNode.value)
                nodeForReplace = unLink(rightMinNode, parentNodeForReplace)

                rebaseNode(deleteNode, parentDeleteNode, nodeForReplace)

                return generateStateDelete(deletedNodeWithoutLinks, parentNodeForReplace)
            }

            (deleteNode.left == null) && (deleteNode.right == null) -> {
                if (parentDeleteNode == null) {
                    root = null
                    return generateStateDelete(deletedNodeWithoutLinks, null)
                } else {
                    when {
                        item inRightOf parentDeleteNode -> parentDeleteNode.right = null
                        item inLeftOf parentDeleteNode -> parentDeleteNode.left = null
                    }
                    return generateStateDelete(deletedNodeWithoutLinks, parentDeleteNode)
                }
            }

            else -> {
                for (child in listOf(deleteNode.right, deleteNode.left))
                    child?.let {
                        connectUnlinkedSubTreeWithParent(deleteNode, parentDeleteNode, it)
                        if (parentDeleteNode != null) {
                            return@deleteItem generateStateDelete(deletedNodeWithoutLinks, parentDeleteNode)
                        } else {
                            return@deleteItem generateStateDelete(deletedNodeWithoutLinks, root)
                        }
                    }
            }
        }
        throw ImpossibleCaseException()
    }

    protected abstract fun connectUnlinkedSubTreeWithParent(
        node: NodeType,
        parent: NodeType?,
        childForLink: NodeType?
    ) /* Behaviour: link rebased node */

    /* Return node with fields: right == left == {parent} == null */
    protected abstract fun getNodeKernel(node: NodeType): NodeType

    protected abstract fun linkNewNode(
        node: NodeType,
        parent: NodeType?,
    ): NodeType

    protected abstract fun createNode(item: Pack): NodeType

    abstract fun insert(item: Pack)

    /* Behaviour: null - means value not in tree; Pack - value was successfully deleted */
    abstract fun delete(item: Pack)

    fun find(obj: Pack): Pack? = findItem(obj).contentNode?.value

    fun inOrder(): List<VertexType> {
        val arrayNodes = mutableListOf<NodeType>()
        var flagVisited = 0
        var current = root
        val parents = ArrayDeque<NodeType>()

        while (current != null) {
            if (flagVisited == 0) {
                while (true) {
                    current?.let {
                        if (it.left == null) return@let null
                        parents.add(it)
                        current = it.left
                        return@let current
                    } ?: break
                }
            }
            current?.let {
                arrayNodes.add(it)
                if (it.right != null) {
                    flagVisited = 0
                    current = it.right
                } else {
                    if (parents.isEmpty())
                        return@inOrder arrayNodes.map { toVertex(it) }
                    flagVisited = 1
                    current = parents.removeLast()
                }
            }
        }
        return arrayNodes.map { toVertex(it) }
    }

    abstract fun toVertex(node: NodeType): VertexType

    fun postOrder(): List<VertexType> {
        val parents = ArrayDeque<NodeType>()
        val arrayNodes = mutableListOf<NodeType>()
        var flagVisited = 0
        var current = root

        while (current != null) {
            if (flagVisited == 0) {
                while (true) {
                    current?.let {
                        if (it.left == null) return@let null
                        parents.add(it)
                        current = it.left
                        return@let current
                    } ?: break
                }
            }
            current?.let {
                if (it.right != null && flagVisited != 2) {
                    parents.add(it)
                    current = it.right
                    flagVisited = 0
                } else {
                    arrayNodes.add(it)
                    if (parents.isEmpty())
                        return@postOrder arrayNodes.map { toVertex(it) }
                    val parent = parents.removeLast()
                    if (parent.right == it) {
                        flagVisited = 2
                    }
                    current = parent
                }
            } ?: throw MultithreadingException(ImpossibleCaseException())
        }
        return arrayNodes.map { toVertex(it) }
    }

    fun preOrder(): List<VertexType> {
        val arrayNodes = mutableListOf<NodeType>()
        var current: NodeType
        val queue = ArrayDeque<NodeType>()

        root?.let { root ->
            queue.add(root)
            while (queue.isNotEmpty()) {
                current = queue.removeLast()
                arrayNodes.add(current)
                if (current.right != null)
                    current.right?.let {
                        queue.add(it)
                    } ?: throw MultithreadingException(ImpossibleCaseException())

                if (current.left != null)
                    current.left?.let {
                        queue.add(it)
                    } ?: throw MultithreadingException(ImpossibleCaseException())
            }
        }
        return arrayNodes.map { toVertex(it) }
    }


}
