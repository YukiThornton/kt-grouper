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
        val lots = Lots(listOf(
            Lot(listOf(
                Group(Members(listOf(Member("a"), Member("b")))),
                Group(Members(listOf(Member("c"), Member("d"), Member("e"))))
            )),
            Lot(listOf(
                Group(Members(listOf(Member("a"), Member("c")))),
                Group(Members(listOf(Member("b"), Member("d"), Member("e"))))
            ))
        ))

        val expected = ScoredLots(listOf(
            ScoredLot(listOf(
                ScoredGroup(Group(Members(listOf(Member("a"), Member("b")))), Score(0)),
                ScoredGroup(Group(Members(listOf(Member("c"), Member("d"), Member("e")))), Score(-140))
            )),
            ScoredLot(listOf(
                ScoredGroup(Group(Members(listOf(Member("a"), Member("c")))), Score(-70)),
                ScoredGroup(Group(Members(listOf(Member("b"), Member("d"), Member("e")))), Score(-210))
            ))
        ))

        target.score(lots) shouldEqual expected
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