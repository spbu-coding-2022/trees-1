package treelib.abstractTree

abstract class TreeStruct<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>> {

    protected abstract var root: NodeType?

    protected fun getLeafForInsert(item: Pack): NodeType? {
        //TODO test - getParentByValue
        var currentNode = root
        while (true) {
            if (currentNode != null) {
                if (item > currentNode.value) {
                    if (currentNode.right != null) currentNode = currentNode.right
                    else return currentNode
                } else {
                    if (currentNode.left != null) currentNode = currentNode.left
                    else return currentNode
                }
            } else return null
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
        if (findItem(item) == null) return null // (2)

        if ((currentNode != null) && (currentNode.value == item)) return null

        while (true) {
            if (currentNode != null) {
                when {
                    currentNode.right != null -> if (currentNode.right!!.value == item) return currentNode
                    currentNode.left != null -> if (currentNode.left!!.value == item) return currentNode
                }
                if (item > currentNode.value) currentNode = currentNode.right
                else currentNode = currentNode.left

            } else return null // (1)
        }
    }

    protected infix fun Pack.inRightOf(node: NodeType?): Boolean {
        //TODO - test inInRightOf
        if ((node != null) && (node.right != null)) {
            if (node.right!!.value == this) return true
        }
        return false
    }

    protected infix fun Pack.inLeftOf(node: NodeType?): Boolean {
        //TODO - test inInLeftOf
        if ((node != null) && (node.left != null)) {
            if (node.left!!.value == this) return true
        }
        return false
    }

    private fun getRightMinNode(localRoot: NodeType): NodeType? {
        //TODO test - getLeafForDelete [проверить, что нет проблем с (->1)]
        /*
        null - means NodeType.right doesn't exist, another variant impossible
        */
        var currentNode: NodeType?
        if (localRoot.right != null) currentNode = localRoot.right
        else return null

        while (true) {
            if (currentNode != null) {
                if (currentNode.left == null) return currentNode
                else currentNode = currentNode.left
            } else return null //(->1)
        }
    }

    private fun getLeftMaxNode(localRoot: NodeType): NodeType? {
        //TODO test - getLeafLeftMax [проверить, что нет проблем с (->1)]
        /*
        null - means NodeType.left doesn't exist, another variant impossible
        */
        var currentNode: NodeType?
        if (localRoot.left != null) currentNode = localRoot.left
        else return null

        while (true) {
            if (currentNode != null) {
                if (currentNode.right == null) return currentNode
                else currentNode = currentNode.right
            } else return null //(->1)
        }
    }

    protected fun getNodeForReplace(localRoot: NodeType?): NodeType? {
        /* Behaviour: null - localRoot doesn't have children */
        if (localRoot != null) {
            val replaceNode = getRightMinNode(localRoot)
            if (replaceNode != null) return replaceNode
            else return getLeftMaxNode(localRoot)
        } else return null
    }

    protected fun findItem(obj: Pack): NodeType? {
        var currentNode = root
        if (root == null) {
            return null
        }
        while (true) {
            if (obj == currentNode?.value) return currentNode
            else {
                if (currentNode != null) {
                    if (obj > currentNode.value) currentNode = currentNode.right
                    else currentNode = currentNode.left
                } else return null
            }
        }
    }

    protected abstract fun insertItem(item: Pack): NodeType?

    protected abstract fun deleteItem(item: Pack): NodeType?

    protected abstract fun unLink(
        node: NodeType,
        parent: NodeType?
    ): NodeType

    protected abstract fun rebaseNode(
        //TODO linkLeafAsNode - [написал какой-то кринж, надо хорошо подумать, как это делать]
        node: NodeType,
        parent: NodeType?,
        replaceNode: NodeType?,
    ): NodeType? // return linked on the node place (null - if the replaceNode == null...)

    fun find(obj: Pack): Pack? {
        /* Behavior: if find(item) == true => replace value with item, otherwise create a new node */
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
