package treelib

import treelib.singleObjects.Markers


class DBNodeRB<K: Comparable<K>, V>(val value: V,
                                    val key: K,
                                    val color: Markers =  Markers.RED,
                                    val x: Double = 0.0,
                                    val y: Double = 0.0) {



}
