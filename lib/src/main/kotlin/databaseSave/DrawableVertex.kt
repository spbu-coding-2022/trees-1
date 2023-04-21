package databaseSave

interface DrawableVertex<Pack : Comparable<Pack>> {
    val value: Pack
    val x: Double
    val y: Double
}
