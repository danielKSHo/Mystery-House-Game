package OOP.ec22637.MP;

class Room_ec22919 extends Room {
    boolean isChestLocked;
    boolean hasShownItem;
    static final Item MASK = new Item("Purple mask");
    
    Room_ec22919(){
        this.isChestLocked = true;
        this.hasShownItem = false;
    }
    
    //Trade Friend's Item with this Room's item
    public Visitor tradeWithPenguin(char [] itemChoices, Visitor v){
        char showItemChoice =  v.getChoice("Would you like to: \na) Give the Immortal Reactor \nb) Refuse", itemChoices);
        if(showItemChoice == 'a'){
            v.tell("You give the Immortal Reactor to the crazy penguin.");
            v.tell("`Thank you for giving me this. Here, have this mask and 7 pieces of gold.`");
            v.giveItem(MASK);
            v.giveGold(7);
            v.tell("You were given 7 pieces of gold and a purple mask, with two big yellow eyes and spikes on the side of the mask.");
            v.tell("You return to the entrance of the room");
            hasShownItem = true;
        }
        if(showItemChoice == 'b'){
            v.tell("`Well feel free to return at anytime if you change your mind.`");
            v.tell("You return to the entrance of the room");
        }
        return v;
    }
        
    
    public Direction visit (Visitor v, Direction d) {
        
        //Three possible choice arrays for the visitor
        char [] choices = {'a','b','c','d'};
        char [] itemChoices = {'a','b'};
        char [] directions = {'N', 'W', 'B'};
        

        v.tell("You think you're in a room, but you've been wrong before");
        v.tell("As you scan around, there are a couple of key elements to take notice of.");
        v.tell("There are a dozen of statues and bookcases scattered around.");
        v.tell("Furthermore, there is a mysterious chest located directly centered in the room.");
        char roomChoice = v.getChoice("Would you like to: \na) Inspect the bookcases \nb) Check out the statues \nc) Try open the chest\nd) Nothing", choices);

        //First option: Bookcases + book
        //Give 4 gold and return visitor to direction they came from
        if(roomChoice == 'a') {
            v.tell("After taking a closer look at the bookcases, it is evident that out of all the dusty books on the shelves, there is a recently cleaned book with a certain page marked.\nOpening to the marked page, there is a single piece of advice written.");       
            v.tell("`To open the chest, you must find the Immortal Reactor. Have 4 gold as an incentive in this quest`");
            v.tell("To your surprise, 4 pieces of gold are thrown at you by one of the statues.\nFearful, you caught the gold and ran in the direction that you came from.");
            v.giveGold(4);
            return Direction.opposite(d);
        }

        //Second option: Statues
        //Take 2 gold and return visitor to direction they came from
        else if(roomChoice == 'b') { 
            v.tell("You decide to approach one of the statues. After analysing the statues, you can see some scratch marks on the statue and dents in the floor near the feet of the statues.");
            v.tell("Confused on what could have done this, you turn around and attempt to start walking to another statue to see if they had the same signs. But before you could walk, a hand grabbed your shoulder.");
            v.tell("Startled and afraid, you shoved the hand off your shoulder and ran in the direction that you came from.");
            v.tell("Unfortunately, 2 gold was dropped during your retreat.");
            v.takeGold(2);
            return Direction.opposite(d);
        }

        //Third option: Chest
        //Locked chest - Open if visitor had "Immortal Reactor" item from friend's room
        else if(roomChoice == 'c') {
            //if chest is locked
            if(isChestLocked == true){
                v.tell("You decide to try to open the chest. After scanning the chest for ways to open it, it seems like there is no visible way to open it.");
                //If they have the item to open it
                if (v.hasIdenticalItem(Room_ec22889.IMMORTAL_REACTOR)){
                    isChestLocked = false;
                    v.tell("As you start to place your hands on the top of the chest, the chest starts to shake rapidly!");
                    v.tell("A crazy looking penguin bursts out of the chest and hits your head causing you to fall flat on your back.");
                    v.tell("The penguin waits for you to pick yourself up before opening its beak and...");
                    v.tell("`Hey there, I haven't seen the light of day in a while. I was sleeping until I felt a presence of a reactor.`");
                    v.tell("`It seems like you would be in possesion of a Immortal Reactor!!!`");
                    v.tell("`Would you let me have that Immortal Reactor?`");
                    //Checking if they will let penguin look at Immortal Reactor 
                    v = tradeWithPenguin(itemChoices, v);
                }

                //If they don't have the key
                else{
                    v.tell("After spending 5 minutes trying to forcefully open the chest, in the end you give up.");
                    v.tell("You return to the entrance of the room.");
                }
            }
            
            //If they have seen the penguin but haven't given the Immortal Reactor item 
            else if(hasShownItem == false && isChestLocked == false){
                //penguin
                v.tell("You find the chest closed again. You open it to find...");
                v.tell("The crazy penguin still sitting inside the chest.");
                v.tell("`Hey, did you want to give me that Immortal Reactor?`");
                //Checking if they will let penguin look at Immortal Reactor 
                v = tradeWithPenguin(itemChoices, v);
            }
            
            else{
                //If they have given the Immortal Reactor item 
                //Penguin thanking for them returning - dont have anything else to give
                v.tell("You find the chest closed again. You open it to find...");
                v.tell("The crazy penguin still sitting inside the chest.");
                v.tell("`Hey! Thank you for giving me that Immortal Reactor. Unfortunately I have nothing else to give in return.`");
                v.tell("You return to the entrance of the room.");
            }
            
        }
        
        
        //Last option if they chose d or just not the first three options
        else{
            v.tell("Having no clue of what to do, you decide to no mess around with the room.");
            v.tell("You return to the entrance of the room.");
        }

        
        char direction = v.getChoice("Which direction are you taking? (N)orth, (W)est, (B)ackwards", directions);
        
        //Allow visitor to choice direction
        if(direction=='N'){
            v.tell("You decide to walk north");
            return Direction.TO_NORTH;
        }

        else if(direction=='W'){
            v.tell("You decide to walk west");
            return Direction.TO_WEST;
        }

        else if(direction=='B'){
            v.tell("You decide to walk in the direction that you came from");
            return Direction.opposite(d);
        }

        else{
        System.out.println("After not having a clue on which direction to take, you decide to return the way that you took.");
        return Direction.opposite(d);
        }
    }

}
