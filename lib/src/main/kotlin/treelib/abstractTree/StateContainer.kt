package treelib.abstractTree

interface StateContainer<V : Comparable<V>, NodeType : Node<V, NodeType>> {
    val contentNode: NodeType?
}
