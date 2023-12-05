package org.example.d_four;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.impl.utility.ArrayIterate;

import java.math.BigInteger;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class ScratchCard {

    public static final Pattern DIGIT_PATTERN = Pattern.compile("\\d+");


    public BigInteger sumCards(String input) {
        MutableList<Card> cards = ArrayIterate.collect(input.split("\\n"), this::score);

        MutableList<BigInteger> collect = cards.collect(card -> {
            if (card.score() > 0) {

                long l = getaLong(cards.select(next -> next.cardNumber > card.cardNumber)
                                       .collect(each -> each.score(card)).sumOfLong(Long::longValue));


                return BigInteger.valueOf(l).add(BigInteger.valueOf(card.score()));
            }
            return BigInteger.ZERO;
        });
        return collect.reduce(BigInteger::add).orElse(BigInteger.ZERO);

    }

    public long getaLong(Long score) {
        return score == 0 ? 0L : (long) Math.pow(2L, score - 1);
    }

    public Card score(String line) {
        String[] gameCardSplit = line.split(": ");
        String[] cardSplit = gameCardSplit[1].split(" \\| ");

        int cardNumber = Integer.parseInt(gameCardSplit[0].split("\\s+")[1]);

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
            return card.select(this::isWinningNumber).size();
        }

        public long score(Card other) {
            return other.card.select(this::isWinningNumber).size();
        }

        private boolean isWinningNumber(int number) {
            return winners.contains(number);
        }
    }

}
