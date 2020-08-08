package project.grouper.domain

import io.mockk.mockk
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class RequirementTest {

    @Test
    fun maxGroupSize() {
        val target1 = Requirement(GroupCount(2), Members(listOf(mockk(), mockk(), mockk(), mockk())))
        target1.maxGroupSize() shouldEqual MaxGroupSize(2)

        val target2 = Requirement(GroupCount(3), Members(listOf(mockk(), mockk(), mockk(), mockk())))
        target2.maxGroupSize() shouldEqual MaxGroupSize(2)

        val target3 = Requirement(GroupCount(3), Members(listOf(mockk(), mockk(), mockk(), mockk(), mockk())))
        target3.maxGroupSize() shouldEqual MaxGroupSize(2)
    }
}