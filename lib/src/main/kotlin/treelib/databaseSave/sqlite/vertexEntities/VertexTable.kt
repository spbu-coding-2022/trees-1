package treelib.databaseSave.sqlite.vertexEntities

import org.jetbrains.exposed.dao.id.IntIdTable


object VertexTable : IntIdTable("vertex") {
    var height = integer("height")
    var value = text("data")
    var order = integer("orderId")
    var x = double("xCord")
    var y = double("yCord")
    var tree = integer("treeId")
}
