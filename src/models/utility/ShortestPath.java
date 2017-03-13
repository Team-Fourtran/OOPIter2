package models.utility;

import models.assetOwnership.TileAssociation;

import java.util.LinkedList;
import java.util.Queue;

public class ShortestPath {
    private TileAssociation start;
    private TileAssociation goal;
    private node startNode;
    private node endNode;

    private Queue<node> openList;
    private Queue<node> closedList;

    public ShortestPath(TileAssociation start, TileAssociation goal) {
        this.start = start;
        this.goal = goal;
        openList = new LinkedList<>();
        closedList = new LinkedList<>();
        openList.add(new node(this.start, null));
        execute();
    }

    public void execute() {
        startNode = openList.peek();
        node current = openList.peek();
        while (!openList.isEmpty()) {
            current = openList.remove();
            if (current.innerState == goal) {
                //have reached the goal
                break;
            }
            closedList.add(current);

            //consider each neighbor
            for (TileAssociation entry : current.innerState.getNeighbors()) {
                node n = new node(
                        entry,
                        current
                );
                if (!inOpenList(n) && !inClosedList(n)){
                    openList.add(n);
                }
            }
        }
        this.endNode = current;
    }

    public int getDistance(){
        node n = this.endNode;
        int distance = 0;
        while (n != startNode && n != null){
            distance++;
            n = n.parent;
        }
        return distance;
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

    private class node {
        private node parent;
        private TileAssociation innerState;

        node(TileAssociation t, node parent) {
            this.innerState = t;
            this.parent = parent;
        }
    }
}
