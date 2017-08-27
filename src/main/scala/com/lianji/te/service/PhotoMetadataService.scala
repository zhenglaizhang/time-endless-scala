package com.lianji.te.service

import java.awt.image.BufferedImage
import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream}
import javax.imageio.ImageIO
import scala.collection.JavaConverters._

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.{ExifDirectoryBase, ExifIFD0Directory, ExifSubIFDDescriptor, ExifSubIFDDirectory}
import com.lianji.te.domain.{Category, Photo}
import org.imgscalr.Scalr
import org.imgscalr.Scalr._
import org.springframework.stereotype.Service

@Service
class PhotoMetadataService {
  def getPhoto(category: java.util.List[Category], name: String, desc: String, url: String, inputStream: InputStream): Photo = {
    try {
      val metadata = ImageMetadataReader.readMetadata(inputStream)
      val exifDir = metadata.getFirstDirectoryOfType(classOf[ExifSubIFDDirectory])
      val exifDesc = new ExifSubIFDDescriptor(exifDir)

      val exifD0Dir = metadata.getFirstDirectoryOfType(classOf[ExifIFD0Directory])


      val width = exifD0Dir.getInt(ExifDirectoryBase.TAG_IMAGE_WIDTH)
      val height = exifD0Dir.getInt(ExifDirectoryBase.TAG_IMAGE_HEIGHT)
      val make = exifD0Dir.getString(ExifDirectoryBase.TAG_MAKE)
      val model = exifD0Dir.getString(ExifDirectoryBase.TAG_MODEL)
      val copyright = exifD0Dir.getString(ExifDirectoryBase.TAG_COPYRIGHT)

      val fNumber = exifDesc.getFNumberDescription
      val originalDate = exifDir.getDate(ExifDirectoryBase.TAG_DATETIME_ORIGINAL)
      val exposureTime = exifDesc.getExposureTimeDescription
      val exposureProgram = exifDesc.getExposureProgramDescription
      val meteringMode = exifDesc.getMeteringModeDescription
      val iso = exifDir.getInt(ExifDirectoryBase.TAG_ISO_EQUIVALENT)
      val apertureValue = exifDesc.getApertureValueDescription
      val maxApertureValue = exifDesc.getMaxApertureValueDescription
      val focalLength = exifDesc.getFocalLengthDescription
      Photo(id = null,
        category = category.asScala.mkString(","),
        name = name,
        description = desc,
        dateTimeOriginal = new java.sql.Date(originalDate.getTime).toLocalDate,
        width = width,
        height = height,
        exposureTime = exposureTime,
        meteringMode = meteringMode,
        exposureProgram = exposureProgram,
        fNumber = fNumber,
        model = model,
        make = make,
        copyright = copyright,
        isoSpeedRatings = iso,
        apertureValue = apertureValue,
        maxApertureValue = maxApertureValue,
        focalLength = focalLength,
        url_mobile = url,
        url_mobile_index = url,
        url = url,
        url_index = url)
    } catch {
      case _: Throwable =>
        Photo().copy(
          id = null,
          category = category.asScala.mkString(","),
          name = name,
          description = desc,
          url_mobile = url,
          url_mobile_index = url,
          url = url,
          url_index = url)
    }

  }

  def crapImgInputStream(inputStream: InputStream, size: Int): ByteArrayInputStream = {
    val img = resize(inputStream, size)
    val os = new ByteArrayOutputStream()
    ImageIO.write(img, "gif", os)
    new ByteArrayInputStream(os.toByteArray)
  }

  def resize(inputStream: InputStream, size: Int): BufferedImage = {
    resize(ImageIO.read(inputStream), size)
  }

  def resize(img: BufferedImage, size: Int): BufferedImage = {
    Scalr.resize(img, Method.ULTRA_QUALITY, Mode.AUTOMATIC, size, size);
  }

  def createThumbnail(img: BufferedImage): BufferedImage = {
    // Create quickly, then smooth and brighten it.
    val image = Scalr.resize(img, Method.SPEED, 125, OP_ANTIALIAS, OP_BRIGHTER)
    Scalr.pad(image, 4)
    // Let's add a little border before we return result.
    pad(img, 4)
  }
}
