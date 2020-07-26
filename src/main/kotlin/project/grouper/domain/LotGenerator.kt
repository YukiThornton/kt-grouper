package project.grouper.domain

class LotGenerator {
    companion object {
        fun generateRandomLot(maxGroupSize: MaxGroupSize, members: Members): Lot {
            return Lot.of(members.shuffled().splitInto(maxGroupSize))
        }
    }
}