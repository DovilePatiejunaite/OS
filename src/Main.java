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
        v.write(99);
        //v.pushs();
        System.out.println(v.getSP());
        v.pops();
        for(int i = 0;i<800;i++){
            System.out.println(i+m.getArray()[i]);
        }

        //is vm-rm adreso kontevrtavimas!!!
        v.printERR();
        System.out.println(v.String());

        //TUI tui = new TUI();
        //tui.start();
    }
}