package treeApp.controller.databaseManage.databaseSave.jsonFormat

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File

class JsonRepository(private val dirPath: String) {

    init {
        File(dirPath).mkdirs()
    }

    fun <Pack : Comparable<Pack>> saveChanges(
        preOrder: Array<DrawableBINVertex<Pack>>,
        fileName: String
    ) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(preOrder)

        File(dirPath, fileName).run {
            createNewFile()
            writeText(json)
        }

    }

    fun <Pack : Comparable<Pack>> exportTree(
        fileName: String,
        typeToken: TypeToken<Array<DrawableBINVertex<Pack>>>
    ): Array<DrawableBINVertex<Pack>> {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = File(dirPath, fileName).readText()

        val preOrder = gson.fromJson<Array<DrawableBINVertex<Pack>>>(json, typeToken.type)

        return preOrder
    }

    fun removeTree(treeName: String) {
        File(dirPath, treeName).delete()
    }

    fun clean() {

        File(dirPath).deleteRecursively()
    }


}
