import java.util.Map;
import java.util.HashMap;

/**
 * Player - the main character in an adventure game
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * A player will navigate the world and collect items
 * 
 * TODO: implement take, drop, move currentRoom to this class
 * 
 * @author Daniel Corritore
 * @version 2023.10.22
 */
public class Player
{
    //private Room currentRoom;
    private int weightThreshold;
    private int weightCarrying;
    private HashMap<String, Item> carriedItems;

    /**
     * Create a Player
     * @maxCarryWeight The maximum weight the player can carry
     */
    public Player(int maxCarryWeight)
    {
        this.weightThreshold = maxCarryWeight;
        carriedItems = new HashMap<>();
        weightCarrying = 0;
    }
    
    /**
     * Return the weight of items being carried
     * @return combined weight of items being carried
     */
    public int carryingWeight()
    {
        return weightCarrying;
    }
    
    /**
     * Print out the items carried and their current weights
     */
    public void items()
    {
        System.out.println("Carried items: ");
        if (carriedItems.size() == 0) {
            System.out.println("none");
            return;
        }
       
        for (Map.Entry<String, Item> entry : carriedItems.entrySet()) {
            //String key = entry.getKey();
            Item item = entry.getValue();
            System.out.println(item.getLongDescription() + " (weight: " + item.getWeight() + ")");
        }
    }

}
