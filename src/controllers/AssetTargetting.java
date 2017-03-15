package controllers;

import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Iterators.AssetIterator;
import models.playerAsset.Iterators.TypeIterator2;
import views.MainScreen;

import java.util.ArrayList;

public class AssetTargetting {
    MainScreen screen;
    MessageGenerator receiver;
    private AssetIterator iter;
    private String mode;

    public AssetTargetting(MainScreen s){
        screen = s;
    }

    protected void targetAsset(MessageGenerator receiver, AssetIterator iter, String mode){
        this.iter = iter;
        this.receiver = receiver;
        this.mode = mode;
        screen.doAssetTargetting(this);
    }

    public void receiveAsset(PlayerAsset asset){
        if(null != asset)
            receiver.receiveTargetAsset(asset);
    }

    public TypeIterator2<PlayerAsset> getIterator(){
        return new TypeIterator2<PlayerAsset>() {
            private ArrayList<PlayerAsset> list = new ArrayList<>(0);
            private int cur;
            private int size;

            @Override
            public void first() {
                String firstmode = iter.getCurrentMode();
                iter.next();
                while(!iter.getCurrentMode().equals(mode)){
                    if(iter.getCurrentMode().equals(firstmode)){
                        return;
                    }
                    iter.next();
                }
                list = new ArrayList<>();
                ///
                list.add((PlayerAsset) iter.current());
                while(true){
                    iter.nextInstance();
                    if(list.contains(iter.current())){
                        iter.nextType();
                        if(list.contains(iter.current())){
                            break;
                        }
                    }
                    list.add((PlayerAsset) iter.current());
                }
                ///
                cur = 0;
                size = list.size();
            }

            @Override
            public void next() {
                cur += 1;
                cur %= size;
            }

            @Override
            public void prev() {
                cur -= 1;
                if (cur < 0){
                    cur = size - 1;
                }
            }

            @Override
            public PlayerAsset current() {
                if(list.isEmpty()){
                    return null;
                }
                return list.get(cur);
            }
        };
    }
}
