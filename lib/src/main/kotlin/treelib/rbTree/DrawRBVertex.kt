package treelib.rbTree

import treelib.singleObjects.Markers

class DrawRBVertex<Pack : Comparable<Pack>>(
    value: Pack,
    color: Markers,
    val x: Double = 0.0,
    val y: Double = 0.0
) : RBVertex<Pack>(value, color)