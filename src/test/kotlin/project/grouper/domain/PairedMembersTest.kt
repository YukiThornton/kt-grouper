package project.grouper.domain

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class PairedMembersTest {

    @Test
    fun `equals returns true when two values are matched`() {
        PairedMembers(Member("a"), Member("b")).equals(PairedMembers(Member("a"), Member("b"))) shouldEqual true
        PairedMembers(Member("a"), Member("b")).equals(PairedMembers(Member("b"), Member("a"))) shouldEqual true
        PairedMembers(Member("a"), Member("b")).equals(PairedMembers(Member("a"), Member("c"))) shouldEqual false
        PairedMembers(Member("a"), Member("b")).equals(PairedMembers(Member("c"), Member("b"))) shouldEqual false
        val pairedMembers = PairedMembers(Member("a"), Member("b"))
        pairedMembers.equals(pairedMembers) shouldEqual true
        PairedMembers(Member("a"), Member("b")).equals(Pair(Member("a"), Member("b"))) shouldEqual false
    }

    @Test
    fun `hashCode returns same value when values are same`() {
        val pairedMembers1 = PairedMembers(Member("a"), Member("b"))
        val pairedMembers2 = PairedMembers(Member("a"), Member("b"))
        val pairedMembers3 = PairedMembers(Member("b"), Member("a"))
        val pairedMembers4 = PairedMembers(Member("a"), Member("c"))
        val pairedMembers5 = PairedMembers(Member("c"), Member("a"))
        (pairedMembers1.hashCode() == pairedMembers2.hashCode()) shouldEqual true
        (pairedMembers1.hashCode() == pairedMembers3.hashCode()) shouldEqual true
        (pairedMembers1.hashCode() == pairedMembers4.hashCode()) shouldEqual false
        (pairedMembers3.hashCode() == pairedMembers5.hashCode()) shouldEqual false
    }
}