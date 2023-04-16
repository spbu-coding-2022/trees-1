package utils

import treelib.binTree.BINNode
import treelib.singleObjects.exceptions.BugInImplementException

class BINAnalyzer<Pack : Comparable<Pack>>(
    val assertMethod: (input: String) -> Unit = {
        throw BugInImplementException(it)
    }
) : Analyzer<Pack, BINNode<Pack>> {

    override fun checkTree(root: BINNode<Pack>) {
        checkInvariant(root)
    }

    private tailrec fun checkInvariant(node: BINNode<Pack>) {
        if ((node.left == null) && (node.right == null)) return

        node.right?.let {
            when {
                it.value == node.value -> {
                    assertMethod("parent.value == RightChild.value => [${node.value} == ${it.value}]")
                    return@checkInvariant
                }

                it.value < node.value -> {
                    assertMethod("parent.value > RightChild.value => [${node.value} > ${it.value}]")
                    return@checkInvariant
                }

                else -> {}
            }
        }

        node.left?.let {
            when {
                it.value == node.value -> {
                    assertMethod("parent.value == LeftChild.value => [${node.value} == ${it.value}]")
                    return@checkInvariant
                }

                it.value > node.value -> {
                    assertMethod("parent.value < LeftChild.value => [${node.value} < ${it.value}]")
                    return@checkInvariant
                }

                else -> {}
            }
        }
    }

}