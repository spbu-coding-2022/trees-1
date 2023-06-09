package treelib.abstractTree.balanced

import treelib.abstractTree.Node
import treelib.abstractTree.StateContainer

interface Balancer<
        Pack : Comparable<Pack>,
        NodeType : Node<Pack, NodeType>,
        State : StateContainer<Pack, NodeType>,
        > {

    fun balance(state: State): NodeType
}
