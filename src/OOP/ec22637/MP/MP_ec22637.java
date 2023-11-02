package OOP.ec22637.MP;

public class MP_ec22637 {
    public static void main(String[] args) {
        Visitable r = new House_ec22637();
        Visitor v = new GUIVisitor(System.out, System.in);
        r.visit(v, Direction.FROM_SOUTH);
    }
}
