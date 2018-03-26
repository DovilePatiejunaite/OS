public class Main {

    public static void main(String[] args) {
        VmCommands v = new VmCommands();
        Memory m = new Memory();
        v.setR(9999);
        m.setArrayWord("5",0);
        //System.out.println(m.getFromArray(0));
       // System.out.println(v.getIP());
        v.mod(m,0);
        System.out.println(v.getR());
       // v.add(m,0);
        //System.out.println(v.getR());

        //pridedami reikalingi nuliai
       // String s = String.format("%04d", v.getIP());
        //System.out.println(s);
        int i = 1;
        int j = 3;
        int k = i-j;
        System.out.println(k);
    }
}
