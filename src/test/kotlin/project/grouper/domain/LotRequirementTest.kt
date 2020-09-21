package project.grouper.domain

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldContainAll
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.Test

class LotRequirementTest {

    @Test
    fun `of returns LotRequirement when values are valid`() {
        val members = Members(listOf(mockk(), mockk(), mockk()))
        val groupCount = GroupCount(2)
        val actual = LotRequirement.of(groupCount, members)
        actual.members shouldBe members
        actual.groupCount shouldBe groupCount
    }

    @Test
    fun `of throws InvalidLotRequirement when GroupCount is bigger than member count`() {
        val members = Members(listOf(mockk(), mockk(), mockk()))
        val groupCount = GroupCount(4);
        { LotRequirement.of(groupCount, members) } shouldThrow InvalidLotRequirement::class
    }

    @Test
    fun `of throws InvalidLotRequirement when GroupCount is same as member count`() {
        val members = Members(listOf(mockk(), mockk(), mockk()))
        val groupCount = GroupCount(3);
        { LotRequirement.of(groupCount, members) } shouldThrow InvalidLotRequirement::class
    }

    @Test
    fun `of throws InvalidLotRequirement when GroupCount is 1`() {
        val members = Members(listOf(mockk(), mockk(), mockk()))
        val groupCount = GroupCount(1);
        { LotRequirement.of(groupCount, members) } shouldThrow InvalidLotRequirement::class
    }

    @Test
    fun `generateRandomLot generates a lot randomly according to requirements`() {
        val members = Members(listOf(
            Member("a"), Member("b"), Member("c"), Member("d"), Member("e"), Member("f")
        ))
        val lot2 = LotRequirement.of(GroupCount(2), members).generateRandomLot()
        assertLotSizes(lot2, 2, 3, 3)
        assertAllMembersAreGrouped(members, lot2)

        val lot3 = LotRequirement.of(GroupCount(3), members).generateRandomLot()
        assertLotSizes(lot3, 3, 2, 2)
        assertAllMembersAreGrouped(members, lot3)

        val lot4 = LotRequirement.of(GroupCount(4), members).generateRandomLot()
        assertLotSizes(lot4, 4, 2, 1)
        assertAllMembersAreGrouped(members, lot4)

        val lot5 = LotRequirement.of(GroupCount(5), members).generateRandomLot()
        assertLotSizes(lot5, 5, 2, 1)
        assertAllMembersAreGrouped(members, lot5)
    }

    @Test
    fun `generateRandomLots generates lots randomly according to requirements`() {
        val lot = mockk<Lot>()

        val groupCount = GroupCount(2)
        val members = Members(listOf(mockk(), mockk(), mockk()))
        val target = spyk(LotRequirement.of(groupCount, members))
        every { target.generateRandomLot() } returns lot
        target.generateRandomLots(3) shouldEqual Lots(listOf(lot, lot, lot))
        verify { target.generateRandomLot() }
    }

    private fun assertLotSizes(
        actual: Lot,
        expectedGroupCount: Int,
        expectedMaxGroupSize: Int,
        expectedMinGroupSize: Int
    ) {
        actual.size() shouldEqual expectedGroupCount
        actual.maxBy { group -> group.members.size() }!!.members.size() shouldEqual expectedMaxGroupSize
        actual.minBy { group -> group.members.size() }!!.members.size() shouldEqual expectedMinGroupSize
    }

    private fun assertAllMembersAreGrouped(members: Members, actual: Lot) {
        val groupedMembers = actual.map { group -> group.members.list }.flatten()
        groupedMembers.size shouldEqual members.size()
        groupedMembers shouldContainAll members.list
    }

}