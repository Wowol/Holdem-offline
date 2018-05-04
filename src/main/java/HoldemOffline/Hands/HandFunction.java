package HoldemOffline.Hands;
import HoldemOffline.*;

import java.util.ArrayList;

import static HoldemOffline.Hand.checkHandToGiven;

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
