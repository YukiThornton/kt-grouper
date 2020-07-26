package project.grouper.domain

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class LotTest {
    @Test
    fun `of creates Lot with split members`() {
        val splitMembers = listOf(
            Members(listOf(Member("a"), Member("b"))),
            Members(listOf(Member("c"), Member("d")))
        )
        val expected = Lot(listOf(
            Group(Members(listOf(Member("a"), Member("b")))),
            Group(Members(listOf(Member("c"), Member("d"))))
        ))
        Lot.of(splitMembers) shouldEqual expected
    }
}