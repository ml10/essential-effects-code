package com.innerproduct.ee.concurrent

import cats.effect._
import com.innerproduct.ee.debug._

object Start extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    for {
      fiber <- task.start // <1>
      _ <- IO("another task").debug()
      t <- fiber.join
      _ <- putStr(t).debug()
      // <2>
    } yield ExitCode.Success

  val task: IO[String] =
    IO("task").debug()

  def putStr(s: String): IO[Unit] = IO(println(s))

}
