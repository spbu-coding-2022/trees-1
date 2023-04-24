package databaseSave.sqlite

import databaseSave.DrawableVertex

class DrawAVLVertex<Pack : Comparable<Pack>>(
    override val value: Pack,
    override val x: Double,
    override val y: Double,
    val height: Int,
) : DrawableVertex<Pack>
