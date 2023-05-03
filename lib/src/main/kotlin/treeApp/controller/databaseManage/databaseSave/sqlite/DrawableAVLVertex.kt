package treeApp.controller.databaseManage.databaseSave.sqlite

import treeApp.controller.databaseManage.databaseSave.DrawableVertex

class DrawableAVLVertex<Pack : Comparable<Pack>>(
    override val value: Pack,
    override val x: Double = 0.0,
    override val y: Double = 0.0,
    val height: UInt,
) : DrawableVertex<Pack>
