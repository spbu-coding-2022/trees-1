package databaseSave.neo4j

import databaseSave.DrawableVertex
import treelib.rbTree.Markers
import treelib.rbTree.RBVertex

class DrawableRBVertex<Pack : Comparable<Pack>>(
    value: Pack,
    color: Markers,
    override val x: Double = 0.0,
    override val y: Double = 0.0
) : RBVertex<Pack>(value, color), DrawableVertex<Pack>
