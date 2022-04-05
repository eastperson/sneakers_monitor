package com.sneakers_monitor.crawler.domain

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null,
    var title: String?,
    var productId:String?,
    var url:String?,
    var color:String?,
    var price:String?,
    var image:String?,
    var brand:Brand?,
    var date: LocalDateTime?,
    var launch:Boolean = true) {

    constructor (title:String, productId:String, url:String, image:String, brand:Brand) : this(
        null, title, productId, url, null, null, null, brand, null
    ) {
        this.title = title
        this.productId = productId
        this.url = url
        this.image = image
        this.brand = brand
    }

    constructor() : this(null, null, null, null, null, null, null, null, null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (productId != other.productId) return false

        return true
    }

    override fun hashCode(): Int {
        return productId.hashCode()
    }

    override fun toString(): String {
        return "Product(id=$id, title=$title, productId=$productId, url=$url, color=$color, price=$price, image=$image, brand=$brand, date=$date, launch=$launch)"
    }

}