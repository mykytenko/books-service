package com.examples.books.repository.table

import com.examples.books.model.Book
import slick.jdbc.H2Profile.api._
import slick.lifted.Tag

// Definition of BOOKS table
class Books(tag: Tag) extends Table[Book](tag, "Books")  {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def title = column[String]("TITLE")
  def author = column[String]("AUTHOR")
  // Every table needs a * projection with the same type as the table's type parameter
  def * = (id, title, author) <> (Book.tupled, Book.unapply)
}


