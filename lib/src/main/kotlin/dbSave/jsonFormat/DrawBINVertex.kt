package dbSave.jsonFormat

import treelib.binTree.BINVertex

class DrawBINVertex<Pack : Comparable<Pack>>(
    value: Pack,
    val x: Double = 0.0,
    val y: Double = 0.0
) : BINVertex<Pack>(value)