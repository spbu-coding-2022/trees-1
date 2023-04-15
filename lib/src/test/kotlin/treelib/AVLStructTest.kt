package treelib

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import treelib.avlTree.AVLNode
import treelib.avlTree.AVLStateContainer
import treelib.avlTree.AVLStruct

@DisplayName("Test: AVL Struct")
class AVLStructTest {
    val treeW = TreeStructWrapper<Int, AVLNode<Int>, AVLStateContainer<Int>, AVLStruct<Int>>()
    var classUnderTest = AVLStruct<Int>()

    @BeforeEach
    fun reInitClassUnderTest() {
        classUnderTest = AVLStruct()
    }
}