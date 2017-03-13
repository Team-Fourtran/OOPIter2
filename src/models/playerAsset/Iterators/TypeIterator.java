package models.playerAsset.Iterators;


public interface TypeIterator<S, T> extends Iterator<T> {
    void nextType();
    void prevType();
    S getElement();
    String getCurrentType();
}

