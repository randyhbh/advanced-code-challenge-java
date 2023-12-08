package com.statista.code.challenge.infra.inmemorydb;

import com.statista.code.challenge.domain.booking.Booking;
import com.statista.code.challenge.domain.booking.BookingRepository;
import com.statista.code.challenge.domain.department.Department;
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

    public List<String> findByDepartment(Department department) {
        return bookings
                .values()
                .stream()
                .filter(booking -> booking.department().equals(department))
                .map(Booking::id)
                .collect(Collectors.toList());
    }

    public Set<String> findCurrenciesUsed() {
        return bookings
                .values()
                .stream()
                .map(Booking::currency)
                .map(Currency::getCurrencyCode)
                .collect(Collectors.toSet());
    }

    @Override
    public Integer getTotalPriceByCurrency(Currency currency) {
        return bookings
                .values()
                .stream()
                .filter(booking -> booking.currency().equals(currency))
                .map(Booking::price)
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    public void removeAll() {
        bookings.clear();
    }
}
