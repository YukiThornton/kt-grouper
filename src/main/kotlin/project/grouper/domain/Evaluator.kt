package project.grouper.domain

import java.lang.RuntimeException

interface Evaluator {

    fun score(group: Group): Score

    companion object {
        fun with(vararg evaluators: Evaluator): Evaluator {
            if (evaluators.isEmpty()) throw RuntimeException("Evaluators are empty.")
            return object : Evaluator {
                override fun score(group: Group): Score {
                    return evaluators.map { it.score(group) }.reduce { acc, score -> acc + score }
                }
            }
        }
    }

    fun addScore(group: Group): ScoredGroup {
        return ScoredGroup(group, score(group))
    }

    fun addScore(lot: Lot): ScoredLot {
        return lot.map { group ->  addScore(group) }.let(::ScoredLot)
    }

    fun addScore(lots: Lots): ScoredLots {
        return lots.map{ lot ->  addScore(lot) }.let(::ScoredLots)
    }
}