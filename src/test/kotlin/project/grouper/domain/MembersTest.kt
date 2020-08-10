package project.grouper.domain

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class MembersTest {
    @Test
    fun `uniquePairCombinations creates all possible pairs`() {
        val target = Members(listOf(Member("a"), Member("b"), Member("c")))

        val expected = setOf(
            PairedMembers(Member("a"), Member("b")),
            PairedMembers(Member("a"), Member("c")),
            PairedMembers(Member("b"), Member("c"))
        )

        target.uniquePairCombinations() shouldEqual expected
    }

}