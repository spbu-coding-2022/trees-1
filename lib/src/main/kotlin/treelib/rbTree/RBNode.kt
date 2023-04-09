package treelib.rbTree

import treelib.abstractTree.NodeParent
import treelib.singleObjects.Markers

class RBNode<Pack : Comparable<Pack>>(
    override var value: Pack,
    override var left: RBNode<Pack>? = null,
    override var right: RBNode<Pack>? = null,
    override var parent: RBNode<Pack>? = null,
    var color: Markers = Markers.RED,
) : NodeParent<Pack, RBNode<Pack>>
