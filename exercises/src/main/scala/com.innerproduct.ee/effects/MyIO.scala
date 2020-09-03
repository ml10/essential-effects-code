package com.innerproduct.ee.effects
case class MyIO[A](unsafeRun: () => A) { // <1>
  def map[B](f: A => B): MyIO[B] =
    MyIO(() => {
      f(unsafeRun())
    })

  def flatMap[B](f: A => MyIO[B]): MyIO[B] =
    MyIO(() => 
      f(unsafeRun()).unsafeRun()
    )
}

object MyIO {
  def putStr(s: => String): MyIO[Unit] =
    MyIO(() => println(s))
  
}

object Printing extends App { // <3>
  lazy val hello = MyIO.putStr("hello!")

  hello.unsafeRun()

  val one = MyIO(() => 1)
  println(one.map(_ + 1).unsafeRun())

  val helloWorld = for {
    _ <- MyIO.putStr("hellp")
    _ <- MyIO.putStr("world")
  } yield ()

  helloWorld.unsafeRun()
}