package HoldemOffline.Model.Utilities;

interface Action<T, U> {
    U execute(T t);
 }