package org.example.d_four;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.impl.utility.ArrayIterate;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class ScratchCard {

    public static final Pattern DIGIT_PATTERN = Pattern.compile("\\d+");


    public long sumCards(String input) {
        MutableList<Card> cards = ArrayIterate.collect(input.split("\\n"), this::score);

        MutableList<Integer> collect = cards.collect(card -> {
            return card.score() * 2 ^ cards.select(next -> {
                return next.cardNumber > card.cardNumber
                       && next.cardNumber < cards.detect(stopStreak -> stopStreak.cardNumber > card.cardNumber
                                                                       && stopStreak.score() == 0).cardNumber;
            }).size();
        });

        return collect.sumOfInt(Integer::intValue);
    }

    public Card score(String line) {
        String[] gameCardSplit = line.split(": ");
        String[] cardSplit = gameCardSplit[1].split(" \\| ");

        int cardNumber = Integer.parseInt(gameCardSplit[0].split(" ")[1]);

        ImmutableIntList winners = Lists.immutable.ofAll(DIGIT_PATTERN.matcher(cardSplit[0])
                                                                      .results()
                                                                      .toList())
                                                  .collect(MatchResult::group)
                                                  .collectInt(Integer::parseInt);

        ImmutableIntList card = Lists.immutable.ofAll(DIGIT_PATTERN.matcher(cardSplit[1])
                                                                   .results()
                                                                   .toList())
                                               .collect(MatchResult::group)
                                               .collectInt(Integer::parseInt);

        return new Card(cardNumber,
                        winners,
                        card);

    }


    public class Card {
        int cardNumber;
        IntList winners, card;

        Card(int cardNumber, IntList winners, IntList card) {
            this.cardNumber = cardNumber;
            this.winners = winners;
            this.card = card;
        }

        public int score() {
            return card.select(each -> winners.contains(each)).size();
        }
    }

}
