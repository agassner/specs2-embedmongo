package com.github.athieriot

import org.specs2.mutable.Specification
import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId
import com.novus.salat.global._
import com.novus.salat._
import com.novus.salat.dao._

class ModelTest extends Specification with EmbedConnection {

  override def embedConnectionPort() = { 12345 }

  "Embed database" should {
    "be able to save a Model" in {
      Model.save(Model(name = "test"))
      Model.count() must be greaterThan 0
    }
  }
}

case class Model(id: ObjectId = new ObjectId, name: String)

object Model extends SalatDAO[Model, ObjectId](collection = MongoClient("localhost", 12345)("test")("model")) {}
