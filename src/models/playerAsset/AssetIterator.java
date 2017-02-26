package models.playerAsset;

public interface AssetIterator<S, T> extends TypeIterator<S, T>{

    public void nextMode();
    public void prevMode();

}
