package tests;

/*
* Dummy PlayerAsset for testing command generation*/
public class PlayerAsset {
    String type;
    String subtype;
    int id;

    public PlayerAsset(String type, String subtype, int id){
        this.type = type;
        this.subtype = subtype;
        this.id = id;
    }

    public String toString(){
        return ("PlayerAsset: " + type + "/" + subtype + "/" + id);
    }
}
