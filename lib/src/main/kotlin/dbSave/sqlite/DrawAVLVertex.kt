package dbSave.sqlite

import dbSave.DrawVertex

class DrawAVLVertex<Pack: Comparable<Pack>>(
    override val value: Pack,
    override val x: Double,
    override val y: Double,
    val height: Int,
) : DrawVertex<Pack>
