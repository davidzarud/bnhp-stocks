package com.bnhp.stock.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UtilService {

    public static Double getDiff(Double n1, Double n2) {
        return n1 - (n2);
    }

    public static Double getDiffPct(Double n1, Double n2) {
        return ((n1 - (n2)) / (n2) * (100.0));
    }

    public static <T> List<List<T>> partitionList(List<T> list, int partitionSize) {
        return IntStream.range(0, (list.size() + partitionSize - 1) / partitionSize)
                .mapToObj(i -> list.subList(i * partitionSize, Math.min((i + 1) * partitionSize, list.size())))
                .collect(Collectors.toList());
    }
}
