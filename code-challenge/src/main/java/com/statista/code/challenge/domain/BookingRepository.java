package com.statista.code.challenge.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Set;

public interface BookingRepository {
    public Booking save(Booking booking);

    public Booking find(String id);

    public List<Booking> findAll();

    public List<String> findByDepartment(Department department);

    public Set<String> findCurrenciesUsed();

    public BigDecimal getTotalPriceByCurrency(Currency currency);
}
