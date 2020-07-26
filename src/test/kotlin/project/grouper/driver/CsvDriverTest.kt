package project.grouper.driver

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class CsvDriverTest {
    private val target = CsvDriver()

    @Test
    fun testToCellsShouldSplitStringIntoCells() {
        target.toCells(listOf("a,b", "c,d,e").asSequence()) shouldEqual listOf(listOf("a", "b"), listOf("c", "d", "e"))
        target.toCells(listOf("a,b", "c,d,e", "").asSequence()) shouldEqual listOf(listOf("a", "b"), listOf("c", "d", "e"))
        target.toCells(listOf("a,b", ",", "").asSequence()) shouldEqual listOf(listOf("a", "b"), listOf("", ""))
    }

    @Test
    fun testToContentText() {
        target.toCsvLines(listOf(listOf("a", "b"), listOf("c", "d", "e"))) shouldEqual "a,b\nc,d,e"
    }
}