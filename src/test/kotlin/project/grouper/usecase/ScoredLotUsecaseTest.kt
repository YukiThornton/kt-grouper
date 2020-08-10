package project.grouper.usecase

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Test
import project.grouper.Mocked
import project.grouper.domain.*
import project.grouper.port.HistoryPort
import project.grouper.port.LotPort
import project.grouper.port.RequestPort
import project.grouper.port.RequirementPort

class ScoredLotUsecaseTest: Mocked() {
    @InjectMockKs
    private lateinit var target: ScoredLotUsecase

    @MockK
    private lateinit var requirementPort: RequirementPort

    @MockK
    private lateinit var requestPort: RequestPort

    @MockK
    private lateinit var historyPort: HistoryPort

    @MockK
    private lateinit var lotPort: LotPort

    @Test
    fun `generate generates a random group lot with highest score`() {
        val requirement = mockk<Requirement>()
        val request = mockk<Request>()
        val groupSize = mockk<MaxGroupSize>()
        val history = mockk<History>()
        val lots = mockk<Lots>()
        val evaluator = mockk<Evaluator>()
        val scoredLots = mockk<ScoredLots>()
        val scoredLot = mockk<ScoredLot>()

        mockkObject(LotGenerator.Companion)
        mockkObject(Evaluator.Companion)
        every { requirementPort.getRequirement() } returns requirement
        every { requestPort.getRequest() } returns request
        every { historyPort.getHistory() } returns history
        every { requirement.maxGroupSize() } returns groupSize
        every { LotGenerator.generateRandomLots(groupSize, requirement.members, 100) } returns lots
        every { Evaluator.with(request, history) } returns evaluator
        every { evaluator.addScore(lots) } returns scoredLots
        every { scoredLots.highest() } returns scoredLot
        every { lotPort.saveScoredLot(scoredLot) } just runs

        target.generate()

        verify { requirementPort.getRequirement() }
        verify { requestPort.getRequest() }
        verify { historyPort.getHistory() }
        verify { requirement.maxGroupSize() }
        verify { LotGenerator.generateRandomLots(groupSize, requirement.members, 100) }
        verify { Evaluator.with(request, history) }
        verify { evaluator.addScore(lots) }
        verify { scoredLots.highest() }
        verify { lotPort.saveScoredLot(scoredLot) }
    }
}