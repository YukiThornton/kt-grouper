package project.grouper.usecase

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import project.grouper.domain.*
import project.grouper.gateway.LotGateway
import project.grouper.gateway.RequirementGateway
import project.grouper.port.HistoryPort

class LotUsecaseTest {
    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @AfterEach
    fun tearDown() = unmockkAll()

    @InjectMockKs
    private lateinit var target: LotUsecase

    @MockK
    private lateinit var requirementPort: RequirementGateway

    @MockK
    private lateinit var historyPort: HistoryPort

    @MockK
    private lateinit var lotPort: LotGateway


    @Test
    fun `generateRandomLot returns a randomly generated group lot`() {
        val requirement = mockk<Requirement>()
        val groupSize = mockk<MaxGroupSize>()
        val lot = mockk<Lot>()

        mockkObject(LotGenerator.Companion)
        every { requirementPort.getRequirement() } returns requirement
        every { requirement.maxGroupSize() } returns groupSize
        every { LotGenerator.generateRandomLot(groupSize, requirement.members) } returns lot
        every { lotPort.saveLot(lot) } just runs

        target.generateRandomLot()

        verify { requirementPort.getRequirement() }
        verify { requirement.maxGroupSize() }
        verify { LotGenerator.generateRandomLot(groupSize, requirement.members) }
        verify { lotPort.saveLot(lot) }
    }

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

        target.generateLotWithHighestScore()

        verify { requirementPort.getRequirement() }
        verify { historyPort.getHistory() }
        verify { requirement.maxGroupSize() }
        verify { LotGenerator.generateRandomLots(groupSize, requirement.members, 100) }
        verify { history.score(lots) }
        verify { scoredLots.highest() }
        verify { lotPort.saveScoredLot(scoredLot) }
    }
}