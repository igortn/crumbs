import scala.concurrent.Future

package object future {
  type Thunk[T] = () => Future[T]
}
