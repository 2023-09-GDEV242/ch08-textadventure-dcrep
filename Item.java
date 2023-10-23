
/**
 * Class Item - an item in an adventure game
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * An "Item" represents any object inside any room in the game.
 * Each item has a weight which will be useful if the player collects it
 * 
 * @author Daniel Corritore
 * @version 2023.10.22
 */

public class Item
{
    private String description;
    private int weight;

    /**
     * Create an item with a description and weight
     * @param description The description of the item
     * @param weight The weight of the item
     */
    public Item(String description, int weight)
    {
        this.description = description;
        this.weight = weight;
    }
    
    /**
     * Return the weight of the item
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * Return a short description of the room.
     * (the one that was defined in the constructor).
     * @return The short description of the item 
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the item in the form:
     *     A(n) envelope with a star on it is here.    
     * @return A long description of this item
     */
    public String getLongDescription()
    {
        return "A(n) " + description + " is here.\n";
    }

}
