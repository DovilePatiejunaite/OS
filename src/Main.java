public class Main {

    public static void main(String[] args) {
        VmCommands v = new VmCommands();
        Memory m = new Memory();
        v.setR(9999);
        m.setArrayWord("5",3);
        //System.out.println(m.getFromArray(0));
       // System.out.println(v.getIP());
        //v.mod(m,0);
        //System.out.println(v.getR());
       // v.add(m,0);
        //System.out.println(v.getR());

        //pridedami reikalingi nuliai
       // String s = String.format("%8s", "balius");

        //v.prt(m,3);
         //System.out.println(s);
        v.wrt(m,4);
        System.out.println(m.getFromArray(4));
    }
}
