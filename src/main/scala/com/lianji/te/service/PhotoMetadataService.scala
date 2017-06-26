package com.lianji.te.service

import java.io.InputStream

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.{ ExifDirectoryBase, ExifIFD0Directory, ExifSubIFDDescriptor, ExifSubIFDDirectory }
import com.lianji.te.domain.Photo
import org.springframework.stereotype.Service

@Service
class PhotoMetadataService {
  def getPhoto(name: String, desc: String, inputStream: InputStream): Photo = {
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
    val exposureModel = exifDesc.getExposureModeDescription
    val iso = exifDir.getInt(ExifDirectoryBase.TAG_ISO_EQUIVALENT)
    val apertureValue = exifDesc.getApertureValueDescription
    val maxApertureValue = exifDesc.getMaxApertureValueDescription
    val focalLength = exifDesc.getFocalLengthDescription

    new Photo(
      id = null,
      name = name,
      description = desc,
      dateTimeOriginal = new java.sql.Date(originalDate.getTime).toLocalDate,
      width = width,
      height = height,
      exposureTime = exposureTime,
      fNumber = fNumber,
      model = model,
      make = make,
      copyright = copyright,
      isoSpeedRatings = iso,
      apertureValue = apertureValue,
      maxApertureValue = maxApertureValue,
      focalLength = focalLength,
      url = "dummy"
    )
  }

}
