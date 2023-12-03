package org.example.a_one;

import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.impl.utility.ArrayIterate;


public class Trebuchet {

    ImmutableMap<String, String> digits = Maps.immutable.of("one", "1")
                                                        .newWithKeyValue("two", "2")
                                                        .newWithKeyValue("three", "3")
                                                        .newWithKeyValue("four", "4")
                                                        .newWithKeyValue("five", "5")
                                                        .newWithKeyValue("six", "6")
                                                        .newWithKeyValue("seven", "7")
                                                        .newWithKeyValue("eight", "8")
                                                        .newWithKeyValue("nine", "9");

    public long calibrationIntValue(String code) {
        MutableList<String> numbers = ArrayIterate.select(code.split(""), each -> each.matches("\\d"));
        return Integer.parseInt(numbers.getFirst() + numbers.getLast());
    }

    public long calibrationStringValue(String code) {
        Multimap<Integer, String> firstOfString = digits.keysView().groupBy(code::indexOf)
                                                        .selectKeysValues((key, value) -> key >= 0);
        Multimap<Integer, String> lastOfString = digits.keysView().groupBy(code::lastIndexOf)
                                                       .selectKeysValues((key, value) -> key >= 0);
        Integer minString = firstOfString.keySet().minOptional().orElse(code.length() + 1);
        Integer maxString = lastOfString.keySet().maxOptional().orElse(-1);

        Multimap<Integer, String> firstOfInts = digits.valuesView().groupBy(code::indexOf)
                                                      .selectKeysValues((key, value) -> key >= 0);
        Multimap<Integer, String> lastOfInts = digits.valuesView().groupBy(code::lastIndexOf)
                                                     .selectKeysValues((key, value) -> key >= 0);
        Integer minInt = firstOfInts.keySet().minOptional().orElse(code.length() + 1);
        Integer maxInt = firstOfInts.keySet().maxOptional().orElse(-1);


        String first = minString < minInt ? digits.get(firstOfString.get(minString).getAny()) : firstOfInts.get(minInt).getAny();
        String last = maxString > maxInt ? digits.get(lastOfString.get(maxString).getAny()) : lastOfInts.get(maxInt).getAny();

        return Integer.parseInt((first == null ? "" : first) + (last == null ? "" : last));
    }

    public long sumOfCalibrationStrings(String document) {
        return ArrayIterate.collect(document.split("\n"), this::calibrationStringValue).sumOfInt(Long::intValue);
    }


    public long sumOfCalibrationInts(String document) {
        return ArrayIterate.collect(document.split("\n"), this::calibrationIntValue).sumOfInt(Long::intValue);
    }


}
