package com.hamzabinkhalid;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatasetComparatorTest {

    @Test
    void testReadCSVWithValidFileName() {
        // Test the readCSV method with a valid file name
        Map<String, Integer> map = DatasetComparator.readCSV("A_f-s.csv");
        // Assert that the map is not null and has the expected size
        assertEquals(6, map.size());
        // Assert that the map contains the expected keys and values
        assertEquals(1, map.get("A"));
        assertEquals(1, map.get("B"));
        assertEquals(1, map.get("C"));
        assertEquals(2, map.get("D"));
        assertEquals(1, map.get("E"));
        assertEquals(2, map.get("F"));
    }

    @Test
    void testReadCSVWithQuotedElements() {
        // Test the readCSV method with file having some quoted elments
        Map<String, Integer> map = DatasetComparator.readCSV("quoted.csv");
        // Assert that the map is not null and has the expected size
        assertEquals(3, map.size());
        // Assert that the map contains the expected keys and values
        assertEquals(2, map.get("apple"));
        assertEquals(1, map.get("banana"));
        assertEquals(1, map.get("orange"));
    }

    @Test
    void testCompareMapsWithNonEmptyMaps() {
        // Test the compareMaps method with non-empty maps
        Map<String, Integer> map1 = DatasetComparator.readCSV("A_f-s.csv");
        Map<String, Integer> map2 = DatasetComparator.readCSV("B_f-s.csv");
        DatasetComparator.ComparisonResult result = DatasetComparator.compareMaps(map1, map2);
        // Assert that the result is not null and has the expected values
        assertEquals(8, result.count1());
        assertEquals(9, result.count2());
        assertEquals(6, result.distinct1());
        assertEquals(6, result.distinct2());
        assertEquals(11, result.totalOverlap());
        assertEquals(4, result.distinctOverlap());
    }

    @Test
    void testCompareMapsWithEmptyMap1() {
        // Test the compareMaps method with an empty map as the first argument
        Map<String, Integer> map1 = DatasetComparator.readCSV("empty.csv");
        Map<String, Integer> map2 = DatasetComparator.readCSV("B_f-s.csv");
        DatasetComparator.ComparisonResult result = DatasetComparator.compareMaps(map1, map2);
        // Assert that the result is not null and has the expected values
        assertEquals(0, result.count1());
        assertEquals(9, result.count2());
        assertEquals(0, result.distinct1());
        assertEquals(6, result.distinct2());
        assertEquals(0, result.totalOverlap());
        assertEquals(0, result.distinctOverlap());
    }

    @Test
    void testCompareMapsWithEmptyMap2() {
        // Test the compareMaps method with an empty map as the second argument
        Map<String, Integer> map1 = DatasetComparator.readCSV("A_f-s.csv");
        Map<String, Integer> map2 = DatasetComparator.readCSV("empty.csv");
        DatasetComparator.ComparisonResult result = DatasetComparator.compareMaps(map1, map2);
        // Assert that the result is not null and has the expected values
        assertEquals(8, result.count1());
        assertEquals(0, result.count2());
        assertEquals(6, result.distinct1());
        assertEquals(0, result.distinct2());
        assertEquals(0, result.totalOverlap());
        assertEquals(0, result.distinctOverlap());
    }

    @Test
    void testCompareMapsWithBothEmptyMaps() {
        // Test the compareMaps method with both empty maps
        Map<String, Integer> map1 = DatasetComparator.readCSV("empty.csv");
        Map<String, Integer> map2 = DatasetComparator.readCSV("empty.csv");
        DatasetComparator.ComparisonResult result = DatasetComparator.compareMaps(map1, map2);
        // Assert that the result is not null and has the expected values
        assertEquals(0, result.count1());
        assertEquals(0, result.count2());
        assertEquals(0, result.distinct1());
        assertEquals(0, result.distinct2());
        assertEquals(0, result.totalOverlap());
        assertEquals(0, result.distinctOverlap());
    }
}
