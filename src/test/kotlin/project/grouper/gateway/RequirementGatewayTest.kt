package project.grouper.gateway

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import project.grouper.domain.GroupCount
import project.grouper.domain.Member
import project.grouper.domain.Members
import project.grouper.domain.Requirement
import project.grouper.driver.CsvDriver
import java.lang.Exception

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
    fun `getRequirement returns requirement from csv content`() {
        val requirement = Requirement(
            GroupCount(5),
            Members(listOf(Member("a"), Member("b")))
        )
        every { csvDriver.readCells("input/group-count.csv") } returns listOf(listOf("5"))
        every { csvDriver.readCells("input/members.csv") } returns listOf(listOf("a"), listOf("b", "d"))

        target.getRequirement() shouldEqual requirement

        verify { csvDriver.readCells("input/group-count.csv") }
        verify { csvDriver.readCells("input/members.csv") }
    }

    @Test
    fun `getRequirement throws an exception when groupCount csv does not have any content`() {
        every { csvDriver.readCells("input/group-count.csv") } returns emptyList()

        invoking { target.getRequirement() } shouldThrow Exception::class

        verify { csvDriver.readCells("input/group-count.csv") }
    }
}