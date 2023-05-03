package treeApp.controller

import androidx.compose.ui.window.WindowState
import treeApp.controller.databaseManage.AVLTreeManager
import treeApp.controller.databaseManage.BINTreeManager
import treeApp.controller.databaseManage.RBTreeManager
import treelib.commonObjects.Container
import treeApp.controller.viewPart.drawableAVL.AVLDrawableTree
import treeApp.controller.viewPart.drawableBIN.BINDrawableTree
import treeApp.controller.viewPart.drawableRB.RBDrawableTree
import treeApp.controller.viewPart.drawableTree.DrawTree
import java.io.File
import java.lang.Integer.min


class Controller {

    private val avlManager = AVLTreeManager()
    private val rbManager = RBTreeManager()
    private val binManager = BINTreeManager()


    var tree: DrawTree? = null

    fun getSavedTreesNames(): List<List<String>> {
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

    fun createTree(treeName: String, id: Int): DrawTree {
        when (id) {
            0 -> tree = RBDrawableTree(treeName, rbManager)
            1 -> tree = AVLDrawableTree(treeName, avlManager)
            2 -> tree = BINDrawableTree(treeName, binManager)
        }

        tree?.initTree()

        return tree ?: throw NullPointerException()
    }

    fun insert(value: String): DrawTree {
        val key = if (isInt(value.trim())) value.toInt() else value.hashCode()

        tree?.insert(Container(Pair(key, value))) ?: throw NullPointerException()
        tree?.updateTree() ?: throw NullPointerException()
        tree?.repositionTree(800f, 10f) ?: throw NullPointerException()

        return tree ?: throw NullPointerException()
    }

    fun find(value: String, windowState: WindowState): DrawTree {
        val key = if (isInt(value.trim())) value.toInt() else value.hashCode()

        val offset1 = tree?.find(key) ?: throw NullPointerException()
        if (offset1.first >= 0){
            tree?.addOffset(
                windowState.size.width.value / 2 - offset1.first,
                windowState.size.height.value / 2 - offset1.second,
            )
        }


        return tree ?: throw NullPointerException()

    }

    fun delete(value: String): DrawTree {
        val key = if (isInt(value.trim())) value.toInt() else value.hashCode()

        tree?.delete(Container(Pair(key, ""))) ?: throw NullPointerException()
        tree?.updateTree() ?: throw NullPointerException()
        tree?.repositionTree(800f, 10f) ?: throw NullPointerException()

        return tree ?: throw NullPointerException()
    }


    private fun isInt(value: String): Boolean {
        return try {
            Integer.parseInt(value.trim())
            true
        } catch (ex: NumberFormatException) {
            false
        }
    }

    fun deleteTree(): DrawTree {
        tree?.deleteTree()
        return tree ?: throw Exception("Tree not initialized")
    }

    fun saveTree(fileName: String) {
        if (tree == null)
            throw Exception("Tree not initialized")

        tree?.name = fileName
        getSavedTreesNames()
        tree?.saveTreeToDB() ?: throw Exception()

    }


}

