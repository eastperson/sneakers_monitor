package com.sneakers_monitor.crawler.repository

import com.sneakers_monitor.crawler.domain.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Long>