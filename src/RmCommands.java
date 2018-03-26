import java.util.Scanner;

public class RmCommands extends VmCommands{
    RmCommands(){
        VmCommands s = new VmCommands();
    }

    public void prt(Memory m, int adress) {
        String i = m.getFromArray(adress);
        System.out.println(i);
        INC("IP");
    }

    public void wrt(Memory m, int adress) {
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

    public void setM(int mode){
       setMODE(mode);
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

    public void readh(ExternalMemory em, Memory m){
        INC("IP");
    }


}
