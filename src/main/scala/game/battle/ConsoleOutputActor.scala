package game.battle

import akka.actor.Actor

class ConsoleOutputActor extends Actor {
  //Duplicates x String n times
  def repeatString(x: String, n: Int): String = x * n

  //Returns the display of a BattleStateSnapshot as a String
  def genDisplay(state: BattleStateSnapshot): String = {
    val hpLabel = "HP: "
    val mpLabel = "MP: "
    val divider = " | "

    val names = for (x <- state.units) yield x.name
    val hpsWithLabels = for (x <- state.units) yield hpLabel ++ x.hp.toString()
    val mpsWithLabels = for (x <- state.units) yield x.mp.map(mpLabel ++ _.toString()).getOrElse("")
    val statuses = for (x <- state.units) yield x.status.toString()

    //The length of the longest strings
    val maxNameLength = names.map(_.length()).max
    val maxHpLength = hpsWithLabels.map(_.length()).max
    val maxMpLength = mpsWithLabels.map(_.length()).max
    val maxStatusLength = statuses.map(_.length()).max

    val lineComponentLengths = Seq(maxNameLength, maxHpLength, maxMpLength, maxStatusLength)

    //Length of the horizontal dividers
    val minLineLength = (divider.length() * (lineComponentLengths.length - 1))
    val totalLineLength = minLineLength + lineComponentLengths.sum

    //The horizontal dividers
    val thickBorder = repeatString("=", totalLineLength)

    //Generates a list of each unit's display line
    val unitLines: Seq[String] = for ((x, i) <- state.units.zipWithIndex) yield {
      val name = names(i)
      val hpWithLabel = hpsWithLabels(i)
      val mpWithLabel = mpsWithLabels(i)
      val status = statuses(i).capitalize

      //Empty spaces needed to align the strings horizontally
      val nameSpaces = repeatString(" ", maxNameLength - name.length())
      val hpSpaces = repeatString(" ", maxHpLength - hpWithLabel.length())
      val mpSpaces = repeatString(" ", maxMpLength - mpWithLabel.length())
      val statusSpaces = repeatString(" ", maxStatusLength - status.length())

      //Aligned HP and MP labels made by inserting the appropriate amount of spaces before the number
      val alignedHp = hpWithLabel.split(' ').mkString(hpSpaces ++ " ")
      val alignedMp = if (mpWithLabel.length() == 0) mpSpaces else mpWithLabel.split(' ').mkString(mpSpaces ++ " ")

      thickBorder ++ "\n" ++ (Array(name ++ nameSpaces, alignedHp, alignedMp, status ++ statusSpaces).mkString(divider)) ++ "\n"
    }

    unitLines.reduceLeft(_ ++ _) ++ thickBorder
  }

  def receive = {
    case DisplayState(state) => println(genDisplay(state))
  }

}