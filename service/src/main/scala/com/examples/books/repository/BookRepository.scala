package com.examples.books.repository

import com.examples.books.model.Book
import com.examples.books.repository.table.Books
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

class BookRepository {

  // http://slick.lightbend.com/doc/3.2.0-M1/gettingstarted.html
  private val bookSchema = TableQuery[Books]
  private val setup = DBIO.seq(
    bookSchema.schema.create,
    bookSchema += Book(-1,"The Adventures of Tom Sawyer", "Mark Twain"),
    bookSchema += Book(-1, "A Murder, a Mystery, and a Marriage", "Mark Twain"),
    bookSchema += Book(-1, "The Financier", "Theodore Dreiser"),
    bookSchema += Book(-1, "Charlotte’s Web", "E.B. White"),
    bookSchema += Book(-1, "Mieko and the Fifth Treasure", "Eleanor Coerr"),
    bookSchema += Book(-1, "The Outsiders", "S.E. Hinton"),
    bookSchema += Book(-1, "The House On Mango Street", "Sandra Cisneros"),
    bookSchema += Book(-1, "Thirteen Reasons Why", "Jay Asher"),
    bookSchema += Book(-1, "Peter Pan", "J.M. Barrie"),
    bookSchema += Book(-1, "The Old Man and the Sea", "Ernest Hemmingway"),
    bookSchema += Book(-1, "The Giver", "Lois Lowry"),
    bookSchema += Book(-1, "Number the Stars", "Lois Lowry"),
    bookSchema += Book(-1, "A Wrinkle In Time", "Madeline L’engle"),
    bookSchema += Book(-1, "Mr. Mercedes", "Stephen King"),
    bookSchema += Book(-1, "Silent Scream", "Angela Marsons")
  )
  private val setupFuture = DB.connection.run(setup)

  def getAll(): Future[Seq[Book]] = DB.connection.run(bookSchema.result)

  def count(): Future[Int] = DB.connection.run(bookSchema.length.result)

  def get(startIndex: Int, limit: Int): Future[Seq[Book]] = {
    DB.connection.run(bookSchema.drop(startIndex).take(limit).result)
  }

  def getSingle(id: Int): Future[Option[Book]] = {
    val q = bookSchema.filter(_.id === id)
    DB.connection.run(q.result.headOption)
  }

  def insert(newBook: Book): Future[Int] = DB.connection.run(bookSchema += newBook)

  def remove(id: Int): Future[Int] = {
    val q = bookSchema.filter(_.id === id).delete
    DB.connection.run(q)
  }

  def update(bookToEdit: Book): Future[Int] = {
    val q = bookSchema.filter(_.id === bookToEdit.id).update(bookToEdit)
    DB.connection.run(q)
  }
}
