package org.virtuslab.migration.demo.utils

import org.reflections.Reflections
import scala.collection.JavaConverters._
import java.lang.reflect._

object GenerateDocs extends App {
  private val headings =
    List("ErrorCode (Scala-Klasse)", "Parameter", "Beispiel Message")

  //private val runtimeMirror: universe.Mirror = universe.runtimeMirror(getClass.getClassLoader)

  def instantiateParams(paramClass: Class[_]): Option[Any] =
    paramClass match {
      case c if classOf[String].isAssignableFrom(c) => Option("x")
      case c if classOf[Boolean].isAssignableFrom(c) =>
        Option(java.lang.Boolean.TRUE)
      case c if classOf[Iterable[_]].isAssignableFrom(c) => Option(Seq.empty)
      case c if classOf[Option[_]].isAssignableFrom(c)   => Some(None)
      case c if classOf[Int].isAssignableFrom(c)         => Option(0)
      case c if classOf[Long].isAssignableFrom(c)        => Option(0)
      case c if classOf[Double].isAssignableFrom(c)      => Option(0.0)
      case c if classOf[Throwable].isAssignableFrom(c) =>
        Option(new Exception("Error"))
      case o =>
        print("Unhandelable Parameter in ErrorCode instantiation:" + o.toString)
        None
    }

  def handleClasses(cl: Class[_]): ErrorCode = {
    val ctor =
      cl.getDeclaredConstructors()
        .toList
        .head //assume there is only one constructor
    ctor.setAccessible(true)
    val args: scala.Array[Any] =
      ctor.getParameterTypes.map(instantiateParams).collect { case Some(arg) =>
        arg
      }
    println(args)
    ctor.newInstance(args: _*).asInstanceOf[ErrorCode]
  }

//  private def instantiateParams(tp: Type): Option[Any] = tp match {
//    case t if t =:= typeOf[ProductKey]             => Option(FakeProductKey(-1))
//    case t if t =:= typeOf[String]                 => Option("x")
//    case t if t =:= typeOf[Boolean]                => Option(java.lang.Boolean.TRUE)
//    case t if t =:= typeOf[Duration]               => Option(Duration.fromNanos(1))
//    case t if t =:= typeOf[IntervalSet[LocalDate]] => Option(IntervalSet.UNRESTRICTED)
//    case t if t <:< typeOf[Iterable[Any]]          => Option(Seq.empty)
//    case t if t <:< typeOf[Option[Any]]            => Some(None)
//    case t if t =:= typeOf[Int]                    => Option(0)
//    case t if t =:= typeOf[Long]                   => Option(0)
//    case t if t =:= typeOf[Double]                 => Option(0.0)
//    case t if t <:< typeOf[AND[BooleanExpression]] => Option(AND())
//    case t if t <:< typeOf[OR[BooleanExpression]]  => Option(OR())
//    case t if t <:< typeOf[BooleanExpression]      => Option(AND())
//    case t if t =:= typeOf[Throwable]              => Option(new Exception("Error"))
//    case o =>
//      print("Unhandelable Parameter in ErrorCode instantiation:" + o.toString)
//      None
//  }
//
//
//  private def handleClasses(cl: Class[_ <: ErrorCode]): ErrorCode = {
//    val classSymbol: universe.ClassSymbol = universe.runtimeMirror(cl.getClassLoader).classSymbol(cl)
//    val cm = runtimeMirror.reflectClass(classSymbol)
//    val ctor = classSymbol.info.decl(universe.termNames.CONSTRUCTOR).asMethod
//    val ctorm = cm.reflectConstructor(ctor)
//    val params = ctor.paramLists.head.map(_.info)
//    val args = params.map(instantiateParams).collect { case Some(arg) => arg }
//    ctorm(args: _*).asInstanceOf[ErrorCode]
//  }

  override def toString: String = {
    val reflections = new Reflections("org.virtuslab.migration.demo.utils")
    val subClasses = reflections
      .getSubTypesOf(classOf[ErrorCode])
      .asScala
      .filter(!_.isInterface)
      .filter(c => !Modifier.isAbstract(c.getModifiers))
    val instances = subClasses.map(handleClasses).toList.sortBy(_.code)

    val rows = instances.map(c =>
      List(
        List(c.code, "(" + c.getClass.getSimpleName.replace("$", "") + ")").map(
          n => s"| $n"
        ),
        // c.parameters.keySet.map(k => s"- $k").toSeq,
        List(c.message.replace("\n", " "))
      )
    )

    val s = new StringBuilder()
    s.append(".. _sec_errorCodes:\n\n")

    s.append(rows.flatten.mkString(","))

    s.append("\n")

    s.append(s"Es folgt eine Liste aller ${instances.size} ErrorCodes")

    s.toString()
  }

  println(toString())
}
