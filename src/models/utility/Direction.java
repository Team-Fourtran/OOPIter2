package models.utility;

public enum Direction {
    TopLeft(202.5),
    TopRight(157.5),
    Right(90),
    BottomRight(22.5),
    BottomLeft(157.5),
    Left(270);

    private double value;

    Direction(double _v){
        this.value = _v;
    }

    public double getValue(){
        return this.value;
    }
}