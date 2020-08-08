package project.grouper.domain

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class GroupTest {
    @Test
    fun `allPairCombinations creates all possible pairs`() {
        val members = mockk<Members>()
        val target = Group(members)

        val expected = setOf(
            Pair(Member("a"), Member("b")),
            Pair(Member("a"), Member("c")),
            Pair(Member("b"), Member("c"))
        )

        every { members.allPairCombinations() } returns expected

        target.allPairCombinations() shouldEqual expected

        verify { members.allPairCombinations() }
    }

}