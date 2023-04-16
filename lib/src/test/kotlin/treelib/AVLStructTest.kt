package treelib

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import treelib.avlTree.AVLNode
import treelib.avlTree.AVLStateContainer
import treelib.avlTree.AVLStruct
import utils.TreeStructWrapper

@DisplayName("Test: AVL Struct")
class AVLStructTest {
    val treeW = TreeStructWrapper<Int, AVLNode<Int>, AVLStateContainer<Int>, AVLStruct<Int>>()
    var treeStruct = AVLStruct<Int>()

    @BeforeEach
    fun reInitClassUnderTest() {
        treeStruct = AVLStruct()
    }
}