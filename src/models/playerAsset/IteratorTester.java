package src.models.playerAsset;

public class IteratorTester {

    public static void main(String[] args) {

        UnitManager m = new UnitManager();
        m.addNewUnit("", "explorer");
        m.addNewUnit("", "explorer");
        m.addNewUnit("", "explorer");
        m.addNewUnit("", "explorer");
        Iterator<Unit> unitIter = m.makeIterator(m.getExplorerList());

        while (unitIter.hasNext()){
            System.out.println("success");
            unitIter.next();
        }




    }

}
