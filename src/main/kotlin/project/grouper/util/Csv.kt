package project.grouper.util

import java.io.File

internal fun readAsLines(filePath: String): List<List<String>> =
    File(filePath).useLines { linesToCells(it) }

fun linesToCells(lines: Sequence<String>): List<List<String>> =
    lines
        .filterNot(String::isEmpty)
        .map { it.split(",") }
        .toList()

internal fun writeTo(filePath: String, content: List<List<String>>): Unit =
    File(filePath).writeText(toContentText(content))

fun toContentText(content: List<List<String>>): String =
    content.joinToString("\n") { it.joinToString(",") }
