package com.fmpwizard
package code
package model

import net.liftweb.mapper._
import net.liftweb.util._
import net.liftweb.common._

/**
 * The singleton that has methods for accessing the database
 */

object InventoryImages extends InventoryImages with LongKeyedMetaMapper[InventoryImages] {

  override def dbTableName = "inventory_images" // define the DB table name

}

class InventoryImages extends LongKeyedMapper[InventoryImages] {
  def getSingleton = InventoryImages

  def primaryKeyField = id

  object id extends MappedLongIndex(this)
  object inventory_id extends LongMappedMapper(this, Inventory)
  object img_path extends MappedText(this)
  object mime_type  extends MappedString(this, 100)
  object sort_order extends MappedInt(this)



}