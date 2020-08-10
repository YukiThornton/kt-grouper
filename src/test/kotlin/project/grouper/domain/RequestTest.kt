package project.grouper.domain

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class RequestTest {

    @Test
    fun `score counts matching pairs in given group and calculates the score`() {

        val request = Request(listOf(
            RequestedPair(Member("a"), Member("b"), RequestType.SAME_GROUP),
            RequestedPair(Member("b"), Member("a"), RequestType.SAME_GROUP),
            RequestedPair(Member("b"), Member("c"), RequestType.SAME_GROUP),
            RequestedPair(Member("a"), Member("e"), RequestType.SAME_GROUP)
        ))
        val group1 = Group(Members(listOf(Member("a"), Member("b"), Member("c"))))
        request.score(group1) shouldEqual Score(180)
        val group2 = Group(Members(listOf(Member("b"), Member("c"))))
        request.score(group2) shouldEqual Score(60)
        val group3 = Group(Members(listOf(Member("b"), Member("e"))))
        request.score(group3) shouldEqual Score(0)
    }
}