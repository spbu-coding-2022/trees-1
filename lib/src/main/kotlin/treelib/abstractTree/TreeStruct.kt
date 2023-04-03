package treelib.abstractTree


abstract class TreeStruct<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>, State : StateContainer<Pack, NodeType>> {

    protected abstract var root: NodeType?

    protected fun getLeafForInsert(item: Pack): NodeType? {
        //TODO test - getParentByValue; [не нравится, что currentNode = it.right не требует проверки на null]
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
                    else -> throw Exception("getLeafForInsert shouldn't be used with a value exists in Struct")
                }
            } ?: throw Exception("Impossible case or multithreading problem")
        }
    }

    protected fun getParentByValue(item: Pack): NodeType? {
        //TODO test - getParentByValue [что вообще корректно отрабатывает]
        /*
        (1) - shouldn't be used with 'root == null' otherwise - incorrect behavior
        (2) - shouldn't be used with a value doesn't exist in the tree
        null - means root.value == item
        */

        var currentNode = root
        if (findItem(item).contentNode == null) throw Exception("getParentByValue shouldn't be used with a value doesn't exist in the tree")// (2)

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
            } ?: throw Exception("getParentByValue shouldn't be used with value doesn't exist in tree")// (1)l ->
        }
    }

    protected infix fun Pack.inRightOf(node: NodeType?): Boolean {
        //TODO - test inInRightOf
        if ((node == null) || (node.right == null)) return false
        node.right?.let {
            if (it.value == node.value) return@inRightOf true
        }
        return false
    }

    protected infix fun Pack.inLeftOf(node: NodeType?): Boolean {
        //TODO - test inInLeftOf
        if ((node == null) || (node.left == null)) return false
        node.left?.let {
            if (it.value == this) return@inLeftOf true
        }
        return false
    }

    protected fun getRightMinNode(localRoot: NodeType): NodeType {/* null - means NodeType.right doesn't exist, another variant impossible */
        //TODO test - getLeafForDelete [проверить, что нет проблем с (->1)]
        var currentNode: NodeType?

        localRoot.right ?: throw Exception("Incorrect usage of the getRightMinNode") //(->1)

        currentNode = localRoot.right

        while (true) {
            currentNode?.let { curNode ->
                if (curNode.left == null) return@getRightMinNode curNode
                else currentNode = curNode.left
            } ?: throw Exception("Impossible case or multithreading threads problem") //(->1)
        }
    }

    protected abstract fun generateStateFind(
        findNode: NodeType?,
        contentNode: NodeType? = null,
    ): State

    protected fun findItem(obj: Pack): State {
        var currentNode = root
        if (root == null) return generateStateFind(null)

        while (true) {
            if (obj == currentNode?.value) return generateStateFind(currentNode)
            else {
                currentNode?.let {
                    if (obj > it.value) currentNode = it.right
                    else currentNode = it.left
                } ?: return generateStateFind(null)
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

            return generateStateInsert(currentNode, parentNode)
        }

        updateNode.value = item
        return generateStateInsert(null)
    }

    protected abstract fun generateStateDelete(
        deletedNode: NodeType?,
        contentNode: NodeType? = null,
    ): State

    protected fun deleteItem(item: Pack): State {
        val parentDeleteNode: NodeType?
        val deleteNode: NodeType?

        if (findItem(item).contentNode == null) throw Exception("Deleted value doesn't exist in tree")

        parentDeleteNode = getParentByValue(item)
        if (parentDeleteNode != null) {
            deleteNode = when {
                item inRightOf parentDeleteNode -> parentDeleteNode.right
                item inLeftOf parentDeleteNode -> parentDeleteNode.left
                else -> throw Exception("Impossible case in deleteItem (is turned out to be possible)")
            }
        } else deleteNode = root

        if (deleteNode == null) throw Exception("Impossible case in deleteItem (is turned out to be possible)")

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
                if (parentDeleteNode == null) return generateStateDelete(deletedNodeWithoutLinks, null)
                else {
                    parentDeleteNode.left = null
                    parentDeleteNode.right = null
                    return generateStateDelete(deletedNodeWithoutLinks, parentDeleteNode)
                }
            }

            else -> {
                for (child in listOf(deleteNode.right, deleteNode.left))
                    child?.let {
                        connectUnlinkedSubTreeWithParent(deleteNode, parentDeleteNode, it)
                        return@deleteItem generateStateDelete(deletedNodeWithoutLinks, parentDeleteNode)
                    }
            }
        }
        throw Exception("Impossible case")
    }

    protected fun unLink(
        node: NodeType,
        parent: NodeType?,
    ): NodeType {
        val unLinkedNode: NodeType = node
        val childForLink: NodeType?

        when {
            (node.right != null) && (node.left != null) -> throw Exception("unLink - method Shouldn't be used with node with both children")
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

    protected abstract fun connectUnlinkedSubTreeWithParent(
        node: NodeType,
        parent: NodeType?,
        childForLink: NodeType?
    ) /* Behaviour: link rebased node */

    protected fun rebaseNode(
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

    /* Return node with fields: right == left == {parent} == null */
    protected abstract fun getNodeKernel(node: NodeType): NodeType

    protected abstract fun linkNewNode(
        node: NodeType,
        parent: NodeType?,
    ): NodeType

    protected abstract fun createNode(item: Pack): NodeType

    fun find(obj: Pack): Pack? = findItem(obj).contentNode?.value

    abstract fun insert(item: Pack)

    /*Behaviour: null - means value not in tree; Pack - value was successfully deleted*/
    abstract fun delete(item: Pack)

    fun inOrder(): List<Pack> {
        TODO()
    }

    fun postOrder(): List<Pack> {
        TODO()
    }

    fun preOrder(): List<Pack> {
        TODO()
    }
}
