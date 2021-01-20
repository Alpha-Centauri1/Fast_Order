package ch.zli.fastOrder;

public class HardDrink {

    private String name;
    private float price;

    public HardDrink() {

    }

    public HardDrink(String name, float price) {
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
