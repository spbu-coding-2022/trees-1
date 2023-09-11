package utils

import treelib.binTree.BINNode

class BINAnalyzer<Pack : Comparable<Pack>>(
    override val assertMethod: (input: String) -> Unit = {
        throw InternalError(it)
    }
) : Analyzer<Pack, BINNode<Pack>>() {

    override fun checkTree(root: BINNode<Pack>) {
        checkInvariant(root)
    }

    private fun checkInvariant(node: BINNode<Pack>) {
        if ((node.left == null) && (node.right == null)) return

        node.right?.let {
            when {
                it.value == node.value -> {
                    wrappedAssertMethod("parent.value == RightChild.value => [${node.value} == ${it.value}]")
                    return@checkInvariant
                }

                it.value < node.value -> {
                    wrappedAssertMethod("parent.value > RightChild.value => [${node.value} > ${it.value}]")
                    return@checkInvariant
                }

                else -> {}
            }
        }

        node.left?.let {
            when {
                it.value == node.value -> {
                    wrappedAssertMethod("parent.value == LeftChild.value => [${node.value} == ${it.value}]")
                    return@checkInvariant
                }

                it.value > node.value -> {
                    wrappedAssertMethod("parent.value < LeftChild.value => [${node.value} < ${it.value}]")
                    return@checkInvariant
                }

                else -> {}
            }
        }
    }

}
