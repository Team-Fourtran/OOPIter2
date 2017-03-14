package models.playerAsset.Iterators;

public interface AssetIterator<T> extends Iterator2 {
    void update();
    void nextInstance();
    void prevInstance();
    String getCurrentMode();
}
