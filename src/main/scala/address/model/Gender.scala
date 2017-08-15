package address.model

object Gender extends Enumeration {
  type Gender = Value
  val Male = Value(1, "Male")
  val Female = Value(2, "Female")
  val Other = Value(3, "Other")
}
