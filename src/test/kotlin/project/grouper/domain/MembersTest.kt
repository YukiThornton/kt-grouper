package project.grouper.domain

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class MembersTest {
    @Test
    fun `uniquePairCombinations creates all possible pairs`() {
        val target = Members(listOf(Member("a"), Member("b"), Member("c")))

        val expected = setOf(
            MemberPair(Member("a"), Member("b")),
            MemberPair(Member("a"), Member("c")),
            MemberPair(Member("b"), Member("c"))
        )

        target.allPossiblePairings() shouldEqual expected
    }

}