package com.statista.code.challenge.api.http.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statista.code.challenge.api.http.v1.responses.BookingPriceSumByCurrencyResponse;
import com.statista.code.challenge.domain.booking.BookingRepository;
import com.statista.code.challenge.util.CurrencyUtil;
import helpers.MotherObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookingOperationsControllerTest {

    @Autowired
    BookingController bookingController;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        bookingRepository.removeAll();
    }

    @Test
    @DisplayName("price sum of all bookings with the same currency is successful")
    public void testPriceSumOfBookingsIsSuccessful() throws Exception {
        var currency = "EUR";
        var booking1 = MotherObject.newDesignBooking();
        var booking2 = MotherObject.newDesignBooking("USD");
        var booking3 = MotherObject.newMarketingBooking();

        var expectedResult = CurrencyUtil.getPriceInCurrency(
                booking1.price() + booking3.price(),
                CurrencyUtil.getCurrencyOrThrow(currency)
        );

        bookingRepository.save(booking1);
        bookingRepository.save(booking2);
        bookingRepository.save(booking3);

        var mvcResult = this.mockMvc.perform(
                        get("/bookingservice/sum/" + currency)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();

        var contentAsString = mvcResult.getResponse().getContentAsString();
        var body = objectMapper.readValue(contentAsString, BookingPriceSumByCurrencyResponse.class);


        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(body.currency()).isEqualTo(currency);
        Assertions.assertThat(body.total()).isEqualTo(expectedResult);
    }
}