package com.lianji.te.web

import java.lang

import com.lianji.te.domain.EmailMessage
import com.lianji.te.service.{ EmailMessageRepository, EmailService }
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{ HttpStatus, ResponseEntity }
import org.springframework.web.bind.annotation._

//@Controller
//@ResponseBody
@RestController
@RequestMapping(Array("/messages"))
class EmailMessageController {

  private[this] val log = LoggerFactory.getLogger(getClass)

  @Autowired
  private var emailMessageRepository: EmailMessageRepository = _

  @Autowired
  private var emailService: EmailService = _

  //  @RequestMapping(method = Array(RequestMethod.GET))
  @GetMapping
  def getAllBooks: lang.Iterable[EmailMessage] = emailMessageRepository.findAll()

  @PostMapping(Array("/form"))
  def createForm(@ModelAttribute req: EmailMessage) = {
    log.info("creating email with request = {}", req)
    emailService.sendMail("admin@admin.com", "zhenglaizhang@gmail.com", req.subject, req.toString)
    val savedMessage = emailMessageRepository.save(req)
    log.info("saved photo = {}", savedMessage)
    new ResponseEntity("Your message has been sent successfully, thank you!.", HttpStatus.OK)
  }

  // the IsbnEditor is indeed at work, creating an instance of an Isbn class object from the {isbn} parameter
  //  @RequestMapping(value = Array("/{isbn}"), method = Array(RequestMethod.GET))

//  @GetMapping(Array("/{isbn}/reviewers"))
//  def getReviewers(@PathVariable("isbn") book: Book) = book.getReviewers
//
//  @GetMapping(Array("/session"))
//  def getSessionId(req: HttpServletRequest) = req.getSession.getId
}
