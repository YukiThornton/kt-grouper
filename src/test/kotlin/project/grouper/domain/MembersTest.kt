package project.grouper.domain

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class MembersTest {
    @Test
    fun `allPairCombinations creates all possible pairs`() {
        val target = Members(listOf(Member("a"), Member("b"), Member("c")))

        val expected = setOf(
            Pair(Member("a"), Member("b")),
            Pair(Member("a"), Member("c")),
            Pair(Member("b"), Member("c"))
        )

        target.allPairCombinations() shouldEqual expected
    }

}