package com.sii.promocodes.product.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.sii.promocodes.product.domain.ProductConfiguration
import com.sii.promocodes.product.domain.ProductFacade
import com.sii.promocodes.product.infrastructure.ProductController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static com.sii.promocodes.product.domain.ProductFixture.createProductDto
import static com.sii.promocodes.product.domain.ProductFixture.createProductRequest
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ProductController)
@AutoConfigureMockMvc(addFilters = false)
class ProductRestITSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    @MockBean
    private ProductFacade productFacade = Mock()

    private ObjectMapper objectMapper = new ObjectMapper()

    def "should create a product"() {
        when:
        def response = mockMvc.perform(post("/api/product")
                .content(objectMapper.writeValueAsString(createProductRequest())))

        then:
        response.andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(createProductDto())))

        and:
        1 * productFacade.createProduct(createProductRequest()) >> createProductDto()
    }
}
