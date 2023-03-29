package treelib

import org.junit.jupiter.api.*
import kotlin.test.assertEquals

/*    BINStruct    */
import treelib.binTree.BINNode
import treelib.binTree.BINStruct

/*    AVLStruct    */
import treelib.avlTree.AVLNode
import treelib.avlTree.AVLStruct

/*    RBStruct    */
import treelib.rbTree.RBNode
import treelib.rbTree.RBStruct

@DisplayName("Test group: Structs testing")
class TreeStructsTest {
    //TESTS ONLY WITH Comparable = INT, otherwise - errors
    @Nested
    @DisplayName("Test: Binary Search Tree Struct")
    inner class BINStructTest {
        val treeW = TreeStructWrapper<Int, BINNode<Int>, BINStruct<Int>>()
        var classUnderTest = BINStruct<Int>()

        @BeforeEach
        fun reInitClassUnderTest() {
            classUnderTest = BINStruct()
        }
    }

    @Nested
    @DisplayName("Test: AVL Struct")
    inner class AVLStructTest {
        val treeW = TreeStructWrapper<Int, AVLNode<Int>, AVLStruct<Int>>()
        var classUnderTest = AVLStruct<Int>()

        @BeforeEach
        fun reInitClassUnderTest() {
            classUnderTest = AVLStruct()
        }
    }

    @Nested
    @DisplayName("Test: Red-Black Tree Struct")
    inner class RBStructTree {
        val treeW = TreeStructWrapper<Int, RBNode<Int>, RBStruct<Int>>()
        var classUnderTest = RBStruct<Int>()

        @BeforeEach
        fun reInitClassUnderTest() {
            classUnderTest = RBStruct()
        }
    }
}
