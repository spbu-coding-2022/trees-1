package treeApp.controller.databaseManage.databaseSave.sqlite

import treeApp.controller.databaseManage.databaseSave.sqlite.treeEntities.TreeTableEntity
import treeApp.controller.databaseManage.databaseSave.sqlite.treeEntities.TreesTable
import treeApp.controller.databaseManage.databaseSave.sqlite.vertexEntities.VertexTable
import treeApp.controller.databaseManage.databaseSave.sqlite.vertexEntities.VertexTableEntity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.sql.SQLException

class SQLiteRepositoryExposed {
    private var db: Database? = null
    var dbName: String? = null
        private set

    fun initDataBase(name: String) {
        if ((dbName != null) && (dbName == name)) return

        File(System.getProperty("user.dir") + "/sqliteDB").mkdirs()

        db = Database.connect("jdbc:sqlite:sqliteDB/$name", driver = "org.sqlite.JDBC")

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

    fun <Pack : Comparable<Pack>> saveTree(
        treeName: String,
        vertexes: MutableList<DrawableAVLVertex<Pack>>,
        serializeData: (input: Pack) -> String,
    ): Unit = transaction(db) {
        interDelete(treeName)

        val id = TreeTableEntity.new {
            name = treeName
        }

        for (index in vertexes.indices) VertexTableEntity.new {
            height = vertexes[index].height.toInt()
            value = serializeData(vertexes[index].value)
            order = index
            x = vertexes[index].x
            y = vertexes[index].y
            tree = id.id.value
        }
    }

    fun getAllSavedTrees(): List<String> = transaction(db) {
        return@transaction TreeTableEntity.all().map { el -> el.name }
    }

    fun <Pack : Comparable<Pack>> getTree(
        name: String,
        deSerializeData: (input: String) -> Pack,
        ): MutableList<DrawableAVLVertex<Pack>> = transaction(db) {
        val ans = mutableListOf<DrawableAVLVertex<Pack>>()
        val treeId = interIsTreeExist(name) ?: throw SQLException("Tree doesn't exist")

        for (el in VertexTableEntity.find(VertexTable.tree eq treeId).orderBy(VertexTable.order to SortOrder.ASC)){
            ans.add(
                DrawableAVLVertex(
                    value = deSerializeData(el.value),
                    height = el.height.toUInt(),
                    x = el.x,
                    y = el.y
                )
            )
        }
        return@transaction ans
    }

    fun deleteTree(name: String): Boolean =
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
}
