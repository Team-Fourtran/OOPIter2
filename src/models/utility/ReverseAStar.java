package models.utility;

import models.assetOwnership.TileAssociation;
import models.playerAssetNew.PlayerAsset;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
 * Class that finds the optimal path between start TileState and goal TileState
 */
public class ReverseAStar {
    private TileAssociation start;
    private PlayerAsset goalAsset;
    private node startNode;

    private Queue<node> openList;
    private Queue<node> closedList;

    public ReverseAStar(TileAssociation destination, PlayerAsset goalAsset) {
        this.start = destination;
        this.goalAsset = goalAsset;
        openList = new LinkedList<>();
        closedList = new LinkedList<>();
        openList.add(new node(this.start, null, 0));
    }

    //COSTS: current.getTileInfo().getMovementCost();

    public ArrayList<TileAssociation> execute() {
        startNode = openList.peek();
        node current = openList.peek();
        while (!openList.isEmpty()) {
            current = openList.remove();
            if (current.innerState.isAssetOwner(goalAsset)) {
                //have reached the goal
                break;
            }
            closedList.add(current);

            //consider each neighbor
            for (TileAssociation entry : current.innerState.getNeighbors()) {
                if(entry.getMovementCost() != Double.POSITIVE_INFINITY){
                    node n = new node(
                            entry,
                            current,
                            (current.cost) + (entry.getMovementCost())
                    );
                    removeIfInClosedAndCostIsMinium(n);
                    removeIfInOpenAndCostIsMinium(n);
                    if (!inOpenList(n) && !inClosedList(n)){
                        openList.add(n);
                    }
                }
            }
        }
        return buildPath(current);
    }

    private ArrayList<TileAssociation> buildPath(node n){
        ArrayList<TileAssociation> path = new ArrayList<>();
        while(n != startNode){
            path.add(n.innerState);
            n = n.parent;
        }
        path.add(startNode.innerState);  //Postcondition that startNode is included
        return path;
    }

    private boolean inOpenList(node n){
        for (node _n : openList) {
            if (_n.innerState == n.innerState) {
                return true;
            }
        }
        return false;
    }
    private boolean inClosedList(node n){
        for (node _n : closedList) {
            if (_n.innerState == n.innerState) {
                return true;
            }
        }
        return false;
    }

    private boolean removeIfInOpenAndCostIsMinium(node n) {
        for (node _n : openList) {
            if (_n.innerState == n.innerState) {
                if(n.cost < _n.cost){ //new cost is less!
                    openList.remove(_n);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean removeIfInClosedAndCostIsMinium(node n) {
        for (node _n : closedList) {
            if (_n.innerState == n.innerState) {
                if(n.cost < _n.cost){
                    closedList.remove(_n);
                    return true;
                }
            }
        }
        return false;
    }

    private class node {
        private node parent;
        private TileAssociation innerState;
        private double cost;

        node(TileAssociation t, node parent, double cost) {
            this.innerState = t;
            this.parent = parent;
            this.cost = cost;
        }
    }
}
