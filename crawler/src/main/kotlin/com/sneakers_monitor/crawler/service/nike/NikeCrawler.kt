package com.sneakers_monitor.crawler.service.nike

import com.sneakers_monitor.crawler.domain.Brand
import com.sneakers_monitor.crawler.domain.Product
import com.sneakers_monitor.crawler.service.Crawler
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import javax.transaction.Transactional

@Service
class NikeCrawler (
    val nikeProductAppend: NikeProductAppend
) : Crawler {

    private val host: String = "https://www.nike.com"
    private val basePath: String = "/kr/launch"
    private val queryString: List<Pair<String, String>> = listOf(Pair("s","upcoming"))

    private fun getUrl(): String {
        val sj = StringJoiner("&")
        for (pair in queryString) {
            sj.add("${pair.first}=${pair.second}")
        }
        return "$host$basePath?${sj.toString()}"
    }

    private fun getDocument():Document {
        println("url : ${getUrl()}")
        return getDocument(getUrl())
    }

    private fun getDocument(url: String):Document {
        return Jsoup.connect(url).get()
    }

    @Transactional
    override fun crawl(): CrawlingData.Response {
        var products = hashSetOf<Product>()
        val body = getDocument().body()
        val elements = body.select(".item-list-wrap.upcoming-section li a.card-link")
        elements.forEach {
            val href = host + it.attr("href")
            val productId = it.attr("data-tag-pw-rank-product-id")
            val title = it.attr("title")
            val image:Element? = it.getElementsByTag("img").first()

            products.add(Product(title, productId, href, image?.attr("data-src") ?: "", Brand.NIKE))
        }
        detailCrawl(products)
        nikeProductAppend.addAll(products)
        val productDtoList = products.stream().map { ProductDto(it) }.collect(Collectors.toList())
        return CrawlingData.Response(productDtoList)
    }

    private fun detailCrawl(products: Set<Product>) {
        products.forEach {
            println("detailUrl : ${it.url}")
            val url = it.url ?: ""
            val body = getDocument(url).body()
            val title = body.select(".product-info h1.headline-5").first()
            val color = body.select(".product-info h5.headline-1").first()
            val price = body.select(".headline-5.pb6-sm.fs14-sm.fs16-md").first()
            val date = body.select(".product-info .available-date-component").first()
            if (title?.text() === it.title) println(it.title + "title true")
            it.title = title?.text() ?: ""
            it.color = color?.text() ?: ""
            it.price = price?.text() ?: ""
            it.price = price?.text() ?: ""
            it.date = date?.text() ?: ""
            if (it.date.isNotBlank() && it.date.contains("출시 예정")) {
                val date = it.date
                val month = date.substring(0, date.indexOf("월")).trim()
                val day = date.substring(date.indexOf("월") + 1, date.indexOf("일")).trim()
                val hour = date.substring(date.indexOf("시") - 2, date.indexOf("시")).trim()

                it.month = month.toInt()
                it.day = day.toInt()
                if (date.contains("오전")) {
                    it.hour = hour.toInt()
                } else if (date.contains("오후")) {
                    it.hour = hour.toInt() + 12
                }
                it.launch = false
            }
        }
    }
}

data class CrawlingData(val id:Int) {

    class Request
    class Response(data:MutableList<ProductDto>){
        val data = data
        val size:Int = data.size
    }
}

data class ProductDto (
    val id:Long?,
    val title: String?,
    val productId:String?,
    val url:String?,
    val color:String?,
    val price:String?,
    val month:Int?,
    val day:Int?,
    val hour:Int?,
    val image:String?,
    val brand:Brand?,
    val date:String,
    val launch:Boolean
) {
    constructor(product: Product): this (
        product.id,
        product.title,
        product.productId,
        product.url,
        product.color,
        product.price,
        product.month,
        product.day,
        product.hour,
        product.image,
        product.brand,
        product.date,
        product.launch
    )
}