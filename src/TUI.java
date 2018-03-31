import java.util.Scanner;

public class TUI {
    Scanner input = new Scanner(System.in);
    Memory m = new Memory();
    RmCommands r = new RmCommands(m);
    ExternalMemory em = new ExternalMemory();
    public void start(){
        showMenu();
        System.out.print("\nĮveskite meniu pasirinkimą: ");
        int choice = input.nextInt();

        while(true){
            switch(choice){
                case 0: exit();
                case 1:
                    System.out.println("Kodo segmentas:");
                    m.printCS();
                    break;
                case 2:
                    System.out.println("Steko segmentas:");
                    m.printSS();
                    break;
                case 3:
                    System.out.println("Pertraukimų lentelė:");
                    m.printInterruptTable();
                    break;
                case 4:
                    System.out.println("VM kodas:");
                    m.printVM(Integer.parseInt(r.getPTR()), Integer.parseInt(r.getIP()));
                case 5: System.out.println(r.String());
                case 9: showMenu();
                    break;
                default: System.out.println("Tokio pasirinkimo nėra!");
                    break;
            }
            System.out.print("\nĮveskite meniu pasirinkimą: \n");
            choice = input.nextInt();
        }
    }

    private void showMenu(){
        System.out.println("\n*******************************************");
        System.out.println("*              RM ir VM                   *");
        System.out.println("*******************************************");
        System.out.println("*[1] Išvesti kodo segmento atmintį        *");
        System.out.println("*[2] Išvesti steko segmento atmintį       *");
        System.out.println("*[3] Išvesti pertraukimų vektorių lentelę *");
        System.out.println("*[4] Išvesti VM reprezentaciją            *");
        System.out.println("*[5] Išvesti registrų reikšmes            *");
        System.out.println("*[6] Komandų interpretatorius             *");
        System.out.println("*[9] `Pakartoti meniu pasirinkimus        *");
        System.out.println("*[0] `Išeiti iš sistemos                  *");
        System.out.println("*******************************************\n");
    }

    //skaityti ir kompiliuoti komandas

    public void commands(){
        String line = input.nextLine();
        String command = line.substring(0,4);
        String parameters = line.substring(4,8);
    }
    public void exit(){
        System.exit(1);
    }
}
