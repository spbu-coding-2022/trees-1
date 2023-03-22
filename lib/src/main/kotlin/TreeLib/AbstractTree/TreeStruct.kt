package TreeLib.AbstractTree

abstract class TreeStruct<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>> {
    protected abstract var root: NodeType?  //TODO root - not a null

    fun find(obj: Pack): Pack?{
        TODO("Сделать реализацию поиска, дергать реализацию из локализированного класса")
    }

    fun inOrder(){
        TODO("inOrder - implementation")
    }
    fun postOrder(){
        TODO("postOrder - implementation")
    }
    fun preOrder(){
        TODO("preOrder - implementation")
    }

    abstract fun insert(item: Pack)
    abstract fun delete(item: Pack)
}
