package com.statista.code.challenge.api.http.v1;

import com.statista.code.challenge.api.http.v1.requests.CreateBookingRequest;
import com.statista.code.challenge.api.http.v1.requests.UpdateBookingRequest;
import com.statista.code.challenge.api.http.v1.responses.BookingResponse;
import com.statista.code.challenge.usecases.booking.create.CreateBookingCommand;
import com.statista.code.challenge.usecases.booking.create.CreateBookingUseCase;
import com.statista.code.challenge.usecases.booking.find.FindBookingCommand;
import com.statista.code.challenge.usecases.booking.find.FindBookingUseCase;
import com.statista.code.challenge.usecases.booking.upsert.UpdateBookingCommand;
import com.statista.code.challenge.usecases.booking.upsert.UpdateBookingUseCase;
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
    private final UpdateBookingUseCase updateUseCase;
    private final FindBookingUseCase findBookingUseCase;

    public BookingController(
            CreateBookingUseCase bookingUseCase,
            UpdateBookingUseCase updateUseCase,
            FindBookingUseCase findBookingUseCase
    ) {
        this.bookingUseCase = bookingUseCase;
        this.updateUseCase = updateUseCase;
        this.findBookingUseCase = findBookingUseCase;
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

    @Operation(summary = "Update booking by id or create it if no exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid request body supplied", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
    })
    @PutMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateBooking(
            @PathVariable String bookingId,
            @Valid @RequestBody UpdateBookingRequest request
    ) {
        updateUseCase.upsert(UpdateBookingCommand.fromRequest(bookingId, request));
    }

    @Operation(summary = "Return booking by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Booking not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
    })
    @GetMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingResponse getBookingById(@PathVariable String bookingId) {
        return findBookingUseCase.find(FindBookingCommand.fromRequest(bookingId));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getBookingsOfType(@PathVariable String type) {
        return ResponseEntity.ok().build();
    }
}