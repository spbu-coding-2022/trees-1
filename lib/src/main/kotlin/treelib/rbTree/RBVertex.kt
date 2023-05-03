package treelib.rbTree

import treelib.abstractTree.Vertex

open class RBVertex<Pack : Comparable<Pack>>(
    override val value: Pack,
    val color: Markers,
) : Vertex<Pack>()
