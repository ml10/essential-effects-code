package com.innerproduct.ee.apps

import cats.effect._
import scala.concurrent.duration._
import com.innerproduct.ee.debug._

object TickingClock extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    clock2
      .guaranteeCase(_ => IO("something goes here").debug().void) // <2>
      .as(ExitCode.Success)

  val tick: IO[Long] = IO.sleep(1.second) *> IO(System.currentTimeMillis()).debug()

  import cats.syntax.flatMap._
  val clock2: IO[Unit] = tick.foreverM.void
}
