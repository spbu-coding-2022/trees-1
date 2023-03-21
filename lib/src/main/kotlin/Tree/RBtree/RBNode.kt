package RBtree

import AbstractTree.Node
import Single_Objects.Markers

class RBNode<Pack : Comparable<Pack>>(
    override var value: Pack?,
    override var left: RBNode<Pack>?,
    override var right: RBNode<Pack>?,
    var parent: RBNode<Pack>,
    var color: Markers,
) : Node<Pack, RBNode<Pack>>