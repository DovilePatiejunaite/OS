public class Main {
    public static void main(String[] args) {
        Memory m = new Memory();
        RmCommands v = new RmCommands(m);
      //  v.setR(9999);
    //    v.setCS(0000);
        m.setArrayWord("0059",3);
      //  m.setArrayWord("6", 19);
        m.setArrayWord("7", 28);
     //   ExternalMemory ex = new ExternalMemory();
        v.setR("0001");
        v.add(3);
        System.out.println(v.getR());
        String s = String.format("%8s", v.getR());
        System.out.println(s);

        //System.out.println(m.getFromArray(0));
       // System.out.println(v.getIP());
        //v.mod(m,0);
        //System.out.println(v.getR());
       // v.add(m,0);
        //System.out.println(v.getR());

        //pridedami reikalingi nuliai
       // String s = String.format("%8s", "balius");
        v.setPTR("0020");
        //v.prt(m,3);
        //System.out.println(s);
        //int block = m.findFreeSpace(m.getArray(),0,699);
 //       int block = m.realWordAdress(m.getArray(), v.getPTR(),"0","0");
   //     System.out.println(block);

        //is vm-rm adreso kontevrtavimas!!!
    }
}
