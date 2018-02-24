package com.alfianlosari.productlistdetail.model

import java.util.*

interface Comment {

    var id: Int?
    var productId: Int
    var text: String
    var postedAt: Date

}