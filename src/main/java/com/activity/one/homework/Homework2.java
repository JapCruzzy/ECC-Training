package com.activity.one.homework;

import com.activity.one.homework.util.RandomGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Homework2 {
    static Scanner scanner = new Scanner(System.in);
    private static Map<String, String>[][] dataTable;

    private static int columns;

    public static void main(String[] args) {

        int flag2 = 1;
        try {
            while (true) {
                System.out.println("Please enter number of rows: ");
                int rows = scanner.nextInt();
                System.out.println("Please enter number of columns: ");
                columns = scanner.nextInt();
                System.out.println(rows + "x" + columns + "\n");
                generateDataTable(rows, columns);
                do {
                    System.out.println("Select options to do " +
                            "(search/print/edit/reset/sort/download/add column/exit): ");
                    String option = scanner.next();

                    switch (option) {
                        case "search" -> {
                            System.out.println(searchDataTable());
                            flag2 = 0;
                        }
                        case "print" -> {
                            System.out.println(printDataTable());
                            flag2 = 0;
                        }
                        case "edit" -> {
                            System.out.println(editDataTable());
                            flag2 = 0;
                        }
                        case "sort" -> {
                            System.out.println(sortDataTable());
                            flag2 = 0;
                        }
                        case "download" -> {
                            toTextFile();
                            flag2 = 0;
                        }
                        case "add" -> {
                            addColumn(rows);
                            flag2 = 0;
                        }
                        case "reset" -> {
                            dataTable = null;
                            flag2 = 1;
                        }
                        case "exit" -> System.exit(0);
                        default -> System.out.println("Wrong Input. Start Over!");
                    }
                } while (flag2 == 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Wrong Input. Start Over!");
        }

    }

    private static void generateDataTable(Integer rows, Integer columns) {

        int size = 3;
        Map<String, String>[][] tempArr = new Map[rows][columns];

        //generate a 2d array with random set of string inside Map

        //set 2d array with an empty maps
        Arrays.setAll(tempArr, i -> Arrays.stream(new Map[columns])
                .map(hashMap -> new HashMap<String, String>())
                .toArray(Map[]::new));

        //assign the values inside the map that is inside the 2d array
        Arrays.stream(tempArr)
                .flatMap(Arrays::stream)
                .forEach(map -> map.put(RandomGenerator.getRandom(size), RandomGenerator.getRandom(size)));

        //copy tempArr to grid
        dataTable = new Map[tempArr.length][columns];
        for (int i = 0; i < tempArr.length; i++) {
            dataTable[i] = Arrays.copyOf(tempArr[i], tempArr[i].length);
        }


    }

    private static String printDataTable() {

        return "Data Table: \n" + Arrays.deepToString(dataTable)
                .replace("], ", "]\n")
                .replace("[[", "[")
                .replace("]]", "]")
                .replace("[", " |")
                .replace("]", " |")
                .replace(",", " |")
                .replace("=", ",");
    }

    private static String searchDataTable() {

        if (dataTable == null) {
            throw new NullPointerException("No items in data table");
        }

        System.out.println("Search keyword: ");
        String keyword = scanner.next();
        List<String> occurenceList = new ArrayList<>();

        //used linear search for algorithm
        for (int i = 0; i < dataTable.length; i++) {
            for (int j = 0; j < dataTable[i].length; j++) {

                String replace = dataTable[i][j].toString();
                if (replace.length() == 2) {
                    break;
                }
                String key = replace.substring(1, 4);
                String value = replace.substring(5, 8);

                if (replace.contains(keyword)) {
                    long keyOccurrence = 1;
                    long valueOccurrence = 1;

                    if (keyword.length() == 1) {
                        keyOccurrence = Arrays.stream(key.split(""))
                                .filter(e -> e.equals(keyword)).
                                count();
                    }

                    if (keyword.length() == 1) {
                        valueOccurrence = Arrays.stream(value.split(""))
                                .filter(e -> e.equals(keyword)).
                                count();
                    }

                    if (valueOccurrence > 0 && keyOccurrence > 0) {
                        occurenceList.add(String.format("%s - %o Occurrence in Key Field, %o Occurrence in Value Field",
                                Arrays.toString(new int[]{i, j}),
                                keyOccurrence, valueOccurrence));
                    }
                    if (valueOccurrence == 0 && keyOccurrence > 0) {
                        occurenceList.add(String.format("%s - %o Occurrence in Key Field",
                                Arrays.toString(new int[]{i, j}),
                                keyOccurrence));
                    }
                    if (valueOccurrence > 0 && keyOccurrence == 0) {
                        occurenceList.add(String.format("%s - %o Occurrence in Value Field",
                                Arrays.toString(new int[]{i, j}),
                                valueOccurrence));
                    }


                }
            }
        }

        String list = occurenceList.toString().replaceAll("(^\\[|]$)", "");

        return occurenceList.isEmpty() ? "No Searches" : list;

    }

    private static String editDataTable() {

        try {
            System.out.println("Please select row ");
            int rows1 = scanner.nextInt();
            System.out.println("Please select column: ");
            int columns2 = scanner.nextInt();
            System.out.println("Key or Value? : ");
            String keyOrValue = scanner.next();
            System.out.println("New Value: ");
            String modifyValue = scanner.next();

            if (dataTable[rows1][columns2].isEmpty() || dataTable[rows1][columns2] == null) {
                throw new RuntimeException("Data is null/empty. You can't edit null/empty Map");
            }

            String key = dataTable[rows1][columns2].toString().substring(1, 4);
            String value = dataTable[rows1][columns2].toString().substring(5, 8);

            switch (keyOrValue.toLowerCase()) {
                case "key" -> {
                    dataTable[rows1][columns2].remove(key);
                    dataTable[rows1][columns2].put(modifyValue, value);
                }
                case "value" -> dataTable[rows1][columns2].put(key, modifyValue);
                default -> System.out.println("Wrong Input. Start Over!");
            }

            return printDataTable();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String sortDataTable() {

        try {

            if (dataTable == null) {
                throw new NullPointerException("Data Table is empty");
            }

            System.out.println("(Asc)ending or (Desc)ending?: ");
            String sort = scanner.next();

            //Ascending
            if (sort.equalsIgnoreCase("asc") || sort.equalsIgnoreCase("ascending")) {
                for (int i = 0; i < dataTable.length; i++) {
                    for (int j = 0; j < dataTable[i].length; j++) {
                        // compare then swap
                        for (int k = 0; k < dataTable[i].length - j - 1; k++) {
                            if (dataTable[i][k].toString().charAt(1) > dataTable[i][k + 1].toString().charAt(1)) {

                                // swapping of elements
                                Map<String, String> t = dataTable[i][k];
                                dataTable[i][k] = dataTable[i][k + 1];
                                dataTable[i][k + 1] = t;
                            }
                        }
                    }
                }
                //Descending
            } else {
                for (int i = 0; i < dataTable.length; i++) {
                    for (int j = 0; j < dataTable[i].length; j++) {
                        // compare then swap
                        for (int k = 0; k < dataTable[i].length - j - 1; k++) {
                            if (dataTable[i][k].toString().charAt(1) < dataTable[i][k + 1].toString().charAt(1)) {

                                // swapping of elements
                                Map<String, String> t = dataTable[i][k];
                                dataTable[i][k] = dataTable[i][k + 1];
                                dataTable[i][k + 1] = t;
                            }
                        }
                    }
                }
            }
            return "New Data Table\n" + printDataTable();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Wrong Input";
    }

    private static void addColumn(int rows) {

        try {
            System.out.println("Identify on which row to add: ");
            int row = scanner.nextInt();

            if (row >= rows) {
                throw new RuntimeException("Row Input must not be higher than actual length of data Table");
            }

            int column2 = columns + 1;

            //copy current datatable to other temporary array
            Map<String, String>[][] tempTable = new Map[rows][column2];
            Map<String, String> emptyTempMap = Collections.emptyMap();


            for (int i = 0; i < dataTable.length; i++) {
                for (int j = 0; j < dataTable[i].length; j++) {
                    for (int k = 0; k < tempTable.length; k++) {
                        Map<String, String> collect = dataTable[i][j].entrySet().stream().collect(Collectors
                                .toMap(Map.Entry::getKey, Map.Entry::getValue));
                        Map<String, String> map = new HashMap<>();
                        map.put(RandomGenerator.getRandom(3), RandomGenerator.getRandom(3));
                        tempTable[row][columns] = map;
                        for (int l = 0; l < tempTable[k].length; l++) {
                            tempTable[i][j] = collect;
                            if (tempTable[k][l] == null) {
                                tempTable[k][l] = emptyTempMap;
                            }
                        }
                    }

                }
            }

            columns = column2;

            //copy tempArr to grid
            dataTable = new Map[tempTable.length][columns];
            for (int i = 0; i < tempTable.length; i++) {
                dataTable[i] = Arrays.copyOf(tempTable[i], tempTable[i].length);
            }


            System.out.println(printDataTable());
        } catch (InputMismatchException e) {
            System.out.println("Wrong Input! Try Again!");
        }


    }

    private static void toTextFile() throws IOException {

        String result = "Data Table: \n" + Arrays.deepToString(dataTable)
                .replace("], ", "]\n")
                .replace("[[", "[")
                .replace("]]", "]")
                .replace("[", " |")
                .replace("]", " |")
                .replace(",", " |")
                .replace("=", ",");

        BufferedWriter writer = new BufferedWriter(new FileWriter("/home/jucruz/Desktop/2DArray.txt"));
        System.out.println("Downloaded successfully!");
        writer.write(result);//save the string representation of the board
        writer.close();
    }

}

