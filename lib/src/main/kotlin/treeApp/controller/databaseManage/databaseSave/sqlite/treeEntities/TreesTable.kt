package treeApp.controller.databaseManage.databaseSave.sqlite.treeEntities

import org.jetbrains.exposed.dao.id.IntIdTable


object TreesTable : IntIdTable("trees") {

    var name = text("name")

    init {
        uniqueIndex(name)
    }
}
