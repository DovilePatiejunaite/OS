public class Main {
    public static void main(String[] args) {
        //TUI maine.

        TUI tui = new TUI();
        tui.start();


       /* ExternalMemory em = new ExternalMemory();
        Memory m = new Memory();
        RmCommands r = new RmCommands(m);
        m.setArrayWord("Pauliuk1",100);
        m.setArrayWord("Pauliuk9",109);
        r.writeh(em,100);
        String[] array = em.getArray();
        for(int i=0; i<array.length; i++){
            System.out.println(array[i]);
        }
        System.out.println(r.getP());
        */
    }
}