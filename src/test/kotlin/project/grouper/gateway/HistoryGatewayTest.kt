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
import project.grouper.domain.Group
import project.grouper.domain.History
import project.grouper.domain.Member
import project.grouper.domain.Members
import project.grouper.driver.CsvDriver

class HistoryGatewayTest {
    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @AfterEach
    fun tearDown() = unmockkAll()

    @InjectMockKs
    private lateinit var target: HistoryGateway

    @MockK
    private lateinit var csvDriver: CsvDriver

    @Test
    fun `getHistory returns history create from history files`() {
        val expected = History(listOf(
            Group(Members(listOf(Member("a"), Member("b")))),
            Group(Members(listOf(Member("c"), Member("d")))),
            Group(Members(listOf(Member("a"), Member("c"), Member("b")))),
            Group(Members(listOf(Member("d"))))
        ))
        val history1 = listOf(
            listOf("a", "b"),
            listOf("c", "d")
        )
        val history2 = listOf(
            listOf("a", "c", "", "b"),
            listOf("d")
        )
        every { csvDriver.findCsvFileNames("input/history") } returns listOf("20200101.csv", "20200102.csv")
        every { csvDriver.readCells("input/history/20200101.csv") } returns history1
        every { csvDriver.readCells("input/history/20200102.csv") } returns history2

        target.getHistory() shouldEqual expected

        verify { csvDriver.findCsvFileNames("input/history") }
        verify { csvDriver.readCells("input/history/20200101.csv") }
        verify { csvDriver.readCells("input/history/20200102.csv") }
    }
}