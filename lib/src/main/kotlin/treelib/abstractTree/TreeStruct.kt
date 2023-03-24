package treelib.abstractTree

abstract class TreeStruct<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>> {

    protected abstract var root: NodeType?

    fun find(obj: Pack): Pack? {
        if (findItem(obj) == null) return null
        else return obj
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

    //    TODO поведение: если find(item) == true => заменить value на item, иначе создать новую ноду
    protected abstract fun insertItem(item: Pack): NodeType?

    abstract fun insert(item: Pack): Pack?

    abstract fun delete(item: Pack): Pack?

    protected abstract fun deleteItem(item: Pack): NodeType?

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
