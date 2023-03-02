package io.github.zhaofanzhe.scaffold.lottery;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LotteryMachine<T> {

    private final Map<LotteryConfigure<T>, Long> lotteryPool = new HashMap<>();

    private final Random random = new Random(System.currentTimeMillis());

    @SafeVarargs
    public LotteryMachine(LotteryConfigure<T>... configures) {
        for (LotteryConfigure<T> configure : configures) {
            this.lotteryPool.put(configure, configure.getQuantity());
        }
    }

    @SuppressWarnings("unchecked")
    public synchronized T lottery() {

        // 总数量
        long totalQuantity = 0;

        final LotteryConfigure<T>[] configures = lotteryPool.keySet().toArray(LotteryConfigure[]::new);

        for (LotteryConfigure<T> configure : configures) {
            // 剩余数量
            final Long remainderQuantity = lotteryPool.get(configure);
            totalQuantity += remainderQuantity;
        }

        if (totalQuantity == 0) {
            return null;
        }

        // 所有奖项概率
        double[] probabilityArray = new double[configures.length];

        // 初始概率
        double offsetProbability = 0.0;

        for (int i = 0; i < configures.length; i++) {
            final Long remainderQuantity = lotteryPool.get(configures[i]);

            double probability = ((double) remainderQuantity) / ((double) totalQuantity) * 100;

            offsetProbability += probability;

            if (i + 1 == configures.length) {
                probabilityArray[i] = 100.00;
            } else {
                probabilityArray[i] = offsetProbability;
            }
        }

        final double value = random.nextDouble(100.00);

        int currentIndex;

        for (currentIndex = 0; currentIndex < probabilityArray.length; currentIndex++) {
            if (value <= probabilityArray[currentIndex]) {
                break;
            }
        }

        final LotteryConfigure<T> configure = configures[currentIndex];

        lotteryPool.put(configure, lotteryPool.get(configure) - 1);

        return configure.getPayload();
    }

}
