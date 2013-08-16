package com.github.athieriot

import de.flapdoodle.embed.mongo._
import de.flapdoodle.embed.mongo.config.{IMongodConfig, Net, MongodConfigBuilder}
import distribution.Version
import org.specs2.specification.BeforeAfterExample
import de.flapdoodle.embed.process.runtime.Network

trait EmbedConnection extends BeforeAfterExample {

  //Override this method to personalize testing port
  def embedConnectionPort(): Int = { 12345 }

  //Override this method to personalize MongoDB version
  def embedMongoDBVersion(): Version = { Version.V2_4_5 }

  lazy val runtime: MongodStarter = MongodStarter.getDefaultInstance
  lazy val mongodConfig: IMongodConfig = new MongodConfigBuilder().version(embedMongoDBVersion()).net(new Net(embedConnectionPort(), Network.localhostIsIPv6())).build()
  lazy val mongodExe: MongodExecutable = runtime.prepare(mongodConfig)
  lazy val mongod: MongodProcess = mongodExe.start()

  def before() {
    mongod
  }

  def after() {
    mongod.stop()
    mongodExe.cleanup()
  }
}
