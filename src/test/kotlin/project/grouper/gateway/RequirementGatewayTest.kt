package project.grouper.gateway

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import project.grouper.domain.GroupCount
import project.grouper.domain.Member
import project.grouper.domain.Members
import project.grouper.domain.Requirement
import project.grouper.driver.CsvDriver

class RequirementGatewayTest {
    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @AfterEach
    fun tearDown() = unmockkAll()

    @InjectMockKs
    private lateinit var target: RequirementGateway

    @MockK
    private lateinit var csvDriver: CsvDriver

    @Test
    fun getRequirement() {
        val requirement = Requirement(
            GroupCount(5),
            Members(listOf(Member("a"), Member("b")))
        )
        every { csvDriver.readCells("input/requirement.csv") } returns listOf(listOf("a", "c"), listOf("b", "d"))
        target.getRequirement() shouldEqual requirement
        verify { csvDriver.readCells("input/requirement.csv") }
    }
}