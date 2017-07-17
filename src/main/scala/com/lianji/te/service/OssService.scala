package com.lianji.te.service

import java.io.{File, FileInputStream, FileNotFoundException, InputStream}

import com.aliyun.oss._
import com.aliyun.oss.model._

class OssService(
  val endpoint: String,
  val accessKeyId: String,
  val accessKeySecret: String
) extends AutoCloseable {

  val client = new OSSClient(endpoint, accessKeyId, accessKeySecret)

  /*
  You want to let Java users know that a Scala method can throw one or more exceptions so they can handle those exceptions with try/catch
   blocks.

  Add the @throws annotation to your Scala methods so Java consumers will know which methods can throw exceptions and what exceptions
  they throw.

/**
 * @throws DatabaseException Banana banana.
 */
@throws(classOf[DatabaseException])
def insert(game: Game): Long = {
   ...
}

This syntax combines both, and looks preferable to me:

@throws[DatabaseException]("Banana banana.")
def insert(game: Game): Long = {
   ...
}
   */

  @throws(classOf[Throwable])
  @throws[ClientException]("if client error")
  @throws[OSSException]("if oss error")
  def upload(uploadFile: String, bucket: String, key: String): CompleteMultipartUploadResult = {
    val uploadFileRequest = new UploadFileRequest(bucket, key)
    uploadFileRequest.setUploadFile(uploadFile)
    uploadFileRequest.setTaskNum(5)
    uploadFileRequest.setPartSize(1024 * 1024 * 1)
    uploadFileRequest.setEnableCheckpoint(true)
    val uploadResult = client.uploadFile(uploadFileRequest)
    val multipartUploadResult = uploadResult.getMultipartUploadResult
    multipartUploadResult
  }


  @throws[OSSException]
  @throws[ClientException]
  @throws[FileNotFoundException]
  def uploadFile(bucketName: String, objectKey: String, filename: String): PutObjectResult = {
    val file = new File(filename)
    val objectMeta = new ObjectMetadata
    objectMeta.setContentLength(file.length)
    if (filename.endsWith("xml")) objectMeta.setContentType("text/xml")
    else if (filename.endsWith("jpg")) objectMeta.setContentType("image/jpeg")
    else if (filename.endsWith("png")) objectMeta.setContentType("image/png")
    val input = new FileInputStream(file)
    client.putObject(bucketName, objectKey, input, objectMeta)
  }

  @throws[OSSException]
  @throws[ClientException]
  @throws[FileNotFoundException]
  def uploadJpg(bucketName: String, objectKey: String, inputStream: InputStream): PutObjectResult = {
    val objectMeta = new ObjectMetadata
    objectMeta.setContentType("image/jpeg")
    client.putObject(bucketName, objectKey, inputStream, objectMeta)
  }

  @throws[OSSException]
  @throws[ClientException]
  def ensureBucketExits(bucketName: String) = {
    try
      client.createBucket(bucketName)
    catch {
      case e: ServiceException =>
        if (OSSErrorCode.BUCKET_ALREADY_EXISTS != e.getErrorCode)
          throw e
    }
  }


  @throws[OSSException]
  @throws[ClientException]
  def setBucketPublicReadable(bucketName: String): Unit = { //创建bucket
    ensureBucketExits(bucketName)
    client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead)
  }

  override def close() = client.shutdown()
}
