package project.grouper.usecase

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Test
import project.grouper.Mocked
import project.grouper.domain.Lot
import project.grouper.domain.LotGenerator
import project.grouper.domain.MaxGroupSize
import project.grouper.domain.Requirement
import project.grouper.port.LotPort
import project.grouper.port.RequirementPort

class RandomLotUsecaseTest : Mocked() {
    @InjectMockKs
    private lateinit var target: RandomLotUsecase

    @MockK
    private lateinit var requirementPort: RequirementPort

    @MockK
    private lateinit var lotPort: LotPort

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

        target.generate()

        verify { requirementPort.getRequirement() }
        verify { requirement.maxGroupSize() }
        verify { LotGenerator.generateRandomLot(groupSize, requirement.members) }
        verify { lotPort.saveLot(lot) }
    }
}