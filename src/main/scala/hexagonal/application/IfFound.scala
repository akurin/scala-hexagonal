package hexagonal.application

abstract sealed class IfFound[+T]

case object NotFound extends IfFound[Nothing]

case class Result[+T](result: T) extends IfFound[T]

