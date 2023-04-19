package treelib.rbTree

import treelib.abstractTree.Vertex
import treelib.singleObjects.Markers

open class RBVertex<Pack : Comparable<Pack>>(
    override val value: Pack,
    val color: Markers,
) : Vertex<Pack>()