package com.sneakers_monitor.crawler.api.rest

import com.sneakers_monitor.crawler.api.ApiResponse
import com.sneakers_monitor.crawler.service.nike.NikeCrawler
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.transaction.Transactional

@Transactional
@RequestMapping("/api/crawl")
@RestController
class CrawlerApi(val nikeCrawler: NikeCrawler) {

    @GetMapping("/nike")
    fun nikeCrawler():ResponseEntity<*> {
        val data = nikeCrawler.crawl()
        return ResponseEntity.ok(ApiResponse.success(data))
    }
}