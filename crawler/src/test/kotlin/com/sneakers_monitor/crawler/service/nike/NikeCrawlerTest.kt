package com.sneakers_monitor.crawler.service.nike

import com.sneakers_monitor.crawler.repository.ProductRepository
import com.sneakers_monitor.crawler.service.Crawler
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile

@Profile("test")
@SpringBootTest
internal class NikeCrawlerTest {

    @Autowired
    lateinit var crawler: Crawler

    @Autowired
    lateinit var productRepository: ProductRepository

    @DisplayName("크롤링 테스트_성공")
    @Test
    fun crawl() {
        val beforeSize = productRepository.findAll().size
        crawler.crawl()
        val afterSize = productRepository.findAll().size
        Assertions.assertThat(beforeSize).isNotEqualTo(afterSize)
        println("before size: $beforeSize, after size: $afterSize")
    }
}