package com.statista.code.challenge.infra.inmemorydb;

import com.statista.code.challenge.domain.*;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryBookingRepository implements BookingRepository {
    private final Map<String, Booking> bookings;
    private final Clock clock;

    public InMemoryBookingRepository(Clock clock) {
        this.clock = clock;
        bookings = new ConcurrentHashMap<>();
    }

    public Booking save(Booking booking) {
        bookings.put(booking.id(), booking);
        return booking;
    }

    public Booking find(String id) {
        return bookings.get(id);
    }

    public List<Booking> findAll() {
        return bookings.values().stream().toList();
    }

    public List<Booking> findByDepartment(Department department) {
        return bookings
                .values()
                .stream()
                .filter(booking -> booking.department().equals(department))
                .collect(Collectors.toList());
    }

    public Set<Currency> findCurrenciesUsed() {
        return bookings
                .values()
                .stream()
                .map(Booking::currency)
                .collect(Collectors.toSet());
    }

    public List<Booking> findByCurrency(String currency) {
        return bookings
                .values()
                .stream()
                .filter(booking -> booking.currency().getCurrencyCode().equalsIgnoreCase(currency))
                .collect(Collectors.toList());
    }
}
