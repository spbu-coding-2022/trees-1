package treelib.abstractTree

import treelib.binTree.BINNode

abstract class TreeStruct<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>> {

    protected abstract var root: NodeType?

    protected fun getLeafForInsert(item: Pack): NodeType? {
        //TODO test - getParentByValue; [не нравится, что currentNode = it.right не требует проверки на null]
        var currentNode: NodeType? = root ?: return null

        while (true) {
            currentNode?.let {
                if (item > it.value) {
                    if (it.right == null) return currentNode
                    currentNode = it.right
                } else {
                    if (it.left == null) return currentNode
                    currentNode = it.left
                }
            } ?: throw Exception("Impossible case or multithreading problem ")
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
        if (findItem(item) == null) throw Exception("getParentByValue shouldn't be used with a value doesn't exist in the tree")// (2)

        if ((currentNode != null) && (currentNode.value == item)) return null

        while (true) {
            currentNode?.let {
                when {
                    it.right != null -> {
                        if (it.right?.value == item) return currentNode
                        else throw Exception("impossible case, or multithreading threads problem")
                    }

                    it.left != null -> {
                        if (it.left?.value == item) return currentNode
                        else throw Exception("impossible case, or multithreading threads problem")
                    }
                }
                if (item > it.value) currentNode = it.right
                else currentNode = it.left

            } ?: throw Exception("getParentByValue shouldn't be used with 'root == null'")// (1)l ->
        }
    }

    protected infix fun Pack.inRightOf(node: NodeType?): Boolean {
        //TODO - test inInRightOf
        if ((node == null) || (node.right == null)) return false
        node.right?.let {
            if (it.value == node.value) return true
        }
        return false
    }

    protected infix fun Pack.inLeftOf(node: NodeType?): Boolean {
        //TODO - test inInLeftOf
        if ((node == null) || (node.left == null)) return false
        node.left?.let {
            if (it.value == this) return true
        }
        return false
    }

    private fun getRightMinNode(localRoot: NodeType): NodeType? {
        /* null - means NodeType.right doesn't exist, another variant impossible */
        //TODO test - getLeafForDelete [проверить, что нет проблем с (->1)]
        var currentNode: NodeType? = null

        localRoot.right?.let {
            currentNode = localRoot.right
        } ?: return null

        while (true) {
            currentNode?.let { curNode ->
                if (curNode.left == null) return currentNode
                else currentNode = curNode.left
            } ?: throw Exception("impossible case, or multithreading threads problem") //(->1)
        }
    }

    private fun getLeftMaxNode(localRoot: NodeType): NodeType? {
        /* null - means NodeType.left doesn't exist, another variant impossible */
        //TODO test - getLeafLeftMax [проверить, что нет проблем с (->1)]
        var currentNode: NodeType? = null

        localRoot.left?.let {
            currentNode = localRoot.left
        } ?: return null

        while (true) {
            currentNode?.let { curNode ->
                if (curNode.right == null) return currentNode
                else currentNode = curNode.right
            } ?: throw Exception("impossible case, or multithreading threads problem") //(->1)
        }
    }

    protected fun getNodeForReplace(localRoot: NodeType?): NodeType? {
        /* Behaviour: null - localRoot doesn't have children */
        localRoot?.let {
            val replaceNode = getRightMinNode(it)
            if (replaceNode != null) return replaceNode
            else return getLeftMaxNode(it)
        } ?: return null
    }

    protected fun findItem(obj: Pack): NodeType? {
        var currentNode = root
        if (root == null) return null

        while (true) {
            if (obj == currentNode?.value) return currentNode
            else {
                currentNode?.let {
                    if (obj > it.value) currentNode = it.right
                    else currentNode = it.left
                } ?: return null
            }
        }
    }

    protected fun insertItem(item: Pack): NodeType {
        val parentNode: NodeType?
        val currentNode: NodeType
        val updateNode: NodeType? = findItem(item)

        if (updateNode == null) {
            parentNode = getLeafForInsert(item)
            currentNode = createNode(item)

            linkNewNode(currentNode, parentNode)

            return currentNode
        }
        updateNode.value = item
        return updateNode
    }

    protected fun deleteItem(item: Pack): NodeType? {
        /*TODO: test - deleteItem [невозможные ветки: - проверить, что они реально невозможны
           (->1 & 2) => что невозможно достичь deleteNode = parentNode?. со значением null (потому что inRightOf/inLeftOf)
           (->3) => странный кейс, когда удаляемое значение - null; такого вообще быть не должно
           ] */
        val parentNode: NodeType? // parent node of the node for deleting
        val deleteNode: NodeType?  // node for deleting
        var replaceNode: NodeType?   // node for relink on the place deleted node

        if (findItem(item) != null) return null

        parentNode = getParentByValue(item)
        if (parentNode != null) {
            deleteNode = when {
                item inRightOf parentNode -> parentNode.right //(->1)
                item inLeftOf parentNode -> parentNode.left   //(->2)
                else -> null
            }
        } else deleteNode = root
        if (deleteNode == null) throw NullPointerException("impossible case") // (->3)

        replaceNode = getNodeForReplace(deleteNode)
        replaceNode?.let {
            replaceNode = unLink(it, getParentByValue(it.value))
        } // if deleteNode doesn't have children => replaceNode = null

        if (parentNode != null) rebaseNode(deleteNode, parentNode, replaceNode)
        else root = rebaseNode(deleteNode, null, replaceNode)

        return deleteNode
    }

    protected abstract fun unLink(
        node: NodeType,
        parent: NodeType?
    ): NodeType

    protected abstract fun rebaseNode(
        node: NodeType,
        parent: NodeType?,
        replaceNode: NodeType?,
    ): NodeType? // return linked on the node place (null - if the replaceNode == null...)

    protected abstract fun linkNewNode(
        node: NodeType,
        parent: NodeType?,
    ): NodeType

    protected abstract fun createNode(item: Pack): NodeType

    fun find(obj: Pack): Pack? {
        //TODO test - find
        if (findItem(obj) == null) return null
        else return obj
    }

    abstract fun insert(item: Pack): Pack?

    /*Behaviour: null - means value not in tree; Pack - value was successfully deleted*/
    abstract fun delete(item: Pack): Pack?

    fun inOrder() {
        TODO("inOrder - implementation")
    }

    fun postOrder() {
        TODO("postOrder - implementation")
    }

    fun preOrder() {
        TODO("preOrder - implementation")
    }
}
