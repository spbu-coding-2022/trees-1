package treelib.commonObjects

import kotlinx.serialization.Serializable

@Serializable
data class Container<K : Comparable<K>, V>(val pair: Pair<K, V?>) : Comparable<Container<K, V>> {

    val key = pair.first
    val value = pair.second

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
