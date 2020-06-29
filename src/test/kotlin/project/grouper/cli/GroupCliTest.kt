package project.grouper.cli

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import project.grouper.domain.*
import project.grouper.usecase.HighestScoredGroupLot
import project.grouper.util.*

class GroupCliTest {
    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @AfterEach
    fun tearDown() = unmockkAll()

    @InjectMockKs
    private lateinit var target: GroupCli

    @MockK
    private lateinit var usecase: HighestScoredGroupLot

    @Test
    fun loadUserInputReturnsUserInput() {
        mockkStatic("project.grouper.util.CsvKt")
        val targetSpy = spyk(target)
        val expected = UserInput(Requirement(
            GroupCount(5),
            Members(listOf(Member("a"), Member("b")))
        ))

        every { readAsLines("groupRequestPath") } returns listOf(listOf("a", "c"), listOf("b", "d"))
        targetSpy.loadUserInput(5, "groupRequestPath") shouldEqual expected
        verify { readAsLines("groupRequestPath") }
    }

    @Test
    fun testGenerateGroupLotReturnsGroupLot() {
        val targetSpy = spyk(target)

        val groupRequestPath = "groupRequestPath"
        val userInput = mockk<UserInput>()
        val scoredGroupLot = mockk<ScoredGroupLot>()

        every { targetSpy.loadUserInput(5, groupRequestPath) } returns userInput
        every { usecase.generate(userInput) } returns scoredGroupLot
        every { targetSpy.writeGroupLot(scoredGroupLot) } just runs

        targetSpy.generateGroupLot(5, groupRequestPath)

        verify { targetSpy.loadUserInput(5, groupRequestPath) }
        verify { usecase.generate(userInput) }
        verify { targetSpy.writeGroupLot(scoredGroupLot) }
    }

    @Test
    fun testWriteGroupLot() {
        mockkStatic("project.grouper.util.CsvKt")
        val targetSpy = spyk(target)

        val scoredGroupLot = ScoredGroupLot(listOf(
            ScoredGroup(Members(listOf(Member("a"), Member("b"))), Score(40)),
            ScoredGroup(Members(listOf(Member("c"), Member("d"))), Score(60))
        ), Score(100))
        val resultFileName = "resultFileName"

        every { targetSpy.generateResultFileName() } returns resultFileName
        every { writeTo(resultFileName, listOf(listOf("a", "b"), listOf("c", "d"))) } just runs

        targetSpy.writeGroupLot(scoredGroupLot)

        verify { targetSpy.generateResultFileName() }
        verify { writeTo(resultFileName, listOf(listOf("a", "b"), listOf("c", "d"))) }

    }
}

