package org.example.a_one;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class TrebuchetTest {

    private final Trebuchet unit = new Trebuchet();
    String testInput = """
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet
            """;

    String testInput2 = """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen
            """;

    private String input;

    @BeforeTest
    private void setup() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/1.txt")) {
            input = new String(fileInputStream.readAllBytes());
        }
    }

    @Test
    public void testTest() {
        assertEquals(unit.sumOfCalibrationInts(testInput), 142);
    }

    @Test
    public void testTaskOne() {
        assertEquals(unit.sumOfCalibrationInts(input), 55621);
    }

    @Test
    public void testFindDigit() {
        assertEquals(unit.calibrationStringValue("four3q7ctkghhqkpb5four"), 44);
    }

    @Test
    public void testTaskTwoTestInput() {
        assertEquals(unit.sumOfCalibrationStrings(testInput2), 281);
    }

    @Test
    public void testTaskTwo() {
        assertEquals(unit.sumOfCalibrationStrings(input), 281);
    }
}