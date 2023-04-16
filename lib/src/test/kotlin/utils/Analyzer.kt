package utils

import treelib.abstractTree.Node

interface Analyzer<
        Pack : Comparable<Pack>,
        NodeType : Node<Pack, NodeType>,
        > {
    fun checkTree(root: NodeType)
}