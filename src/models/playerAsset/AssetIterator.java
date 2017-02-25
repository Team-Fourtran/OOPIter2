package src.models.playerAsset;

/**
 * Created by Clay on 2/25/2017.
 */
public interface AssetIterator<T> extends TypeIterator<T> {

    public void nextMode();
    public void prevMode();

}
