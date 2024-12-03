package ca.jonathanfritz.aoc2k4

import java.net.URL
import java.nio.file.Files
import java.nio.file.Path

class Utils {
    companion object {
        fun loadFromFile(filename: String): List<String> {
            return try {
                val path: Path = Path.of(getResource(filename).toURI())
                Files.lines(path).toList()
            } catch (e: Exception) {
                throw RuntimeException("Failed to load file", e)
            }
        }

        // stolen from https://github.com/krosenvold/struts2/blob/master/xwork-core/src/main/java/com/opensymphony/xwork2/util/ClassLoaderUtil.java
        private fun getResource(resourceName: String): URL {
            var url: URL? = Thread.currentThread().contextClassLoader.getResource(resourceName)
            if (url == null) {
                url = Utils::class.java.classLoader.getResource(resourceName)
            }
            if (url == null) {
                val cl = (this as Any).javaClass.classLoader
                if (cl != null) {
                    url = cl.getResource(resourceName)
                }
            }
            return if (url == null && resourceName[0] != '/') {
                getResource("/$resourceName")
            } else url!!
        }
    }
}