package org.example.e_five;

import org.eclipse.collections.api.factory.primitive.IntLists;
import org.eclipse.collections.api.factory.primitive.LongLists;
import org.eclipse.collections.api.list.primitive.ImmutableLongList;
import org.eclipse.collections.impl.utility.ArrayIterate;

import java.util.stream.IntStream;

public class SeedToLocation {


    public long getLocation(String input) {
        String[] split = input.split("\n\n");


        ImmutableLongList seeds = LongLists.immutable.ofAll(ArrayIterate.collectLong(split[0].split(": ")[1]
                                                                                             .split(" "), Long::parseLong));

        IntLists.immutable.ofAll(IntStream.range(1, split.length + 1)).collect(each -> split[each].split(" map:")).flatCollect();


        return 0L;
    }

    public static class Mapping {
        long destinationStart, sourceStart, length;

        Mapping(long destinationStart, long sourceStart, long length) {
            this.destinationStart = destinationStart;
            this.sourceStart = sourceStart;
            this.length = length;
        }

        public long destination(long source) {
            if (source >= sourceStart && source <= sourceStart + length) {
                long difference = destinationStart - sourceStart;
                return source + difference;
            }
            return source;
        }
    }
}
