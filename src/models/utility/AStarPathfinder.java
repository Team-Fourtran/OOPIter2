package models.utility;

import models.assetOwnership.TileAssociation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AStarPathfinder {
    private TileAssociation start;
    private TileAssociation goal;
    private node startNode;
    private node endNode;
    private node finalPath;

    private Queue<node> openList;
    private Queue<node> closedList;

    public AStarPathfinder(TileAssociation start, TileAssociation goal) {
        this.start = start;
        this.goal = goal;
        openList = new LinkedList<>();
        closedList = new LinkedList<>();
        openList.add(new node(this.start, null, 0));
        finalPath = null;
        execute();
    }

    //COSTS: current.getTileInfo().getMovementCost();

    public void execute() {
        startNode = openList.peek();
        node current = openList.peek();
        while (!openList.isEmpty()) {
            current = openList.remove();
            if (current.innerState == goal) {
                //have reached the goal
                if (finalPath == null){
                    finalPath = current;
                }
                else if (current.cost < finalPath.cost){
                    finalPath = current;
                }
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
        this.endNode = finalPath;
    }

    public ArrayList<TileAssociation> getPath(){
        ArrayList<TileAssociation> path = new ArrayList<>();
        node n = this.endNode;
        if (n == null){
            System.out.println("Invalid Destination!");
            path.add(startNode.innerState);
            return path;
        }
        while(n != startNode){
            path.add(0, n.innerState);
            n = n.parent;
        }
        path.add(0, startNode.innerState);  //Postcondition that startNode is included
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
