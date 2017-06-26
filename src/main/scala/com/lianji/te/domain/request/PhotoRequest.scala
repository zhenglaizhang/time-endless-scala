package com.lianji.te.domain.request

import org.springframework.web.multipart.MultipartFile

class PhotoRequest(
  var name: String,
 var description: String,
  file: MultipartFile
)
