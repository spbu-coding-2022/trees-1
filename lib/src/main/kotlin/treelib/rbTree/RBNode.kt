package treelib.rbTree

import treelib.abstractTree.NodeParent
import treelib.singleObjects.Markers

class RBNode<Pack : Comparable<Pack>>(
    override var value: Pack,
    override var left: RBNode<Pack>?,
    override var right: RBNode<Pack>?,
    override var parent: RBNode<Pack>?,
    var color: Markers,
) : NodeParent<Pack, RBNode<Pack>>