package util

import java.io.File
import java.lang.RuntimeException
import java.nio.file.Paths

fun getResource(name: String): String {
    return ClassLoader.getSystemClassLoader().getResource(name)?.readText()
        ?: throw RuntimeException("Could not open resource $name")
}

fun writeResource(name: String, content: String) {
    val resourcePath = Paths.get(
        System.getProperty("user.dir"),
        "src/main/resources",
        name
    )

    File(resourcePath.toUri()).writeText(content)
}