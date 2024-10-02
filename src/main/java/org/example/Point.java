package org.example;

public class Point {
    private Float x;
    private Float y;
    private Float r;

    public Point(Float x, Float y, Float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                '}';
    }
}
