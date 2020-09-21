package project.grouper.usecase

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Test
import project.grouper.Mocked
import project.grouper.domain.Lot
import project.grouper.domain.LotRequirement
import project.grouper.port.LotPort
import project.grouper.port.LotRequirementPort

class RandomLotUsecaseTest : Mocked() {
    @InjectMockKs
    private lateinit var target: RandomLotUsecase

    @MockK
    private lateinit var lotRequirementPort: LotRequirementPort

    @MockK
    private lateinit var lotPort: LotPort

    @Test
    fun `generates a group lot randomly and saves it`() {
        val requirement = mockk<LotRequirement>()
        val lot = mockk<Lot>()

        every { lotRequirementPort.getRequirement() } returns requirement
        every { requirement.generateRandomLot() } returns lot
        every { lotPort.saveLot(lot) } just runs

        target.generateAndSave()

        verify { lotRequirementPort.getRequirement() }
        verify { requirement.generateRandomLot() }
        verify { lotPort.saveLot(lot) }
    }
}