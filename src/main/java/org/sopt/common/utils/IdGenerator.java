package org.sopt.common.utils;

import java.util.concurrent.atomic.AtomicLong;

public final class IdGenerator {

    private static final AtomicLong sequence = new AtomicLong(1);

    private IdGenerator() {}

    public static Long next() {
        return sequence.getAndIncrement();
    }
}