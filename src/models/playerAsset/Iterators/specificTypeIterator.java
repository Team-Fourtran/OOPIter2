package models.playerAsset.Iterators;

import java.util.ArrayList;
import java.util.Map;

public class specificTypeIterator<T> implements TypeIterator2 {
    private int current = 0;
    private ArrayList<Map.Entry<String, ArrayList<T>>> entries = new ArrayList<>();
    private Map<String, ArrayList<T>> map;

    public specificTypeIterator(Map<String, ArrayList<T>> map){
        this.map = map;
    }

    @Override
    public void first() {
        for (Map.Entry<String, ArrayList<T>> entry : map.entrySet()){
            entries.add(entry);
        }
        current = 0;
    }

    @Override
    public void next() {
        current += 1;
        current %= map.size();
    }

    @Override
    public void prev() {
        current -= 1;
        if (current < 0){
            current = map.size()-1;
        }
    }

    @Override
    public Map.Entry<String, ArrayList<T>> current() {
        if(entries.isEmpty()){
            return null;
        }
        return entries.get(current);
    }
}
