package OOP.ec22637.MP;

public class House_ec22637 extends House {

    // Instance variables for rooms
    Room_ec22637 lobby;
    Room_ec22889 basement;
    Room_ec22601 lounge;
    Room_ec22597 study;
    Room_ec22605 bazaar;
    Room_ec22919 gallery;

    Room [] rooms;
    int roomIndex;
    int goldAmount;
    boolean inHallway;

    // Constructor
    House_ec22637() {

        // Creating instances of rooms
        Room_ec22637 lobby = new Room_ec22637();
        Room_ec22889 basement = new Room_ec22889();
        Room_ec22601 lounge = new Room_ec22601();
        Room_ec22597 study = new Room_ec22597();
        Room_ec22605 bazaar = new Room_ec22605();
        Room_ec22919 gallery = new Room_ec22919();

        rooms = new Room[] {lobby, basement, lounge, study, bazaar, gallery};
        roomIndex = 0;
        goldAmount = 3;
    }

    // Visitor method
    public Direction visit(Visitor visitor, Direction d) {
        // Visitor message
        visitor.tell("You wake up in the lobby, you can look around to see where you want to go");

        d = rooms[roomIndex].visit(visitor, d);

        // Visitor to direction [North/East/South/West]
        System.out.println(d);

        while (!(roomIndex == 5 && d == Direction.TO_NORTH)) {
            if (roomIndex == 0) {
                if (d == Direction.TO_NORTH) {
                    roomIndex = 2; // To the Lounge
                    d = Direction.FROM_SOUTH;
                }
                else if (d == Direction.TO_EAST) {
                    roomIndex = 3; // To the Study
                    d = Direction.FROM_SOUTH;
                }
                else if (d == Direction.TO_SOUTH) {
                    roomIndex = 0; // Stay in the Lobby
                    d = Direction.FROM_SOUTH;
                }
                else if (d == Direction.TO_WEST) {
                    roomIndex = 1; // To the Basement
                    d = Direction.FROM_SOUTH;
                }
            }
            else if (roomIndex == 1) {
                if (d == Direction.TO_NORTH) {
                    roomIndex = 4; // To the Bazaar
                    d = Direction.FROM_WEST;
                }
                else if (d == Direction.TO_SOUTH) {
                    roomIndex = 0; // To the Lobby
                    d = Direction.FROM_WEST;
                }
            }
            else if (roomIndex == 2) {
                if (d == Direction.TO_NORTH) {
                    roomIndex = 4; // To the Bazaar
                    d = Direction.FROM_SOUTH;
                }
                else if (d == Direction.TO_EAST) {
                    roomIndex = 3; // To the Study
                    d = Direction.FROM_WEST;
                }
                else if (d == Direction.TO_SOUTH) {
                    roomIndex = 0; // To the Lobby
                    d = Direction.FROM_NORTH;
                }
                else if (d == Direction.TO_WEST) {
                    roomIndex = 1; // To the Basement
                    d = Direction.FROM_EAST;
                }
            }
            else if (roomIndex == 3) {
                if (d == Direction.TO_NORTH) {
                    roomIndex = 5; // To the Gallery
                    d = Direction.FROM_SOUTH;
                }
            }
            else if (roomIndex == 4) {
                if (d == Direction.TO_NORTH) {
                    roomIndex = 4; // To the Bazaar
                    d = Direction.FROM_SOUTH;
                }
                else if (d == Direction.TO_EAST) {
                    roomIndex = 5; // To the Study
                    d = Direction.FROM_WEST;
                }
                else if (d == Direction.TO_SOUTH) {
                    roomIndex = 0; // To the Lobby
                    d = Direction.FROM_NORTH;
                }
                else if (d == Direction.TO_WEST) {
                    roomIndex = 1; // To the Basement
                    d = Direction.FROM_EAST;
                }
            }
            else if (roomIndex == 4) {
                if (d == Direction.TO_NORTH) {
                    roomIndex = 4; // To the Bazaar
                    d = Direction.FROM_SOUTH;
                }
                else if (d == Direction.TO_EAST) {
                    roomIndex = 3; // To the Study
                    d = Direction.FROM_WEST;
                }
                else if (d == Direction.TO_SOUTH) {
                    roomIndex = 0; // To the Lobby
                    d = Direction.FROM_NORTH;
                }
                else if (d == Direction.TO_WEST) {
                    roomIndex = 1; // To the Basement
                    d = Direction.FROM_EAST;
                }
            }
            travel(roomIndex);
            d = rooms[roomIndex].visit(visitor, d);
            System.out.println(d);
        }
        return d;
    }

    void travel(int roomIndex) {
        String location = "";
        if (roomIndex == 0) {
            location = "lobby";
        }
        else if (roomIndex == 1) {
            location = "basement";
        }
        else if (roomIndex == 2) {
            location = "lounge";
        }
        else if (roomIndex == 3) {
            location = "study";
        }
        else if (roomIndex == 4) {
            location = "bazaar";
        }
        else if (roomIndex == 5) {
            location = "gallery";
        }
        System.out.println("\n... Entering the " + location + " ...\n");
    }

}
