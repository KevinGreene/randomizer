import scopt.OptionParser

case class Config(count: Int = 1, objects: List[String] = List(), mode: String = "choose")

object Main extends App {
  val parser = new OptionParser[Config]("randomizer") {
    override def showUsageOnError = true

    head("randomizer", "1.x")

    arg[String]("<options>...").unbounded().optional().action((x, c) =>
      c.copy(objects = c.objects :+ x)).text("Options to choose from")

    help("help").text("prints this usage text")
  }

  parser.parse(args, Config()) match {
    case Some(config) =>
      config.mode match {
        case "choose" =>
          Picker.choose(config.objects, config.count).foreach(println)
      }

    case None =>
    // arguments are bad, error message will have been displayed
  }
}