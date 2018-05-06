package HoldemOffline.Model.Hands;
import HoldemOffline.Model.Card;
import HoldemOffline.Model.Hand;
import HoldemOffline.Model.HandName;

import java.util.ArrayList;

import static HoldemOffline.Model.Hand.checkHandToGiven;

public abstract class HandFunction {
    HandName handName;

    abstract Hand check(ArrayList<Card> cards);

    public Hand apply(ArrayList<Card> c) {
        if (checkHandToGiven(c, handName)) {
            return null;
        }
        return check(c);
    }
}
