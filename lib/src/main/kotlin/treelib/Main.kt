package treelib

import Controller
import treelib.rbTree.RBStruct
import treelib.singleObjects.Container


fun main() {
    // 25, 15, 10, 4, 12, 22, 18, 24, 50, 35, 31, 44, 70, 66, 90
    // 4, 10, 12, 15, 18, 22, 24, 25, 31, 35, 44, 50, 66, 70, 90

    /*

    val neo4jRep = Neo4jRepository()
    val a1 = DBNodeRB(Container(Pair(1, 25)))
    val a2 = DBNodeRB(Container(Pair(1, 15)))
    val a3 = DBNodeRB(Container(Pair(1, 10)))
    val a4 = DBNodeRB(Container(Pair(1, 4)))
    val a5 = DBNodeRB(Container(Pair(1, 12)))
    val a6 = DBNodeRB(Container(Pair(1, 22)))
    val a7 = DBNodeRB(Container(Pair(1, 18)))
    val a8 = DBNodeRB(Container(Pair(1, 24)))
    val a9 = DBNodeRB(Container(Pair(1, 50)))
    val a10 = DBNodeRB(Container(Pair(1, 35)))
    val a11 = DBNodeRB(Container(Pair(1, 31)))
    val a12 = DBNodeRB(Container(Pair(1, 44)))
    val a13 = DBNodeRB(Container(Pair(1, 70)))
    val a14 = DBNodeRB(Container(Pair(1, 66)))
    val a15 = DBNodeRB(Container(Pair(1, 90)))
    val preArr = arrayOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)
    val inArr = arrayOf(a4, a3, a5, a2, a7, a6, a8, a1, a11, a10, a12, a9, a14, a13, a15)
    neo4jRep.open("bolt://localhost:7687", "neo4j", "test-neo4j")
    neo4jRep.saveChanges(preArr, inArr)
     */

    val tree = RBStruct<Container<Int, Int>>()
    tree.insert(Container(Pair(25 , 1)))
    tree.insert(Container(Pair(15 , 1)))
    tree.insert(Container(Pair(50 , 1)))
    tree.insert(Container(Pair(10 , 1)))
    tree.insert(Container(Pair(22 , 1)))
    tree.insert(Container(Pair(35 , 1)))
    tree.insert(Container(Pair(70 , 1)))
    tree.insert(Container(Pair(4 , 1)))
    tree.insert(Container(Pair(12 , 1)))
    tree.insert(Container(Pair(18 , 1)))
    tree.insert(Container(Pair(24 , 1)))
    tree.insert(Container(Pair(31 , 1)))
    tree.insert(Container(Pair(44 , 1)))
    tree.insert(Container(Pair(66 , 1)))
    tree.insert(Container(Pair(90 , 1)))
    val controller = Controller()
    controller.saveTree(tree)
    tree.insert(Container(Pair(100, 1)))
    controller.saveTree(tree)
    controller.initTree()

    //neo4jRep.exportRBtree()

    //neo4jRep.close()
}

fun test() {
    val cont = Controller()
    cont.initTree()
}