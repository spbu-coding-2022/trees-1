package treelib.rbTree

import treelib.abstractTree.Node
import treelib.singleObjects.Markers

class RBNode<Pack : Comparable<Pack>>(
    override var value: Pack,
    override var left: RBNode<Pack>? = null,
    override var right: RBNode<Pack>? = null,
    var parent: RBNode<Pack>? = null,
    var color: Markers = Markers.RED,
) : Node<Pack, RBNode<Pack>>
