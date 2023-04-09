package treelib

import org.junit.jupiter.api.*
import kotlin.test.assertEquals

/*    BINStruct    */
import treelib.binTree.BINNode
import treelib.binTree.BINStruct

/*    AVLStruct    */
import treelib.avlTree.AVLNode
import treelib.avlTree.AVLStateContainer
import treelib.avlTree.AVLStruct
import treelib.binTree.BINStateContainer

/*    RBStruct    */
import treelib.rbTree.RBNode
import treelib.rbTree.RBStateContainer
import treelib.rbTree.RBStruct

@DisplayName("Test group: Structs testing")
class TreeStructsTest {
    //TESTS ONLY WITH Comparable = INT, otherwise - errors
    @Nested
    @DisplayName("Test: Binary Search Tree Struct")
    inner class BINStructTest {
        val treeW = TreeStructWrapper<Int, BINNode<Int>, BINStateContainer<Int>, BINStruct<Int>>()
        var classUnderTest = BINStruct<Int>()

        @BeforeEach
        fun reInitClassUnderTest() {
            classUnderTest = BINStruct()
        }
        @Test
        fun `test delete root`() {
            val num = mutableListOf(5, 3, 7, 1, 9, -1, 4 ,2, 0, 6)
            for (i in num) {
                classUnderTest.insert(i)
            }
            classUnderTest.delete(5)
            val root = treeW.getPrivateNode(classUnderTest)?.value

            assertEquals(expected = 6, actual = root)
        }

        @Test
        fun `test insert`() {
            val num = mutableListOf(1, 2, 3, 4, 5, 8)
            for (i in num) {
                classUnderTest.insert(i)
            }

            val additional_num = mutableListOf(1, 2, 3, 5, 7, 8, 11)
            for (i in additional_num) {
                classUnderTest.insert(i)
            }

            val root = treeW.getPrivateNode(classUnderTest)?.value
            val node_2 = treeW.getPrivateNode(classUnderTest)?.right?.value
            val node_null = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_3 = treeW.getPrivateNode(classUnderTest)?.right?.right?.value
            val node_null1 = treeW.getPrivateNode(classUnderTest)?.right?.left?.value
            val node_4 = treeW.getPrivateNode(classUnderTest)?.right?.right?.right?.value

            assertEquals(expected = root, actual = 1)
            assertEquals(expected = node_null, actual = null)
            assertEquals(expected = node_4, actual = 4)
            assertEquals(expected = node_2, actual = 2)
            assertEquals(expected = node_3, actual = 3)
            assertEquals(expected = node_null1, actual = null)
        }

        @Test
        fun `test find ordinary`() {
            val num = mutableListOf(2, 3, 1, 4, 5 ,10)

            assertEquals(expected = classUnderTest.find(2), actual = null)

            for (i in num) {
                classUnderTest.insert(i)
            }

            assertEquals(expected = classUnderTest.find(2), actual = 2)
        }

        @Test
        fun `test find null`() {
            val num = mutableListOf(1)
            classUnderTest.insert(num[0])

            assertEquals(classUnderTest.find(2), null)

        }

        @Test
        fun `test find root`() {
            val num = mutableListOf(1)
            classUnderTest.insert(num[0])

            assertEquals(classUnderTest.find(1), 1)
        }

        @Test
        fun `test insert and delete root`() {
            val num = mutableListOf(1, 2)
            for (i in num) {
                classUnderTest.insert(i)
            }

            classUnderTest.delete(1)

            val additional_num = mutableListOf(1, 2, 11)
            for (i in additional_num) {
                classUnderTest.insert(i)
            }

            val root = treeW.getPrivateNode(classUnderTest)?.value
            val node_1 = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_11 = treeW.getPrivateNode(classUnderTest)?.right?.value


            assertEquals(expected = root, actual = 2)
            assertEquals(expected = node_1, actual = 1)
            assertEquals(expected = node_11, actual = 11)
        }

        @Test
        fun `test delete nonexistent value right`() {
            val num = mutableListOf(5, 6)
            for (value in num) {
                classUnderTest.insert(value)
            }
            classUnderTest.delete(6)

            val root = treeW.getPrivateNode(classUnderTest)?.value
            val node_null_left = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_null_right = treeW.getPrivateNode(classUnderTest)?.right?.value

            assertEquals(expected = null, actual = node_null_left)
            assertEquals(expected = null, actual = node_null_right)
            assertEquals(expected = 5, actual = root)
        }

        @Test
        fun `test delete nonexistent value left`() {
            val num = mutableListOf(6, 5)
            for (value in num) {
                classUnderTest.insert(value)
            }
            classUnderTest.delete(5)

            val root = treeW.getPrivateNode(classUnderTest)?.value
            val node_null_left = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_null_right = treeW.getPrivateNode(classUnderTest)?.right?.value

            assertEquals(expected = null, actual = node_null_left)
            assertEquals(expected = null, actual = node_null_right)
            assertEquals(expected = 6, actual = root)
        }

        @Test
        fun `test delete no child root`(){
            val num = mutableListOf(3)
            for (i in num) {
                classUnderTest.insert(i)
            }
            classUnderTest.delete(3)

            val node_null_left = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_null_right = treeW.getPrivateNode(classUnderTest)?.right?.value
            val root = treeW.getPrivateNode(classUnderTest)?.value

            assertEquals(expected = null, actual = node_null_left)
            assertEquals(expected = null, actual = node_null_right)
            assertEquals(expected = null, actual = root)
        }

        @Test
        fun `test delete no child right`(){
            val num = mutableListOf(3, 10, 15)
            for (i in num) {
                classUnderTest.insert(i)
            }
            classUnderTest.delete(15)

            val node_10 = treeW.getPrivateNode(classUnderTest)?.right?.value
            val node_null_left = treeW.getPrivateNode(classUnderTest)?.right?.left?.value
            val node_null_right = treeW.getPrivateNode(classUnderTest)?.right?.right?.value
            val root = treeW.getPrivateNode(classUnderTest)?.value

            
            assertEquals(expected = 10, actual = node_10)
            assertEquals(expected = null, actual = node_null_left)
            assertEquals(expected = null, actual = node_null_right)
            assertEquals(expected = 3, actual = root)
        }

        @Test
        fun `test delete no child left`(){
            val num = mutableListOf(15, 10, 3)
            for (i in num) {
                classUnderTest.insert(i)
            }
            classUnderTest.delete(3)

            val node_10 = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_null_left = treeW.getPrivateNode(classUnderTest)?.left?.left?.value
            val node_null_right = treeW.getPrivateNode(classUnderTest)?.left?.right?.value
            val root = treeW.getPrivateNode(classUnderTest)?.value


            assertEquals(expected = 10, actual = node_10)
            assertEquals(expected = null, actual = node_null_left)
            assertEquals(expected = null, actual = node_null_right)
            assertEquals(expected = 15, actual = root)
        }

        @Test
        fun `test delete empty tree`() {
            classUnderTest.delete(3)

            val node_null_left = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_null_right = treeW.getPrivateNode(classUnderTest)?.right?.value
            val root = treeW.getPrivateNode(classUnderTest)?.value

            assertEquals(expected = null, actual = node_null_left)
            assertEquals(expected = null, actual = node_null_right)
            assertEquals(expected = null, actual = root)
        }

        @Test
        fun `test delete in one root tree`() {
            classUnderTest.insert(1)
            classUnderTest.delete(3)

            val node_null_left = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_null_right = treeW.getPrivateNode(classUnderTest)?.right?.value
            val root = treeW.getPrivateNode(classUnderTest)?.value

            assertEquals(expected = null, actual = node_null_left)
            assertEquals(expected = null, actual = node_null_right)
            assertEquals(expected = 1, actual = root)
        }

        @Test
        fun `test delete one child left`(){
            val num = mutableListOf(3, 2, 1, 5)
            for (value in num) {
                classUnderTest.insert(value)
            }
            classUnderTest.delete(2)

            val node_1 = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_null_left = treeW.getPrivateNode(classUnderTest)?.left?.left?.value
            val node_null_right = treeW.getPrivateNode(classUnderTest)?.left?.right?.value
            val root = treeW.getPrivateNode(classUnderTest)?.value

            assertEquals(expected = 1 , actual = node_1)
            assertEquals(expected = null , actual = node_null_left)
            assertEquals(expected = null , actual = node_null_right)
            assertEquals(expected = 3, actual = root)
        }

        @Test
        fun `test delete one child right`(){
            val num = mutableListOf(3, 1, 5, 6)
            for (value in num) {
                classUnderTest.insert(value)
            }
            classUnderTest.delete(5)

            val node_6 = treeW.getPrivateNode(classUnderTest)?.right?.value
            val node_null_left = treeW.getPrivateNode(classUnderTest)?.right?.left?.value
            val node_null_right = treeW.getPrivateNode(classUnderTest)?.right?.right?.value
            val root = treeW.getPrivateNode(classUnderTest)?.value

            assertEquals(expected = 6 , actual = node_6)
            assertEquals(expected = null , actual = node_null_left)
            assertEquals(expected = null , actual = node_null_right)
            assertEquals(expected = 3, actual = root)
        }

        @Test
        fun `test delete one child root`() {
            val num = mutableListOf(3, 6)
            for (value in num) {
                classUnderTest.insert(value)
            }
            classUnderTest.delete(3)

            val node_6 = treeW.getPrivateNode(classUnderTest)?.value
            val node_null_left = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_null_right = treeW.getPrivateNode(classUnderTest)?.right?.value

            assertEquals(expected = 6 , actual = node_6)
            assertEquals(expected = null , actual = node_null_left)
            assertEquals(expected = null , actual = node_null_right)
        }

        @Test
        fun `test delete one child with family`() {
            val num = mutableListOf(10, 7, 13, 6, 3, 1, 5, 2, 4, 15)
            for (value in num) {
                classUnderTest.insert(value)
            }
            classUnderTest.delete(7)
            val node_6 = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_3 = treeW.getPrivateNode(classUnderTest)?.left?.left?.value
            val node_null_right = treeW.getPrivateNode(classUnderTest)?.left?.right?.value
            val node_1 = treeW.getPrivateNode(classUnderTest)?.left?.left?.left?.value
            val node_2 = treeW.getPrivateNode(classUnderTest)?.left?.left?.left?.right?.value
            val node_5 = treeW.getPrivateNode(classUnderTest)?.left?.left?.right?.value
            val node_4 = treeW.getPrivateNode(classUnderTest)?.left?.left?.right?.left?.value
            val root = treeW.getPrivateNode(classUnderTest)?.value

            assertEquals(expected = 6 , actual = node_6)
            assertEquals(expected = 3 , actual = node_3)
            assertEquals(expected = null , actual = node_null_right)
            assertEquals(expected = 1 , actual = node_1)
            assertEquals(expected = 2 , actual = node_2)
            assertEquals(expected = 5 , actual = node_5)
            assertEquals(expected = 4 , actual = node_4)
            assertEquals(expected = 10, actual = root)
        }

        @Test
        fun `test delete two child only three element`() {
            val num = mutableListOf(2, 1 ,3)
            for (i in num) {
                classUnderTest.insert(i)
            }
            classUnderTest.delete(2)

            val root = treeW.getPrivateNode(classUnderTest)?.value
            val node_1 = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_null_left1 = treeW.getPrivateNode(classUnderTest)?.left?.left?.value
            val node_null_right1 = treeW.getPrivateNode(classUnderTest)?.left?.right?.value
            val node_null_right_root = treeW.getPrivateNode(classUnderTest)?.right?.value

            assertEquals(expected = root, actual = 3)
            assertEquals(expected = node_1, actual = 1)
            assertEquals(expected = node_null_left1, actual = null)
            assertEquals(expected = node_null_right1, actual = null)
            assertEquals(expected = node_null_right_root, actual = null)
        }

        @Test
        fun `test delete two child without family`() {
            val num = mutableListOf(10, 7, 5, 4, 6)
            for (i in num) {
                classUnderTest.insert(i)
            }
            classUnderTest.delete(7)

            val root = treeW.getPrivateNode(classUnderTest)?.value
            val node_5 = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_4 = treeW.getPrivateNode(classUnderTest)?.left?.left?.value
            val node_6 = treeW.getPrivateNode(classUnderTest)?.left?.right?.value
            val node_null_left4 = treeW.getPrivateNode(classUnderTest)?.left?.left?.left?.value
            val node_null_right4 = treeW.getPrivateNode(classUnderTest)?.left?.left?.right?.value
            val node_null_left6 = treeW.getPrivateNode(classUnderTest)?.left?.right?.left?.value
            val node_null_right6 = treeW.getPrivateNode(classUnderTest)?.left?.right?.left?.value

            assertEquals(expected = root, actual = 10)
            assertEquals(expected = node_5, actual = 5)
            assertEquals(expected = node_4, actual = 4)
            assertEquals(expected = node_6, actual = 6)
            assertEquals(expected = node_null_left4, actual = null)
            assertEquals(expected = node_null_right4, actual = null)
            assertEquals(expected = node_null_left6, actual = null)
            assertEquals(expected = node_null_right6, actual = null)
        }

        @Test
        fun `test double delete`() {
            val num = mutableListOf(1, 2)
            for (value in num) {
                classUnderTest.insert(value)
            }
            classUnderTest.delete(2)
            classUnderTest.delete(2)

            val root = treeW.getPrivateNode(classUnderTest)?.value

            assertEquals(expected = root, actual = 1)
        }

        @Test
        fun `test double delete root`() {
            val num = mutableListOf(2)
            for (value in num) {
                classUnderTest.insert(value)
            }
            classUnderTest.delete(2)
            classUnderTest.delete(2)

            val root = treeW.getPrivateNode(classUnderTest)?.value

            assertEquals(expected = root, actual = null)
        }

        @Test
        fun `test two child double delete and delete root`() {
            val num = mutableListOf(6, 8, 10, 7)
            for (i in num) {
                classUnderTest.insert(i)
            }

            classUnderTest.delete(6)
            classUnderTest.delete(7)

            val root = treeW.getPrivateNode(classUnderTest)?.value
            val node_null_left = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_10 = treeW.getPrivateNode(classUnderTest)?.right?.value
            val node_null_right10 = treeW.getPrivateNode(classUnderTest)?.right?.right?.value
            val node_null_left10 = treeW.getPrivateNode(classUnderTest)?.right?.left?.value

            assertEquals(expected = root, actual = 8)
            assertEquals(expected = node_10, actual = 10)
            assertEquals(expected = node_null_left10, actual = null)
            assertEquals(expected = node_null_right10, actual = null)
            assertEquals(expected = node_null_left, actual = null)
        }

        @Test
        fun `test two child delete min element in right tree`() {
            val num = mutableListOf(6, 8, 10, 7, 12, 9)
            for (i in num) {
                classUnderTest.insert(i)
            }

            classUnderTest.delete(8)

            val root = treeW.getPrivateNode(classUnderTest)?.value
            val node_null_left = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_9 = treeW.getPrivateNode(classUnderTest)?.right?.value
            val node_7 = treeW.getPrivateNode(classUnderTest)?.right?.left?.value
            val node_10 = treeW.getPrivateNode(classUnderTest)?.right?.right?.value
            val node_12 = treeW.getPrivateNode(classUnderTest)?.right?.right?.right?.value
            val node_null = treeW.getPrivateNode(classUnderTest)?.right?.right?.left?.value

            assertEquals(expected = root, actual = 6)
            assertEquals(expected = node_9, actual = 9)
            assertEquals(expected = node_null, actual = null)
            assertEquals(expected = node_null_left, actual = null)
            assertEquals(expected = node_7, actual = 7)
            assertEquals(expected = node_10, actual = 10)
            assertEquals(expected = node_12, actual = 12)
        }

        @Test
        fun `test two child delete min element in right tree for root`() {
            val num = mutableListOf(8, 10, 7, 12, 9)
            for (i in num) {
                classUnderTest.insert(i)
            }

            classUnderTest.delete(8)

            val root = treeW.getPrivateNode(classUnderTest)?.value
            val node_7 = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_10 = treeW.getPrivateNode(classUnderTest)?.right?.value
            val node_12 = treeW.getPrivateNode(classUnderTest)?.right?.right?.value
            val node_null = treeW.getPrivateNode(classUnderTest)?.right?.left?.value

            assertEquals(expected = root, actual = 9)
            assertEquals(expected = node_null, actual = null)
            assertEquals(expected = node_7, actual = 7)
            assertEquals(expected = node_10, actual = 10)
            assertEquals(expected = node_12, actual = 12)
        }

        @Test
        fun `test two child delete min element in right tree for rightmost element`() {
            val num = mutableListOf(8, 10, 7, 12, 13, 14)
            for (i in num) {
                classUnderTest.insert(i)
            }

            classUnderTest.delete(8)

            val root = treeW.getPrivateNode(classUnderTest)?.value
            val node_7 = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_12 = treeW.getPrivateNode(classUnderTest)?.right?.value
            val node_13 = treeW.getPrivateNode(classUnderTest)?.right?.right?.value
            val node_14 = treeW.getPrivateNode(classUnderTest)?.right?.right?.right?.value
            val node_null = treeW.getPrivateNode(classUnderTest)?.right?.left?.value

            assertEquals(expected = root, actual = 10)
            assertEquals(expected = node_null, actual = null)
            assertEquals(expected = node_7, actual = 7)
            assertEquals(expected = node_13, actual = 13)
            assertEquals(expected = node_14, actual = 14)
            assertEquals(expected = node_12, actual = 12)
        }

        @Test
        fun `test two child delete min element in right tree but in Tree`() {
            val num = mutableListOf(8, 12, 15, 13, 10 , 11, 9)
            for (i in num) {
                classUnderTest.insert(i)
            }

            classUnderTest.delete(12)

            val root = treeW.getPrivateNode(classUnderTest)?.value
            val node_13 = treeW.getPrivateNode(classUnderTest)?.right?.value
            val node_15 = treeW.getPrivateNode(classUnderTest)?.right?.right?.value
            val node_10 = treeW.getPrivateNode(classUnderTest)?.right?.left?.value
            val node_11 = treeW.getPrivateNode(classUnderTest)?.right?.left?.right?.value
            val node_9 = treeW.getPrivateNode(classUnderTest)?.right?.left?.left?.value
            val node_null = treeW.getPrivateNode(classUnderTest)?.right?.right?.left?.value

            assertEquals(expected = root, actual = 8)
            assertEquals(expected = node_10, actual = 10)
            assertEquals(expected = node_11, actual = 11)
            assertEquals(expected = node_13, actual = 13)
            assertEquals(expected = node_9, actual = 9)
            assertEquals(expected = node_15, actual = 15)
            assertEquals(expected = node_null, actual = null)
        }

        @Test
        fun `test two child delete min element in right tree for leftmost element`() {
            val num = mutableListOf(8, 10, 7, 6)
            for (i in num) {
                classUnderTest.insert(i)
            }

            classUnderTest.delete(8)

            val root = treeW.getPrivateNode(classUnderTest)?.value
            val node_7 = treeW.getPrivateNode(classUnderTest)?.left?.value
            val node_6 = treeW.getPrivateNode(classUnderTest)?.left?.left?.value
            val node_null = treeW.getPrivateNode(classUnderTest)?.right?.value

            assertEquals(expected = root, actual = 10)
            assertEquals(expected = node_null, actual = null)
            assertEquals(expected = node_7, actual = 7)
            assertEquals(expected = node_6, actual = 6)
        }

    }

    @Nested
    @DisplayName("Test: AVL Struct")
    inner class AVLStructTest {
        val treeW = TreeStructWrapper<Int, AVLNode<Int>, AVLStateContainer<Int>, AVLStruct<Int>>()
        var classUnderTest = AVLStruct<Int>()

        @BeforeEach
        fun reInitClassUnderTest() {
            classUnderTest = AVLStruct()
        }
    }

    @Nested
    @DisplayName("Test: Red-Black Tree Struct")
    inner class RBStructTree {
        val treeW = TreeStructWrapper<Int, RBNode<Int>, RBStateContainer<Int>,RBStruct<Int>>()
        var classUnderTest = RBStruct<Int>()

        @BeforeEach
        fun reInitClassUnderTest() {
            classUnderTest = RBStruct()
        }
    }
}
