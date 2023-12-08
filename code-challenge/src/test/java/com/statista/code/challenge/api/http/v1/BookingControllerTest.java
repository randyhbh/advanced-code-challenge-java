package com.statista.code.challenge.api.http.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statista.code.challenge.api.http.v1.requests.CreateBookingRequest;
import com.statista.code.challenge.api.http.v1.requests.UpdateBookingRequest;
import com.statista.code.challenge.domain.booking.BookingRepository;
import com.statista.code.challenge.domain.department.Department;
import helpers.MotherObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {

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
    @DisplayName("creating a booking with empty description and invalid email fails with bad request")
    public void testCreateBookingValidationFailed() throws Exception {
        var data = new CreateBookingRequest(
                "",
                1000,
                "EUR",
                Instant.now(),
                "validgmail.com",
                Department.MARKETING
        );
        var mvcResult = this.mockMvc.perform(
                        post("/bookingservice/booking")
                                .content(objectMapper.writeValueAsBytes(data))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andReturn();

        var contentAsString = mvcResult.getResponse().getContentAsString();
        var body = objectMapper.readValue(contentAsString, ProblemDetail.class);

        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(body.getProperties()).isNotNull();
        Assertions.assertThat(body.getProperties().size()).isEqualTo(2);
        Assertions.assertThat(body.getProperties().get("description")).isEqualTo("must not be empty");
        Assertions.assertThat(body.getProperties().get("email")).isEqualTo("must be a well-formed email address");
    }

    @Test
    @DisplayName("creating a booking with valid request body is successful")
    public void testCreateBookingIsSuccessful() throws Exception {
        var data = new CreateBookingRequest(
                "Test Description",
                1000,
                "EUR",
                Instant.now(),
                "valid@gmail.com",
                Department.MARKETING
        );

        this.mockMvc.perform(
                post("/bookingservice/booking")
                        .content(objectMapper.writeValueAsBytes(data))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("updating a booking with empty description and invalid email fails with bad request")
    public void testUpdateBookingValidationFailed() throws Exception {
        var booking = MotherObject.newMarketingBooking();
        bookingRepository.save(booking);
        var data = new UpdateBookingRequest(
                "",
                1000,
                "EUR",
                Instant.now(),
                "validgmail.com",
                Department.MARKETING
        );
        var mvcResult = this.mockMvc.perform(
                        put("/bookingservice/booking/" + booking.id())
                                .content(objectMapper.writeValueAsBytes(data))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andReturn();

        var contentAsString = mvcResult.getResponse().getContentAsString();
        var body = objectMapper.readValue(contentAsString, ProblemDetail.class);

        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(body.getProperties()).isNotNull();
        Assertions.assertThat(body.getProperties().size()).isEqualTo(2);
        Assertions.assertThat(body.getProperties().get("description")).isEqualTo("must not be empty");
        Assertions.assertThat(body.getProperties().get("email")).isEqualTo("must be a well-formed email address");
    }

    @Test
    @DisplayName("updating a booking with valid request body is successful")
    public void testUpdateBookingIsSuccessful() throws Exception {
        var newPrice = 30_000;
        var booking = MotherObject.newMarketingBooking();
        bookingRepository.save(booking);

        var data = new UpdateBookingRequest(
                "Test Description",
                newPrice,
                "EUR",
                Instant.now(),
                "valid@gmail.com",
                Department.MARKETING
        );

        this.mockMvc.perform(
                put("/bookingservice/booking/" + booking.id())
                        .content(objectMapper.writeValueAsBytes(data))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isAccepted());

        var bookingFromDb = bookingRepository.find(booking.id());

        Assertions.assertThat(bookingFromDb).isNotNull();
        Assertions.assertThat(bookingFromDb.price()).isEqualTo(newPrice);
    }

    @Test
    @DisplayName("find a booking with that do not exist fails with bad request")
    public void testFindBookingThrowsException() throws Exception {
        var bookingId = UUID.randomUUID().toString();

        var mvcResult = this.mockMvc.perform(
                        get("/bookingservice/booking/" + bookingId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andReturn();

        var contentAsString = mvcResult.getResponse().getContentAsString();
        var body = objectMapper.readValue(contentAsString, ProblemDetail.class);

        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(body.getDetail()).isEqualTo("Booking with id " + bookingId + " does not exist");
    }

    @Test
    @DisplayName("do business with a booking fails with unprocessable exception")
    public void testDoBusinessBookingThrowsException() throws Exception {
        var booking = MotherObject.newDesignBooking();
        bookingRepository.save(booking);

        var mvcResult = this.mockMvc.perform(
                        get("/bookingservice/booking/dobusiness/" + booking.id())
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isUnprocessableEntity())
                .andReturn();

        var contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        var body = objectMapper.readValue(contentAsString, ProblemDetail.class);

        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(body.getProperties()).isNotNull();
        Assertions.assertThat(body.getProperties().get("price")).isEqualTo("Minimum price must be higher than 200,00 €");
        Assertions.assertThat(body.getDetail()).isEqualTo("Problems while doing business with department: DESIGN");
    }
}