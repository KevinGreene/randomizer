import scopt.OptionParser

case class Config(count: Int = 1, objects: List[String] = List(), mode: String = "choose")


object Main extends App {
  val parser = new OptionParser[Config]("randomizer") {
    override def showUsageOnError = true

    head("randomizer", "1.x")

    val flipConfig = Config(objects = List("Heads", "Tails"))
    val diceConfig = Config(objects = List("1", "6"), mode = "roll")

    cmd("flip").action((_, _) => flipConfig).
      text("Flips a coin")
    cmd("dice").action((_, _) => diceConfig).
      text("Rolls a die")

    cmd("choose").action((_, c) => c.copy(mode = "choose")).
      text("choose picks from an unbounded number of strings.").
      children(
        opt[Int]('c', "count").action((x, c) =>
          c.copy(count = x)).text("count determines how many items will be picked"))

    cmd("roll").action((_, c) => c.copy(mode = "roll")).
      text("roll picks a number between the min and max arguments.").
      children(
        arg[String]("Min Max").minOccurs(2).maxOccurs(2).required().action((x, c) =>
          c.copy(objects = c.objects :+ x)).text("Min followed by Max"))

    arg[String]("<options>...").unbounded().optional().action((x, c) =>
      c.copy(objects = c.objects :+ x)).text("Options to choose from")

    help("help").text("prints this usage text")
  }

  parser.parse(args, Config()) match {
    case Some(config) =>
      config.mode match {
        case "choose" =>
          Picker.choose(config.objects, config.count).foreach(println)
        case "roll" =>
          println(Picker.roll(config.objects(0).toInt, config.objects(1).toInt))
      }

    case None =>
    // arguments are bad, error message will have been displayed
  }
}
