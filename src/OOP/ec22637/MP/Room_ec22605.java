package OOP.ec22637.MP;

class Room_ec22605 extends Room {
    static final Item SkywardSword = new Item("Skyward Sword");
    static final Item SacredShield = new Item("Sacred Shield");
    static final Item DekuSeeds = new Item("Deku Seeds");
    static final Item Arrows = new Item("Arrows");

    public Direction visit(Visitor visitor, Direction visitorDirection){
        char [] choices = {'1', '2', '3', '4'};
        visitor.tell("Beedle- Hello Link, welcome to skyloft's Bazaar");
        visitor.tell("Beedle- This is my shop, feel free to look around");
        visitor.tell("Beedle- Here's our stock for today: ");

        char choice = visitor.getChoice("1. Skyward Sword (5 gold) from North | 2. Sacred Shield (3 gold) from East | 3. Deku seeds (2 gold) from South | 4. Arrows (free) from West", choices);

        if (choice == '1') {
            visitor.tell("Beedle- This is a trusty sword that should serve you well on your quest to recuse Zelda");
            visitorDirection = Direction.TO_NORTH;
            visitor.takeGold(5);
            visitor.giveItem(SkywardSword);
        }

        else if (choice == '2') {
            visitor.tell("Beedle- This is a shield that will repel even the strongest of foes, time your parry well and prepare to release a counter strike of mass effect");
            visitorDirection = Direction.TO_EAST;
            visitor.takeGold(3);
            visitor.giveItem(SacredShield);
        }

        else if (choice == '3') {
            visitor.tell("Beedle- These seeds can be launched at foes to stun them, once they are dazed, charge and go for the kill");
            visitorDirection = Direction.TO_SOUTH;
            visitor.takeGold(2);
            visitor.giveItem(DekuSeeds);
        }

        else if (choice == '4') {
            visitor.giveItem(Arrows);
            visitorDirection = Direction.TO_EAST;
            visitor.tell("Beedle- Here take these free of charge for you patronage, use these, you'll have to find the divine bow if you want to use it, it's somewhere where the korok's play");
        }

        else {
            visitor.tell("Beedle- Hey buddy, what do you think you're doing, don't get any funny ideas now, you've annoyed me now, that's it, i'll be taking this");
            visitor.takeGold(2);
            visitorDirection = Direction.opposite(visitorDirection);
        }

        return visitorDirection;


    }

}
