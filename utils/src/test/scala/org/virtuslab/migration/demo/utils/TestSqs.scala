package org.virtuslab.migration.demo.utils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.elasticmq.rest.sqs.SQSRestServer
import org.elasticmq.rest.sqs.SQSRestServerBuilder
import akka.actor.Extension
import akka.actor.ExtendedActorSystem

class TestSqs extends AnyFlatSpec with Matchers {
  class Sth extends Extension
  val id = new akka.actor.ExtensionId[Sth] {

    override def createExtension(system: ExtendedActorSystem): Sth = ???

  }
  val server: SQSRestServer = SQSRestServerBuilder.withDynamicPort().start()
}
