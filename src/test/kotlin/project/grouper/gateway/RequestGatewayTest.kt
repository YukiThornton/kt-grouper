package project.grouper.gateway

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test
import project.grouper.Mocked
import project.grouper.domain.Member
import project.grouper.domain.Request
import project.grouper.domain.RequestType
import project.grouper.domain.RequestedPair

import project.grouper.driver.CsvDriver

class RequestGatewayTest: Mocked() {
    @InjectMockKs
    private lateinit var target: RequestGateway

    @MockK
    private lateinit var csvDriver: CsvDriver

    @Test
    fun `getRequest returns request from csv content`() {
        val expected = Request(listOf(
            RequestedPair(Member("a"), Member("b"), RequestType.SAME_GROUP),
            RequestedPair(Member("a"), Member("c"), RequestType.SAME_GROUP),
            RequestedPair(Member("c"), Member("d"), RequestType.SAME_GROUP),
            RequestedPair(Member("e"), Member("f"), RequestType.BLOCK),
            RequestedPair(Member("e"), Member("g"), RequestType.BLOCK),
            RequestedPair(Member("g"), Member("h"), RequestType.BLOCK)
        ))

        every { csvDriver.readCells("input/group-request.csv") } returns listOf(listOf("a", "b", "c"), listOf("c", "d"))
        every { csvDriver.readCells("input/block-request.csv") } returns listOf(listOf("e", "f", "g"), listOf("g", "h"))

        target.getRequest() shouldEqual expected

        verify { csvDriver.readCells("input/group-request.csv") }
        verify { csvDriver.readCells("input/block-request.csv") }
    }
}