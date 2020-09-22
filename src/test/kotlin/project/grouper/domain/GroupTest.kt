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
            MemberPair(Member("a"), Member("b")),
            MemberPair(Member("a"), Member("c")),
            MemberPair(Member("b"), Member("c"))
        )

        every { members.allPossiblePairings() } returns expected

        target.allPossiblePairings() shouldEqual expected

        verify { members.allPossiblePairings() }
    }

}