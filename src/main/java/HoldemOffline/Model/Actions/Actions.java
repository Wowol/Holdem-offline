package HoldemOffline.Model.Actions;

import HoldemOffline.Model.Player;
import HoldemOffline.Model.Actions.Exceptions.ActionException;

public enum Actions {
    All_IN {
        @Override
        public void make(Player p, int... arg) throws ActionException {
            new AllIn().makeAction(p);
        }
    },

    BET {
        @Override
        public void make(Player p, int... arg) throws ActionException {
            new Bet(arg[0]).makeAction(p);
        }
    },

    CALL {
        @Override
        public void make(Player p, int... arg) throws ActionException {
            new Call().makeAction(p);
        }
    },

    CHECK {
        @Override
        public void make(Player p, int... arg) throws ActionException {
            new Check().makeAction(p);
        }
    },

    FOLD {
        @Override
        public void make(Player p, int... arg) throws ActionException {
            new Fold().makeAction(p);
        }
    },

    RAISE {
        @Override
        public void make(Player p, int... arg) throws ActionException {
            new Raise(arg[0]).makeAction(p);
        }
    };

    public abstract void make(Player player, int... args) throws ActionException;
}
