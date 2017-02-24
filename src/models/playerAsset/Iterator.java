package src.models.playerAsset;

/**
 * Created by Clay on 2/24/2017.
 */
public interface Iterator<T> {

    public boolean hasNext();
    public T first();
    public void next();
    public void prev();
    public T current();

}
