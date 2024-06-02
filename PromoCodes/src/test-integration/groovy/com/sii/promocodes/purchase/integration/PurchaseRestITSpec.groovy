package com.sii.promocodes.purchase.integration

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.sii.promocodes.purchase.domain.PurchaseFacade
import com.sii.promocodes.purchase.infrastructure.PurchaseController
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import org.springframework.http.MediaType


import static com.sii.promocodes.purchase.domain.PurchaseFixture.*

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(PurchaseController)
@AutoConfigureMockMvc(addFilters = false)
class PurchaseRestITSpec extends Specification {

    @SpringBean
    PurchaseFacade purchaseFacade = Mock()

    @Autowired
    MockMvc mockMvc

    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    def "should create a purchase"() {
        when:
        def response = mockMvc.perform(post("/api/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPurchaseRequest())))

        then:
        response.andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(createPurchaseDto())))

        and:
        1 * purchaseFacade.createPurchase(createPurchaseRequest()) >> createPurchaseDto()
    }
}
