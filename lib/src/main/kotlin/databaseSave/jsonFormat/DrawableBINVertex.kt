package databaseSave.jsonFormat

import treelib.binTree.BINVertex

class DrawableBINVertex<Pack : Comparable<Pack>>(
    value: Pack,
    val x: Double = 0.0,
    val y: Double = 0.0
) : BINVertex<Pack>(value)