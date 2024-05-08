package com.bnhp.stock.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UtilService {

    public static BigDecimal getDiff(BigDecimal n1, BigDecimal n2) {
        return n1.subtract(n2).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal getDiffPct(BigDecimal n1, BigDecimal n2) {
        return ((n1.subtract(n2)).divide(n2, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
    }

    public static <T> List<List<T>> partitionList(List<T> list, int partitionSize) {
        return IntStream.range(0, (list.size() + partitionSize - 1) / partitionSize)
                .mapToObj(i -> list.subList(i * partitionSize, Math.min((i + 1) * partitionSize, list.size())))
                .collect(Collectors.toList());
    }
}
