package ch.zli.clientapp.ui.softDrink;

public class SoftDrink {

    private String name;
    private float price;

    public SoftDrink() {

    }

    public SoftDrink(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
