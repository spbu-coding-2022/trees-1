package controller

import databaseManage.AVLTreeManager
import databaseManage.BINTreeManager
import databaseManage.RBTreeManager
import treelib.commonObjects.Container
import viewPart.nodes.drawableAVL.AVLDrawableTree
import viewPart.nodes.drawableBIN.BINDrawableTree
import viewPart.nodes.drawableRB.RBDrawableTree
import java.io.File
import java.lang.Integer.min


class Controller(/*  */) {
    private val avlManager = AVLTreeManager()
    private val rbManager = RBTreeManager()
    private val binManager = BINTreeManager()

    //val tree: test? = null


    // lazy
    private var binTree = BINDrawableTree("baseName.json", binManager)
    private var avlTree = AVLDrawableTree("baseName", avlManager)
    private var rbTree = RBDrawableTree("baseName", rbManager)

    //private var num by remember { mutableStateOf(0) }

    fun showFiles(): List<List<String>> {
        val avlTrees = avlManager.getSavedTreesNames()
        val rbTrees = rbManager.getSavedTreesNames()

        createDummyFiles(avlTrees, rbTrees)

        return listOf(
            rbTrees.subList(0, min(3, rbTrees.size)),
            avlTrees.subList(0, min(3, avlTrees.size)),
            binManager.getSavedTreesNames()
        )
    }

    private fun createDummyFiles(avlTrees: List<String>, rbTrees: List<String>) {
        val avlPath = System.getProperty("user.dir") + "/saved-trees/AVL-trees"
        val rbPath = System.getProperty("user.dir") + "/saved-trees/RB-trees"

        File(avlPath).mkdirs()
        File(rbPath).mkdirs()

        if (avlTrees.isNotEmpty()) {
            for (name in avlTrees) {
                File(avlPath, name).run {
                    createNewFile()
                }
            }
        }

        if (rbTrees.isNotEmpty()) {
            for (name in rbTrees) {
                File(rbPath, name).run {
                    createNewFile()
                }
            }
        }

    }

    fun createTree(treeName: String, /*id*/) {
        binTree = BINDrawableTree(treeName, binManager) // тут буду создавать один из 3х видов
        //num = index

        // tree = AVLDrawableTree(...)

        // tree = RBDrawableTree(..)

        //tree.value = RBDrawableTree(tree, rbManager)
    }

    fun insert(value: String): BINDrawableTree { // DrawableTreeType
        val key = if (isInt(value.trim())) value.toInt() else value.hashCode()

        binTree.insert(Container(Pair(key, value)))
        binTree.updateTree()
        binTree.repositisonTree(800f, 10f)

        //return tree
        return binTree
    }

    fun find(value: String): BINDrawableTree {
        val key = if (isInt(value.trim())) value.toInt() else value.hashCode()

        binTree.find(key)

        return binTree

    }

    fun delete(value: String): BINDrawableTree {
        val key = if (isInt(value.trim())) value.toInt() else value.hashCode()

        binTree.delete(Container(Pair(key, "")))
        binTree.updateTree()
        binTree.repositisonTree(800f, 10f)

        return binTree
    }


    private fun isInt(value: String): Boolean {
        return try {
            Integer.parseInt(value.trim())
            true
        } catch (ex: NumberFormatException) {
            false
        }
    }

}

