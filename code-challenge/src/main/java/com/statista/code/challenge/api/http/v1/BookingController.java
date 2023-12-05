package com.statista.code.challenge.api.http.v1;

import com.statista.code.challenge.api.http.v1.requests.CreateBookingRequest;
import com.statista.code.challenge.api.http.v1.responses.BookingResponse;
import com.statista.code.challenge.usecases.booking.create.CreateBookingCommand;
import com.statista.code.challenge.usecases.booking.create.CreateBookingUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking-service/booking")
public class BookingController {

    private final CreateBookingUseCase bookingUseCase;

    public BookingController(CreateBookingUseCase bookingUseCase) {
        this.bookingUseCase = bookingUseCase;
    }

    @Operation(summary = "Create a new booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid request body supplied", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))})
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBooking(@Valid @RequestBody CreateBookingRequest request) {
        bookingUseCase.create(CreateBookingCommand.fromRequest(request));
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<?> updateBooking(@PathVariable String transactionId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable String bookingId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getBookingsOfType(@PathVariable String type) {
        return ResponseEntity.ok().build();
    }
}