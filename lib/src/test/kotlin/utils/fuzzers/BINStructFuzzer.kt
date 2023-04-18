package utils.fuzzers

import treelib.binTree.BINNode
import treelib.binTree.BINStateContainer
import treelib.binTree.BINStruct
import treelib.binTree.BINVertex
import utils.BINAnalyzer

class BINStructFuzzer<Pack:Comparable<Pack>>(
    override val baseInput: Array<Pack>,
    override val assertMethod: (input: String) -> Unit
): TreeStructFuzzer<Pack, BINNode<Pack>, BINVertex<Pack>, BINAnalyzer<Pack>, BINStateContainer<Pack>, BINStruct<Pack>>() {

    override fun createTreeStruct(): BINStruct<Pack> = BINStruct()

    override fun createAnalyzer(): BINAnalyzer<Pack> = BINAnalyzer(assertMethod)
}