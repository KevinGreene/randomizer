import scala.util.Random

object Picker {
  val random = new Random(System.currentTimeMillis())

  def choose(objects: List[String], count: Int): List[String] = {
    println(Random.nextInt(10))
    random.shuffle(objects).take(count)
  }

  def roll(min: Int, max: Int): Int = {
    random.nextInt(max + 1- min) + min
  }
}
