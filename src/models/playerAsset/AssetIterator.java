package models.playerAsset;

public interface AssetIterator<T> extends TypeIterator<T> {

    public void nextMode();
    public void prevMode();

}
