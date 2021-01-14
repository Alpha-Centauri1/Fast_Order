package ch.zli.fastOrder;

public class SoftDrink {

    private String name;
    private float price;

    public SoftDrink() {

    }

    public SoftDrink(String name, float price, int amount) {
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
