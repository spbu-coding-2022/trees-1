package treelib.singleObjects

class Container<K : Comparable<K>, V>(private val pair: Pair<K, V?>) : Comparable<Container<K, V>> {

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true

        other as Container<*, *>
        return this.pair.first == other.pair.first
    }

    override fun hashCode(): Int {
        return pair.hashCode()
    }

    override fun compareTo(other: Container<K, V>): Int {
        return compareValuesBy(this, other) { it.pair.first }
    }
}
