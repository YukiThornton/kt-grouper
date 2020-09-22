package project.grouper.domain

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class MemberPairTest {

    @Test
    fun `equals returns true when two values are matched`() {
        MemberPair(Member("a"), Member("b")).equals(MemberPair(Member("a"), Member("b"))) shouldEqual true
        MemberPair(Member("a"), Member("b")).equals(MemberPair(Member("b"), Member("a"))) shouldEqual true
        MemberPair(Member("a"), Member("b")).equals(MemberPair(Member("a"), Member("c"))) shouldEqual false
        MemberPair(Member("a"), Member("b")).equals(MemberPair(Member("c"), Member("b"))) shouldEqual false
        val pairedMembers = MemberPair(Member("a"), Member("b"))
        pairedMembers.equals(pairedMembers) shouldEqual true
        MemberPair(Member("a"), Member("b")).equals(Pair(Member("a"), Member("b"))) shouldEqual false
    }

    @Test
    fun `hashCode returns same value when values are same`() {
        val pairedMembers1 = MemberPair(Member("a"), Member("b"))
        val pairedMembers2 = MemberPair(Member("a"), Member("b"))
        val pairedMembers3 = MemberPair(Member("b"), Member("a"))
        val pairedMembers4 = MemberPair(Member("a"), Member("c"))
        val pairedMembers5 = MemberPair(Member("c"), Member("a"))
        (pairedMembers1.hashCode() == pairedMembers2.hashCode()) shouldEqual true
        (pairedMembers1.hashCode() == pairedMembers3.hashCode()) shouldEqual true
        (pairedMembers1.hashCode() == pairedMembers4.hashCode()) shouldEqual false
        (pairedMembers3.hashCode() == pairedMembers5.hashCode()) shouldEqual false
    }
}