package models.playerAsset;

public class StructureFactory {
    public Structure makeStructure(String type){
        switch (type){
            case "capital":
                return new Capital();
        }

        return null;
    }
}
