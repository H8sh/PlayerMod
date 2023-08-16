package net.h8sh.playermod.world.dimension.mansion;

import com.mojang.datafixers.util.Pair;

import java.util.*;

public class SuperMansionManager {
     public static List<Integer> DOMAIN_X = new ArrayList<>();
     public static List<Integer> DOMAIN_Y = new ArrayList<>();
     public static List<Pair<Integer, Integer>> TABLE = new ArrayList<>();
     public static Map<Integer, List<Integer>> SUPPORT_X = new HashMap<>();
     public static Map<Integer, Integer> SUPPORT_COUNT_X = new HashMap<>();
     public static Map<Integer, Integer> SUPPORT_COUNT_Y = new HashMap<>();
     public static Map<Integer, List<Integer>> SUPPORT_Y = new HashMap<>();

     public static void main(String[] args) {
         setVariables();
         System.out.println("DOMAIN_X: " + DOMAIN_X);
         System.out.println("DOMAIN_Y: " + DOMAIN_Y);

         setTable();
         System.out.println("TABLE: " + TABLE);

         ac4Loop();
     }

     public static void setVariables() {
         DOMAIN_X.add(1);
         DOMAIN_X.add(2);
         DOMAIN_Y.add(3);
         DOMAIN_Y.add(4);
         DOMAIN_Y.add(5);
     }

     public static void setTable() {
         TABLE.add(new Pair<>(1, 3));
         TABLE.add(new Pair<>(1, 4));
         TABLE.add(new Pair<>(2, 4));
         TABLE.add(new Pair<>(1, 6));
     }

     public static List<Pair<String, Integer>> setSupports() {
         List<Pair<String, Integer>> variableToRemove = new ArrayList<>();
         Iterator<Integer> iteratorX = DOMAIN_X.iterator();
         while (iteratorX.hasNext()) {
             int variable = iteratorX.next();
             List<Integer> supports = new ArrayList<>();
             int count = 0;
             for (Pair<Integer, Integer> pair : TABLE) {
                 if (pair.getFirst() == variable && DOMAIN_Y.contains(pair.getSecond())) {
                     count++;
                     supports.add(pair.getSecond());
                 }
             }
             SUPPORT_Y.put(variable, supports);
             SUPPORT_COUNT_X.put(variable, count);
             if (count == 0) {
                 iteratorX.remove();
                 variableToRemove.add(new Pair<>("x", variable));
             }
         }

         Iterator<Integer> iteratorY = DOMAIN_Y.iterator();
         while (iteratorY.hasNext()) {
             int variable = iteratorY.next();
             List<Integer> supports = new ArrayList<>();
             int count = 0;
             for (Pair<Integer, Integer> pair : TABLE) {
                 if (pair.getSecond() == variable && DOMAIN_X.contains(pair.getFirst())) {
                     count++;
                     supports.add(pair.getFirst());
                 }
             }
             SUPPORT_X.put(variable, supports);
             SUPPORT_COUNT_Y.put(variable, count);
             if (count == 0) {
                 iteratorY.remove();
                 variableToRemove.add(new Pair<>("y", variable));
             }
         }

         System.out.println("SUPPORT_X: " + SUPPORT_X);
         System.out.println("SUPPORT_Y: " + SUPPORT_Y);
         System.out.println("SUPPORT_COUNT_X: " + SUPPORT_COUNT_X);
         System.out.println("SUPPORT_COUNT_Y: " + SUPPORT_COUNT_Y);

         return variableToRemove;
     }

     public static List<Pair<String, Integer>> variablesToBeRemove(String domainName, int value) {
         List<Pair<String, Integer>> variableToRemove = new ArrayList<>();
         if ("x".equals(domainName) && SUPPORT_X.containsKey(value)) {
             for (Integer variable : SUPPORT_X.get(value)) {
                 if (DOMAIN_Y.contains(variable)) {
                     SUPPORT_COUNT_Y.put(variable, SUPPORT_COUNT_Y.get(variable) - 1);
                     if (SUPPORT_COUNT_Y.get(variable) == 0) {
                         SUPPORT_Y.remove(variable);
                         variableToRemove.add(new Pair<>("y", variable));
                     }
                 }
             }
         }

         if ("y".equals(domainName) && SUPPORT_Y.containsKey(value)) {
             for (Integer variable : SUPPORT_Y.get(value)) {
                 if (DOMAIN_X.contains(variable)) {
                     SUPPORT_COUNT_X.put(variable, SUPPORT_COUNT_X.get(variable) - 1);
                     if (SUPPORT_COUNT_X.get(variable) == 0) {
                         SUPPORT_X.remove(variable);
                         variableToRemove.add(new Pair<>("x", variable));
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
         System.out.println("DOMAIN_X: " + DOMAIN_X);
         System.out.println("DOMAIN_Y: " + DOMAIN_Y);
         System.out.println("SUPPORT_COUNT_X: " + SUPPORT_COUNT_X);
         System.out.println("SUPPORT_COUNT_Y: " + SUPPORT_COUNT_Y);
     }

}

