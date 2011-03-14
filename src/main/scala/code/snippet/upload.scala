package com.fmpwizard
package code
package snippet


import com.fmpwizard.code.model._

import scala.xml.{NodeSeq, Text, Elem}

import java.io.{File,FileOutputStream}

import net.liftweb._
import util._
import common._
import mapper._

import Helpers._
import http._
import S._
import js.JsCmds.Noop



class Upload extends Logger {

  object product extends RequestVar(Inventory.create)
  object img extends RequestVar(InventoryImages.create)
  object imageFile extends RequestVar[Box[FileParamHolder]](Empty)

  private def saveFile(fp: FileParamHolder): Unit = {
    fp.file match {
      case null =>
      case x if x.length == 0 => info("File size is 0")
      case x =>{
        info("We got a file!")

        /**
         * Change this path once you go on production
         */
        val filePath = "src/main/webapp/images"

        /**
         * Set some fields on the Image table
         */
        img.is.img_path.set(filePath + "/" + fp.fileName)
        img.is.mime_type.set(fp.mimeType)
        img.is.sort_order.set(0)

        /**
         * This tell helps save the product_id
         * on the image table, so you can keep the
         * relationship
         */

        product.is.images += img
        product.is.save

        /**
         * Here we save the file to the File System
         */

        val oFile = new File(filePath, fp.fileName)
              val output = new FileOutputStream(oFile)
              output.write(fp.file)
              output.close()
        info("File uploaded!")
        S.notice("Thanks for the upload")
      }
    }
  }

  def render ={
    // process the form
    def process() {

      (imageFile.is, product.is.part_number.is) match {
        case (Empty, _) => S.error("You forgot to choose a file to upload")
        case (_, "") => S.error("You forgot to enter a part number")
        case (image, partNumber) => {
          info("The RequestVar content is: %s".format(imageFile.is))
          imageFile.is.map{ info("About to start the file upload"); file => saveFile(file)}
          info("Done")
        }
      }

    }
    "name=part_number" #> SHtml.onSubmit(product.is.part_number.set(_)) &
    uploadImg &
    "type=submit" #> SHtml.onSubmitUnit(process)
  }


  def uploadImg: CssBindFunc = {
    /**
     * If it is a GET request, show the upload field,
     * else show a link to the image we just uploaded.
     */
    (S.get_?, imageFile.is, product.is.part_number.is) match {
      case (true, _, _)  => "name=image" #> SHtml.fileUpload(s => imageFile(Full(s)))
      case (_, Empty, _) => "name=image" #> SHtml.fileUpload(s => imageFile(Full(s)))
      case (_, _, "")    => "name=image" #> SHtml.fileUpload(s => imageFile(Full(s)))
      case (false, _, _) => "name=image" #> SHtml.link(
        "http://127.0.0.1:8080/images/" +
          imageFile.is.dmap("?")(_.fileName) ,
        () => Unit ,
        <span>Click to see image: {imageFile.is.dmap("?")(_.fileName)}</span>)
    }
  }


}
