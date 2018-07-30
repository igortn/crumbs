package future

import cats.Monad

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


/*
 * Simple example of wrapping the `Future` into a thunk to control
 * its execution schedule and side effects. In addition, this example defines
 * a monadic instance of the wrapper which enables `Future` composition,
 * again, with the full control over its execution schedule and side effects.
 *
 * It's also worth mentioning that with this wrapper the Liskov Substitution Principle,
 * which is broken for `Future`, is valid again. This is very important for
 * the consistent functional programming.
 *
 * This little example is a simple illustration of the concept of a program
 * as a value which is common for homoiconic languages like Lisp, but
 * is not the first class concept in Scala. The thunk that is shown below can be
 * further transformed and composed with other thunks to result in the final program value
 * that can be run when a user decides to do so.
 *
 * This example could be extended further by taking control over the execution
 * context that could be given explicitly or implicitly to the thunk.
 *
 * Note that the type `Thunk` is defined in the `future` package object as
 * type Thunk[T] = () => Future[T] .
 *
 * Usage example:
 *
 * import cats.implicits._
 * import future.implicits._
 * import future.Thunk
 *
 * val t1 = Thunk { println("t1"); 7 }
 * val t2 = Thunk { println("t2"); 13 }
 *
 * val t = for {
 *   x <- t1
 *   y <- t2
 *   z <- t1
 *   u <- Thunk { x * y * z }
 * } yield y + u
 *
 * Here, the thunked `Future` is fully composed but not run yet. Let's run it now:
 *
 * val f: Future[Int] = t.run()
 *
 * At this point the `Future` has been evaluated, all the computations started
 * and all the side effects kicked in.
 */

object Thunk {
  def apply[T](block: => T): Thunk[T] = () => Future { block }
}

object implicits {

  implicit val monadThunkInst: Monad[Thunk] =
    new Monad[Thunk] {
      def pure[A](x: A): Thunk[A] = () => Future { x }

      def flatMap[A, B](fa: Thunk[A])(f: A => Thunk[B]): Thunk[B] =
        () => fa().flatMap { a => f(a)() }

      def tailRecM[A, B](a: A)(f: A => Thunk[Either[A, B]]): Thunk[B] =
        () => f(a)().flatMap {
          case Left(nextA) => tailRecM(nextA)(f)()
          case Right(b) => pure(b)()
        }
    }

  /*
   * We could just call `thunk()` to evaluate the `Future` instance,
   * but `thunk.run()` looks semantically much nicer. This is the whole
   * purpose of setting up this implicit converter.
   *
   * As a minor optimization we extend this class from `AnyVal` in order
   * to avoid creating its instance on the heap.
   */
  implicit class RunnableThunk[T](val thunk: Thunk[T]) extends AnyVal {
    def run(): Future[T] = thunk()
  }
}
