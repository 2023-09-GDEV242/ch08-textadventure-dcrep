import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Daniel Corritore
 *          original: Michael Kölling and David J. Barnes
 * @version 2023.10.22
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    // Track room movement
    private Stack<Room> roomTraverser;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        roomTraverser = new Stack<Room>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // Note: the objects assigned to these variable exist past the scope of this method
        Room outside, theater, pub, lab, office;
        Room gym, cafeteria, fitnessRoom, courtyard, lounge;
        Room musicRoom, artsRoom, dungeon, mausoleum, graveyard, cave, water;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        gym = new Room("in the campus gym");
        cafeteria = new Room("in the cafeteria");
        fitnessRoom = new Room("in a weight training room");
        courtyard = new Room("in the campus courtyard");
        lounge = new Room("in the campus lounge");
        musicRoom = new Room("in the music room");
        artsRoom = new Room("in the arts room");
        dungeon = new Room("in the dungeon");
        mausoleum = new Room("in a partially-underground mausoleum");
        graveyard = new Room("in the graveyard near a mausoleum with an open door");
        cave = new Room("in a cave with a pool of water");
        water = new Room("in an underground river");
        
        
        // add items
        outside.addItem("calculator", new Item("calculator with cyrillic numerals", 1));
        theater.addItem("podium", new Item("podium with red stains", 60));
        theater.addItem("propbrella", new Item("an umbrella prop", 8));
        lab.addItem("beaker", new Item("beaker with green liquid inside", 2));
        cave.addItem("magic-cookie", new Item("a magic cookie", 0));
        
        // initialise room exits
        outside.setExit("north", lab);
        outside.setExit("west", pub);
        outside.setExit("east", theater);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", courtyard);
        lab.setExit("south", outside);
        lab.setExit("east", office);        

        office.setExit("west", lab);
        
        courtyard.setExit("north", artsRoom);
        courtyard.setExit("south", lab);        
        courtyard.setExit("west", gym);
        courtyard.setExit("east", cafeteria);
        courtyard.setExit("down", dungeon);
        
        artsRoom.setExit("north", musicRoom);
        artsRoom.setExit("south", courtyard);
        artsRoom.setExit("west", fitnessRoom);
        artsRoom.setExit("east", lounge);
        
        musicRoom.setExit("south", artsRoom);
        
        gym.setExit("north", fitnessRoom);
        gym.setExit("east", courtyard);
        
        fitnessRoom.setExit("south", gym);
        fitnessRoom.setExit("east", artsRoom);
        
        cafeteria.setExit("north", lounge);
        cafeteria.setExit("west", courtyard);
        
        lounge.setExit("south", cafeteria);
        lounge.setExit("west", artsRoom);
        
        dungeon.setExit("northeast", mausoleum);
        dungeon.setExit("up", courtyard);
        
        mausoleum.setExit("southeast", dungeon);
        mausoleum.setExit("out", graveyard);
        
        graveyard.setExit("in", mausoleum);
        graveyard.setExit("north", cave);
        
        cave.setExit("south", graveyard);
        cave.setExit("water", water);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;
            
            case LOOK:
                look();
                break;
            
            case BACK:
                goBack();
                break;
                
            case EAT:
                eatFood();
                break;
                
            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Look command - prints out the description of the current room
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Eat food - for now this action just prints out that the character eats food
     */
    private void eatFood()
    {
        System.out.println("You have eaten now and you are not hungry any more.");
    }
    
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            // Track previous room
            roomTraverser.push(currentRoom);
            // Then move
            currentRoom = nextRoom;
            // Special case - swept down a river
            if (currentRoom.getShortDescription().contains("river")) {
                System.out.println("You get in the pool of water but soon realize you are " +
                    currentRoom.getShortDescription() + "!");
                System.out.println("You are carried by the current and lose consciousness.\n" +
                    "You awaken at the end of a sewage pipe near the college entrance.\n" +
                    "You stumble to your feet");

                // Clear room traversal (no more "back"!) and reset location to the entrance
                while (!roomTraverser.empty()) {
                    currentRoom = roomTraverser.pop();
                }
            }
            
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * Go back a room (if possible)
     */
    private void goBack()
    {
        if (roomTraverser.empty()) {
            System.out.println("Can't go back any further; we are at the origin!");
        }
        else {
            currentRoom = roomTraverser.pop();
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Main - start point outside of BlueJ
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
