package com.fmpwizard
package code
package lib


import com.fmpwizard.code.model._

import scala.xml.{NodeSeq, Text, Elem}

import java.io.{File,FileOutputStream}

import net.liftweb._
import common.Logger

object ImageServer extends Logger {

  /**
   * Comented out as we are not storing images on the database
   */
  /*
  def serveImage: LiftRules.DispatchPF = {
    case req @ Req("images" :: _ :: Nil, _, GetRequest) if findFromRequest(req).isDefined =>
      () => {
        val info = findFromRequest(req).open_! // open is valid here because we just tested in the guard
        // Test for expiration
        req.testFor304(info.date, "Expires" -> toInternetDate(millis + 30.days)) or
        // load the blob and return it
        info.blob.obj.map(blob => InMemoryResponse(blob.image,
        List(
          ("Last-Modified", toInternetDate(info.date.is)),
          ("Expires", toInternetDate(millis + 30.days)),
          ("Content-Type", info.mimeType.is)), Nil,  200))
      }
  }
  */

}