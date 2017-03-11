package models.playerAsset.Assets;

import java.util.ArrayList;

public class AssetArray<P extends PlayerAsset> {
    private ArrayList<P> assetArray;

    private int max;
    private int numberOfElements;

    AssetArray(){
        this.assetArray = new ArrayList<>();
        this.numberOfElements = 0;
        this.max = 1;
    }

    public void add(P asset){
        if(numberOfElements == max){
            increaseSize();
        }
        assetArray.add(numberOfElements++, asset);

    }

    public void remove(P c){
        for(int i = 0; i < numberOfElements; i++){
            if (assetArray.get(i) == c){
                assetArray.set(i, null);
                return;
            }
        }
    }

    private void consolidate(){
        ArrayList<P> newAssets = new ArrayList<>();
        int newIndex = 0;
        int nullCount = 0;
        for(int i = 0; i < numberOfElements; i++){
            if (assetArray.get(i) != null){
                newAssets.add(newIndex, assetArray.get(i));
                newIndex++;
            }
            else{
                nullCount++;
            }
        }
        this.assetArray = newAssets;
        this.numberOfElements -= nullCount;
    }

    private void increaseSize(){
        this.max = max*2;
        ArrayList<P> newAssetArray = new ArrayList<>();
        for(int i = 0; i < numberOfElements; i++){
            newAssetArray.add(i, this.assetArray.get(i));
        }
        this.assetArray = newAssetArray;
    }

    public void execute(){
        for(int i = 0; i < numberOfElements; i++){
            assetArray.get(i).executeCommand();
        }
        consolidate();
    }

    public void resetCommands(){
        for(int i = 0; i < numberOfElements; i++){
            assetArray.get(i).executeCommand();
        }
        consolidate();
    }

    public P get(int index){
        return assetArray.get(index);
    }

    public int size(){
        return this.numberOfElements;
    }
}
