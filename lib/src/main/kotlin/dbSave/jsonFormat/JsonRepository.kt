package dbSave.jsonFormat
import com.google.common.reflect.TypeToken
import com.google.gson.GsonBuilder
import java.io.File

class JsonRepository<Pack: Comparable<Pack>>(private val dirPath: String) {

    init {
        File(dirPath).mkdirs()
    }

    fun saveChanges(preOrder: Array<DrawBINVertex<Pack>>, typeToken: TypeToken<Array<DrawBINVertex<Pack>>>, fileName: String) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(preOrder)

        File(dirPath, fileName).run {
            createNewFile()
            writeText(json)
        }

        val preOrd = gson.fromJson<Array<DrawBINVertex<Pack>>>(json, typeToken.type)

    }

    fun exportTree(fileName: String) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        //val json = gson.fromJson(File(dirPath, fileName).readText(), ArrayVertices::class.java)


    }


}