public class Main {

    public static void main(String[] args) {
        Memory m = new Memory();
        RmCommands v = new RmCommands(m);
        v.setR(9999);
        v.setCS(0000);
        m.setArrayWord("5",3);
      //  m.setArrayWord("6", 19);
        m.setArrayWord("7", 28);
        ExternalMemory ex = new ExternalMemory();
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

        int block = m.findFreeSpace(m.getArray(),0,699);
        System.out.println(block);
        //is vm-rm adreso kontevrtavimas!!!
    }
}
