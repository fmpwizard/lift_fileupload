package com.fmpwizard
package code
package model

import net.liftweb.mapper._
import net.liftweb.util._
import net.liftweb.common._

/**
 * The singleton that has methods for accessing the database
 */

object Inventory extends Inventory with LongKeyedMetaMapper[Inventory] {

  override def dbTableName = "inventory" // define the DB table name

  def getItemDetails(id: Int): ((Long, String), String)= {
    val item= getItem(id)
    item.zip(getImages(id)) match {
      case head :: tail => head
      case Nil => ((1L,"Nb/A"), "N/A")
    }
  }

  def getItem(id: Int): List[(Long, String)]= {
    Inventory.findAllFields(Seq[SelectableField] (
          Inventory.id, Inventory.part_number),
          By(Inventory.id, id)
          ).map{
            row => println(row);(row.id.is, row.part_number.is )
          }
  }

  def getImages(id: Int): List[String]= {
    InventoryImages.findAllFields(Seq[SelectableField](InventoryImages.img_path),
          By(InventoryImages.inventory_id, id)
          ).map{
            row => row.img_path.is
          }
  }



}

class Inventory extends LongKeyedMapper[Inventory] with OneToMany[Long, Inventory]{

  def getSingleton = Inventory
  def primaryKeyField = id

  object id extends MappedLongIndex(this)
  object part_number extends MappedString(this, 250) {
    override def dbIndexed_? = true
    override def setFilter = trim _ :: toUpper _ :: super.setFilter
  }

  object images extends MappedOneToMany(
    InventoryImages, InventoryImages.inventory_id, OrderBy(
      InventoryImages.id, Ascending
    )
  )


  
}
