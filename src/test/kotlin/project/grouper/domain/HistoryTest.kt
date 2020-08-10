package project.grouper.domain

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class HistoryTest {

    @Test
    fun `score calculates scores for each group in lots`() {
        val target = History(listOf(
            Group(Members(listOf(Member("a"), Member("c")))),
            Group(Members(listOf(Member("e"), Member("c")))),
            Group(Members(listOf(Member("b"), Member("d"), Member("e"))))
        ))
        target.score(Group(Members(listOf(Member("a"), Member("b"))))) shouldEqual Score(0)
        target.score(Group(Members(listOf(Member("a"), Member("b"), Member("c"))))) shouldEqual Score(-70)
        target.score(Group(Members(listOf(Member("a"), Member("e"), Member("c"))))) shouldEqual Score(-140)
    }

    @Test
    fun `countPairedTimes counts how many times the given pair paired up in history`() {
        val target = History(listOf(
            Group(Members(listOf(Member("a"), Member("c")))),
            Group(Members(listOf(Member("d"), Member("c")))),
            Group(Members(listOf(Member("b"), Member("d"), Member("c"))))
        ))

        target.countPairedTimes(PairedMembers(Member("a"), Member("b"))) shouldEqual 0
        target.countPairedTimes(PairedMembers(Member("b"), Member("c"))) shouldEqual 1
        target.countPairedTimes(PairedMembers(Member("c"), Member("d"))) shouldEqual 2
    }
}