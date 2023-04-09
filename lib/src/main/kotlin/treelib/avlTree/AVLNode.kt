package treelib.avlTree

import treelib.abstractTree.Node

class AVLNode<Pack : Comparable<Pack>>(
    override var value: Pack,
    override var left: AVLNode<Pack>?,
    override var right: AVLNode<Pack>?,
    var height: UInt = 1u,
) : Node<Pack, AVLNode<Pack>> {

}