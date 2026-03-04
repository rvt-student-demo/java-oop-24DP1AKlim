package rvt.interfaceInABox;

import java.util.ArrayList;

public class Box implements Packable {
    private double maxCapacity;
    private ArrayList<Packable> items;

    public Box(double maxCapacity) {
        this.maxCapacity = maxCapacity;
        items = new ArrayList<>();
    }

    public double weight() {
        double weight = 0;
        for (Packable item : items) {
            weight += item.weight();
        }
        return weight;
    }

    public void add(Packable item) {
        if (weight() + item.weight() > maxCapacity)
            // throw new Exception("Box current weight (" + currWeight + "/" + maxCapacity
            // + ") does not allow to store item with weight " + iw);
            return;

        items.add(item);
    }

    public String toString() {
        return "Box: " + items.size() + " items, total weight " + weight() + " kg";
    }
}
