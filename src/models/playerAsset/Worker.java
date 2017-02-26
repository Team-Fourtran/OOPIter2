package models.playerAsset;

public class Worker {
    int production;

    public Worker(){
        production = 1;
    }

    public String getType(){ return "Worker";}
    public void setProduction(int i){ production = i;}
}
