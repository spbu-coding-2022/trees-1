package databaseSave.jsonFormat

import databaseSave.DrawableVertex
import treelib.binTree.BINVertex

class DrawableBINVertex<Pack : Comparable<Pack>>(
    value: Pack,
    override val x: Double = 0.0,
    override val y: Double = 0.0
) : BINVertex<Pack>(value), DrawableVertex<Pack>

