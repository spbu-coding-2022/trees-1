package treelib.rbTree

import treelib.abstractTree.balanced.BalancerParent


class RBBalancer<Pack : Comparable<Pack>>(private var root: RBNode<Pack>?) :
    BalancerParent<Pack, RBNode<Pack>, RBStateContainer<Pack>>() {

    init {
        root?.color = Markers.BLACK
    }

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

    override fun balance(stateContainer: RBStateContainer<Pack>): RBNode<Pack> {
        val node = stateContainer.contentNode
            ?: throw InternalError()
        val uncle = getUncle(node)
        when {
            /** node insertion case **/
            node.color == Markers.RED && node.right == null && node.left == null -> {
                var currentNode = node

                if (currentNode.parent?.color == Markers.RED && uncle?.color == Markers.RED) {
                    currentNode = afterInsert(node)
                }
                if (currentNode.parent?.color != Markers.RED) {
                    return getRoot(currentNode)
                }

                var parent =
                    currentNode.parent ?: throw IllegalStateException() // в данном случае родитель не может быть null
                when (parent) {
                    parent.parent?.left -> {
                        if (currentNode == parent.right) {
                            leftRotate(parent)
                            currentNode = parent
                        }
                        parent =
                            currentNode.parent?.parent ?: throw InternalError()
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
                        parent =
                            currentNode.parent?.parent ?: throw InternalError()
                        currentNode = leftRotate(parent)
                        currentNode.color = Markers.BLACK
                        currentNode.right?.color = Markers.RED
                        currentNode.left?.color = Markers.RED
                    }

                    else -> throw IllegalStateException() // невозможное условие выполнения
                }
                if (currentNode.parent == null)
                    root = currentNode
                return root ?: throw NullPointerException()
            }
            /** node removal cases **/
            node.color == Markers.RED && (node.right != null || node.left != null) -> {
                when {
                    /** black leaf removal case **/
                    node.right?.color == Markers.BLACK -> {
                        return firstCase(node, node.left)
                    }

                    node.left?.color == Markers.BLACK -> {
                        return firstCase(node, node.right)
                    }
                }

            }

            node.color == Markers.BLACK -> {
                return when {
                    /** red leaf removal case **/
                    (node.left == null && node.right == null) ||
                            (node.left == null && node.right?.color == Markers.RED && nodeIsLeaf(node.right)) ||
                            (node.right == null && node.left?.color == Markers.RED && nodeIsLeaf(node.left)) -> {
                        getRoot(node)
                    }
                    /** black leaf removal case **/
                    node.left == null || node.right == null -> {
                        firstCase(node, null)

                    }

                    else -> throw IllegalStateException()
                }
            }
        }
        throw IllegalStateException()
    }

    private fun afterInsert(node: RBNode<Pack>): RBNode<Pack> {
        var currentNode = node
        while (currentNode.parent?.color == Markers.RED) {
            val uncle = getUncle(currentNode)
            if (uncle?.color == Markers.RED) {
                currentNode.parent?.color = Markers.BLACK
                currentNode = currentNode.parent?.parent ?: throw InternalError()
                currentNode.color = Markers.RED
                uncle.color = Markers.BLACK
            } else if (uncle != null) {
                return currentNode
            }
        }
        root?.color = Markers.BLACK
        return currentNode
    }

    /** black node removal case **/
    private fun firstCase(parent: RBNode<Pack>?, node: RBNode<Pack>?): RBNode<Pack> {
        return when {
            parent == null && node == null -> throw NullPointerException()
            parent != null -> {
                when (parent.color) {
                    Markers.RED -> secondCase(parent, node)
                    Markers.BLACK -> thirdCase(parent, node)
                }
                getRoot(parent)
            }

            else -> getRoot(node ?: throw InternalError())
        }
    }

    /** parent is red **/
    private fun secondCase(parent: RBNode<Pack>, node: RBNode<Pack>?) {
        var brother = getBrother(parent, node) ?: throw InternalError()
        if (brother.color == Markers.RED)
            throw NullPointerException()

        if (brother.right?.color != Markers.RED && brother.left?.color != Markers.RED) {
            brother.color = Markers.RED
            parent.color = Markers.BLACK
            return
        }

        when (node) {
            parent.left -> {
                if (brother.right?.color == Markers.RED) {
                    leftRotate(parent)
                    brother.left?.color = Markers.RED
                    brother.right?.color = Markers.RED
                    brother.color = Markers.BLACK
                } else if (brother.left?.color == Markers.RED) {
                    brother = rightRotate(brother)
                    leftRotate(parent)
                    brother.left?.color = Markers.BLACK
                    brother.left?.color = Markers.BLACK
                    brother.color = Markers.RED
                } else {
                    throw IllegalStateException()
                }
            }

            parent.right -> {
                if (brother.left?.color == Markers.RED) {
                    rightRotate(parent)
                    brother.color = Markers.BLACK
                    brother.left?.color = Markers.RED
                    brother.right?.color = Markers.RED
                } else if (brother.right?.color == Markers.RED) {
                    brother = leftRotate(brother)
                    rightRotate(parent)
                    brother.color = Markers.RED
                    brother.left?.color = Markers.BLACK
                    brother.right?.color = Markers.BLACK
                } else {
                    throw IllegalStateException()
                }

            }

            else -> throw IllegalStateException()
        }
    }

    /** parent is black **/
    private fun thirdCase(parent: RBNode<Pack>, node: RBNode<Pack>?) {
        val brother = getBrother(parent, node) ?: throw InternalError()
        when (brother.color) {
            Markers.RED -> thirdCaseSubFirst(brother, parent)
            Markers.BLACK -> thirdCaseSubSecond(brother, parent)
        }
    }

    /** black parent and red brother **/
    private fun thirdCaseSubFirst(brother: RBNode<Pack>, parent: RBNode<Pack>) {
        when (brother) {
            brother.parent?.left -> {
                var rightBrotherSon = brother.right ?: throw InternalError()

                if (rightBrotherSon.right?.color != Markers.RED && rightBrotherSon.left?.color != Markers.RED) {
                    rightBrotherSon.color = Markers.RED
                    brother.color = Markers.BLACK
                    rightRotate(parent)
                    return
                }
                /** if the right son of the right son of the brother is red, then we make it the right son
                 * of the brother, and the right son of the brother is the left son of the new right son
                 * of the brother and repaint it red so that the following condition works **/

                if (rightBrotherSon.right?.color == Markers.RED) {
                    rightBrotherSon.color = Markers.RED
                    leftRotate(rightBrotherSon)

                    rightBrotherSon =
                        rightBrotherSon.parent ?: throw InternalError()
                    rightBrotherSon.color = Markers.BLACK
                }

                if (rightBrotherSon.left?.color == Markers.RED) {
                    rightBrotherSon.left?.color = Markers.BLACK
                    leftRotate(brother)
                    rightRotate(parent)
                }
            }

            brother.parent?.right -> {
                var leftBrotherSon = brother.left ?: throw NullPointerException()
                if (leftBrotherSon.right?.color != Markers.RED && leftBrotherSon.left?.color != Markers.RED) {
                    leftBrotherSon.color = Markers.RED
                    brother.color = Markers.BLACK
                    leftRotate(brother.parent ?: throw InternalError())
                    return
                }

                if (leftBrotherSon.left?.color == Markers.RED) {
                    rightRotate(leftBrotherSon)
                    leftBrotherSon.color = Markers.RED
                    leftBrotherSon =
                        leftBrotherSon.parent ?: throw InternalError()
                    leftBrotherSon.color = Markers.BLACK
                }

                if (leftBrotherSon.right?.color == Markers.RED) {
                    leftBrotherSon.right?.color = Markers.BLACK
                    rightRotate(brother)
                    leftRotate(parent)
                }
            }

            else -> throw IllegalStateException()
        }
    }

    /** black parent and black brother **/
    private fun thirdCaseSubSecond(brother: RBNode<Pack>, parent: RBNode<Pack>) {
        /** if the brother hasn't read children, restart
         * from the parent (the height in the subtree has decreased by 1) **/

        if (brother.left?.color != Markers.RED && brother.right?.color != Markers.RED) {
            brother.color = Markers.RED
            firstCase(parent.parent, parent)
            return
        }
        when {
            brother.left?.color == Markers.RED -> {
                brother.left?.color = Markers.BLACK
                if (brother == parent.left) {
                    rightRotate(parent)
                } else {
                    rightRotate(brother)
                    leftRotate(parent)
                }
            }

            brother.right?.color == Markers.RED -> {
                brother.right?.color = Markers.BLACK
                if (brother == parent.right) {
                    leftRotate(parent)
                } else {
                    leftRotate(brother)
                    rightRotate(parent)
                }

            }

            else -> throw IllegalStateException()
        }
    }

}
