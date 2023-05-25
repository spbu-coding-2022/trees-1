package treelib.databaseSave.sqlite.treeEntities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TreeTableEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TreeTableEntity>(TreesTable)

    var name by TreesTable.name
}
