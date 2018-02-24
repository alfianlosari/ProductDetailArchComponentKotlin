package com.alfianlosari.productlistdetail.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.alfianlosari.productlistdetail.model.Product

@Entity(tableName = "products")
class ProductEntity: Product {

    @PrimaryKey
    override var id: Int
    override var name: String
    override var description: String
    override var price: Int

    constructor(id: Int, name: String, description: String, price: Int) {
        this.id = id
        this.name = name
        this.description = description
        this.price = price
    }

    constructor(product: Product) {
        this.id = product.id
        this.name = product.name
        this.description = product.description
        this.price = product.price
    }

}