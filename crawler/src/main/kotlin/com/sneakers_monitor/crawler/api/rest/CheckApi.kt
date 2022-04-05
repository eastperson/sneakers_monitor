package com.sneakers_monitor.crawler.api.rest

import com.sneakers_monitor.crawler.api.ApiResponse
import com.sneakers_monitor.crawler.service.nike.NikeCrawler
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.transaction.Transactional

@Transactional
@RequestMapping("/api/check")
@RestController
class CheckApi {

    @GetMapping("")
    fun nikeCrawler():ResponseEntity<*> {
        println("Response : check")
        return ResponseEntity.ok(ApiResponse.success(body = "OK"))
    }
}