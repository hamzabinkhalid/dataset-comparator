package com.hamzabinkhalid;

import java.io.BufferedInputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * Provides methods to read two CSV files and compare their keys.
 * <p>
 * This class provides methods to read two CSV files and compare their keys.
 * It prints out some statistics about the count, distinct, and overlap of the keys.
 * </p>
 * @author Hamza Bin Khalid
 * @version 1.0
 */
public class DatasetComparator {

    /**
     * A record that holds the data for the comparison result of two CSV files.
     * <p>
     * This record holds the data for the comparison result of two CSV files.
     * It has six components: count1, count2, distinct1, distinct2, totalOverlap, and distinctOverlap.
     * </p>
     * @param count1 the count of the keys in the first file
     * @param count2 the count of the keys in the second file
     * @param distinct1 the count of the distinct keys in the first file
     * @param distinct2 the count of the distinct keys in the second file
     * @param totalOverlap the count of the overlap of all keys between the files
     * @param distinctOverlap the count of the overlap of distinct keys between the files
     */
    public record ComparisonResult(int count1, int count2, int distinct1, int distinct2,
                                   int totalOverlap, int distinctOverlap) {}

    /**
     * Reads a CSV file and returns a map of keys and their frequencies.
     * <p>
     * This method reads a CSV file and returns a map of keys and their frequencies.
     * It assumes that the first line of the file is a header and ignores it.
     * It also removes any double quotes and whitespace from the keys.
     * </p>
     * @param fileName the name of the CSV file to read
     * @return a map of keys and their frequencies
     */
    public static Map<String, Integer> readCSV(String fileName) {
        Map<String, Integer> map = new LinkedHashMap<>();
        try (Scanner scanner = new Scanner(new BufferedInputStream(Objects.requireNonNull(
                DatasetComparator.class.getClassLoader().getResourceAsStream(fileName))))) {
            scanner.nextLine(); // Skip the header line
            while (scanner.hasNextLine()) {
                String key = scanner.nextLine().trim(); // Read a line and remove any whitespace
                key = key.replace("\"", ""); // Remove any double quotes
                if (!key.isEmpty()) // If the key is not empty
                    map.put(key, map.getOrDefault(key, 0) + 1); // Increment the frequency of the key in the map
            }
        }
        return map;
    }

    /**
     * Compares two maps of keys and their frequencies and returns a ComparisonResult record.
     * <p>
     * This method compares two maps of keys and their frequencies and returns a ComparisonResult record.
     * It calculates the count, distinct, and overlap of the keys in both maps.
     * </p>
     * @param map1 the first map to compare
     * @param map2 the second map to compare
     * @return a ComparisonResult record with the comparison data
     */
    public static ComparisonResult compareMaps(Map<String, Integer> map1, Map<String, Integer> map2) {
        int count1 = 0, count2 = 0, distinct1 = 0, distinct2 = 0, totalOverlap = 0,
                distinctOverlap = 0;
        for (Map.Entry<String, Integer> entry : map1.entrySet()) {
            String key = entry.getKey(); // Get the key from the first map
            int freq = entry.getValue(); // Get the frequency of the key from the first map
            count1 += freq; // Add the frequency to the total count of the first map
            distinct1++; // Increment the number of distinct keys in the first map
            if (map2.containsKey(key)) { // Get the frequency of the key from the second map
                totalOverlap += freq * map2.get(key); // Multiply the frequencies and add to the total overlap
                distinctOverlap++; // Increment the number of distinct keys that overlap
            }
        }
        for (int freq : map2.values()) { // For each frequency in the second map
            count2 += freq; // Add the frequency to the total count of the second map
            distinct2++; // Increment the number of distinct keys in the second map
        }
        // Create and return a ComparisonResult object with the six values
        return new ComparisonResult(count1, count2, distinct1, distinct2, totalOverlap, distinctOverlap);
    }

    /**
     * Entry point of the program.
     * <p>
     * This method is the entry point of the program.
     * It reads two CSV files, compares them, and prints out the comparison result.
     * </p>
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        Map<String, Integer> map1 = readCSV("A_f.csv"); // Read the first CSV file and store the result in a map
        Map<String, Integer> map2 = readCSV("B_f.csv"); // Read the second CSV file and store the result in a map
        // Call the compareMaps method and store the result in a variable
        ComparisonResult result = compareMaps(map1, map2);
        // Display the info using the getters of the result object
        System.out.println("The count of the keys in the first file is " + result.count1());
        System.out.println("The count of the keys in the second file is " + result.count2());
        System.out.println("The count of the distinct keys in the first file is " +
                result.distinct1());
        System.out.println("The count of the distinct keys in the second file is " +
                result.distinct2());
        System.out.println("The count of the overlap of all keys between the files is " +
                result.totalOverlap());
        System.out.println("The count of the overlap of distinct keys between the files is " +
                result.distinctOverlap());
    }
}
