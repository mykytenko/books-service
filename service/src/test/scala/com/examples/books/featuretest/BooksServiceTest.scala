package com.examples.books.featuretest

import com.examples.books.BooksServer
import com.twitter.finagle.http.Status.Ok
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class BooksServiceTest extends FeatureTest{

  override val server: EmbeddedHttpServer = new EmbeddedHttpServer(new BooksServer)

  test("Server returns OK on GET /books") {
    server.httpGet(path = "/books",
                   andExpect = Ok)
  }

//  test("Server returns OK on GET /books/1") {
//    server.httpGet(path = "/books/1",
//      andExpect = Ok)
//  }
}
