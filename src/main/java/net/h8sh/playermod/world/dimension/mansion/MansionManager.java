package net.h8sh.playermod.world.dimension.mansion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojang.datafixers.util.Pair;
import net.h8sh.playermod.world.dimension.mansion.reader.Prototypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MansionManager {
    private static final Pair<Integer, Integer> RIGHT = new Pair<>(0, 1);
    private static final Pair<Integer, Integer> LEFT = new Pair<>(0, -1);
    private static final Pair<Integer, Integer> UP = new Pair<>(-1, 0);
    private static final Pair<Integer, Integer> DOWN = new Pair<>(1, 0);
    private static final int ROW = 3;
    private static final int COLUMN = 3;
    private static final int MAX_ITERATIONS = 20;
    private static Prototypes PROTOTYPES;

    public static void main(String[] args) {
        getTemplatesLocationFromMap();
    }

    public static void setPrototypesFromJson(Prototypes prototypes) {
        MansionManager.PROTOTYPES = prototypes;
    }

    public static Prototypes getPrototypes() {
        Prototypes prototypes = null;
        try {
            prototypes = PROTOTYPES.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return prototypes;
    }

    public static void createMansionInWorld(ServerLevel world, ServerPlayer player) {

        //TODO

    }

    public static List<Pair<String, Integer>> getTemplatesLocationFromMap() {
        var mansion = createMansion();
        List<ArrayList<Object>> structures = new ArrayList<>();
        List<Pair<String, Integer>> textures = new ArrayList<>();

        //var prototypes = loadJsonInputFiles(INPUT_DIRECTORY);
        List<Prototypes> prototypes = new ArrayList<>();
        prototypes.add(getPrototypes());
        int prototypesTypes = prototypes.get(0).getPrototype().size();
        for (int i = 0; i < prototypesTypes; i++) {

            var template = new ArrayList<>();

            template.add(prototypes.get(0).getPrototype().get(i).getStructure().getName().getName_type());
            template.add(prototypes.get(0).getPrototype().get(i).getStructure().getMesh().getMesh_type());
            template.add(prototypes.get(0).getPrototype().get(i).getStructure().getRotation().getRotation_type());

            structures.add(template);
        }

        for (List<Prototypes>[] lists : mansion) {
            for (List<Prototypes> list : lists) {
                String name = list.get(0).getPrototype().get(0).getStructure().getName().getName_type();
                for (ArrayList<Object> structure : structures) {
                    if (structure.contains(name)) {
                        var template = new Pair<>((String) structure.get(1), (Integer) structure.get(2));
                        textures.add(template);
                    }
                }
            }
        }
        System.out.println(textures);
        return textures;
    }

    public static List<Prototypes>[][] createMansion() {
        var wave = createWave(ROW, COLUMN);
        var entropy = createEntropy(ROW, COLUMN);

        return createMansionMap(wave, entropy);
    }

    private static List<Prototypes>[][] createMansionMap(List<Prototypes>[][] wave, int[][] entropy) {
        for (int i = 0; i < MAX_ITERATIONS && !isCollapsed(entropy); i++) {
            iterate(wave, entropy);
        }
        if (isCollapsed(entropy)) {
            System.out.println("All cells have collapsed to a single structure. Stopping the process.");
            System.out.println("Entropy: ");
            for (int i = 0; i < wave.length; i++) {
                for (int j = 0; j < wave[i].length; j++) {
                    System.out.print(entropy[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("Wave: ");
            for (List<Prototypes>[] lists : wave) {
                for (List<Prototypes> list : lists) {
                    System.out.print(list.get(0).getPrototype().get(0).getStructure().getName().getName_type() + " ");
                }
                System.out.println();
            }
        }

        return wave;
    }

    private static boolean isCollapsed(int[][] entropy) {
        int cellsNotCollapsed = 0;
        for (int i = 0; i < entropy.length; i++) {
            for (int j = 0; j < entropy[i].length; j++) {
                if (entropy[i][j] > 1) {
                    cellsNotCollapsed++;
                }
            }
        }
        return cellsNotCollapsed == 0;
    }

    private static int[][] createEntropy(int rowCount, int columnCount) {
        //int prototypesTypes = loadJsonInputFiles(INPUT_DIRECTORY).get(0).getPrototype().size();
        List<Prototypes> list = new ArrayList<>();
        list.add(getPrototypes());
        int prototypesTypes = list.get(0).getPrototype().size();
        var entropy = new int[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                entropy[i][j] = prototypesTypes;
            }
        }
        return entropy;
    }

    private static void iterate(List<Prototypes>[][] wave, int[][] entropy) {
        var minCoordinates = getMinEntropyCoordinates(entropy, wave);
        collapseAt(wave, entropy, minCoordinates);
        propagate(wave, minCoordinates, entropy);
    }

    private static void propagate(List<Prototypes>[][] wave, Pair<Integer, Integer> minCoordinates, int[][] entropy) {
        List<Pair<Integer, Integer>> cells = new ArrayList<>();
        cells.add(minCoordinates);


        while (cells.size() != 0) {
            var currentCoordinates = cells.get(cells.size() - 1);
            cells.remove(cells.size() - 1);

            for (Pair<Integer, Integer> direction : validDirections(currentCoordinates, wave)) {
                var neighborCoordinates = addDirection(currentCoordinates, direction);

                if (isValidCoordinates(neighborCoordinates, wave)) {

                    var neighborCell = wave[neighborCoordinates.getFirst()][neighborCoordinates.getSecond()];
                    var currentCell = wave[currentCoordinates.getFirst()][currentCoordinates.getSecond()];

                    var neighborPrototypes = getStructuresAvailable(neighborCell);
                    var possiblePrototypes = getStructuresAvailableInDirection(currentCell, direction);

                    if (neighborPrototypes.size() == 0) {
                        continue;
                    }

                    for (String neighborPrototype : neighborPrototypes) {
                        if (!possiblePrototypes.contains(neighborPrototype)) {

                            removeStructure(wave, neighborCell, neighborCoordinates, neighborPrototype);

                            if (!cells.contains(neighborCoordinates)) {
                                cells.add(neighborCoordinates);
                            }
                        }
                    }

                    entropy[neighborCoordinates.getFirst()][neighborCoordinates.getSecond()] = neighborPrototypes.size();

                }
            }
        }
    }

    private static void removeStructure(List<Prototypes>[][] wave, List<Prototypes> currentCell, Pair<Integer, Integer> currentCoordinates, String neighborPrototype) {
        int neighborPrototypeId = 0;
        int structuresInCell = currentCell.get(0).getPrototype().size();
        for (int i = 0; i < structuresInCell; i++) {
            if (currentCell.get(0).getPrototype().get(i).getStructure().getName().getName_type() == neighborPrototype) {
                neighborPrototypeId = i;
            }
        }
        wave[currentCoordinates.getFirst()][currentCoordinates.getSecond()].get(0).getPrototype().remove(neighborPrototypeId);
    }

    private static boolean isValidCoordinates(Pair<Integer, Integer> coordinates, List<Prototypes>[][] wave) {
        int row = coordinates.getFirst();
        int col = coordinates.getSecond();
        return row >= 0 && row < wave.length && col >= 0 && col < wave[0].length;
    }

    private static List<String> getStructuresAvailableInDirection(List<Prototypes> currentCell, Pair<Integer, Integer> direction) {

        List<List<String>> structuresAvailableInDirection = new ArrayList<>();

        int structuresAvailable = currentCell.get(0).getPrototype().size();
        for (int i = 0; i < structuresAvailable; i++) {
            if (direction == UP) {
                if (currentCell.get(0).getPrototype().get(i).getStructure().getNeighbors().get(0).getNorthNeighbor() != null) {
                    structuresAvailableInDirection.add(currentCell.get(0).getPrototype().get(i).getStructure().getNeighbors().get(0).getNorthNeighbor());
                }
            }
            if (direction == DOWN) {
                if (currentCell.get(0).getPrototype().get(i).getStructure().getNeighbors().get(0).getSouthNeighbor() != null) {
                    structuresAvailableInDirection.add(currentCell.get(0).getPrototype().get(i).getStructure().getNeighbors().get(0).getSouthNeighbor());
                }
            }
            if (direction == RIGHT) {
                if (currentCell.get(0).getPrototype().get(i).getStructure().getNeighbors().get(0).getEastNeighbor() != null) {
                    structuresAvailableInDirection.add(currentCell.get(0).getPrototype().get(i).getStructure().getNeighbors().get(0).getEastNeighbor());
                }
            }
            if (direction == LEFT) {
                if (currentCell.get(0).getPrototype().get(i).getStructure().getNeighbors().get(0).getWestNeighbor() != null) {
                    structuresAvailableInDirection.add(currentCell.get(0).getPrototype().get(i).getStructure().getNeighbors().get(0).getWestNeighbor());
                }
            }
        }
        return cleanList(structuresAvailableInDirection);
    }

    private static List<String> cleanList(List<List<String>> structuresAvailableInDirection) {

        List<String> result = new ArrayList<>();
        Set<String> uniqueElements = new HashSet<>();

        for (List<String> subList : structuresAvailableInDirection) {
            for (String element : subList) {
                if (!uniqueElements.contains(element)) {
                    uniqueElements.add(element);
                    result.add(element);
                }
            }
        }

        return result;

    }

    private static Pair<Integer, Integer> addDirection(Pair<Integer, Integer> currentCoordinates, Pair<Integer, Integer> direction) {

        return new Pair<>(currentCoordinates.getFirst() + direction.getFirst(), currentCoordinates.getSecond() + direction.getSecond());

    }

    private static List<Pair<Integer, Integer>> validDirections(Pair<Integer, Integer> currentCoordinates, List<Prototypes>[][] wave) {

        List<Pair<Integer, Integer>> possibleCoordinates = new ArrayList<>();
        possibleCoordinates.add(RIGHT);
        possibleCoordinates.add(LEFT);
        possibleCoordinates.add(UP);
        possibleCoordinates.add(DOWN);

        if (currentCoordinates.getFirst() == 0) {
            possibleCoordinates.remove(UP);
        }
        if (currentCoordinates.getFirst() == wave.length - 1) {
            possibleCoordinates.remove(DOWN);
        }
        if (currentCoordinates.getSecond() == 0) {
            possibleCoordinates.remove(LEFT);
        }
        if (currentCoordinates.getSecond() == wave[0].length - 1) {
            possibleCoordinates.remove(RIGHT);
        }

        return possibleCoordinates;
    }

    private static Pair<Integer, Integer> getMinEntropyCoordinates(int[][] entropy, List<Prototypes>[][] wave) {
        List<Pair<Integer, Integer>> coordinatesList = new ArrayList<>();

        for (int i = 0; i < entropy.length; i++) {
            for (int j = 0; j < entropy[i].length; j++) {
                if (entropy[i][j] > 1) {
                    coordinatesList.add(new Pair<>(i, j));
                }
            }
        }

        if (coordinatesList.isEmpty()) {
            return new Pair<>(0, 0);
        }

        Random random = new Random();
        int randomIndex = random.nextInt(coordinatesList.size());
        return coordinatesList.get(randomIndex);

    }

    private static void collapseAt(List<Prototypes>[][] wave, int[][] entropy, Pair<Integer, Integer> minCoordinates) {
        var cell = wave[minCoordinates.getFirst()][minCoordinates.getSecond()];
        var prototypesInCell = getStructuresAvailable(cell);

        Random random = new Random();
        int prototypeId = random.nextInt(prototypesInCell.size());

        var prototypeToKeep = prototypesInCell.get(prototypeId);

        entropy[minCoordinates.getFirst()][minCoordinates.getSecond()] = 1;

        cell.get(0).getPrototype().removeIf(prototype -> !prototype.getStructure().getName().getName_type().equals(prototypeToKeep));

    }

    private static List<Prototypes>[][] createWave(int rowCount, int columnCount) {
        List<Prototypes>[][] wave = new List[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                //wave[i][j] = loadJsonInputFiles(INPUT_DIRECTORY);
                wave[i][j] = new ArrayList<>();
                wave[i][j].add(getPrototypes());
            }
        }
        return wave;
    }

    private static List<String> getStructuresAvailable(List<Prototypes> prototypes) {
        List<String> structures = new ArrayList<>();
        int length = prototypes.get(0).getPrototype().size();
        for (int i = 0; i < length; i++) {
            structures.add(prototypes.get(0).getPrototype().get(i).getStructure().getName().getName_type());
        }
        return structures;
    }

    public static List<Prototypes> loadJsonInputFiles(String path) {
        List<Prototypes> inputJsonFiles = new ArrayList<>();

        try (Stream<Path> stream = Files.list(Paths.get(path))) {
            var files = stream.filter(file -> !Files.isDirectory(file)).map(p -> p.toFile()).collect(Collectors.toSet());

            ObjectMapper objectMapper = new ObjectMapper();
            for (var file : files) {
                Prototypes Prototypes = objectMapper.readValue(file, Prototypes.class);
                inputJsonFiles.add(Prototypes);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No file found for mansion generation: json method");
        }

        return inputJsonFiles;
    }


}
