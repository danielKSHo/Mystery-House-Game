package OOP.ec22637.MP;

interface Visitor {
    
    void tell(String messageForVisitor);
    
    char getChoice( // Returns visitor's choice.
                   String descriptioOfChoices,
                   char[] arrayOfPossibleChoices);
    
    boolean giveItem( // Returns true if item is accepted.
                     Item itemGivenToVisitor);
    
    boolean hasIdenticalItem( // Returns true if visitor has the identical (==) item.
                             Item itemToCheckFor);
        
    boolean hasEqualItem( // Returns true if visitor has an equal item (same name).
                         Item itemToCheckFor);
    
    void giveGold(int numberOfPiecesToGive);
        
    int takeGold( // Returns number of pieces actually obtained from visitor.
                 int numberOfPiecesToTake);
}