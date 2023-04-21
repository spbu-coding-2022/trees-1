package dbSave.neo4j

import treelib.singleObjects.Container
import treelib.singleObjects.Markers

fun main() {
    val a1 = DrawRBVertex(Container(Pair(5, 1)), Markers.BLACK)
    val a2 = DrawRBVertex(Container(Pair(4, 1)), Markers.BLACK)
    val a3 = DrawRBVertex(Container(Pair(6, 1)), Markers.BLACK)
    val a4 = DrawRBVertex(Container(Pair(7, 1)), Markers.BLACK)
    val a5 = DrawRBVertex(Container(Pair(2, 1)), Markers.BLACK)
    val inOrder = arrayOf(a5, a2, a1, a3, a4)
    val preOrder = arrayOf(a1, a2, a5, a3, a4)
    val p = Neo4jRepository()
    p.open("bolt://localhost:7687", "neo4j", "password")
    //p.saveChanges(preOrder, inOrder)
    val a = p.exportRBtree()
    val pre = a.first
    for (i in pre.indices)
        println(pre[i].value.key)

}