package project.grouper.domain

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class LotGeneratorTest {

    @Test
    fun generateRandomLot() {
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

}