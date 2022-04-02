package com.sneakers_monitor.crawler.domain

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
    var month:Int?,
    var day:Int?,
    var hour:Int?,
    var brand:Brand?,
    var date:String = "",
    var launch:Boolean = true) {

    constructor (title:String, productId:String, url:String, brand:Brand) : this(
        null, title, productId, url, null, null, null, null, null, brand
    ) {
        this.title = title
        this.productId = productId
        this.url = url
        this.brand = brand
    }

    constructor() : this(null, null, null, null, null, null, null, null, null, null)

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
        return "Product(title='$title', productId='$productId', url='$url', color='$color', price='$price', date='$date', month=$month, day=$day, hour=$hour)"
    }
}