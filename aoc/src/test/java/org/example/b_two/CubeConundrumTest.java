package org.example.b_two;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CubeConundrumTest {

    public static final String RED_1_BLUE_2 = "1 red, 2 blue";

    public static final String RED_300_BLUE_400_GREEN_500 = "300 red, 500 green, 400 blue";

    public static final String RED_12_GREEN_13_BLUE_14 = "12 red, 13 green, 14 blue";

    private final CubeConundrum unit = new CubeConundrum();

    private final String testInput = """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
            """;

    private String input;

    @BeforeTest
    private void setup() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/2.txt")) {
            input = new String(fileInputStream.readAllBytes());
        }
    }


    @Test
    public void testSetPossible_true() {
        assertTrue(unit.isSetPossible("1 red", "1 red"));
    }

    @Test
    public void testSetPossible_false() {
        assertFalse(unit.isSetPossible(RED_1_BLUE_2, "1 red"));
    }

    @Test
    public void testGame_withTestInput() {
        assertEquals(unit.game(testInput.split("\n")[0], RED_300_BLUE_400_GREEN_500), 1);
    }

    @Test
    public void testSumOfGames_withTestInput() {
        assertEquals(unit.sumOfGames(testInput, RED_12_GREEN_13_BLUE_14), 8);

    }

    @Test
    public void testSumOfGames_taskOne() {
        assertEquals(unit.sumOfGames(input, RED_12_GREEN_13_BLUE_14), 2476);

    }
}