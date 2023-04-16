package utils

import treelib.abstractTree.Node

abstract class Analyzer<
        Pack : Comparable<Pack>,
        NodeType : Node<Pack, NodeType>,
        > {

    var message: String = ""

    protected abstract val assertMethod: (input: String) -> Unit

    protected fun wrappedAssertMethod(input: String) = assertMethod("$message$input")

    abstract fun checkTree(root: NodeType)
}