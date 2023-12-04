package org.example.c_three;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

public class GearRatioTest {

    private final GearRatio unit = new GearRatio();

    private final String testInput = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..
            """;

    private static String input;

    @BeforeTest
    private void setup() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/3.txt")) {
            input = new String(fileInputStream.readAllBytes());
        }
    }

    @Test
    private void setTestInput() {
        assertEquals(10, unit.getNumbers(testInput).size());
    }

    @Test
    public void testSumOfSymbolAdjacent_testInput() {
        assertEquals(4361, unit.sumOfSymbolAdjacent(testInput));

    }

    @Test
    public void testSumOfSymbolAdjacent() {
        assertEquals(531932, unit.sumOfSymbolAdjacent(input));

    }


    @Test
    public void testGetSymbols() {
        assertEquals(6, unit.getSymbols(testInput, GearRatio.SYMBOL_PATTERN).size());
    }

    @Test
    public void testDigits() {
        assertEquals(2, (int) (Math.log10(100)));

    }

    @Test
    public void testSumOfGears() {
        assertEquals(467835, unit.sumOfGears(testInput));
    }
}