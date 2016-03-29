package game.battle

import akka.actor.{ ActorSystem, Props }

final case class DisplayState(state: BattleStateSnapshot)

object Main extends App {
  val system = ActorSystem()

  val player = new UnitSnapshot("Player", 100, Option(16), Waiting)
  val enemy = new UnitSnapshot("Enemy", 20, None, Waiting)

  val state = new BattleStateSnapshot(Seq(player, enemy))

  val output = system.actorOf(Props(new ConsoleOutputActor))

  output ! DisplayState(state)
}
