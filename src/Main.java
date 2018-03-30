public class Main {
    public static void main(String[] args) {
        Memory m = new Memory();
        RmCommands v = new RmCommands(m);
        v.createVirtualMachine();
        v.moreMemoryForVM(Integer.parseInt(v.getIP()));
        for(int i = 0;i<800;i++){
            System.out.println(i+m.getArray()[i]);
        }
        System.out.println(m.getArray());
        //is vm-rm adreso kontevrtavimas!!!
    }
}
