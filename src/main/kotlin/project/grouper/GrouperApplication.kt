package project.grouper

import project.grouper.cli.GroupCli
import project.grouper.usecase.HighestScoredGroupLot

fun main(args: Array<String>) {
    GroupCli(HighestScoredGroupLot()).generateGroupLot(args[0].toInt(), args[1])
}
