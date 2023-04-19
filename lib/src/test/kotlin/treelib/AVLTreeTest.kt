package treelib

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import treelib.avlTree.AVLNode
import treelib.avlTree.AVLStateContainer
import treelib.avlTree.AVLStruct
import treelib.avlTree.AVLTree
import treelib.singleObjects.Container
import utils.TreeStructWrapper
import utils.TreeWrapper
import kotlin.test.assertEquals


class AVLTreeTest {
    private val tree = AVLTree<Int, Int>()
    private val treeW =
        TreeWrapper<Int, Int, AVLNode<Container<Int, Int?>>, AVLStateContainer<Container<Int, Int?>>, AVLStruct<Container<Int, Int?>>, AVLTree<Int, Int>>()
    private val treeSW =
        TreeStructWrapper<Container<Int, Int?>, AVLNode<Container<Int, Int?>>, AVLStateContainer<Container<Int, Int?>>, AVLStruct<Container<Int, Int?>>>()


    @ParameterizedTest
    @ValueSource(strings = ["5 3 8 9", "1 2 3 4", "4 3 5 2", "4 3 2 1", "2 3 1 4"])
    fun `test check root`(str: String) {
        val numbers = str.split(" ").map{ it.toInt() }.toMutableList()
        val num = mutableListOf<Pair<Int, Int>>()
        for (i in numbers) {
            num.add(Pair(i, 1))
        }
        tree.putItems(num)

        numbers.sort()
        val root = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.value?.key
        if (root == numbers[1]) {
            assertEquals(expected = root, actual = numbers[1])
        }
        else {
            assertEquals(expected = root, actual = numbers[2])
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["1 1000", "1 10000", "1 100000", "1 1000000"])
    fun `test add many args and delete root`(str: String) {
        val numbers = str.split(" ").map{ it.toInt() }.toMutableList()
        for (i in numbers[0] .. numbers[1]) {
            tree.putItem(Pair(i, i))
        }
        val root = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.value?.key
        if (root != null) {
            treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.value?.let { tree.deleteItem(it.key) }
        }

        when (numbers[1]) {
            1000 -> assertEquals(expected = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.value?.key, actual = 513)
            10000 -> assertEquals(expected = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.value?.key, actual = 4097)
            100000 -> assertEquals(expected = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.value?.key, actual = 65537)
            else -> assertEquals(expected = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.value?.key, actual = 524289)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["5", "0"])
    fun `test delete root one arg`(str: String) {
        val numbers = str.split(" ").map{ it.toInt() }.toMutableList()
        val num = mutableListOf<Pair<Int, Int>>()
        for (i in numbers) {
            num.add(Pair(i, 1))
        }
        tree.putItems(num)
        tree.deleteItem(numbers[0])

        assertEquals(expected = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.value?.key, actual = null)
    }
}