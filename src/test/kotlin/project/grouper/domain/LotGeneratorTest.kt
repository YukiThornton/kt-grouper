package project.grouper.domain

import io.mockk.*
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class LotGeneratorTest {

    @Test
    fun `generateRandomLot returns a randomly generated group lot`() {
        val maxGroupSize = MaxGroupSize(3)
        val members = mockk<Members>()

        val shuffledMembers = Members(listOf(
            Member("c"), Member("e"), Member("d"),
            Member("b"), Member("a")
        ))
        val expected = Lot(
            listOf(
                Group(Members(listOf(Member("c"), Member("e"), Member("d")))),
                Group(Members(listOf(Member("b"), Member("a"))))
            )
        )

        every { members.shuffled() } returns shuffledMembers
        LotGenerator.generateRandomLot(maxGroupSize, members) shouldEqual expected
        verify { members.shuffled() }
    }

    @Test
    fun `generateRandomLots generates lots specified times`() {

        val maxGroupSize = MaxGroupSize(3)
        val members = mockk<Members>()
        val shuffledMembers = Members(listOf(
            Member("c"), Member("e"), Member("d"),
            Member("b"), Member("a")
        ))
        val expected = Lots(listOf(
            Lot(listOf(
                Group(Members(listOf(Member("c"), Member("e"), Member("d")))),
                Group(Members(listOf(Member("b"), Member("a"))))
            )),
            Lot(listOf(
                Group(Members(listOf(Member("c"), Member("e"), Member("d")))),
                Group(Members(listOf(Member("b"), Member("a"))))
            )),
            Lot(listOf(
                Group(Members(listOf(Member("c"), Member("e"), Member("d")))),
                Group(Members(listOf(Member("b"), Member("a"))))
            ))
        ))

        every { members.shuffled() } returns shuffledMembers
        LotGenerator.generateRandomLots(maxGroupSize, members, 3) shouldEqual expected
        verify(exactly = 3) { members.shuffled() }
    }

}