package com.sii.promocodes.promocode.integration

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.sii.promocodes.promocode.domain.PromoCodeFacade
import com.sii.promocodes.promocode.infrastructure.PromoCodeController
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static com.sii.promocodes.promocode.domain.PromoCodeFixture.createPromoCodeDto
import static com.sii.promocodes.promocode.domain.PromoCodeFixture.createPromoCodeRequest
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(PromoCodeController)
@AutoConfigureMockMvc(addFilters = false)
class PromoCodeRestITSpec extends Specification {

    @SpringBean
    PromoCodeFacade promoCodeFacade = Mock()

    @Autowired
    MockMvc mockMvc

    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)


    def "should create a promo code"() {
        when:
        def response = mockMvc.perform(post("/api/promo-code")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPromoCodeRequest())))

        then:
        response.andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(createPromoCodeDto())))

        and:
        1 * promoCodeFacade.createPromoCode(createPromoCodeRequest()) >> createPromoCodeDto()
    }

}
