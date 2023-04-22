package utils

import treelib.rbTree.RBNode
import treelib.rbTree.Markers
import treelib.commonObjects.exceptions.BugInImplementException

class RBAnalyzer<Pack : Comparable<Pack>>(
    override val assertMethod: (input: String) -> Unit = {
        throw BugInImplementException(it)
    }
) : Analyzer<Pack, RBNode<Pack>>() {
    /** Magic number for error := -9999999 -> just an impossible value **/
    private val errorMagicNumber = -9999999

    override fun checkTree(root: RBNode<Pack>) {
        if (root.color != Markers.BLACK) wrappedAssertMethod("The root isn't black!!!")
        checkInvariant(root)
    }

    private fun checkInvariant(node: RBNode<Pack>): Int {
        var leftBlackCount = 0
        var rightBlackCount = 0

        if ((node.right == null) && (node.left == null)) {
            if (node.color == Markers.RED) return 0
            else return 1
        }
        node.right?.let {
            when {
                it.value == node.value -> {
                    wrappedAssertMethod("parent.value == RightChild.value => [${node.value} == ${it.value}]")
                    return@checkInvariant errorMagicNumber
                }

                it.value < node.value -> {
                    wrappedAssertMethod("parent.value > RightChild.value => [${node.value} > ${it.value}]")
                    return@checkInvariant errorMagicNumber
                }

                (it.color == Markers.RED) && (node.color == Markers.RED) -> {
                    wrappedAssertMethod("parent.color == RED == RightChild.color => [parent - ${node.value} <color> RightChild - ${it.value}]")
                    return@checkInvariant errorMagicNumber
                }

                else -> {}
            }
        }

        node.left?.let {
            when {
                it.value == node.value -> {
                    wrappedAssertMethod("parent.value == LeftChild.value => [${node.value} == ${it.value}]")
                    return@checkInvariant errorMagicNumber
                }

                it.value > node.value -> {
                    wrappedAssertMethod("parent.value < LeftChild.value => [${node.value} < ${it.value}]")
                    return@checkInvariant errorMagicNumber
                }

                (it.color == Markers.RED) && (node.color == Markers.RED) -> {
                    wrappedAssertMethod("parent.color == RED == LeftChild.color => [parent - ${node.value} <color> LeftChild - ${it.value}]")
                    return@checkInvariant errorMagicNumber
                }

                else -> {}
            }
        }

        leftBlackCount = node.left?.let { return@let checkInvariant(it) } ?: 0
        rightBlackCount = node.right?.let { return@let checkInvariant(it) } ?: 0

        if (leftBlackCount < 0 || rightBlackCount < 0) return errorMagicNumber

        if (leftBlackCount != rightBlackCount) {
            wrappedAssertMethod(
                "Number of black nodes does not match in children: parent.value - ${node.value} =>[left - $leftBlackCount] != [right - $rightBlackCount]"
            )
            return errorMagicNumber
        }

        if (node.color == Markers.BLACK) return leftBlackCount + 1
        else return rightBlackCount
    }
}
