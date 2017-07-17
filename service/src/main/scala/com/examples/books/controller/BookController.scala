package com.examples.books.controller

import javax.inject.Inject

import com.examples.books.controller.BookController.{LIMIT, START_INDEX}
import com.examples.books.model.Book
import com.examples.books.service.BookService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.finatra.response.Mustache

import scala.concurrent.ExecutionContext.Implicits.global


class BookController @Inject()(bookService: BookService, response: ResponseBuilder) extends Controller {

  get("/books") { request: Request =>
    info("In /books")
    val startIndex = request.params.getIntOrElse("startIndex", START_INDEX)
    val limit = request.params.getIntOrElse("limit", LIMIT)

    bookService.get(startIndex, limit) map {
      result => response.ok.view("/books.mustache", BooksView(result))
    }

//  bookService.count() map { count =>
//    var pages: Pages = null
//    if (limit > count)
//      pages = Pages(null, null)
//    else if (startIndex + limit >= count)
//      pages = Pages(Page(startIndex - limit, limit), null)
//    else if (startIndex <= 0)
//      pages = Pages(null, Page(startIndex + limit, limit))
//    else
//      pages = Pages(Page(startIndex - limit, limit), Page(startIndex + limit, limit))
//    //          pages
  }

  get("/books/:id") { request: Request =>
    val bookId: String = request.params("id")
    bookService.findById(bookId.toInt).map {
      case Some(book) => response.ok.view("/books.mustache", BooksView(Seq(book)))
      case None => response.badRequest.html("No book with such id")
    }
  }

  post("/books") { request : Request =>
    val book: Book = Book(request.params("id").toInt, request.params("author"), request.params("title"))
    bookService.insert(book).map {
      case 1 => response.movedPermanently.location("/books")
      case 0 => response.internalServerError("Sth is wrong. Book inserting failed")
    }
  }

  post("/books/:id/delete") { request: Request =>
    val id: Int = request.params("id").toInt
    bookService.delete(id).map{
      case 1 => response.movedPermanently.location("/books")
      case 0 => response.internalServerError("Sth is wrong.")
    }
  }

  post("/books/:id/edit") { request: Request =>
    val book: Book = Book(request.params("id").toInt, request.params("author"), request.params("title"))
    bookService.update(book).map{
      case 1 => response.movedPermanently.location("/books")
      case 0 => response.internalServerError("Sth is wrong.")
    }
  }

}

object BookController {
  val START_INDEX = 0
  val LIMIT = 25
}

case class Page(startIndex: Int, limit: Int)
case class Pages(prev: Page, next: Page)

@Mustache("books")
case class BooksView(books: Seq[Book]) //, pages: Pages = Pages(null, null))
