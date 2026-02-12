package org.example.modelo;

import java.io.Serializable;

public class PuntoGeografico implements Serializable {

    private double x;
    private double y;

    public PuntoGeografico(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "PuntoGeografico{" + "x=" + x + ", y=" + y + '}';
    }
}
