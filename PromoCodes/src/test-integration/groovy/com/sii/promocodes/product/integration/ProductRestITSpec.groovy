package com.sii.promocodes.product.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.sii.promocodes.product.domain.ProductFacade
import com.sii.promocodes.product.infrastructure.ProductController
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static com.sii.promocodes.product.domain.ProductFixture.createProductDto
import static com.sii.promocodes.product.domain.ProductFixture.createProductRequest
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ProductController)
@AutoConfigureMockMvc(addFilters = false)
class ProductRestITSpec extends Specification {

    @SpringBean
    ProductFacade productFacade = Mock()

    @Autowired
    MockMvc mockMvc

    ObjectMapper objectMapper = new ObjectMapper()

    def "should create a product"() {
        when:
        def response = mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createProductRequest())))

        then:
        response.andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(createProductDto())))

        and:
        1 * productFacade.createProduct(createProductRequest()) >> createProductDto()
    }

    def "should not create a product with blank name"() {
        given:
        def request = createProductRequest({
            it.name = ""
        })

        when:
        def response = mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(status().isBadRequest())
    }

    def "should not create a product with negative price"() {
        given:
        def request = createProductRequest({
            it.price = -1
        })

        when:
        def response = mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(status().isBadRequest())
    }

    def "should not create a product without a currency"() {
        given:
        def request = createProductRequest({
            it.currency = null
        })

        when:
        def response = mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(status().isBadRequest())
    }

}
