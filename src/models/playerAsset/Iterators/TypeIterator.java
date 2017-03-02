package models.playerAsset.Iterators;

import models.playerAsset.Iterators.Iterator;

public interface TypeIterator<S, T> extends Iterator<T> {

    public void nextType();
    public void prevType();
    public S getElement();
}

