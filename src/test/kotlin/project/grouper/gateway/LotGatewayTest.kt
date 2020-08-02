package project.grouper.gateway

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import project.grouper.domain.*
import project.grouper.driver.CsvDriver

class LotGatewayTest {

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @AfterEach
    fun tearDown() = unmockkAll()

    @InjectMockKs
    private lateinit var target: LotGateway

    @MockK
    private lateinit var csvDriver: CsvDriver

    @Test
    fun `saveLot saves a lot to csv file`() {
        val targetSpy = spyk(target)
        val groupLot = Lot(listOf(
            Group(Members(listOf(Member("a"), Member("b")))),
            Group(Members(listOf(Member("c"), Member("d"))))
        ))
        val fileName = "fileName"
        val filePath = "output/fileName"

        every { targetSpy.generateUniqueLotFileName() } returns fileName
        every { csvDriver.writeCells(filePath, listOf(listOf("a", "b"), listOf("c", "d"))) } just runs

        targetSpy.saveLot(groupLot)

        verify { targetSpy.generateUniqueLotFileName() }
        verify { csvDriver.writeCells(filePath, listOf(listOf("a", "b"), listOf("c", "d"))) }
    }

    @Test
    fun `saveScoredLot saves a lot to csv file`() {
        val targetSpy = spyk(target)
        val scoredLot = ScoredLot(listOf(
            ScoredGroup(Group(Members(listOf(Member("a"), Member("b")))), Score(30)),
            ScoredGroup(Group(Members(listOf(Member("c"), Member("d")))), Score(10))
        ))
        val fileName = "fileName"
        val filePath = "output/fileName"

        every { targetSpy.generateUniqueLotFileName() } returns fileName
        every { csvDriver.writeCells(filePath, listOf(listOf("a", "b"), listOf("c", "d"))) } just runs

        targetSpy.saveScoredLot(scoredLot)

        verify { targetSpy.generateUniqueLotFileName() }
        verify { csvDriver.writeCells(filePath, listOf(listOf("a", "b"), listOf("c", "d"))) }
    }
}