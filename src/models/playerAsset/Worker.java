package models.playerAsset;

public class Worker {
    private int production;

    public Worker(){
        production = 1;
    }

    public String getType(){ return "Worker";}
    public int getProduction(){ return production;}
    public void setProduction(int i){ production = i;}
}
