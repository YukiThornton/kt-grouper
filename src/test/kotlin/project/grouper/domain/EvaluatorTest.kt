package project.grouper.domain

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.Test
import java.lang.RuntimeException

class EvaluatorTest {

    @Test
    fun `with calls 1 or more evaluators score functions and combines scores`() {
        val evaluator1 = spyk(object : Evaluator {
            override fun evaluate(group: Group): Score = throw Error("Should not reach this function.")
        })
        val evaluator2 = spyk(object : Evaluator {
            override fun evaluate(group: Group): Score = throw Error("Should not reach this function.")
        })
        val group = mockk<Group>()

        every { evaluator1.evaluate(group) } returns Score(70)
        every { evaluator2.evaluate(group) } returns Score(80)
        Evaluator.with(evaluator1, evaluator2).evaluate(group) shouldEqual Score(150)
        verify { evaluator1.evaluate(group) }
        verify { evaluator2.evaluate(group) }
    }

    @Test
    fun `with throws an exception when nothing is given for the argument`() {
        { Evaluator.with() } shouldThrow RuntimeException::class
    }

    @Test
    fun `attachScore(Group) adds score to given group`() {
        val group = Group(Members(listOf(Member("a"), Member("c"))))
        val expected = ScoredGroup(group, Score(-70))

        val target = spyk(object : Evaluator {
            override fun evaluate(group: Group): Score = throw Error("Should not reach this function.")
        })

        every { target.evaluate(group) } returns Score(-70)

        target.attachScore(group) shouldEqual expected

        verify { target.evaluate(group) }
    }

    @Test
    fun `attachScore(Lot) adds score to given lot`() {
        val group1 = mockk<Group>()
        val group2 = mockk<Group>()
        val lot = Lot(listOf(group1, group2))

        val scoredGroup1 = ScoredGroup(group1, Score(-70))
        val scoredGroup2 = ScoredGroup(group2, Score(-210))
        val expected = ScoredLot(listOf(scoredGroup1, scoredGroup2))

        val target = spyk(object : Evaluator {
            override fun evaluate(group: Group): Score = throw Error("Should not reach this function.")
        })

        every { target.attachScore(group1) } returns scoredGroup1
        every { target.attachScore(group2) } returns scoredGroup2

        target.attachScore(lot) shouldEqual expected

        verify { target.attachScore(group1) }
        verify { target.attachScore(group2) }
    }

    @Test
    fun `attachScore(Lots) adds score to given lots`() {
        val lot1 = mockk<Lot>()
        val lot2 = mockk<Lot>()
        val lots = Lots(listOf(lot1, lot2))

        val scoredLot1 = mockk<ScoredLot>()
        val scoredLot2 = mockk<ScoredLot>()
        val expected = ScoredLots(listOf(scoredLot1, scoredLot2))

        val target = spyk(object : Evaluator {
            override fun evaluate(group: Group): Score = throw Error("Should not reach this function.")
        })

        every { target.attachScore(lot1) } returns scoredLot1
        every { target.attachScore(lot2) } returns scoredLot2

        target.attachScore(lots) shouldEqual expected

        verify { target.attachScore(lot1) }
        verify { target.attachScore(lot2) }
    }
}