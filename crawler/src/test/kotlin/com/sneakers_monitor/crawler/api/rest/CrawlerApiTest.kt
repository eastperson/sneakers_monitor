package com.sneakers_monitor.crawler.api.rest

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.sneakers_monitor.crawler.api.ApiResponse
import com.sneakers_monitor.crawler.repository.ProductRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

internal class CrawlerApiTest: RestControllerTest() {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `나이키 데이터를 크롤링하고 저장한다`() {
        val result = mockMvc.get("/api/crawl/nike") {

            }.andDo {
                print()
            }.andExpect {
                status { isOk() }
                jsonPath("data.size").exists()
                jsonPath("data").exists()
            }.andReturn()
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
        val response = objectMapper.readValue(result.response.contentAsString, ApiResponse::class.java)
        val body:LinkedHashMap<String, Object> = response.body as LinkedHashMap<String, Object>
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(body).isNotNull
        Assertions.assertThat(body["size"] as Int).isEqualTo(productRepository.findAll().size)
    }
}