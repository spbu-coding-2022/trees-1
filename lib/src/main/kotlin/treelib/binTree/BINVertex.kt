package treelib.binTree

import treelib.abstractTree.Vertex

class BINVertex<Pack: Comparable<Pack>>(override val value: Pack): Vertex<Pack>()