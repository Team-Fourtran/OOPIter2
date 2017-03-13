package models.playerAsset.Iterators;

import java.util.ArrayList;

public interface TypeIterator2<T>{

    public T first();
    public void next();
    public void prev();
    public T current();
}
