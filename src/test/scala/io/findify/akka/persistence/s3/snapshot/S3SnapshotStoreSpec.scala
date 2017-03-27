package io.findify.akka.persistence.s3.snapshot

import java.io.File

import akka.persistence.snapshot.SnapshotStoreSpec
import com.typesafe.config.ConfigFactory
import io.findify.akka.persistence.s3.{S3Client, S3ClientConfig}
import io.findify.s3mock.S3Mock
import io.findify.s3mock.provider.FileProvider

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.collection.JavaConversions._

class S3SnapshotStoreSpec
    extends SnapshotStoreSpec(
      ConfigFactory
        .parseString(
          """
    |akka.persistence.snapshot-store.plugin = "s3-snapshot-store"
    |s3-client{
    |  region = "us-west-2"
    |  endpoint = "http://127.0.0.1:4567"
    |  options {
    |    path-style-access = true
    |  }
    |}
  """.stripMargin
        )
        .withFallback(ConfigFactory.load()))
    with SnapshotKeySupport {

  var s3Client: S3Client = _
  var s3mock: S3Mock = new S3Mock(4567, new FileProvider("/tmp/s3snap"))
  val bucketName = "snapshot"

  val extensionName: String = "ss"

  override def beforeAll() = {
    import system.dispatcher
    s3mock.start
    s3Client = new S3Client {
      override val s3ClientConfig: S3ClientConfig = new S3ClientConfig(
        system.settings.config.getConfig("s3-client"))
    }
    Await.result(s3Client.createBucket(bucketName), 5 seconds)
    println(s"""bucket `$bucketName` created""")
    super.beforeAll()
  }

  override def afterAll(): Unit = {
    s3mock.stop
    super.afterAll()
  }
}
