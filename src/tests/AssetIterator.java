package tests;

import java.util.ArrayList;

public class AssetIterator {
    private ArrayList<ArrayList<PlayerAsset>> listList;
    private ArrayList<PlayerAsset> currentList;
    private int listIndex;

    private int assetIndex;
    private PlayerAsset currentAsset;

    public AssetIterator(ArrayList<ArrayList<PlayerAsset>> lists){
        this.listList = lists;

        this.listIndex = 0;
        this.currentList = listList.get(listIndex);

        this.assetIndex = 0;
        this.currentAsset = currentList.get(assetIndex);
    }

    public void nextMode(){
        listIndex = Util.mod(++listIndex,listList.size());
        currentList = listList.get(listIndex);
        assetIndex = 0;
        currentAsset = currentList.get(assetIndex);
    }

    public void prevMode(){
        listIndex = Util.mod(--listIndex,listList.size());
        currentList = listList.get(listIndex);
        assetIndex = 0;
        currentAsset = currentList.get(assetIndex);
    }

    public void nextType(){

    }

    public void prevType(){

    }

    public void next(){
        assetIndex = Util.mod(++assetIndex,currentList.size());
        currentAsset = currentList.get(assetIndex);
    }

    public void previous(){
        assetIndex = Util.mod(--assetIndex,currentList.size());
        currentAsset = currentList.get(assetIndex);
    }

    public PlayerAsset getCurrent(){
        return currentAsset;
    }
}

class Util{
    static protected int mod(int idx, int max){
        return (idx >= 0) ? (idx%max) : (max+idx);
    }
}