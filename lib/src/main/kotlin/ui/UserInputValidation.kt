package ui

import java.util.*

private val INVALID_WINDOWS_SPECIFIC_CHAR = arrayOf('"', '*', ':', '<', '>', '/', '\"', '|', '?')
private val INVALID_UNIX_SPECIFIC_CHAR = arrayOf('/') // add '\000' // single .

fun validate(fileName: String): Boolean {
    if (fileName == "" || fileName.length > 255) {
        return false
    }
    val os = System.getProperty("os.name").lowercase(Locale.getDefault())
    return when {
        os.contains("win") ->
            Arrays.stream(INVALID_WINDOWS_SPECIFIC_CHAR)
                .noneMatch { ch -> fileName.contains(ch.toString()) }

        os.contains("unix") || os.contains("linux") || os.contains("mac") ->
            Arrays.stream(INVALID_UNIX_SPECIFIC_CHAR)
                .noneMatch { ch -> fileName.contains(ch.toString()) }

        else -> true
    }

}