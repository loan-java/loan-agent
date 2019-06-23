package com.mod.loan.util;

import java.util.*;

import org.apache.commons.math3.util.Pair;

/**
 * @ author liujianjian
 * @ date 2019/6/23 17:00
 */
public class WeightUtil {

    public static String random(Map<String, Integer> map) {
        //获取权重总和
        int sum = map.values().parallelStream().reduce(Integer::sum).get();
        Integer random = new Random().nextInt(sum);
        for (String str : map.keySet()) {
            int weight = map.get(str);
            if (random > weight) {
                random -= weight;
            } else {
                return str;
            }
        }
        return "";
    }

    public static String random2(Map<String, Double> map) {
        List<Pair<String, Double>> list = new ArrayList<>();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            list.add(new Pair<String, Double>(entry.getKey(), entry.getValue()));
        }
        WeightRandom w = new WeightRandom<>(list);
        return (String) w.random();
    }

    static class WeightRandom<K, V extends Number> {

        private TreeMap<Double, K> weightMap = new TreeMap<Double, K>();

        WeightRandom(List<Pair<K, V>> list) {
            for (Pair<K, V> pair : list) {
                double lastWeight = this.weightMap.size() == 0 ? 0 : this.weightMap.lastKey().doubleValue();//统一转为double
                this.weightMap.put(pair.getValue().doubleValue() + lastWeight, pair.getKey());//权重累加
            }
        }

        K random() {
            double randomWeight = this.weightMap.lastKey() * Math.random();
            SortedMap<Double, K> tailMap = this.weightMap.tailMap(randomWeight, false);
            return this.weightMap.get(tailMap.firstKey());
        }

    }
}
