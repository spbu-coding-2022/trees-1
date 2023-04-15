package treelib

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import treelib.rbTree.RBNode
import treelib.rbTree.RBStateContainer
import treelib.rbTree.RBStruct

@DisplayName("Test: Red-Black Tree Struct")
class RBStructTree {
    val treeW = TreeStructWrapper<Int, RBNode<Int>, RBStateContainer<Int>, RBStruct<Int>>()
    var classUnderTest = RBStruct<Int>()

    @BeforeEach
    fun reInitClassUnderTest() {
        classUnderTest = RBStruct()
    }
}