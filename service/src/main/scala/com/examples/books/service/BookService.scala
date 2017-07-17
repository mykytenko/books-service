package com.examples.books.service

import javax.inject.Inject

import com.examples.books.model.Book
import com.examples.books.repository.BookRepository

import scala.concurrent.Future

class BookService @Inject() (bookRepository: BookRepository) {

  def count(): Future[Int] = bookRepository.count()

  def get(startIndex: Int, limit:Int): Future[Seq[Book]] = bookRepository.get(startIndex, limit)

  def findById(id: Int): Future[Option[Book]] = bookRepository.getSingle(id)

  def insert(book: Book): Future[Int] = bookRepository.insert(book)

  def delete(id: Int): Future[Int] = bookRepository.remove(id)

  def update(book: Book): Future[Int] = bookRepository.update(book)
}
