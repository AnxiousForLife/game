package game.battle

sealed class BattleUnitStatus(str: String) { override def toString() = str }

final object Waiting extends BattleUnitStatus("waiting")
final object Attacking extends BattleUnitStatus("attacking")
final object Casting extends BattleUnitStatus("casting")
final object CoolingDown extends BattleUnitStatus("cooling down")
final object Stunned extends BattleUnitStatus("stunned")