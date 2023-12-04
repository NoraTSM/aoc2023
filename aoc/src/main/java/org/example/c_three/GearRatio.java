package org.example.c_three;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.primitive.IntLists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.utility.ArrayIterate;

import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class GearRatio {


    public static final Pattern DIGIT_PATTERN = Pattern.compile("\\d+");

    public static final Pattern SYMBOL_PATTERN = Pattern.compile("[^\\d|\\.]");

    public static final Pattern GEAR_PATTERN = Pattern.compile("\\*");


    public long sumOfGears(String input) {
        MutableList<Number> numbers = getNumbers(input);
        MutableList<Symbol> symbols = getSymbols(input, GEAR_PATTERN);
        MutableListMultimap<Symbol, Pair<Symbol, Number>> pairs = numbers.flatCollect(number -> adjacentSymbols(number, symbols)
                .collect(symbol -> Tuples.pair(symbol, number))).groupBy(Pair::getOne);
        RichIterable<MutableList<Pair<Symbol, Number>>> select = pairs.keySet().collect(pairs::get).select(each -> each.size() == 2);
        return select.flatCollect(each -> each.collect(Pair::getTwo)).sumOfInt(Number::getNumber);

    }

    public long sumOfSymbolAdjacent(String input) {
        MutableList<Number> numbers = getNumbers(input);
        MutableList<Symbol> symbols = getSymbols(input, SYMBOL_PATTERN);


        return numbers.select(each -> isAdjacentToSymbol(each, symbols)).sumOfInt(Number::getNumber);
    }

    public MutableList<Symbol> adjacentSymbols(Number number, MutableList<Symbol> symbols) {
        return symbols.select(symbol -> range(number.x_min, number.x_max()).contains(symbol.x)
                                        && range(number.y, number.y).contains(symbol.y));
    }

    public boolean isAdjacentToSymbol(Number number, MutableList<Symbol> symbols) {
        return symbols.anySatisfy(symbol -> range(number.x_min, number.x_max()).contains(symbol.x)
                                            && range(number.y, number.y).contains(symbol.y));
    }

    private static ImmutableIntList range(int min, int max) {
        return IntLists.immutable.ofAll(IntStream.range(Math.max(0, min - 1), max + 2));
    }

    public MutableList<Symbol> getSymbols(String input, Pattern pattern) {
        return ArrayIterate.zipWithIndex(input.split("\n"))
                           .collect(each -> mapSymbols(each, pattern))
                           .flatCollect(each -> each);

    }

    private ImmutableList<Symbol> mapSymbols(Pair<String, Integer> row, Pattern pattern) {
        return Lists.immutable.ofAll(pattern.matcher(row.getOne())
                                            .results()
                                            .toList())
                              .collect(each -> new Symbol(each.group().charAt(0),
                                                          each.start(),
                                                          row.getTwo()));
    }

    public MutableList<Number> getNumbers(String input) {
        return ArrayIterate.zipWithIndex(input.split("\n"))
                           .collect(this::mapNumbers)
                           .flatCollect(each -> each);

    }

    private ImmutableList<Number> mapNumbers(Pair<String, Integer> row) {
        return Lists.immutable.ofAll(DIGIT_PATTERN.matcher(row.getOne())
                                                  .results()
                                                  .toList())
                              .collect(each -> new Number(Integer.parseInt(each.group()),
                                                          each.start(),
                                                          row.getTwo()));

    }


    public class Symbol {
        public char symbol;
        public int x, y;

        public Symbol(char symbol, int x, int y) {
            this.symbol = symbol;
            this.x = x;
            this.y = y;
        }

    }

    public class Number {
        public int number, x_min, y;

        public Number(int number, int x_min, int y) {
            this.number = number;
            this.x_min = x_min;
            this.y = y;
        }

        public int getNumber() {
            return number;
        }

        public int x_max() {
            return x_min + (int) (Math.log10(number));
        }

    }
}
