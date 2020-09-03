package com.innerproduct.ee.concurrent

import cats.effect._
import com.innerproduct.ee.debug._
import scala.concurrent.duration.FiniteDuration
import java.util.concurrent.TimeUnit

object Cancel extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    for {
      fiber <- task.start // <2>
      _ <- IO("pre-cancel").debug()
      _ <- fiber.cancel
      _ <- IO("canceled").debug()
    } yield ExitCode.Success

  val task: IO[Nothing] =
    IO.sleep(FiniteDuration(2, TimeUnit.SECONDS)) *>
      IO("task").debug() *>
      IO.never // <1>
}
