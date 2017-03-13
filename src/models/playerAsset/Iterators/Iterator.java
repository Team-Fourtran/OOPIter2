package models.playerAsset.Iterators;

import models.playerAsset.Assets.Units.Unit;

import java.util.ArrayList;

public interface Iterator<T>{

    T first();
    void next();
    void prev();
    T current();
}
