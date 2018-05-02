package HoldemOffline;

import java.util.ArrayList;

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
            return null;
        }
    },

    STRAIGHT {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return null;
        }
    },

    FLUSH {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return null;
        }
    },

    FULL_HOUSE {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return null;
        }
    },

    FOUR_OF_A_KIND {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return null;
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
