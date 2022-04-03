package com.sneakers_monitor.api.rest

import com.sneakers_monitor.crawler.service.Crawler
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/crawl")
@RestController
class CrawlerApi(val crawler: Crawler) {

    @GetMapping("/nike")
    fun nikeCrawler():ResponseEntity<*> {
        val data = crawler.crawl()
        return ResponseEntity.ok(data)
    }
}