package boilerplate.examples

import boilerplate._

object ZippableExample {

  val zippableEx =
    bp"""
    implicit def Zippable$size[${char.rep(", ")}, Z]: Zippable.Out[(${char.rep(", ")}), Z, (${char.rep(", ")}, Z)] =
      new Zippable[(${char.rep(", ")}), Z] {
        type Out = (${char.rep(", ")}, Z)
        def zip(left: (${char.rep(", ")}), right: Z): (${char.rep(", ")}, Z) =
          (${bp"left._${num(1)}".rep(", ")}, right)
      }
    """

  def main(args: Array[String]): Unit =
    println(zippableEx.print(2, 4))

  // OUTPUT
  // ======
  //
  // implicit def Zippable2[A, B, Z]: Zippable.Out[(A, B), Z, (A, B, Z)] =
  //   new Zippable[(A, B), Z] {
  //     type Out = (A, B, Z)
  //     def zip(left: (A, B), right: Z): (A, B, Z) =
  //       (left._1, left._2, right)
  //   }
  //
  // implicit def Zippable3[A, B, C, Z]: Zippable.Out[(A, B, C), Z, (A, B, C, Z)] =
  //   new Zippable[(A, B, C), Z] {
  //     type Out = (A, B, C, Z)
  //     def zip(left: (A, B, C), right: Z): (A, B, C, Z) =
  //       (left._1, left._2, left._3, right)
  //   }
  //
  // implicit def Zippable4[A, B, C, D, Z]: Zippable.Out[(A, B, C, D), Z, (A, B, C, D, Z)] =
  //   new Zippable[(A, B, C, D), Z] {
  //     type Out = (A, B, C, D, Z)
  //     def zip(left: (A, B, C, D), right: Z): (A, B, C, D, Z) =
  //       (left._1, left._2, left._3, left._4, right)
  //   }
  //
}
