package project.grouper.domain

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class RequestTest {

    @Test
    fun `score counts matching pairs in given group and calculates the score`() {

        val request = Request(listOf(
            RequestedPair(Member("a"), Member("b"), RequestType.SAME_GROUP),
            RequestedPair(Member("b"), Member("a"), RequestType.SAME_GROUP),
            RequestedPair(Member("a"), Member("c"), RequestType.BLOCK),
            RequestedPair(Member("b"), Member("c"), RequestType.SAME_GROUP),
            RequestedPair(Member("a"), Member("e"), RequestType.SAME_GROUP)
        ))
        val group1 = Group(Members(listOf(Member("b"), Member("e"))))
        request.score(group1) shouldEqual Score(0)
        val group2 = Group(Members(listOf(Member("b"), Member("c"))))
        request.score(group2) shouldEqual Score(60)
        val group3 = Group(Members(listOf(Member("a"), Member("c"))))
        request.score(group3) shouldEqual Score(-50)
        val group4 = Group(Members(listOf(Member("a"), Member("b"), Member("c"))))
        request.score(group4) shouldEqual Score(130)
    }
}