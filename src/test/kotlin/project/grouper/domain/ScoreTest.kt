package project.grouper.domain

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ScoreTest {
    @Test
    fun `Returns a new Score instance of added values`() {
        Score(30) + Score(20) shouldEqual Score(50)
    }
}