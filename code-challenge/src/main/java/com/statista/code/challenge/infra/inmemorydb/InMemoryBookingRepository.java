package com.statista.code.challenge.infra.inmemorydb;

import com.statista.code.challenge.domain.Booking;
import com.statista.code.challenge.domain.BookingRepository;
import com.statista.code.challenge.domain.Department;
import com.statista.code.challenge.domain.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        if (!bookings.containsKey(id))
            throw new EntityNotFoundException("Booking with id " + id + " does not exist");
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
