package project.grouper.domain

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class ScoredLotTest {
    @Test
    fun `score returns total score`() {
        val target = ScoredLot(
            listOf(
                ScoredGroup(Group(Members(listOf(Member("a"), Member("b")))), Score(-80)),
                ScoredGroup(Group(Members(listOf(Member("a"), Member("b")))), Score(140))
            )
        )

        target.score shouldEqual Score(60)
    }
}