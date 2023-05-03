package utils.fuzzers

import treelib.rbTree.RBNode
import treelib.rbTree.RBStateContainer
import treelib.rbTree.RBStruct
import treelib.rbTree.RBVertex
import utils.RBAnalyzer

class RBStructFuzzer<Pack : Comparable<Pack>>(
    override val baseInput: Array<Pack>,
    override val assertMethod: (input: String) -> Unit
) : TreeStructFuzzer<Pack, RBNode<Pack>, RBVertex<Pack>, RBAnalyzer<Pack>, RBStateContainer<Pack>, RBStruct<Pack>>() {

    override fun createTreeStruct(): RBStruct<Pack> = RBStruct()

    override fun createAnalyzer(): RBAnalyzer<Pack> = RBAnalyzer(assertMethod)
}