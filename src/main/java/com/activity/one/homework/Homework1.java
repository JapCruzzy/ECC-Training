package com.activity.one.homework;

import java.util.*;

public class Homework1 {

    static Scanner scanner = new Scanner(System.in);
    private static String[][] grid;

    public static void main(String[] args) {

        int flag2 = 1;
        try {
            do {
                System.out.println("Please enter number of rows: ");
                Integer rows = scanner.nextInt();
                System.out.println("Please enter number of columns: ");
                Integer columns = scanner.nextInt();
                System.out.println(rows + "x" + columns + "\n");
                showData(rows, columns);
                do {
                    System.out.println("Select options to do (search/print/edit/reset/exit): ");
                    String option = scanner.next();

                    switch (option) {
                        case "search" -> {
                            System.out.println(search());
                            flag2 = 0;
                        }
                        case "print" -> {
                            System.out.println(showData(rows, columns));
                            flag2 = 0;
                        }
                        case "edit" -> {
                            System.out.println(edit());
                            flag2 = 0;
                        }
                        case "reset" -> {
                            grid = null;
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

    private static String getRandom (Integer num) {

        final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz"
                + "!@#$%^&*_+:|>?<./;'=-";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            char ranChar = ALPHA_NUMERIC_STRING.charAt(rnd.nextInt(ALPHA_NUMERIC_STRING.length()));
            sb.append(ranChar);
        }
        return sb.toString();
    }

    private static String showData(Integer rows, Integer columns) {

        int size = 3;
        String[][] tempArr = new String[rows][columns];

        if (grid != null) {
            return "Data Table: \n" + Arrays.deepToString(grid);
        }

        //generate a 2d array with random string
        for (int i = 0; i < rows; i++) {
            tempArr[i] = Arrays.stream(new String[columns])
                    .map(e -> String.format("%3s", getRandom(size)))
                    .toArray(String[]::new);
        }
        System.out.println(Arrays.deepToString(tempArr));

        //copy tempArr to grid
        grid = new String[tempArr.length][columns];
        for (int i = 0; i < tempArr.length; i++) {
            grid[i] = Arrays.copyOf(tempArr[i], tempArr[i].length);
        }

        return "Data Table: \n" + Arrays.deepToString(grid);
    }

    private static List<String> search() {

        if (grid == null){
            throw new NullPointerException("No items in grid table");
        }

        System.out.println("Search keyword: ");
        String keyword = scanner.next();
        long occurrence;
        List<String> occurenceList = new ArrayList<>();

        //used linear search for algorithm
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].contains(keyword)) {
                    if (keyword.length() > 1){
                        occurrence = 1;
                    } else {
                        occurrence = Arrays.stream(grid[i][j].split(""))
                                .filter(e -> e.equals(keyword)).
                                count();
                    }
                    occurenceList.add(String.format("%s - %o Occurrence",
                            Arrays.toString(new int[]{i, j}),
                            occurrence));
                }
            }
        }

        return occurenceList;

    }

    private static String edit() {

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
            grid[rows1][columns2] = modifyValue;
            return "New Data Table: \n" + Arrays.deepToString(grid);
        } catch (Exception e) {
            System.out.println("Wrong Input!");
        }

        return "Start Over!";
    }

}

