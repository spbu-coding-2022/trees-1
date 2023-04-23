package databaseSave.sqlite.vertexEntities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class VertexTableEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<VertexTableEntity>(VertexTable)

    var height by VertexTable.height
    var value by VertexTable.value
    var order by VertexTable.order
    var x by VertexTable.x
    var y by VertexTable.y
    var tree by VertexTable.tree
}
