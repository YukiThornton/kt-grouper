package project.grouper.domain

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class ScoredLotsTest {

    @Test
    fun `highest returns lot with highest total score`() {
        val target = ScoredLots(
            listOf(
                ScoredLot(
                    listOf(
                        ScoredGroup(Group(Members(listOf(Member("a"), Member("b")))), Score(-70)),
                        ScoredGroup(Group(Members(listOf(Member("a"), Member("b")))), Score(140))
                    )
                ),
                ScoredLot(
                    listOf(
                        ScoredGroup(Group(Members(listOf(Member("a"), Member("b")))), Score(-60)),
                        ScoredGroup(Group(Members(listOf(Member("a"), Member("b")))), Score(140))
                    )
                ),
                ScoredLot(
                    listOf(
                        ScoredGroup(Group(Members(listOf(Member("a"), Member("b")))), Score(-70)),
                        ScoredGroup(Group(Members(listOf(Member("a"), Member("b")))), Score(160))
                    )
                )
            )
        )

        val expected = ScoredLot(
            listOf(
                ScoredGroup(Group(Members(listOf(Member("a"), Member("b")))), Score(-70)),
                ScoredGroup(Group(Members(listOf(Member("a"), Member("b")))), Score(160))
            )
        )

        target.highest() shouldEqual expected
    }
}