package treeLib.AbstractTree

interface StateContainer<V: Comparable<V>, NodeType: Node<V, NodeType>> {
    val contentNode: NodeType
}