package dbSave.jsonFormat
import com.google.common.reflect.TypeToken
import com.google.gson.GsonBuilder
import java.io.File

class JsonRepository(private val dirPath: String) {

    init {
        File(dirPath).mkdirs()
    }

    fun <Pack: Comparable<Pack>>saveChanges(preOrder: Array<DrawBINVertex<Pack>>, fileName: String) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(preOrder)

        File(dirPath, fileName).run {
            createNewFile()
            writeText(json)
        }

    }

    fun<Pack: Comparable<Pack>> exportTree(fileName: String, typeToken: TypeToken<Array<DrawBINVertex<Pack>>>): Array<DrawBINVertex<Pack>> {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = File(dirPath, fileName).readText()
        val preOrder = gson.fromJson<Array<DrawBINVertex<Pack>>>(json, typeToken.type)

        return preOrder
    }

}