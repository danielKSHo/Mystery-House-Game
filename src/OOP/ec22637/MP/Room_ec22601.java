package OOP.ec22637.MP;//Room_ec22601.java
//ec22601's room

//Imports random library for use in the class
import java.util.Random;

class Room_ec22601 extends Room{

    //Items in the room
    final Item lantern = new Item("lantern");
    final Item bag = new Item("bag");

    //Instance variables - state of room
    boolean lightStatus;  //false = off //tue = on 
    boolean ghostStatus;  //true = happy //false = unhappy
    boolean windowOpen; //true = open // false = closed
    boolean roomCold; //true = cold //false = warm

    public Direction visit(Visitor roomVisitor, Direction directionFrom){

        int gold = 0;
        char[] possibleChoices = {'a', 'b', 'c', 'd'};
        char[] lightChoices = {'a','b'};
        char[] windowChoices = {'a', 'b'};

        roomVisitor.tell("You are now in ec22601's room. ");
        
        //Checks direction the visiitor is coming from and changes state based on this
        //Checks if visitor is heading north
        if(directionFrom.toString().equals("heading North")){

            lightStatus = false;
            roomVisitor.tell("Currently the lights are off and the ghost which haunts this room is happy...but this can change!");
            roomVisitor.tell("Would you like to switch on the light?");
            char userChoice = roomVisitor.getChoice("Choices: a) Turn on lights, b) Keep lights off", lightChoices);

            if(userChoice == 'a'){  //Checks if user wants lights on
                lightStatus = true;
                ghostStatus = false;
                roomVisitor.tell("The lights are now on, but the ghost is unhappy!");
            }

            else{
                roomVisitor.tell("You can search in the dark!");
            }

        }

        //Checks if visitor is heading east
        else if(directionFrom.toString().equals("heading East")){
            
            lightStatus = true;
            windowOpen = true;
            roomCold = true;

            roomVisitor.tell("The rooom lights are on, the window is open and a cold breeze is blowing through the open window, causing the curtain to ripple in the wind.");
            roomVisitor.tell("Would you like to close the window?");
            char userChoice = roomVisitor.getChoice("Choices: a) Close window, b) Keep window open", windowChoices);

            if(userChoice == 'a'){  
                windowOpen = false;
                roomVisitor.tell("The window is now closed and the curtain is still.");
            }

            else{
                roomVisitor.tell("The room temperature is slowly dropping...");
            }

        }

        //Checks if visitor is heading south
        else if(directionFrom.toString().equals("heading South")){
            
            ghostStatus = false;
            roomVisitor.tell("The ghost is currently unhappy and may cause mischief during your visit to this room!");

        }

        //Checks if visitor is heading west
        else{

            lightStatus = true; 
            ghostStatus = true;  
            windowOpen = false; 
            roomCold = false;

            roomVisitor.tell("Currently, the lights are on, the ghost is happy, the window is closed and the room is warm.");
        }

        //Room information before choices
        roomVisitor.tell("In this room, there is a bed on the right, a chest at the end of the bed and a large cupboard on the left");
        char userChoice = roomVisitor.getChoice("Choices: a) Open chest, b) Look on the table, c) Look under bed, d) Leave room ", possibleChoices);

        //Option a - open chest
        if(userChoice == 'a'){

            roomVisitor.tell("You have opened the chest...");
            roomVisitor.tell("You have found 5 pieces of gold!");
            roomVisitor.giveGold(5);

            return Direction.opposite(directionFrom);  //Once user opens chest they are sent in opposite direction that they came from
        }

        //Option b - look on the table
        else if(userChoice == 'b'){

            roomVisitor.tell("You look on the table...");
            roomVisitor.tell("You find a lantern");
            
            //Checks if user has the item (the lantern)
            if(roomVisitor.hasEqualItem(lantern) == false){
                roomVisitor.giveItem(lantern);
            }
            else{
                roomVisitor.tell("You already have this item");
            }

            return Direction.TO_WEST;
        }

        //Option c - look under the bed
        else if(userChoice == 'c'){

            roomVisitor.tell("You look under the bed...");
            roomVisitor.tell("You find a bag under the bed");

            //Ghost takes gold whether they have the item (the bag) or not. 
            if(roomVisitor.hasEqualItem(bag) == false){
                roomVisitor.giveItem(bag);

                if(ghostStatus == false){
                    roomVisitor.tell("The ghost steals 3 pieces of gold!");
                }

                else{
                    roomVisitor.tell("You lose 3 pieces of gold through the gap in the floorboards...");
                }
            }

            else{
                if(ghostStatus == false){
                    roomVisitor.tell("The ghost steals 3 pieces of gold!");
                }

                else{
                    roomVisitor.tell("You lose 3 pieces of gold through the gap in the floorboards...");
                }
            }
            gold = roomVisitor.takeGold(3);
            return Direction.TO_EAST;
        }
        
        //Option d - leave room
        //Generates random number using random() method and then uses this to select a direction to leave in.
        else{

            roomVisitor.tell("You will leave the room in a random direction!");
            
            Random ranNum = new Random();
            int randomInt = ranNum.nextInt(3);

            if(randomInt == 0){
                return Direction.TO_NORTH;
            }

            else if(randomInt == 1){
                return Direction.TO_SOUTH;
            }

            else if(randomInt == 2){
                return Direction.TO_WEST;
            }

            else{
                return Direction.TO_EAST;
            }

        }

    }
}
