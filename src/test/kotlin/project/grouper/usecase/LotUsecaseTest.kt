package project.grouper.usecase

import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import project.grouper.domain.*
import project.grouper.gateway.LotGateway
import project.grouper.gateway.RequirementGateway

class LotUsecaseTest {
    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @AfterEach
    fun tearDown() = unmockkAll()

    private val requirementPort = mockk<RequirementGateway>()
    private val lotPort = mockk<LotGateway>()

    private val target = LotUsecase(requirementPort, lotPort)

    @Test
    fun `generateRandomLot returns a highest scored group lot`() {
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
}