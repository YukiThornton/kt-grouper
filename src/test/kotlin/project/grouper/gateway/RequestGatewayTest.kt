package project.grouper.gateway

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test
import project.grouper.Mocked
import project.grouper.domain.Member
import project.grouper.domain.PairingRequests
import project.grouper.domain.PairingRequestType
import project.grouper.domain.PairingRequest

import project.grouper.driver.CsvDriver

class RequestGatewayTest: Mocked() {
    @InjectMockKs
    private lateinit var target: PairingRequestGateway

    @MockK
    private lateinit var csvDriver: CsvDriver

    @Test
    fun `getPairingRequests returns request from csv content`() {
        val expected = PairingRequests(listOf(
            PairingRequest(Member("a"), Member("b"), PairingRequestType.SAME_GROUP),
            PairingRequest(Member("a"), Member("c"), PairingRequestType.SAME_GROUP),
            PairingRequest(Member("c"), Member("d"), PairingRequestType.SAME_GROUP),
            PairingRequest(Member("e"), Member("f"), PairingRequestType.BLOCK),
            PairingRequest(Member("e"), Member("g"), PairingRequestType.BLOCK),
            PairingRequest(Member("g"), Member("h"), PairingRequestType.BLOCK)
        ))

        every { csvDriver.readCells("input/group-request.csv") } returns listOf(listOf("a", "b", "c"), listOf("c", "d"))
        every { csvDriver.readCells("input/block-request.csv") } returns listOf(listOf("e", "f", "g"), listOf("g", "h"))

        target.getPairingRequests() shouldEqual expected

        verify { csvDriver.readCells("input/group-request.csv") }
        verify { csvDriver.readCells("input/block-request.csv") }
    }
}