package project.grouper.domain

interface Evaluator {

    fun evaluate(group: Group): Score

    companion object {
        fun with(vararg evaluators: Evaluator): Evaluator {
            if (evaluators.isEmpty()) throw IllegalArgumentException("Evaluators are empty.")
            return object : Evaluator {
                override fun evaluate(group: Group): Score {
                    return evaluators.map { it.evaluate(group) }.reduce { acc, score -> acc + score }
                }
            }
        }
    }

    fun attachScore(group: Group): ScoredGroup {
        return ScoredGroup(group, evaluate(group))
    }

    fun attachScore(lot: Lot): ScoredLot {
        return lot.map { group ->  attachScore(group) }.let(::ScoredLot)
    }

    fun attachScore(lots: Lots): ScoredLots {
        return lots.map{ lot ->  attachScore(lot) }.let(::ScoredLots)
    }
}