package net.h8sh.playermod.world.dimension.mansion;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;

import java.util.*;

public class ac {

    public static List<Pair<String, List<Integer>>> DOMAINS = new ArrayList<>();
    public static List<Pair<Integer, Integer>> TABLE = new ArrayList<>();
    public static Map<String, Map<Integer, List<Integer>>> SUPPORTS = new HashMap<>();
    public static Map<String, Map<Integer, Integer>> SUPPORTS_COUNTER = new HashMap<>();


    public static void main(String[] args) {
        setVariables();

        setTable();
        System.out.println("TABLE: " + TABLE);

        ac4Loop();
    }

    public static void setVariables() {
        List<Integer> DOMAIN_X = new ArrayList<>();
        DOMAIN_X.add(1);
        DOMAIN_X.add(2);
        DOMAINS.add(new Pair<>("domain_x", DOMAIN_X));

        List<Integer> DOMAIN_Y = new ArrayList<>();
        DOMAIN_Y.add(3);
        DOMAIN_Y.add(4);
        DOMAIN_Y.add(5);
        DOMAINS.add(new Pair<>("domain_y", DOMAIN_Y));

        System.out.println("DOMAIN_X: " + DOMAIN_X);
        System.out.println("DOMAIN_Y: " + DOMAIN_Y);

    }

    public static void setTable() {
        TABLE.add(new Pair<>(1, 3));
        TABLE.add(new Pair<>(1, 4));
        TABLE.add(new Pair<>(2, 4));
        TABLE.add(new Pair<>(1, 6));
    }

    public static List<Pair<String, Integer>> setSupports() {
        List<Pair<String, Integer>> variableToRemove = new ArrayList<>();
        Set<Pair<String, String>> processedPairs = new HashSet<>();

        for (var domainX : DOMAINS) {
            Map<Integer, List<Integer>> supportX = new HashMap<>();
            Map<Integer, Integer> supportCounterX = new HashMap<>();
            for (var domainY : DOMAINS) {

                if (!domainX.getFirst().equals(domainY.getFirst()) && (!processedPairs.contains(new Pair<>(domainX.getFirst(), domainY.getFirst())) || !processedPairs.contains(new Pair<>(domainX.getFirst(), domainY.getFirst())))) {
                    processedPairs.add(new Pair<>(domainX.getFirst(), domainY.getFirst()));
                    processedPairs.add(new Pair<>(domainY.getFirst(), domainX.getFirst()));


                    Map<Integer, List<Integer>> supportY = new HashMap<>();
                    Map<Integer, Integer> supportCounterY = new HashMap<>();

                    Iterator<Integer> iteratorX = domainX.getSecond().iterator();
                    while (iteratorX.hasNext()) {
                        int variable = iteratorX.next();
                        List<Integer> supports = new ArrayList<>();
                        int counter = 0;
                        for (Pair<Integer, Integer> pair : TABLE) {
                            if (pair.getFirst() == variable && domainY.getSecond().contains(pair.getSecond())) {
                                counter++;
                                supports.add(pair.getSecond());
                            }
                        }
                        supportY.put(variable, supports);
                        supportCounterX.put(variable, counter);
                        if (counter == 0) {
                            iteratorX.remove();
                            variableToRemove.add(new Pair<>(domainX.getFirst(), variable));
                        }
                    }

                    Iterator<Integer> iteratorY = domainY.getSecond().iterator();
                    while (iteratorY.hasNext()) {
                        int variable = iteratorY.next();
                        List<Integer> supports = new ArrayList<>();
                        int count = 0;
                        for (Pair<Integer, Integer> pair : TABLE) {
                            if (pair.getSecond() == variable && domainX.getSecond().contains(pair.getFirst())) {
                                count++;
                                supports.add(pair.getFirst());
                            }
                        }
                        supportX.put(variable, supports);
                        supportCounterY.put(variable, count);
                        if (count == 0) {
                            iteratorY.remove();
                            variableToRemove.add(new Pair<>(domainY.getFirst(), variable));
                        }
                    }
                    SUPPORTS.put(domainY.getFirst(), supportY);
                    SUPPORTS_COUNTER.put(domainY.getFirst(), supportCounterY);
                }

            }
            SUPPORTS.put(domainX.getFirst(), supportX);
            SUPPORTS_COUNTER.put(domainX.getFirst(), supportCounterX);
        }

        System.out.println(DOMAINS);
        return variableToRemove;
    }

    public static List<Pair<String, Integer>> variablesToBeRemove(String domainName, int value) {
        List<Pair<String, Integer>> variableToRemove = new ArrayList<>();
        for (var domainX : DOMAINS) {
            Map<Integer, List<Integer>> supportX = SUPPORTS.get(domainX.getFirst());
            Map<Integer, Integer> supportCounterX = SUPPORTS_COUNTER.get(domainX.getFirst());
            for (var domainY : DOMAINS) {
                if (!domainX.getFirst().equals(domainY.getFirst())) {
                    Map<Integer, List<Integer>> supportY = SUPPORTS.get(domainY.getFirst());
                    Map<Integer, Integer> supportCounterY = SUPPORTS_COUNTER.get(domainY.getFirst());

                    if (domainX.getFirst().equals(domainName) && supportX.containsKey(value)) {
                        for (Integer variable : supportX.get(value)) {
                            if (domainY.getSecond().contains(variable)) {
                                supportCounterY.put(variable, supportCounterY.get(variable) - 1);
                                if (supportCounterY.get(value) == 0) {
                                    supportY.remove(variable);
                                    variableToRemove.add(new Pair<>(domainY.getFirst(), variable));
                                }
                            }
                        }
                    }

                    if (domainY.getFirst().equals(domainName) && supportY.containsKey(value)) {
                        for (Integer variable : supportY.get(value)) {
                            if (domainX.getSecond().contains(variable)) {
                                supportCounterX.put(variable, supportCounterX.get(variable) - 1);
                                if (supportCounterX.get(variable) == 0) {
                                    supportX.remove(variable);
                                    variableToRemove.add(new Pair<>(domainX.getFirst(), variable));
                                }
                            }
                        }
                    }
                }
            }
        }

        return variableToRemove;
    }

    public static void ac4Loop() {
        List<Pair<String, Integer>> queue = new ArrayList<>();
        for (Pair<String, Integer> pair : setSupports()) {
            queue.add(pair);
        }
        while (queue.size() > 0) {
            Pair<String, Integer> pair = queue.get(queue.size() - 1);
            queue.remove(queue.size() - 1);
            for (Pair<String, Integer> couple : variablesToBeRemove(pair.getFirst(), pair.getSecond())) {
                queue.add(couple);
            }
        }
        System.out.println("RESULT ----------------------");
        System.out.println("DOMAIN_X: " + DOMAINS.get(0).getSecond());
        System.out.println("DOMAIN_Y: " + DOMAINS.get(1).getSecond());

    }

}

