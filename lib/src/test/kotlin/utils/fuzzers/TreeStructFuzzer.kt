package utils.fuzzers

import treelib.abstractTree.Node
import treelib.abstractTree.StateContainer
import treelib.abstractTree.TreeStruct
import treelib.abstractTree.Vertex
import treelib.singleObjects.exceptions.*
import utils.Analyzer
import utils.TreeStructWrapper
import java.io.File
import java.time.Instant
import kotlin.random.Random
import kotlin.random.nextInt

abstract class TreeStructFuzzer<
        Pack : Comparable<Pack>,
        NodeType : Node<Pack, NodeType>,
        VertexType: Vertex<Pack>,
        AnalyzerType : Analyzer<Pack, NodeType>,
        State : StateContainer<Pack, NodeType>,
        TreeStructType : TreeStruct<Pack, NodeType, State, VertexType>,
        > {
    abstract val baseInput: Array<Pack>

    protected abstract val assertMethod: (input: String) -> Unit

    private var saveFlag: Boolean = false

    private var dirPath: String? = null

    protected val treeWrapper = TreeStructWrapper<Pack, NodeType, VertexType,  State, TreeStructType>()

    protected abstract fun createTreeStruct(): TreeStructType

    protected abstract fun createAnalyzer(): AnalyzerType

    /**
     * testNumbers - How many times fuzzer would be run
     * inputSize - How many elements from baseInput would be used
     * **/
    fun fuzzInvariantInsert(testNumbers: Int, inputSize: Int? = null) {
        val dataSize: Int = checkCorrectInput(testNumbers, inputSize)

        for (iterations in 0 until testNumbers) {
            val treeStruct = createTreeStruct()
            val testSetIndexes = getInputSetIndexes(dataSize)
            val testID = "${Instant.now().toEpochMilli() + iterations}-insert"
            val analyzer = createAnalyzer()

            analyzer.message = "$testID: "

            if (saveFlag) saveCurrentTestSet(testID, testSetIndexes)

            try {
                for (index in testSetIndexes) {
                    treeStruct.insert(baseInput[index])
                }
            } catch (ex: Exception) {
                exceptionsCatch(ex)
            }

            val root = treeWrapper.getPrivateNode(treeStruct, "root")
            // todo: probably won't work with junit.fail()
            root?.let { analyzer.checkTree(it) } ?: assertMethod("The root was not created in the test $testID")
        }
    }

    fun fuzzInvariantDelete(testNumbers: Int, inputSize: Int? = null) {
        val dataSize: Int = checkCorrectInput(testNumbers, inputSize)
        TODO("DON'T IMPLEMENTED YET")
    }

    fun fuzzInvariant(testNumbers: Int, inputSize: Int? = null) {
        val dataSize: Int = checkCorrectInput(testNumbers, inputSize)
        TODO("DON'T IMPLEMENTED YET")
    }

    private fun checkCorrectInput(testNumbers: Int, inputSize: Int?): Int {
        val dataSize: Int

        if (inputSize == null) dataSize = baseInput.size
        else dataSize = inputSize

        if (dataSize > baseInput.size) throw BugInImplementException("inputSize > size of the baseInput")
        return dataSize
    }

    private fun getInputSetIndexes(inputSize: Int): List<Int> {
        return generateSequence { Random.nextInt(baseInput.indices) }.distinct().take(inputSize).toList()
    }

    private fun exceptionsCatch(ex: Exception) {
        when (ex) {
            is BugInImplementException,
            is IllegalBaseNodeException,
            is IllegalNodeStateException,
            is ImpossibleCaseException,
            is MultithreadingException,
            is NonExistentValueException,
            -> {/*TODO: Implement */
            }

            else -> throw ex
        }
    }

    fun saveNextTestSets(dirName: String) {
        dirPath = dirName
        val file = File("./$dirPath")
        file.mkdir()
        if (file.isDirectory) saveFlag = true
    }

    fun dontSaveNextTestSets() {
        saveFlag = false
    }

    private fun saveCurrentTestSet(testId: String, testSet: List<Int>) {
        val file = File("./$dirPath/$testId.txt")
        file.createNewFile()
        for (index in testSet) {
            file.appendText("${baseInput[index]} \n")
        }
    }

}
