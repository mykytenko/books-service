package com.examples.books.featuretest

import com.examples.books.BooksServer
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class BooksServiceStartupTest extends FeatureTest {

  override protected def server: EmbeddedHttpServer = new EmbeddedHttpServer(new BooksServer)

  test("Server startup") {
    server.assertHealthy()
  }
}
