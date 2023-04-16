package treelib

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import treelib.avlTree.AVLNode
import treelib.avlTree.AVLStateContainer
import treelib.avlTree.AVLStruct
import utils.AVLAnalyzer
import utils.TreeStructWrapper

@DisplayName("Test: AVL Struct")
class AVLStructTest {
    private val treeW = TreeStructWrapper<Int, AVLNode<Int>, AVLStateContainer<Int>, AVLStruct<Int>>()
    private val treeH = AVLAnalyzer<Int>(::testAssert)
    private var treeStruct = AVLStruct<Int>()

    private fun testAssert(msg: String): Nothing = fail(msg)
    @BeforeEach
    fun reInitClassUnderTest() {
        treeStruct = AVLStruct()
    }

    @Test
    fun `test one root`() {
        val num = mutableListOf(1)
        for (i in num) {
            treeStruct.insert(i)
        }
        treeH.checkTree(treeW.getPrivateNode(treeStruct)!!)
    }

    @Test
    fun `test height 3`() {
        val num = mutableListOf(3, 2, 1, 4)
        for (i in num) {
            treeStruct.insert(i)
        }
        treeH.checkTree(treeW.getPrivateNode(treeStruct)!!)
    }

    @Test
    fun `test height 3 with delete root`() {
        val num = mutableListOf(3, 2, 1, 4)
        for (i in num) {
            treeStruct.insert(i)
        }
        treeStruct.delete(2)
        treeH.checkTree(treeW.getPrivateNode(treeStruct)!!)
    }

    @Test
    fun `test 100000 arguments`() {
        for (i in 1..100000) {
            treeStruct.insert(i)
        }
        treeH.checkTree(treeW.getPrivateNode(treeStruct)!!)
    }

    @Test
    fun `test 100000 arguments and delete`() {
        for (i in 1..100000) {
            treeStruct.insert(i)
        }
        treeStruct.delete(5000)
        treeH.checkTree(treeW.getPrivateNode(treeStruct)!!)
    }

    @Test
    fun `test two arguments`() {
        treeStruct.insert(2)
        treeStruct.insert(3)
        treeH.checkTree(treeW.getPrivateNode(treeStruct)!!)
    }

    @Test
    fun `test many arguments`() {
        val num = mutableListOf(3, 2, 1, 4, 2343, 123213, 3213, 657, 534, 12432, 5676756, 321, 5436546, 5435)
        for (i in num) {
            treeStruct.insert(i)
        }
        treeH.checkTree(treeW.getPrivateNode(treeStruct)!!)
    }
}