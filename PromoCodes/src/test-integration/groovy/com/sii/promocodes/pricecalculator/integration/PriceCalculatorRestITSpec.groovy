package com.sii.promocodes.pricecalculator.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.sii.promocodes.pricecalculator.domain.PriceCalculatorFacade
import com.sii.promocodes.pricecalculator.infrastructure.PriceCalculatorController
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static com.sii.promocodes.pricecalculator.domain.PriceCalculatorFixture.*

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(PriceCalculatorController)
@AutoConfigureMockMvc(addFilters = false)
class PriceCalculatorRestITSpec extends Specification {

    @SpringBean
    PriceCalculatorFacade priceCalculatorFacade = Mock()

    @Autowired
    MockMvc mockMvc

    ObjectMapper objectMapper = new ObjectMapper()

    def "should calculate the price" () {

        given:
        def expectedCalculatedPrice = createCalculatedPrice()

        when:
        def response = mockMvc.perform(post("/api/calculate-price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCalculatePriceRequest())))

        then:
        response.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCalculatedPrice)))

        and:
        1 * priceCalculatorFacade.calculatePrice(createCalculatePriceRequest()) >> expectedCalculatedPrice
    }

    def "should calculate the price and return warning for mismatched currencies" () {
        given:
        def request = createCalculatePriceRequest({
            it.currency = "USD"
        })

        def expectedCalculatedPrice = createCalculatedPrice({
            it.warning = "Currency mismatch"
        })

        when:
        def response = mockMvc.perform(post("/api/calculate-price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCalculatedPrice)))

        and:
        1 * priceCalculatorFacade.calculatePrice(request) >> expectedCalculatedPrice
    }

    def "should calculate the price to 0 and return warning for when the discount is larger than the price" () {
        given:
        def request = createCalculatePriceRequest({
            it.promoCode = "EUR_CODE_ACTIVE_BIG"
        })

        def expectedCalculatedPrice = createCalculatedPrice({
            it.calculatedPrice = 0
            it.warning = "Discount is larger than the price"
        })

        when:
        def response = mockMvc.perform(post("/api/calculate-price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCalculatedPrice)))

        and:
        1 * priceCalculatorFacade.calculatePrice(request) >> expectedCalculatedPrice
    }

}
