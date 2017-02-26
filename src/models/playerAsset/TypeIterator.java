package models.playerAsset;

public interface TypeIterator<S, T> extends Iterator<T> {
    public void nextType();
    public void prevType();
    public S getElement();
}

