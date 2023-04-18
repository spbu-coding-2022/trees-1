package treelib.dbSave.avlSQLite

import treelib.controller.DrawVertex

class AVLDrawVertex<Pack: Comparable<Pack>>(
    override val value: Pack,
    override val x: Double,
    override val y: Double,
    val height: Int,
) : DrawVertex<Pack>
