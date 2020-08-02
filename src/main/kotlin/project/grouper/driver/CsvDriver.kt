package project.grouper.driver

import java.io.File

class CsvDriver {
    fun readCells(filePath: String): List<List<String>> {
        return File(filePath).useLines { toCells(it) }
    }

    fun writeCells(filePath: String, content: List<List<String>>): Unit =
        File(filePath).writeText(toCsvLines(content))


    fun toCells(lines: Sequence<String>): List<List<String>> =
        lines
            .filterNot(String::isEmpty)
            .map { it.split(",") }
            .toList()

    fun toCsvLines(content: List<List<String>>): String =
        content.joinToString("\n") { it.joinToString(",") }

    fun findCsvFileNames(rootPath: String): List<String> {
        return File(rootPath).list().filter { it.endsWith(".csv") }
    }

}