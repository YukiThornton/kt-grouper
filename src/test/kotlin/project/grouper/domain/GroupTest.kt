package project.grouper.domain

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class GroupTest {
    @Test
    fun `uniquePairCombinations creates all possible pairs`() {
        val members = mockk<Members>()
        val target = Group(members)

        val expected = setOf(
            PairedMembers(Member("a"), Member("b")),
            PairedMembers(Member("a"), Member("c")),
            PairedMembers(Member("b"), Member("c"))
        )

        every { members.allPossiblePairings() } returns expected

        target.allPossiblePairings() shouldEqual expected

        verify { members.allPossiblePairings() }
    }

}