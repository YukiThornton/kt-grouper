package project.grouper.usecase

import io.mockk.*
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import project.grouper.domain.*

class HighestScoredGroupLotTest {
    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @AfterEach
    fun tearDown() = unmockkAll()

    @Test
    fun `generate returns a highest scored group lot`() {
        mockkObject(GroupLotGenerator.Companion)
        val requirement = mockk<Requirement>()
        val userInput = UserInput(requirement)
        val generatedLot = mockk<GroupLot>()
        val evaluator = mockk<Evaluator>()
        val scoredLot = mockk<ScoredGroupLot>()
        val highestScoredLot = mockk<ScoredGroupLot>()

        every { GroupLotGenerator.random(userInput.requirement) } returns generatedLot
        every { Input(userInput).createEvaluator() } returns evaluator
        every { evaluator.score(generatedLot) } returns scoredLot
        every { ScoredGroupLots(listOf(scoredLot, scoredLot, scoredLot)).pickHighest() } returns highestScoredLot

        HighestScoredGroupLot().generate(userInput) shouldEqual scoredLot

        verify { GroupLotGenerator.random(userInput.requirement) }
        verify { Input(userInput).createEvaluator() }
        verify { evaluator.score(generatedLot) }
        verify { ScoredGroupLots(listOf(scoredLot, scoredLot, scoredLot)).pickHighest() }
    }
}