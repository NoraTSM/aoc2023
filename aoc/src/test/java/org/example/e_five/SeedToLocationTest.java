package org.example.e_five;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.IntStream;

import static org.testng.Assert.assertEquals;

public class SeedToLocationTest {

    private SeedToLocation unit = new SeedToLocation();
    private static String testInput = """
            seeds: 79 14 55 13
                        
            seed-to-soil map:
            50 98 2
            52 50 48
                        
            soil-to-fertilizer map:
            0 15 37
            37 52 2
            39 0 15
                        
            fertilizer-to-water map:
            49 53 8
            0 11 42
            42 0 7
            57 7 4
                        
            water-to-light map:
            88 18 7
            18 25 70
                        
            light-to-temperature map:
            45 77 23
            81 45 19
            68 64 13
                        
            temperature-to-humidity map:
            0 69 1
            1 0 69
                        
            humidity-to-location map:
            60 56 37
            56 93 4
            """;


    private String input;

    @BeforeTest
    private void setup() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/5.txt")) {
            input = new String(fileInputStream.readAllBytes());
        }
    }

    @Test
    private void testGetLocation() {
        assertEquals(unit.getLocation(testInput), 0L);
    }

    @Test
    private void testGetDestination() {
        SeedToLocation.Mapping mapping = new SeedToLocation.Mapping(52, 50, 48);
        IntStream.range((int) mapping.sourceStart, (int) (mapping.sourceStart + mapping.length + 1))
                 .forEach(each -> assertEquals(each + 2, mapping.destination(each)));

        assertEquals(50 + 48 + 1, mapping.destination(50 + 48 + 1));
        assertEquals(49, mapping.destination(49));
    }

}