package OOP.ec22637.MP;

public class Room_ec22597 extends Room{

    public Direction visit(Visitor visitor, Direction directionVistorArrivesFrom) {
       
        visitor.tell("You've entered Theo's room, it is fairly clean and the desk is ready for you to study at it");
        char choice = visitor.getChoice("Do you study? [y/n]", new char[] {'y', 'n'});
        if (choice == 'y') {
            visitor.tell("Great! You chose to study, +3 Gold!, You may go North...");
            visitor.giveGold(3);
            // gives gold;

        } else {
            visitor.tell("Aw! You didn't choose to study :(\n-3 Gold!, You must go North...");
        }
        return Direction.TO_NORTH;

    }
}
