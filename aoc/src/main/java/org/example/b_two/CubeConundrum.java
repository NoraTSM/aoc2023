package org.example.b_two;

import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.utility.ArrayIterate;

public class CubeConundrum {


    public boolean isSetPossible(String gameSet, String bagSet) {
        MutableMap<String, Integer> bag = getCubes(bagSet);
        MutableMap<String, Integer> game = getCubes(gameSet);

        return game.keyValuesView().allSatisfy(each -> each.getTwo() <= bag.getOrDefault(each.getOne(), 0));
    }

    public MutableMap<String, Integer> getCubes(String set) {
        return ArrayIterate.collect(set.split(", "), each -> {
            String[] numberColourPair = each.split(" ");
            return Tuples.pair(numberColourPair[1], Integer.parseInt(numberColourPair[0]));
        }).toMap(Pair::getOne, Pair::getTwo);
    }

    public int game(String moves, String bag) {
        String[] split = moves.split(": ");
        boolean gameIsPossible = ArrayIterate.allSatisfy(split[1].split("; "), each -> isSetPossible(each, bag));
        return gameIsPossible ? Integer.parseInt(split[0].split(" ")[1]) : 0;
    }

    public long sumOfGames(String games, String bag) {
        return ArrayIterate.collect(games.split("\n"), each -> game(each, bag)).sumOfInt(Integer::intValue);
    }
}
