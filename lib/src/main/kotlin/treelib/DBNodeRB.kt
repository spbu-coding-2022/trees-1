package treelib

import treelib.singleObjects.Markers


class DBNodeRB<Pack : Comparable<Pack>>(
    val value: Pack,
    val color: Markers = Markers.RED,
    val x: Double = 0.0,
    val y: Double = 0.0
)
