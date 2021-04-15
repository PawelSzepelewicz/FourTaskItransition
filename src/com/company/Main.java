package com.company;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> strings = new FakerCsv().testDataCsvStringsList(Integer.parseInt(args[0]), args[1]);
        strings.forEach(System.out::println);
    }
}
