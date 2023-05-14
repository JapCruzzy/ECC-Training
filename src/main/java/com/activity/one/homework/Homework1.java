package com.activity.one.homework;

import com.activity.one.homework.util.RandomGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Homework1 {

    static Scanner scanner = new Scanner(System.in);
    private static String[][] dataTable;

    public static void main(String[] args) {

        int flag2 = 1;
        try {
            do {
                System.out.println("Please enter number of rows: ");
                Integer rows = scanner.nextInt();
                System.out.println("Please enter number of columns: ");
                Integer columns = scanner.nextInt();
                System.out.println(rows + "x" + columns + "\n");
                generateDataTable(rows, columns);
                do {
                    System.out.println("Select options to do (search/print/edit/reset/exit): ");
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
                        case "reset" -> {
                            dataTable = null;
                            flag2 = 1;
                        }
                        case "exit" -> System.exit(0);
                        default -> System.out.println("Wrong Input. Start Over!");
                    }
                } while (flag2 == 0);
            } while (true);
        } catch (Exception e) {
            System.out.println("Wrong Input. Start Over!");
        }

    }

    private static void generateDataTable(Integer rows, Integer columns) {

        int size = 3;
        String[][] tempArr = new String[rows][columns];

        //generate a temporary 2d array with random string
        for (int i = 0; i < rows; i++) {
            tempArr[i] = Arrays.stream(new String[columns])
                    .map(e -> String.format("%3s", RandomGenerator.getRandom(size)))
                    .toArray(String[]::new);
        }

        //copy tempArr to grid
        dataTable = new String[tempArr.length][columns];
        for (int i = 0; i < tempArr.length; i++) {
            dataTable[i] = Arrays.copyOf(tempArr[i], tempArr[i].length);
        }

    }

    private static String printDataTable() {

        String result = "Data Table: \n" + Arrays.deepToString(dataTable)
                .replace("], ", "]\n")
                .replace("[[", "[")
                .replace("]]", "]")
                .replace("[", " |")
                .replace("]", " |")
                .replace(",", " |");

        return result;
    }

    private static String searchDataTable() {

        if (dataTable == null) {
            throw new NullPointerException("No items in grid table");
        }

        System.out.println("Search keyword: ");
        String keyword = scanner.next();
        List<String> occurenceList = new ArrayList<>();


        //used linear search for algorithm
        for (int i = 0; i < dataTable.length; i++) {
            for (int j = 0; j < dataTable[i].length; j++) {
                if (dataTable[i][j].contains(keyword)) {
                    long occurrence = 1;
                    if (keyword.length() == 1) {
                        occurrence = Arrays.stream(dataTable[i][j].split(""))
                                .filter(e -> e.equals(keyword)).
                                count();
                    }

                    occurenceList.add(String.format("%s - %o Occurrence",
                            Arrays.toString(new int[]{i, j}),
                            occurrence));
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
            System.out.println("New Value: ");
            String modifyValue = scanner.next();

//        if (modifyValue.length() > 3) {
//            return "New Value must be 3 characters only!";
//        }
            dataTable[rows1][columns2] = modifyValue;
            return printDataTable();
        } catch (Exception e) {
            System.out.println("Wrong Input!");
        }

        return "Start Over!";
    }

}

