package treelib.abstractTree

interface Node<Pack : Comparable<Pack>, SubNode : Node<Pack, SubNode>> {
    var value: Pack
    var left: SubNode?
    var right: SubNode?
}
