package TreeLib.Single_Objects

class Container<K : Comparable<K>, V>(private val pair: Pair<K, V?>) : Comparable<Container<K, V>> {
    val key = pair.first

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true

        other as Container<*, *>
        if (this.pair.first == other.pair.first) {
            return true
        }

        return false
    }

    override fun hashCode(): Int {
        return pair.hashCode()
    }

    override fun compareTo(other: Container<K, V>): Int {
        return compareValuesBy(this, other) { it.pair.first }
    }
}