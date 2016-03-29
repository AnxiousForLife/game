package game.battle

sealed trait BattleUnitStatus

final object Waiting extends BattleUnitStatus { override def toString = "waiting"}
final object Attacking extends BattleUnitStatus { override def toString = "attacking"}
final object Casting extends BattleUnitStatus { override def toString = "casting"}
final object CoolingDown extends BattleUnitStatus { override def toString = "cooling down"}
final object Stunned extends BattleUnitStatus { override def toString = "stunned"}


