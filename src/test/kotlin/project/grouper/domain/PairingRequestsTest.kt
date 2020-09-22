package project.grouper.domain

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class PairingRequestsTest {

    @Test
    fun `evaluate counts matching pairs in given group and calculates the score`() {

        val requests = PairingRequests(listOf(
            PairingRequest(Member("a"), Member("b"), PairingRequestType.SAME_GROUP),
            PairingRequest(Member("b"), Member("a"), PairingRequestType.SAME_GROUP),
            PairingRequest(Member("a"), Member("c"), PairingRequestType.BLOCK),
            PairingRequest(Member("b"), Member("c"), PairingRequestType.SAME_GROUP),
            PairingRequest(Member("a"), Member("e"), PairingRequestType.SAME_GROUP)
        ))
        val group1 = Group(Members(listOf(Member("b"), Member("e"))))
        requests.evaluate(group1) shouldEqual Score(0)
        val group2 = Group(Members(listOf(Member("b"), Member("c"))))
        requests.evaluate(group2) shouldEqual Score(60)
        val group3 = Group(Members(listOf(Member("a"), Member("c"))))
        requests.evaluate(group3) shouldEqual Score(-50)
        val group4 = Group(Members(listOf(Member("a"), Member("b"), Member("c"))))
        requests.evaluate(group4) shouldEqual Score(130)
    }
}