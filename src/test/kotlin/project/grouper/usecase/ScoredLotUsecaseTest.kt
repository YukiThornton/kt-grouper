package project.grouper.usecase

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Test
import project.grouper.Mocked
import project.grouper.domain.*
import project.grouper.port.HistoryPort
import project.grouper.port.LotPort
import project.grouper.port.LotRequirementPort
import project.grouper.port.PairingRequestPort

class ScoredLotUsecaseTest: Mocked() {
    @InjectMockKs
    private lateinit var target: ScoredLotUsecase

    @MockK
    private lateinit var lotRequirementPort: LotRequirementPort

    @MockK
    private lateinit var pairingRequestPort: PairingRequestPort

    @MockK
    private lateinit var historyPort: HistoryPort

    @MockK
    private lateinit var lotPort: LotPort

    @Test
    fun `generates a random group lot with highest score and saves it`() {
        val requirement = mockk<LotRequirement>()
        val request = mockk<PairingRequests>()
        val history = mockk<History>()
        val lots = mockk<Lots>()
        val evaluator = mockk<Evaluator>()
        val scoredLots = mockk<ScoredLots>()
        val scoredLot = mockk<ScoredLot>()

        mockkObject(Evaluator.Companion)
        every { lotRequirementPort.getLotRequirement() } returns requirement
        every { requirement.generateRandomLots(100) } returns lots
        every { pairingRequestPort.getPairingRequests() } returns request
        every { historyPort.getHistory() } returns history
        every { Evaluator.with(request, history) } returns evaluator
        every { evaluator.attachScore(lots) } returns scoredLots
        every { scoredLots.highest() } returns scoredLot
        every { lotPort.saveScoredLot(scoredLot) } just runs

        target.generateLotsAndSaveHighest()

        verify { lotRequirementPort.getLotRequirement() }
        verify { requirement.generateRandomLots(100) }
        verify { pairingRequestPort.getPairingRequests() }
        verify { historyPort.getHistory() }
        verify { Evaluator.with(request, history) }
        verify { evaluator.attachScore(lots) }
        verify { scoredLots.highest() }
        verify { lotPort.saveScoredLot(scoredLot) }
    }
}