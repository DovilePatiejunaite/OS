import java.util.Scanner;

public class RmCommands extends VmCommands{
    RmCommands(Memory m){
        super(m);
        System.out.println("RM KONSTR");

    }

    public void prt(int adress) {
        String i = m.getFromArray(adress);
        System.out.println(i);
        INC("IP");
    }

    public void wrt(int adress) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Įveskite iki 8 simbolių, įvedimui į atmintį:");
        String input = "";
        while(scanner.hasNextLine()){
            input = scanner.nextLine();
            if(input.length()<9){
                break;
            } else {
                System.out.println("Neteisinga įvestis!");
            }
        }
        m.setArrayWord(input, adress);
        INC("IP");
    }
    //setMODE 0 - USER, 1 - SUPERVISOR
    //JEI MODE 0 - VM ISIJUNGIA IR PUSLAPIAVMIMAS VYKDOMAS, IP, CS IKELIAMI IS STEKO;
    //JEI REALI MASINA ISIJUNGIA ?
    public void setM(){
       setMODE(0);
       INC("IP");
    }
    public void inter(int number){
        setIR(number);
        INC("IP");
    }

    public void setCH1(int number){
        setCHST1(number);
        INC("IP");
    }
    public void setCH2(int number){
        setCHST2(number);
        INC("IP");
    }
    public void setCH3(int number){
        setCHST3(number);
        INC("IP");
    }

    public void increg(String register){
        INC(register);
        INC("IP");
    }
    public void decreg(String register){
        DEC(register);
        INC("IP");
    }
    //1 bloko skaitymas iš disko. 10 žodžių
    public void readh(ExternalMemory em, int adress){
        int last = em.getLast_read();
        for (int i = last; i<last+10; i++){
            m.setArrayWord(em.getFromArray(i),adress);
            i++;
            adress++;
            em.setLast_read(em.getLast_read()+1);
        }
        INC("IP");
    }
    //1 bloko rašymas į diską.
    public void writeh(ExternalMemory em, int adress){
        for (int i = 0; i<10; i++){
            em.setArrayWord(m.getFromArray(i),adress);
            i++;
            adress++;
            em.setLast_free_space(em.getLast_free_space()+10);
        }
        INC("IP");
    }
    //IRET veikia priklausomai nuo koks interaptas buvo vykdytas
    public void iret(){
       int inter = getIR();
        //kodas


    }
    public void setRmCS(int cs){
        setCS(cs);
        INC("IP");
    }
    //Timeris
    public void setTimer(int ti){
        setTI(ti);
        INC("IP");
    }

}
