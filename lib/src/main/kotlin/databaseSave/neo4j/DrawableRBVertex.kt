package databaseSave.neo4j

import treelib.rbTree.RBVertex
import treelib.rbTree.Markers

class DrawableRBVertex<Pack : Comparable<Pack>>(
    value: Pack,
    color: Markers,
    val x: Double = 0.0,
    val y: Double = 0.0
) : RBVertex<Pack>(value, color)
