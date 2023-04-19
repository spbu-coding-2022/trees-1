package treelib.binTree

import treelib.abstractTree.Vertex

open class BINVertex<Pack : Comparable<Pack>>(override val value: Pack) : Vertex<Pack>()