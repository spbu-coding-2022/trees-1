package treeLib.AbstractTree

abstract class TreeStruct<T : Comparable<T>, NodeType : Node<T, NodeType>> {
    protected abstract var root: NodeType?  //TODO root - not a null

    fun find(obj: T): T?{
        TODO("Сделать реализацию поиска, дергать реализацию из локализированного класса")
    }

    fun inOrder(): MutableList<T> {
        val arrayNodes = mutableListOf<T>()
        var flagVisited = 0
        var current = root
        val parents = ArrayDeque<NodeType>()
        while(current != null) {
            while(current!!.left != null && flagVisited == 0) {
                parents.add(current)
                current = current.left
            }
            arrayNodes.add(current.value)
            if (current.right != null) {
                flagVisited = 0
                current = current.right
            }
            else {
                if (parents.isEmpty())
                    break
                flagVisited = 1
                current = parents.removeLast()
            }
        }
        return arrayNodes
    }

    fun postOrder(): MutableList<T>{
        val parents = ArrayDeque<NodeType>()
        val arrayNodes = mutableListOf<T>()
        var flagVisited = 0
        var current = root
        while(current != null) {
            while (current!!.left != null && flagVisited == 0) {
                parents.add(current)
                current = current.left
            }
            if (current.right != null && flagVisited != 2) {
                parents.add(current)
                current = current.right
                flagVisited = 0
            }
            else {
                arrayNodes.add(current.value)
                if (parents.isEmpty())
                    break
                val parent = parents.removeLast()
                if (parent.right == current) {
                    flagVisited = 2
                }
                current = parent
            }
        }
        return arrayNodes
    }

    fun preOrder(): MutableList<T>{
        val arrayNodes = mutableListOf<T>()
        var current: NodeType
        val queue = ArrayDeque<NodeType>()
        queue.add(root!!)
        while (queue.isNotEmpty()) {
            current = queue.removeLast()
            arrayNodes.add(current.value)
            if (current.right != null)
                queue.add(current.right!!)
            if (current.left != null)
                queue.add(current.left!!)
        }
        return arrayNodes
    }

    abstract fun insert(item: T)
    abstract fun delete(item: T)
}
