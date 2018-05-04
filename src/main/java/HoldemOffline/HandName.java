package HoldemOffline;

import java.util.ArrayList;

import HoldemOffline.Hands.*;

public enum HandName {
    HIGH_CARD {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new HighCard().check(cards);
        }
    },

    ONE_PAIR {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new OnePair().check(cards);
        }
    },

    TWO_PAIR {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new TwoPair().check(cards);
        }
    },

    THREE_OF_A_KIND {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new ThreeOfAKind().check(cards);
        }
    },

    STRAIGHT {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new Straight().check(cards);
        }
    },

    FLUSH {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new Flush().check(cards);
        }
    },

    FULL_HOUSE {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new FullHouse().check(cards);
        }
    },

    FOUR_OF_A_KIND {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new FourOfAKind().check(cards);
        }
    },

    STRAIGHT_FLUSH {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new StraightFlush().check(cards);
        }
    };

    public abstract Hand checkMe(ArrayList<Card> cards);
}
