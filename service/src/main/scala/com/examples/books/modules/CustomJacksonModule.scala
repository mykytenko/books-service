package com.examples.books.modules

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.SerializationFeature
import com.twitter.finatra.json.modules.FinatraJacksonModule
import com.twitter.finatra.json.utils.CamelCasePropertyNamingStrategy

object CustomJacksonModule extends FinatraJacksonModule {
  override val serializationInclusion = Include.ALWAYS

  override val propertyNamingStrategy = CamelCasePropertyNamingStrategy

}
