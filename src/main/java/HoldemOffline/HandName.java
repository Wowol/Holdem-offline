package HoldemOffline;

import java.util.ArrayList;

import HoldemOffline.Hands.*;

public enum HandName {
    HIGH_CARD {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new HighCard().apply(cards);
        }
    },

    ONE_PAIR {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new OnePair().apply(cards);
        }
    },

    TWO_PAIR {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new TwoPair().apply(cards);
        }
    },

    THREE_OF_A_KIND {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new ThreeOfAKind().apply(cards);
        }
    },

    STRAIGHT {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new Straight().apply(cards);
        }
    },

    FLUSH {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new Flush().apply(cards);
        }
    },

    FULL_HOUSE {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new FullHouse().apply(cards);
        }
    },

    FOUR_OF_A_KIND {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new FourOfAKind().apply(cards);
        }
    },

    STRAIGHT_FLUSH {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return null;
        }
    };

    public abstract Hand checkMe(ArrayList<Card> cards);
}
