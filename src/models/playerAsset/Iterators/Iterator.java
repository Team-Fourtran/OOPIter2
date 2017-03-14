package models.playerAsset.Iterators;

public interface Iterator<T>{

    T first();
    void next();
    void prev();
    T current();
}
