package OOP.ec22637.MP;

import java.util.Random;
import java.util.Scanner;

class Room_ec22889 extends Room{
    boolean locked;
    boolean candle1;
    boolean candle2;
    boolean candle3;
    static final Item IMMORTAL_REACTOR = new Item("Immortal Reactor");

    Room_ec22889(){
        this.locked = true;
        this.candle1 = true;
        this.candle2 = true;
        this.candle3 = true;
    }

    public Direction visit(Visitor v, Direction d){
        v.tell("Welcome, the door is currently " + doorStatus(locked) + ".");
        v.tell("In the center of the room, there is a table with a magic cup and die and some candles.");
        v.tell("Hiding in the corner of the room is a small creature with a birdlike face");
        char opt = v.getChoice("a) Guess the die \nb) Talk to the creature \nc) Take a look at the candles \nd) enter the door \ne) leave", new char [] {'a','b','c','d','e'});

        // guess the die if the user picks a
        if(opt=='a'){
            int dice = diceRoll();
            int guess = input("Guess the die roll for a special prize (enter an integer)");

            if(guess==dice){
                v.giveItem(IMMORTAL_REACTOR);
                v.tell("You acquired an immortal reactor. Perhaps a crazy penguin may be willing to purchase it...");
            }

            else{
                v.tell("Wrong! The dice rolled " + guess);
            }
        }

        // talk to the creature if the user picks b
        else if(opt=='b'){
            if(v.hasIdenticalItem(Room_ec22919.MASK)){
                v.tell("The creature steals your mask.");
            }

            else{
                v.tell("The creature runs away.");
            }
        }

        // guess the combination to unlock the doors
        else if(opt=='c'){
            char [] candleChoice = {'a','b','c'};

            v.tell("Perhaps something will happen if they are lit in a certain order...");

            // candle 1
            v.tell("You take a look at the candle on the left, it is currently " + candleStatus(candle1));
            candle1 = candleOpt(v, candleChoice, candle1);

            // candle 2
            v.tell("You take a look at the candle in the middle, it is currently " + candleStatus(candle2));
            candle2 = candleOpt(v, candleChoice, candle2);

            // candle 3
            v.tell("You take a look at the candle on the right, it is currently " + candleStatus(candle3));
            candle3 = candleOpt(v, candleChoice, candle3);

            // if the user guesses the correct combination
            if(candle1==true && candle2==false && candle3==true && locked==true){
                v.tell("The door begins to rumble and the lock shatters. You walk through the door");
                locked = false;
                return Direction.TO_NORTH;
            }

            else if(candle1==true && candle2==false && candle3==true && locked==false){
                v.tell("The door is already open. You walk through it");
                return Direction.TO_NORTH;
            }

            else{
                v.tell("You couldn't figure out how to open the door.");
            }
        }

        // if the user tries to enter the door
        else if(opt=='d'){
            if(locked){
                v.tell("The door is locked");
            }

            else{
                v.tell("You walk through the door");
                return Direction.TO_NORTH;
            }
        }

        else{
            v.tell("You do nothing");
        }

        // if the user has arrived from north (direction of the door)
        if(d==Direction.FROM_NORTH){
            v.tell("You cannot return the way you came. You go south.");
            return Direction.TO_SOUTH;
        }

        v.tell("You leave the way you came.");
        return Direction.opposite(d);
    }

    // return the status of the door
    public String doorStatus(boolean locked){
        if(locked){
            return "locked";
        }

        return "unlocked";
    }

    // return the status of the candle
    public String candleStatus(boolean c){
        if(c){
            return "lit";
        }

        return "extinguished";
    }

    // choose candle option
    public boolean candleOpt(Visitor v, char [] candleChoice, boolean c){
        char choice = v.getChoice("a) blow out the candle \nb) light it \nc) leave it", candleChoice);
        if(choice=='a'){
            v.tell("You blow out the candle");
            c = false;
        }

        else if(choice=='b'){
            v.tell("You light the candle");
            c = true;
        }

        else{
                v.tell("You leave the candle");
        }

        return c;
    }

    // roll a random number from 1-6
    public int diceRoll(){
        return new Random().nextInt(6) + 1;
    }

    // choose die guess
    public int input(String message){
        System.out.println(message);
        int guess = 0;
        while(guess<1 || guess>6){
            guess = Integer.parseInt(new Scanner(System.in).nextLine());
        }
        return guess;
    }
}
