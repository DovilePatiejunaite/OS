public class Main {

    public static void main(String[] args) {
        VmCommands v = new VmCommands();
        Memory m = new Memory();
        v.setR(3);
        m.setArrayWord("0002",0);
        //System.out.println(m.getFromArray(0));
        v.add(m,0);
        System.out.println(v.getR());
        //pridedami reikalingi nuliai
        String s = String.format("%04d", v.getIP());
        System.out.println(s);
    }
}
