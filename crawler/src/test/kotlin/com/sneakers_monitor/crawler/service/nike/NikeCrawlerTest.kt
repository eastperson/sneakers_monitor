package com.sneakers_monitor.crawler.service.nike

import com.sneakers_monitor.crawler.repository.ProductRepository
import com.sneakers_monitor.crawler.service.Crawler
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
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

    @Test
    fun `페이지의 모든 정보를 크롤링하고 데이터를 저장하고 앞으로 3일치 슬랙을 보낸다`() {
        val beforeSize = productRepository.findAll().size
        crawler.crawl()
        val afterSize = productRepository.findAll().size
        Assertions.assertThat(beforeSize).isNotEqualTo(afterSize)
        println("before size: $beforeSize, after size: $afterSize")
    }
}