package treelib.avlTree

import treelib.abstractTree.Node

class AVLNode<Pack : Comparable<Pack>>(
    override var value: Pack,
    override var left: AVLNode<Pack>? = null,
    override var right: AVLNode<Pack>? = null,
    var height: UInt = 1U,
) : Node<Pack, AVLNode<Pack>>