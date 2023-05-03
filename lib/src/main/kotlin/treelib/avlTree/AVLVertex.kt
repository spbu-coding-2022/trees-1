package treelib.avlTree

import treelib.abstractTree.Vertex

class AVLVertex<Pack : Comparable<Pack>>(
    override val value: Pack,
    val height: UInt,
) : Vertex<Pack>()
