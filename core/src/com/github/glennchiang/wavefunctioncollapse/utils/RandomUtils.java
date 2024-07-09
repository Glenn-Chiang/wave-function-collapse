package com.github.glennchiang.wavefunctioncollapse.utils;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomUtils {
    public static <T> T weightedRandomSelect(Map<T, Float> elements) {
        double totalWeight = elements.values().stream().mapToDouble(Float::doubleValue).sum();
        double randomValue = new Random().nextDouble() * totalWeight;
        for (Map.Entry<T, Float> weightedElement: elements.entrySet()) {
            randomValue -= weightedElement.getValue();
            if (randomValue <= 0) {
                return weightedElement.getKey();
            }
        }
        // Should not be possible to reach this point
        return null;
    }
}
