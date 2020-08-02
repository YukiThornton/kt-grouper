package project.grouper.domain

class LotGenerator {
    companion object {
        fun generateRandomLot(maxGroupSize: MaxGroupSize, members: Members): Lot {
            return Lot.of(members.shuffled().splitInto(maxGroupSize))
        }

        fun generateRandomLots(groupSize: MaxGroupSize, members: Members, generationCount: Int): Lots {
            return (1..generationCount).map { generateRandomLot(groupSize, members) }.let(::Lots)
        }
    }
}