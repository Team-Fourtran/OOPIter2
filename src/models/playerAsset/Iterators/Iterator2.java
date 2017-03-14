package models.playerAsset.Iterators;

public interface Iterator2<T>{

    Iterator2<T> first();
    void next();
    void prev();
    T current();

    void nextType();
    void prevType();
    String getElement();
}
