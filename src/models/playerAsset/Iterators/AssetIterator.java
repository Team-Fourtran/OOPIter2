package models.playerAsset.Iterators;

public interface AssetIterator<T> extends Iterator2 {
    void nextInstance();
    void prevInstance();
    String getCurrentMode();
}
