package com.alfianlosari.productlistdetail.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.alfianlosari.productlistdetail.model.Comment
import java.util.*

@Entity(tableName = "comments",
        foreignKeys = [
                ForeignKey(entity = ProductEntity::class,
                        parentColumns = ["id"],
                        childColumns = ["productId"],
                        onDelete = ForeignKey.CASCADE)
        ],
        indices = [
            Index(value = arrayOf("productId"))
        ])
class CommentEntity: Comment {

    @PrimaryKey(autoGenerate = true)
    override var id: Int? = null
    override var productId: Int
    override var text: String
    override var postedAt: Date

    constructor(productId: Int, text: String, postedAt: Date) {
        this.productId = productId
        this.text = text
        this.postedAt = postedAt
    }

}