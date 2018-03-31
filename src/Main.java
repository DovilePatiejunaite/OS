public class Main {
    public static void main(String[] args) {
        //TUI maine.
        Memory m = new Memory();
        RmCommands v = new RmCommands(m);
        v.setCS("0000");
        v.createVirtualMachine();
        System.out.println(v.getPTR());
        v.getPTR();
        v.moreMemoryForVM(Integer.parseInt(v.getIP()));
        //NUO ČIA VM
        //IP=0

        //IP=1
     /*   v.storeString(m, m.realWordAdress(m.getArray(),Integer.parseInt(v.getPTR()),Integer.parseInt(v.getIP())));
        for(int i = 0;i<800;i++){
            System.out.println(i+m.getArray()[i]);
        }
*/
        //is vm-rm adreso kontevrtavimas!!!
        v.setERR("01");
        v.printERR();
        System.out.println(v.String());

        //TUI tui = new TUI();
        //tui.start();
        String s ="01234567";
        System.out.println(s.substring(5,6));
    }
}