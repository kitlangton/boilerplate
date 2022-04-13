package object boilerplate {
  sealed trait Boilerplate extends Product with Serializable { self =>
    def ++(that: Boilerplate): Boilerplate = Boilerplate.Zip(self, that)

    def rep(separator: String, adjustSize: Int = 0, fromStart: Boolean = true): Boilerplate =
      Boilerplate.Repeat(self, separator, adjustSize, fromStart)

    def printOne(size: Int): String =
      printImpl(size, 0).trim

    def print(from: Int, toInclusive: Int): String =
      (from to toInclusive).map { i =>
        printImpl(i, 0)
      }.mkString("\n")

    def printImpl(size: Int, n: Int): String =
      self match {
        case Boilerplate.TypeVariable(invert) =>
          if (!invert)
            ('A'.toInt + n).toChar.toString
          else
            (('A'.toInt + size) - 1 - n).toChar.toString
        case Boilerplate.Number(adjust) =>
          (n + adjust).toString
        case Boilerplate.Size(adjust) =>
          (size + adjust).toString
        case Boilerplate.Literal(string) =>
          string
        case Boilerplate.Repeat(boilerplate, separator, adjustSize, fromStart) =>
          val newSize    = size + adjustSize
          val difference = size - newSize
          (0 until newSize).map { n =>
            val newN = if (fromStart) n else n + difference
            boilerplate.printImpl(newSize, newN)
          }
            .mkString(separator)
        case Boilerplate.Zip(left, right) =>
          left.printImpl(size, n) + right.printImpl(size, n)

      }
  }

  object Boilerplate {
    final case class Number(adjust: Int = 0) extends Boilerplate

    final case class TypeVariable(invert: Boolean) extends Boilerplate {
      def last: Boilerplate = TypeVariable(true)

    }

    final case class Literal(string: String) extends Boilerplate

    final case class Repeat(boilerplate: Boilerplate, separator: String, adjustSize: Int, fromStart: Boolean)
        extends Boilerplate

    final case class Zip(left: Boilerplate, right: Boilerplate) extends Boilerplate

    case class Size(adjust: Int) extends Boilerplate
  }

  implicit final class BoilerplateStringContext(private val self: StringContext) extends AnyVal {
    def bp(args0: Boilerplate*): Boilerplate = {

      val parts = self.parts.map(Boilerplate.Literal).iterator
      val args  = args0.iterator

      var result: Boilerplate = parts.next()

      while (parts.hasNext) {
        val next     = args.next()
        val nextPart = parts.next()
        result = Boilerplate.Zip(result, Boilerplate.Zip(next, nextPart))
      }

      result
    }
  }

  val char: Boilerplate              = Boilerplate.TypeVariable(false)
  val charLast: Boilerplate          = Boilerplate.TypeVariable(true)
  val num: Boilerplate               = Boilerplate.Number()
  def num(adjust: Int): Boilerplate  = Boilerplate.Number(adjust)
  val size: Boilerplate              = Boilerplate.Size(0)
  def size(adjust: Int): Boilerplate = Boilerplate.Size(adjust)

}
