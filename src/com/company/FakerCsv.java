package com.company;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

public class FakerCsv {

    public List<String> testDataCsvStringsList(int count, String locale) {
        List<String> strings = new ArrayList<>();
        Faker faker = new Faker(new Locale(locale));
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String fullName = faker.name().fullName();
            Address address = faker.address();
            String countryCode = locale.substring(3);

            String zipCode = address.zipCode();

            String fullAddress = countryCode.equals("US") ? faker.address().fullAddress() : faker.address().fullAddress().substring(zipCode.length() + 1);
            if (random.nextInt(1000) % 3 == 0) {
                fullAddress = countryCode.equals("US") ? format("%s, %s", countryCode, fullAddress) :
                        format("%s%s", countryCode, fullAddress.substring(fullAddress.split(",")[0].length()));
            }
            fullAddress = countryCode.equals("US") ? fullAddress : format("%s %s", zipCode, fullAddress);

            String phone = faker.phoneNumber().phoneNumber();
            strings.add(convertToCSV(fullName, fullAddress, phone));
        }
        return strings;
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public String convertToCSV(String... data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(";"));
    }
}
