package com.alfianlosari.productlistdetail.db

import com.alfianlosari.productlistdetail.db.entity.CommentEntity
import com.alfianlosari.productlistdetail.db.entity.ProductEntity
import java.util.*
import java.util.concurrent.TimeUnit

class DataGenerator {

    companion object {
        private val FIRST = arrayOf("Special edition", "New", "Cheap", "Quality", "Used")
        private val SECOND = arrayOf("Three-headed Monkey", "Rubber Chicken", "Pint of Grog", "Monocle")
        private val DESCRIPTION = arrayOf("is finally here", "is recommended by Stan S. Stanman", "is the best sold product on Mêlée Island", "is \uD83D\uDCAF", "is ❤️", "is fine")
        private val COMMENTS = arrayOf("Comment 1", "Comment 2", "Comment 3", "Comment 4", "Comment 5", "Comment 6")

        fun generateProducts(): List<ProductEntity> {
            val products = ArrayList<ProductEntity>(FIRST.size * SECOND.size)
            val rnd = Random()
            for (i in FIRST.indices) {
                for (j in SECOND.indices) {
                    val name = FIRST[i] + " " + SECOND[j]
                    val description = name + " " + DESCRIPTION[j]
                    val price = rnd.nextInt(240)
                    val id = FIRST.size * i + j + 1

                    val product = ProductEntity(id, name, description, price)
                    products.add(product)
                }
            }
            return products
        }

        fun generateCommentsForProducts(
                products: List<ProductEntity>): List<CommentEntity> {
            val comments = ArrayList<CommentEntity>()
            val rnd = Random()

            for (product in products) {
                val commentsNumber = rnd.nextInt(5) + 1
                for (i in 0 until commentsNumber) {
                    val productId = product.id
                    val text = COMMENTS[i] + " for " + product.name
                    val postedAt = Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis((commentsNumber - i).toLong()) + TimeUnit.HOURS.toMillis(i.toLong()))
                    val comment = CommentEntity(productId, text, postedAt)
                    comments.add(comment)
                }
            }

            return comments
        }

    }


}