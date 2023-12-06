package com.statista.code.challenge.api.http.v1;

import com.statista.code.challenge.api.http.v1.requests.CreateBookingRequest;
import com.statista.code.challenge.api.http.v1.requests.UpdateBookingRequest;
import com.statista.code.challenge.api.http.v1.responses.BookingResponse;
import com.statista.code.challenge.api.http.v1.responses.BookingUsedCurrenciesResponse;
import com.statista.code.challenge.api.http.v1.responses.BookingsByDepartmentResponse;
import com.statista.code.challenge.domain.department.Department;
import com.statista.code.challenge.usecases.booking.create.CreateBookingCommand;
import com.statista.code.challenge.usecases.booking.create.CreateBookingUseCase;
import com.statista.code.challenge.usecases.booking.dobusiness.DoBusinessForBookingCommand;
import com.statista.code.challenge.usecases.booking.dobusiness.DoBusinessForBookingUseCase;
import com.statista.code.challenge.usecases.booking.find.FindBookingCommand;
import com.statista.code.challenge.usecases.booking.find.FindBookingUseCase;
import com.statista.code.challenge.usecases.booking.findbydepartment.FindBookingByDepartmentCommand;
import com.statista.code.challenge.usecases.booking.findbydepartment.FindBookingByDepartmentUseCase;
import com.statista.code.challenge.usecases.booking.findcurrency.FindUsedCurrenciesUseCase;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookingservice/booking")
public class BookingController {

    private final CreateBookingUseCase bookingUseCase;
    private final UpdateBookingUseCase updateUseCase;
    private final FindBookingUseCase findBookingUseCase;
    private final FindBookingByDepartmentUseCase findBookingByDepartmentUseCase;
    private final FindUsedCurrenciesUseCase findUsedCurrenciesUseCase;
    private final DoBusinessForBookingUseCase doBusinessForBookingUseCase;

    public BookingController(
            CreateBookingUseCase bookingUseCase,
            UpdateBookingUseCase updateUseCase,
            FindBookingUseCase findBookingUseCase,
            FindBookingByDepartmentUseCase findBookingByDepartmentUseCase,
            FindUsedCurrenciesUseCase findUsedCurrenciesUseCase,
            DoBusinessForBookingUseCase doBusinessForBookingUseCase
    ) {
        this.bookingUseCase = bookingUseCase;
        this.updateUseCase = updateUseCase;
        this.findBookingUseCase = findBookingUseCase;
        this.findBookingByDepartmentUseCase = findBookingByDepartmentUseCase;
        this.findUsedCurrenciesUseCase = findUsedCurrenciesUseCase;
        this.doBusinessForBookingUseCase = doBusinessForBookingUseCase;
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
    public void updateBooking(@PathVariable String bookingId, @Valid @RequestBody UpdateBookingRequest request) {
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

    @Operation(summary = "Return all bookings for the same department")
    @GetMapping("/department/{department}")
    @ResponseStatus(HttpStatus.OK)
    public BookingsByDepartmentResponse getBookingByDepartment(@PathVariable Department department) {
        return findBookingByDepartmentUseCase.find(FindBookingByDepartmentCommand.fromRequest(department));
    }

    @Operation(summary = "Returns all used currencies in bookings")
    @GetMapping("/currencies")
    @ResponseStatus(HttpStatus.OK)
    public BookingUsedCurrenciesResponse getCurrencies() {
        return findUsedCurrenciesUseCase.find();
    }

    @Operation(summary = "Returns the result of doing business with the department of the booking")
    @GetMapping("/dobusiness/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public Object doBusiness(@PathVariable String bookingId) {
        return doBusinessForBookingUseCase.doBusiness(DoBusinessForBookingCommand.fromRequest(bookingId));
    }
}