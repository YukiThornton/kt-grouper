package project.grouper.usecase

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Test
import project.grouper.Mocked
import project.grouper.domain.*
import project.grouper.port.HistoryPort
import project.grouper.port.LotPort
import project.grouper.port.RequirementPort

class ScoredLotUsecaseTest: Mocked() {
    @InjectMockKs
    private lateinit var target: ScoredLotUsecase

    @MockK
    private lateinit var requirementPort: RequirementPort

    @MockK
    private lateinit var historyPort: HistoryPort

    @MockK
    private lateinit var lotPort: LotPort

    @Test
    fun `generateLotWithHighestScore generates a random group lot with highest score`() {
        val requirement = mockk<Requirement>()
        val groupSize = mockk<MaxGroupSize>()
        val history = mockk<History>()
        val lots = mockk<Lots>()
        val scoredLots = mockk<ScoredLots>()
        val scoredLot = mockk<ScoredLot>()

        mockkObject(LotGenerator.Companion)
        every { requirementPort.getRequirement() } returns requirement
        every { historyPort.getHistory() } returns history
        every { requirement.maxGroupSize() } returns groupSize
        every { LotGenerator.generateRandomLots(groupSize, requirement.members, 100) } returns lots
        every { history.score(lots) } returns scoredLots
        every { scoredLots.highest() } returns scoredLot
        every { lotPort.saveScoredLot(scoredLot) } just runs

        target.generate()

        verify { requirementPort.getRequirement() }
        verify { historyPort.getHistory() }
        verify { requirement.maxGroupSize() }
        verify { LotGenerator.generateRandomLots(groupSize, requirement.members, 100) }
        verify { history.score(lots) }
        verify { scoredLots.highest() }
        verify { lotPort.saveScoredLot(scoredLot) }
    }
}