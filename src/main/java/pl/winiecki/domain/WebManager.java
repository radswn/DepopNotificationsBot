package pl.winiecki.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WebManager {

    private final WebDriver driver;
    private List<Item> oldItems;
    private List<Item> newItems;
    private Map<String, List<Item>> itemsPerTag = new HashMap<>();
    private Set<Item> newItemsToShow = new HashSet<>();
    private final String[] tags = {"golf wang", "#golflefleur", "le fleur"};

    public WebManager(final WebDriver driver) {
        this.driver = driver;
    }

    public void assignFirstItems(){
        for (String tag : tags) {
            itemsPerTag.put(tag, getItemsFromPage(tag));
        }
    }

    public void clearNewItems(){
        getNewItemsToShow().clear();
    }

    public void clearFirst20NewItems(){
        List<Item> itemList = new ArrayList<>(getNewItemsToShow());
        itemList.subList(0,20).clear();
        Set<Item> newSet = new HashSet<>(itemList);
        setNewItemsToShow(newSet);
    }

    public void reloadItems() {

        for (String query : tags) {

            setOldItems(itemsPerTag.get(query));
            setNewItems(getItemsFromPage(query));

            for (Item item : newItems) {
                if (!getOldItems().contains(item)) {
                    getNewItemsToShow().add(item);
                } else break;
            }

            if (!oldItems.contains(newItems.get(0)))
                getItemsPerTag().put(query, newItems);

        }

    }

    public List<Item> getItemsFromPage(String searchPhrase) {

        String baseUrl = "https://www.depop.com/search/?q=";
        String url = baseUrl + searchPhrase.replaceAll("#", "%23");
        driver.get(url);
        List<WebElement> aElements = driver.findElements(By.tagName("a"));

        List<WebElement> items = new ArrayList<>();
        List<Item> finalItems = new ArrayList<>();

        for (WebElement element : aElements) {
            String href = element.getAttribute("href");
            if (href.length() >= 30)
                if (href.substring(21, 30).equalsIgnoreCase("/products"))
                    items.add(element);
        }

        for (WebElement element : items) {
            String hrefUrl = element.getAttribute("href");
            List<WebElement> spans = element.findElements(By.tagName("span"));
            String price;
            if (spans.size() == 1)
                price = spans.get(0).getText();
            else
                price = "Discount: " + spans.get(0).getText() + "-->" + spans.get(1).getText();

            Item item = new Item(hrefUrl, price);
            finalItems.add(item);
        }

        return finalItems;
    }

    public List<Item> getOldItems() {
        return oldItems;
    }

    public void setOldItems(List<Item> oldItems) {
        this.oldItems = oldItems;
    }

    public List<Item> getNewItems() {
        return newItems;
    }

    public void setNewItems(List<Item> newItems) {
        this.newItems = newItems;
    }

    public Map<String, List<Item>> getItemsPerTag() {
        return itemsPerTag;
    }

    public void setItemsPerTag(Map<String, List<Item>> itemsPerTag) {
        this.itemsPerTag = itemsPerTag;
    }

    public Set<Item> getNewItemsToShow() {
        return newItemsToShow;
    }

    public void setNewItemsToShow(Set<Item> newItemsToShow) {
        this.newItemsToShow = newItemsToShow;
    }

}
