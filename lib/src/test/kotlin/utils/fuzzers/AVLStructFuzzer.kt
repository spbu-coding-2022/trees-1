package utils.fuzzers

import treelib.avlTree.AVLNode
import treelib.avlTree.AVLStateContainer
import treelib.avlTree.AVLStruct
import utils.AVLAnalyzer

class AVLStructFuzzer<Pack : Comparable<Pack>>(
    override val baseInput: Array<Pack>,
    override val assertMethod: (input: String) -> Unit
) : TreeStructFuzzer<Pack, AVLNode<Pack>, AVLAnalyzer<Pack>, AVLStateContainer<Pack>, AVLStruct<Pack>>() {

    override fun createTreeStruct(): AVLStruct<Pack> = AVLStruct()

    override fun createAnalyzer(): AVLAnalyzer<Pack> = AVLAnalyzer(assertMethod)
}