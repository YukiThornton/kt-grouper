package project.grouper.usecase

import project.grouper.domain.*
import kotlin.math.ceil

class HighestScoredGroupLot {
    fun generate(userInput: UserInput): ScoredGroupLot {
//        val members = input.requirement.members.values
//        val groupSize: Int = ceil(members.size / input.requirement.groupCount.count.toDouble()).toInt()
//        val groups = members.toList().shuffled().chunked(groupSize).map { ScoredGroup(Members(it), Score(0)) }
//        return ScoredGroupLot(groups, Score(0))
        val evaluator = Input(userInput).createEvaluator()
        val lots = (1..3).map {
            evaluator.score(GroupLotGenerator.random(userInput.requirement))
        }.let(::ScoredGroupLots)
        return lots.pickHighest()
    }
}