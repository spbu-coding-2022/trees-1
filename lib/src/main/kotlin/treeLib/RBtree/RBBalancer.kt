package treeLib.RBtree

import treeLib.AbstractTree.Weighted.BalancerParent
import treeLib.Single_Objects.Markers

class RBBalancer<Pack: Comparable<Pack>>(private var root: RBNode<Pack>?): BalancerParent<Pack, RBNode<Pack>>() {

    private fun getUncle(node: RBNode<Pack>): RBNode<Pack>? {
        val parent = node.parent
        return when (parent?.parent?.left) {
            parent -> parent?.parent?.right
            else -> parent?.parent?.left
        }
    }

    private fun getBrother(parent: RBNode<Pack>?, node: RBNode<Pack>?): RBNode<Pack>? {
        return if (parent?.right == node)
            parent?.left
        else
            parent?.right
    }

    private fun getRoot(node: RBNode<Pack>): RBNode<Pack> {
        var currentNode = node
        while (currentNode.parent != null)
            currentNode = currentNode.parent ?: throw NullPointerException()
        root = currentNode
        root?.color = Markers.BLACK
        return currentNode
    }
    private fun nodeIsLeaf(node: RBNode<Pack>?): Boolean {
        if (node == null)
            throw NullPointerException()
        return node.right == null && node.left == null
    }

    override fun balance(node: RBNode<Pack>): RBNode<Pack> {
        val uncle = getUncle(node)
        val brother = getBrother(node.parent, node)
        when {
            // возможно только при вставке листа или если у рута был один красный сын и мы удалили рут
            node.color == Markers.RED  && node.right == null && node.left == null &&
            brother?.color != Markers.BLACK && brother?.right?.color != Markers.BLACK->
            {
                var currentNode = node

                if (uncle?.color != Markers.BLACK) {
                    currentNode = afterInsert(node)
                }
                if (currentNode.parent?.color != Markers.RED) {
                    return getRoot(currentNode)
                }
                var parent = currentNode.parent ?: throw NullPointerException()
                when (parent) {
                    parent.parent?.left -> {
                        if (currentNode == parent.right) {
                            leftRotate(parent)
                            currentNode = parent
                        }
                        parent = currentNode.parent ?: throw NullPointerException()
                        currentNode = rightRotate(parent)
                        currentNode.color = Markers.BLACK
                        currentNode.right?.color = Markers.RED
                        currentNode.left?.color = Markers.RED
                    }
                    parent.parent?.right -> {
                        if (currentNode == parent.left) {
                            rightRotate(parent)
                            currentNode = parent
                        }
                        parent = currentNode.parent ?: throw NullPointerException()
                        currentNode = leftRotate(parent)
                        currentNode.color = Markers.BLACK
                        currentNode.right?.color = Markers.RED
                        currentNode.left?.color = Markers.RED
                    }
                    else -> throw NullPointerException()
                }
                if (currentNode.parent == null)
                    root = currentNode
                return root ?: throw NullPointerException()
            }
            // учитывая прошлый случай,возможно только если удаляли черную вершину с одним красным сыном листом,заменили
            // ее на сына и передали сына в качестве родителя
            node.color == Markers.RED && (node.right != null || node.left != null) ->
            {
                when {
                    // удалили черную вершину, у которой один красный сын (черного сына быть не может)
                    // или же удалили черную вершину, у которой два сына - красные листы и на ее место
                    // место поставили  лист, его же пеоедали в качестве родитедя
                    node.left?.color == Markers.RED -> {
                        when (node.right) {
                            null -> node.color = Markers.BLACK
                            else -> node.left?.color = Markers.BLACK
                        }
                        return getRoot(node)
                    }
                    node.right?.color == Markers.RED -> {
                        node.right?.color = Markers.RED // кинуть в случае чего экс
                        return getRoot(node)
                    }
                    // удалили черный лист
                    node.right?.color == Markers.BLACK -> {
                        return firstCase(node, node.left)
                    }
                    node.left?.color == Markers.BLACK -> {
                        return firstCase(node, node.right)
                    }
                }

            }
            node.color == Markers.BLACK ->
            {
                when {
                    // удалили красный лист
                    (node.left == null && node.right == null) ||
                            (node.left == null && node.right?.color == Markers.RED && nodeIsLeaf(node.right)) ||
                            (node.right == null && node.left?.color == Markers.RED && nodeIsLeaf(node.left)) ->
                    {
                        return root ?: throw NullPointerException() // return getRoot(node)
                    }
                    // удалили черный лист
                    node.left == null -> {
                        return firstCase(node, null)

                    }
                    node.right == null -> {
                        return firstCase(node, null)

                    }

                }

            }
        }
        throw NullPointerException()
    }
    private fun afterInsert(node: RBNode<Pack>): RBNode<Pack> {
        var currentNode = node
        while (currentNode.parent?.color == Markers.RED) {
            val uncle = getUncle(currentNode)
            if (uncle?.color == Markers.RED) {
                currentNode.parent?.color = Markers.BLACK
                currentNode = currentNode.parent?.parent ?: throw NullPointerException()
                currentNode.color = Markers.RED
                uncle.color = Markers.BLACK
            }
            else if(uncle != null){
                return currentNode
            }
        }
        root?.color = Markers.BLACK
        return currentNode
    }

    private fun firstCase(parent: RBNode<Pack>?, node: RBNode<Pack>?): RBNode<Pack> {
        when {
            parent == null && node == null -> throw NullPointerException()
            parent != null -> {
                when (parent.color) {
                    Markers.RED -> secondCase(parent, node)
                    Markers.BLACK -> thirdCase(parent, node)
                }
                return getRoot(parent)
            }
            else -> return getRoot(node ?: throw NullPointerException())
        }
    }

    // родитель вершины - красный, son - корень дерева с удаленной черной вершиной (поддерева node)
    private fun secondCase(parent: RBNode<Pack>, node: RBNode<Pack>?) {
        var brother = getBrother(parent, node) ?: throw NullPointerException()
        //  у красной вершины могут быть только черные дети
        if (brother.color == Markers.RED)
            throw NullPointerException()
        // не зависит от того с какой стороны расположены сын и брат
        if (brother.right?.color != Markers.RED && brother.left?.color != Markers.RED) {
            brother.color = Markers.RED
            parent.color = Markers.BLACK
            return
        }

        when (node) {
            parent.left ->
            {
                if (brother.left?.color == Markers.RED) {
                    brother = rightRotate(brother)
                    leftRotate(parent)
                    brother.left?.color = Markers.BLACK
                    brother.left?.color = Markers.BLACK
                    brother.color = Markers.RED
                }
                else if (brother.right?.color == Markers.RED) {
                    leftRotate(parent)
                    brother.left?.color = Markers.RED
                    brother.right?.color = Markers.RED
                    brother.color = Markers.BLACK
                }
                else {
                    throw NullPointerException()
                }
            }
            parent.right ->
            {
                if (brother.right?.color == Markers.RED) {
                    brother = leftRotate(brother)
                    rightRotate(parent)
                    brother.color = Markers.RED
                    brother.left?.color = Markers.BLACK
                    brother.right?.color = Markers.BLACK
                }
                else if (brother.left?.color == Markers.RED) {
                    rightRotate(parent)
                    brother.color = Markers.BLACK
                    brother.left?.color = Markers.RED
                    brother.right?.color = Markers.RED

                }
                else {
                    throw NullPointerException()
                }

            }
            else -> throw NullPointerException()
        }
    }
    // родитель вершины - черный
    private fun thirdCase(parent: RBNode<Pack>, node: RBNode<Pack>?) {
        val brother = getBrother(parent, node) ?: throw NullPointerException()
        when (brother.color) {
            Markers.RED -> thirdCaseSubFirst(brother, parent)
            Markers.BLACK -> thirdCaseSubSecond(brother, parent)
        }
    }
    // родитель - черный, брат - красный
    private fun thirdCaseSubFirst(brother: RBNode<Pack>, parent: RBNode<Pack>) {
        when (brother) {
            brother.parent?.left ->
            {
                var rightBrotherSon = brother.right ?: throw NullPointerException()

                if (rightBrotherSon.right?.color != Markers.RED && rightBrotherSon.left?.color != Markers.RED) {
                    rightBrotherSon.color = Markers.RED
                    //brother.color = Markers.RED
                    brother.color = Markers.BLACK
                    rightRotate(parent)
                    return
                }
                // если правый сын правого сына брата - красный, то делаем его правым сыном брата, а правого сына брата
                // левым сыном нового правого сына брата и перекрашиваем его в красный, чтоб сработало след условие
                if (rightBrotherSon.right?.color == Markers.RED) {
                    rightBrotherSon.color = Markers.RED
                    leftRotate(rightBrotherSon)

                    rightBrotherSon = rightBrotherSon.parent ?: throw NullPointerException()
                    rightBrotherSon.color = Markers.BLACK
                }

                if (rightBrotherSon.left?.color == Markers.RED) {
                    rightBrotherSon.left?.color = Markers.BLACK
                    leftRotate(brother)
                    rightRotate(parent)
                }
            }
            brother.parent?.right ->
            {
                var leftBrotherSon = brother.left ?: throw NullPointerException()
                if (leftBrotherSon.right?.color != Markers.RED && leftBrotherSon.left?.color != Markers.RED) {
                    leftBrotherSon.color = Markers.RED
                    brother.color = Markers.BLACK
                    leftRotate(brother.parent ?: throw NullPointerException())
                    return
                }

                if (leftBrotherSon.left?.color == Markers.RED) {
                    rightRotate(leftBrotherSon)
                    leftBrotherSon.color = Markers.RED
                    leftBrotherSon = leftBrotherSon.parent ?: throw NullPointerException()
                    leftBrotherSon.color = Markers.BLACK
                }

                if (leftBrotherSon.right?.color == Markers.RED) {
                    leftBrotherSon.right?.color = Markers.BLACK
                    rightRotate(brother)
                    leftRotate(parent)
                }
            }
            else -> throw NullPointerException()
        }
    }

    // родитель - черный, брат - черный
    private fun thirdCaseSubSecond(brother: RBNode<Pack>, parent: RBNode<Pack>) {
        // если у брата нет красных детей, запускаем алгоритм заново от родителя, тк высота в поддереве уменьшилась на 1
        if (brother.left?.color != Markers.RED && brother.right?.color != Markers.RED) {
            brother.color = Markers.RED
            firstCase(parent.parent, parent)
            return
        }
        when {
            brother.left?.color == Markers.RED ->
            {
                brother.left?.color = Markers.BLACK
                if (brother == parent.left) {
                    rightRotate(parent)
                }
                else {
                    rightRotate(brother)
                    leftRotate(parent)
                }
            }
            brother.right?.color == Markers.RED ->
            {
                brother.right?.color = Markers.BLACK
                if (brother == parent.right) {
                    leftRotate(parent)
                }
                else {
                    leftRotate(brother)
                    rightRotate(parent)
                }

            }
            else -> throw NullPointerException()
        }
    }

}
