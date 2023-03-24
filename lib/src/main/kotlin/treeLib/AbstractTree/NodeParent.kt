package treeLib.AbstractTree


interface NodeParent<Pack: Comparable<Pack>, SubNode: NodeParent<Pack, SubNode>>: Node<Pack, SubNode> {
    var parent: SubNode?
    override var right: SubNode?
    override var left: SubNode?
}