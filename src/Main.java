public class Main {
    public static void main(String[] args) {
        //TUI maine.
        Memory m = new Memory();
        RmCommands v = new RmCommands(m);
        v.createVirtualMachine();
        System.out.println(v.getPTR());
        v.getPTR();
        v.moreMemoryForVM(Integer.parseInt(v.getIP()));
        //is vm-rm adreso kontevrtavimas!!!
        v.writePTR(98);
        v.writeP(101);
        System.out.println(v.getMODE());
        v.printERR();
        for(int i = 0;i<800;i++){
            System.out.println(i+m.getArray()[i]);
        }
    }
}