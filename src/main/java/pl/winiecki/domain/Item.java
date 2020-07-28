package pl.winiecki.domain;

import java.util.Objects;

public class Item {

    private String hrefUrl;
    private String price;

    public Item(String hrefUrl, String price) {
        this.hrefUrl = hrefUrl;
        this.price = price;
    }

    public String getHrefUrl() {
        return hrefUrl;
    }

    public void setHrefUrl(String hrefUrl) {
        this.hrefUrl = hrefUrl;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return getHrefUrl() + "\n" + "\n"
                + getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(hrefUrl, item.hrefUrl) &&
                Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hrefUrl, price);
    }
}
