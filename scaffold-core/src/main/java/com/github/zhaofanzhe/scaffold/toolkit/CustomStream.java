package com.github.zhaofanzhe.scaffold.toolkit;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class CustomStream {

    public static Stream<LocalDate> range(LocalDate start, LocalDate end) {
        final AtomicReference<LocalDate> current = new AtomicReference<>(start);
        return Stream.generate(() -> {
            LocalDate result = current.get();
            current.set(current.get().plusDays(1));
            return result;
        }).limit(Duration.between(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MIN)).toDays() + 1);
    }

}
