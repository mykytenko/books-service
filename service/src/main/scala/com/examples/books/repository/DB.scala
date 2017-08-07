package com.examples.books.repository

import slick.jdbc.H2Profile.api.Database

object DB {

  // http://slick.lightbend.com/doc/3.2.0-M1/gettingstarted.html
  val connection = Database.forConfig("h2db")
}
