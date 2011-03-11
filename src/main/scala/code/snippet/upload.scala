package code.snippet


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
  object imageFile extends RequestVar[Box[FileParamHolder]](Empty)

  private def saveFile(fp: FileParamHolder): Unit = {
    fp.file match {
      case null => info("File uploaded!!!!!!!!!!!!!!!!!!!!2!")
      case x if x.length == 0 => info("File uploaded!!!!!!!!!!!!!!!!!!!!!1")
      case x =>{
        info("we made it!")
        //val blob = ImageBlob.create.image(x).saveMe
        //ImageInfo.create.name
        //(fp.fileName).mimeType(fp.mimeType).blob(blob).saveMe
        val filePath = "src/main/webapp/images"
        val oFile = new File(filePath, fp.fileName)
              val output = new FileOutputStream(oFile)
              output.write(fp.file)
              output.close()
        info("File uploaded!!!!!!!!!!!!!!!!!!!!!!")
        //S.notice("Thanks for the upload")
        //S.redirectTo("/")
      }
    }
  }

  def render ={
    // define some variables to put our values into
    var name = ""



    // process the form
    def process() {
      info("The RequestVar content is: %s".format(imageFile.is))
      imageFile.is.map{ file => saveFile(file); info("Here")}
      S.notice("I hope it worked")
      info("Done")
    }

    uploadVehicles &
    "type=submit" #> SHtml.onSubmitUnit(process)
  }

  /*This goves a compiler error, so commented out for now 
  private def processUpload[T]() : CssBindFunc = {
    val fileName:NodeSeq = Text(imageFile.is.dmap("?")(_.fileName))
  }
  */

  def uploadVehicles: CssBindFunc = {

      if (S.get_?) {
        "name=image" #> SHtml.fileUpload(s => imageFile(Full(s)))
      } else {
        "name=image" #> Text(imageFile.is.dmap("?")(_.fileName))
      }
  }


}
