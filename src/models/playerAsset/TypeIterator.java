package src.models.playerAsset;

/**
 * Created by Clay on 2/25/2017.
 */
public interface TypeIterator<T> extends Iterator<T> {

    public void nextType();
    public void prevType();

}

