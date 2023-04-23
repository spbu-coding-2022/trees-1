package databaseSave.sqlite

import databaseSave.sqlite.vertexEntities.VertexTableEntity
import databaseSave.sqlite.vertexEntities.VertexTable
import databaseSave.sqlite.treeEntities.TreeTableEntity
import databaseSave.sqlite.treeEntities.TreesTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLException

class SQLiteRepositoryExposed(
    private val dbPath: String = "exposed_database.db"
) {
    val db = Database.connect("jdbc:sqlite:$dbPath", driver = "org.sqlite.JDBC")

    init {
        if (!isEmptyDB()) createTables()
    }

    private fun createTables() {
        transaction(db) {
            when {
                !TreesTable.exists() && !VertexTable.exists() -> {
                    SchemaUtils.create(TreesTable)
                    SchemaUtils.create(VertexTable)
                }

                !TreesTable.exists() -> SchemaUtils.create(TreesTable)
                !VertexTable.exists() -> SchemaUtils.create(VertexTable)
                else -> {}
            }
        }
    }

    fun <Pack : Comparable<Pack>> save(
        treeName: String,
        vertexes: MutableList<DrawAVLVertex<Pack>>,
        serializeData: (input: Pack) -> String,
    ): Unit = transaction(db) {
        interDelete(treeName)

        val id = TreeTableEntity.new {
            name = treeName
        }

        for (index in vertexes.indices) VertexTableEntity.new {
            height = vertexes[index].height
            value = serializeData(vertexes[index].value)
            order = index
            x = vertexes[index].x
            y = vertexes[index].y
            tree = id.id.value
        }
    }

    fun getAllSavedTrees(): List<String> = transaction(db) {
        return@transaction TreeTableEntity.all().map<TreeTableEntity, String> { el -> el.name }
    }

    fun <Pack : Comparable<Pack>> getTree(
        name: String,
        deSerializeData: (input: String) -> Pack,
        ): MutableList<DrawAVLVertex<Pack>> = transaction(db) {
        val ans = mutableListOf<DrawAVLVertex<Pack>>()
        val treeId = interIsTreeExist(name) ?: throw SQLException("Tree doesn't exist")

        for (el in VertexTableEntity.find(VertexTable.tree eq treeId).orderBy(VertexTable.order to SortOrder.ASC)){
            ans.add(
                DrawAVLVertex(
                    value = deSerializeData(el.value),
                    height = el.height,
                    x = el.x,
                    y = el.y
                )
            )
        }
        return@transaction ans
    }

    fun delete(name: String): Boolean =
        transaction(db) {
            val treeId = TreeTableEntity.find(TreesTable.name eq name).firstOrNull()?.id ?: return@transaction false
            TreeTableEntity.find(TreesTable.name eq name).firstOrNull()?.delete()

            VertexTableEntity.find(VertexTable.tree eq treeId.value).firstOrNull() ?: return@transaction false
            VertexTable.deleteWhere { tree eq treeId.value }
            return@transaction true
        }

    fun isEmptyDB(): Boolean = transaction(db) {
        if (TreesTable.exists() && VertexTable.exists()) return@transaction true
        else false
    }

    fun isTreeExist(name: String): Boolean = transaction(db) {
        return@transaction TreeTableEntity.find(TreesTable.name eq name).firstOrNull() != null
    }

    private fun interIsTreeExist(name: String): Int? {
        val id = TreeTableEntity.find(TreesTable.name eq name).firstOrNull()?.id ?: return null
        return id.value
    }

    private fun interDelete(name: String) {
        val treeId = TreeTableEntity.find(TreesTable.name eq name).firstOrNull()?.id ?: return
        TreeTableEntity.find(TreesTable.name eq name).firstOrNull()?.delete()

        VertexTableEntity.find(VertexTable.tree eq treeId.value).firstOrNull() ?: return
        VertexTable.deleteWhere { tree eq treeId.value }
        return
    }
}
