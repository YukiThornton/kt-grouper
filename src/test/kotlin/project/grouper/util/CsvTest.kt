package project.grouper.util

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class CsvTest {
    @Test
    fun testToCellsShouldSplitStringIntoCells() {
        linesToCells(listOf("a,b", "c,d,e").asSequence()) shouldEqual listOf(listOf("a", "b"), listOf("c", "d", "e"))
        linesToCells(listOf("a,b", "c,d,e", "").asSequence()) shouldEqual listOf(listOf("a", "b"), listOf("c", "d", "e"))
        linesToCells(listOf("a,b", ",", "").asSequence()) shouldEqual listOf(listOf("a", "b"), listOf("", ""))
    }

    @Test
    fun testToContentText() {
        toContentText(listOf(listOf("a", "b"), listOf("c", "d", "e"))) shouldEqual "a,b\nc,d,e"
    }
}