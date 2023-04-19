package dbSave

interface DrawVertex<Pack : Comparable<Pack>> {
    val value: Pack
    val x: Double
    val y: Double
}
