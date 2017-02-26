package models.playerAsset.Iterators;

public interface Iterator<T>{

    public T first();
    public void next();
    public void prev();
    public T current();
}
