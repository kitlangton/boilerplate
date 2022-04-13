package boilerplate.examples

import boilerplate._

object FromFunctionExample {

  val ex =
    bp"""
def fromFunction[${bp"$char: Tag".rep(", ")}](
  f: (${char.rep(", ", -1)}) => $charLast
)(implicit trace: ZTraceElement): ZLayer[${char.rep(" with ", -1)}, Nothing, $charLast] =
  ZLayer.fromZIOEnvironment {
    ZIO.environmentWith[${char.rep(" with ", -1)}] { env =>
      ZEnvironment(
        f(${bp"env.get[$char]".rep(", ", -1)})
      )
    }
  }
"""

  def main(args: Array[String]): Unit =
    println(ex.print(2, 4))

  // OUTPUT
  // ======
  //
  //  def fromFunction[A: Tag, B: Tag](
  //    f: (A) => B
  //  )(implicit trace: ZTraceElement): ZLayer[A, Nothing, B] =
  //    ZLayer.fromZIOEnvironment {
  //      ZIO.environmentWith[A] { env =>
  //        ZEnvironment(
  //          f(env.get[A])
  //        )
  //      }
  //    }
  //
  //  def fromFunction[A: Tag, B: Tag, C: Tag](
  //    f: (A, B) => C
  //  )(implicit trace: ZTraceElement): ZLayer[A with B, Nothing, C] =
  //    ZLayer.fromZIOEnvironment {
  //      ZIO.environmentWith[A with B] { env =>
  //        ZEnvironment(
  //          f(env.get[A], env.get[B])
  //        )
  //      }
  //    }
  //
  //  def fromFunction[A: Tag, B: Tag, C: Tag, D: Tag](
  //    f: (A, B, C) => D
  //  )(implicit trace: ZTraceElement): ZLayer[A with B with C, Nothing, D] =
  //    ZLayer.fromZIOEnvironment {
  //      ZIO.environmentWith[A with B with C] { env =>
  //        ZEnvironment(
  //          f(env.get[A], env.get[B], env.get[C])
  //        )
  //      }
  //    }
}
