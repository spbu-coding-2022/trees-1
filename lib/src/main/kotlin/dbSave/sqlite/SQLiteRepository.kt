package dbSave.sqlite

import java.io.Closeable
import java.sql.DriverManager
import java.sql.SQLException

class SQLiteRepository<Pack : Comparable<Pack>>(
    private val dbPath: String,
    private val serializeData: (input: Pack) -> String,
    private val deSerializeData: (input: String) -> Pack,
    private val logErrorMethod: (input: Exception) -> Unit = { throw it },
    private val logInfoMethod: (input: String) -> Unit = { /* Nothing to do */ },
) : Closeable {

    private val treeTable = "AVLTreesTable"
    private val avlTreeName = "name"

    private val value = "value"
    private val height = "height"
    private val xCord = "x"
    private val yCord = "y"

    private val dbDriver = "jdbc:sqlite"
    private val connection = DriverManager.getConnection("$dbDriver:$dbPath")
        ?: throw SQLException("Cannot connect to database")

    init {
        createTreeTable()
    }

    fun createTreeTable() {
        connection.createStatement().also { stmt ->
            try {
                stmt.execute("CREATE TABLE if not exists $treeTable(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name text);")
                logInfoMethod("Table with trees created or already exists")
            } catch (ex: SQLException) {
                logErrorMethod(ex)
            } finally {
                stmt.close()
            }
        }
    }

    fun clearTreeTable() {
        connection.createStatement().also { stmt ->
            try {
                stmt.execute("DELETE FROM $treeTable;")
                logInfoMethod("TreeTable: $treeTable has been deleted")
            } catch (ex: SQLException) {
                logErrorMethod(ex)
            } finally {
                stmt.close()
            }
        }
    }

    fun addTree(treeName: String) {
        val isInDB = isNameInDB(treeName, avlTreeName, treeTable)

        if (isInDB) {
            logInfoMethod("Tree - $treeName, have been exist yet in treeTable - $treeTable")
            return
        }

        connection.createStatement().also { stmt ->
            try {
                stmt.execute("CREATE TABLE if not exists $treeName(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, $value text, $height INT, $xCord DOUBLE, $yCord DOUBLE);")
                stmt.execute("INSERT INTO $treeTable ($avlTreeName) VALUES ('$treeName');")
                logInfoMethod("Was created Tree: $treeName in table: $treeTable")
            } catch (ex: SQLException) {
                logErrorMethod(ex)
            } finally {
                stmt.close()
            }
        }
    }

    fun getTreeNames(): MutableList<String> {
        val info = mutableListOf<String>()
        connection.createStatement().also { stmt ->
            try {
                val result = stmt.executeQuery("SELECT $treeTable.$avlTreeName as $avlTreeName FROM $treeTable;")
                while (result.next()) {
                    info.add(result.getString(avlTreeName))
                }
                logInfoMethod("Available tree is given")
            } catch (ex: SQLException) {
                logErrorMethod(ex)
            } finally {
                stmt.close()
            }
            return info
        }
    }

    fun deleteTree(treeName: String) {
        val deleteId = getTreeId(treeName)
        if (deleteId == 0) return

        connection.createStatement().also { stmt ->
            try {
                stmt.execute("DROP TABLE $treeName;")
                stmt.execute("DELETE FROM $treeTable WHERE id=$deleteId;")
                logInfoMethod("Tree: $treeName has been deleted")
            } catch (ex: SQLException) {
                logErrorMethod(ex)
            } finally {
                stmt.close()
            }
        }
    }

    fun getTreeId(treeName: String): Int {
        var id: Int? = null
        try {
            val statement = connection.prepareStatement("SELECT id FROM $treeTable WHERE name=?;")
            statement.setString(1, treeName)
            id = statement.executeQuery().getInt(1)
            statement.close()
        } catch (ex: SQLException) {
            logErrorMethod(ex)
        }
        if (id != null) return id
        else throw SQLException("Impossible case")
    }

    fun addVertex(avlDVertex: DrawAVLVertex<Pack>, treeName: String) {
        val isInDB = getVertexId(avlDVertex, treeName)
        if (isInDB != 0) {
            deleteVertex(isInDB, treeName)
            logInfoMethod("Attempt write duplicate of the vertex: value = ${avlDVertex.value}; hieght = ${avlDVertex.height}; x = ${avlDVertex.x}; y = ${avlDVertex.y}")
        }

        try {
            val stmt = connection.prepareStatement("INSERT INTO $treeName (value, height, x, y) VALUES (?, ?, ?, ?);")
            val info = serializeData(avlDVertex.value)
            stmt.setString(1, info)
            stmt.setInt(2, avlDVertex.height)
            stmt.setDouble(3, avlDVertex.x)
            stmt.setDouble(4, avlDVertex.y)
            stmt.execute()
            stmt.close()
            logInfoMethod("Vertex: value = $info has been saved")
        } catch (ex: SQLException) {
            logErrorMethod(ex)
        }

    }

    fun addVertexes(list: MutableList<DrawAVLVertex<Pack>>, treeName: String) {
        for (el in list) addVertex(el, treeName)
    }

    fun deleteVertex(id: Int, treeName: String) {
        try {
            val stmt = connection.prepareStatement("DELETE FROM $treeName WHERE id=?;")
            stmt.setInt(1, id)
            stmt.execute()
            stmt.close()
            logInfoMethod("Element: id = $id has been deleted in table: $treeName")
        } catch (ex: SQLException) {
            logErrorMethod(ex)
        }
    }

    fun getAllVertexes(treeName: String): MutableList<DrawAVLVertex<Pack>> {
        val info = mutableListOf<DrawAVLVertex<Pack>>()
        connection.createStatement().also { stmt ->
            try {
                val result =
                    stmt.executeQuery("SELECT $treeName.$value as $value, $treeName.$height as $height, $treeName.$xCord as $xCord, $treeName.$yCord as $yCord FROM $treeName;")
                while (result.next()) {
                    info.add(
                        DrawAVLVertex(
                            value = deSerializeData(result.getString(value)),
                            height = result.getInt(height),
                            x = result.getDouble(xCord),
                            y = result.getDouble(yCord),
                        )
                    )
                }
                logInfoMethod("Vertexes from $treeName have been received")
            } catch (ex: SQLException) {
                logErrorMethod(ex)
            } finally {
                stmt.close()
            }
            return info
        }
    }

    private fun getVertexId(vertex: DrawAVLVertex<Pack>, tableName: String): Int {
        var id: Int? = null
        try {
            val stmt =
                connection.prepareStatement("SELECT id FROM $tableName WHERE value=? AND height=? AND x=? AND y=?;")
            stmt.setString(1, serializeData(vertex.value))
            stmt.setInt(2, vertex.height)
            stmt.setDouble(3, vertex.x)
            stmt.setDouble(4, vertex.y)
            id = stmt.executeQuery().getInt(1)
            stmt.close()
        } catch (ex: SQLException) {
            logErrorMethod(ex)
        }
        if (id != null) return id
        else throw SQLException("Impossible case")
    }

    private fun isNameInDB(rowName: String, columnName: String, tableName: String): Boolean {
        var isInDB: Boolean? = null
        try {
            val statement = connection.prepareStatement("SELECT EXISTS(SELECT 1 FROM $tableName WHERE $columnName=?);")
            statement.setString(1, rowName)
            isInDB = statement.executeQuery().getBoolean(1)
            statement.close()
        } catch (ex: SQLException) {
            logErrorMethod(ex)
        }
        if (isInDB != null) return isInDB
        else throw SQLException("Impossible case")
    }

    override fun close() {
        connection.close()
    }
}
