package com.sneakers_monitor.crawler.service.nike

import com.sneakers_monitor.crawler.domain.Product
import com.sneakers_monitor.crawler.repository.ProductRepository
import org.springframework.stereotype.Component
import java.util.HashSet

@Component
class NikeProductAppend(
    val productRepository: ProductRepository
) {
    fun addAll(products: HashSet<Product>) {
        productRepository.saveAll(products)
    }
}