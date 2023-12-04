package com.statista.code.challenge.domain;

import java.util.Currency;
import java.util.List;
import java.util.Set;

public interface BookingRepository {
    public Booking save(Booking booking);

    public Booking find(String id);

    public List<Booking> findAll();

    public List<Booking> findByDepartment(Department department);

    public Set<Currency> findCurrenciesUsed();

    public List<Booking> findByCurrency(String currency);
}
