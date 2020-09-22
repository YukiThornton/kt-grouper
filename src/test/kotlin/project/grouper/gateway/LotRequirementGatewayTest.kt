package project.grouper.gateway

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.Test
import project.grouper.Mocked
import project.grouper.domain.GroupCount
import project.grouper.domain.Member
import project.grouper.domain.Members
import project.grouper.domain.LotRequirement
import project.grouper.driver.CsvDriver

class LotRequirementGatewayTest: Mocked() {
    @InjectMockKs
    private lateinit var target: LotRequirementGateway

    @MockK
    private lateinit var csvDriver: CsvDriver

    @Test
    fun `getLotRequirement returns lot requirement from csv content`() {
        val requirement = LotRequirement.of(
            GroupCount(2),
            Members(listOf(Member("a"), Member("b"), Member("c")))
        )
        every { csvDriver.readCells("input/group-count.csv") } returns listOf(listOf("2"))
        every { csvDriver.readCells("input/members.csv") } returns listOf(listOf("a"), listOf("b", "d"), listOf("c", "e"))

        target.getLotRequirement() shouldEqual requirement

        verify { csvDriver.readCells("input/group-count.csv") }
        verify { csvDriver.readCells("input/members.csv") }
    }

    @Test
    fun `getLotRequirement throws an exception when groupCount csv does not have any content`() {
        every { csvDriver.readCells("input/group-count.csv") } returns emptyList()

        invoking { target.getLotRequirement() } shouldThrow Exception::class

        verify { csvDriver.readCells("input/group-count.csv") }
    }
}