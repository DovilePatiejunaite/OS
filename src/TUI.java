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
        input.nextLine();

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
                    break;
                case 5: System.out.println(r.String());
                        break;
                case 6: commands();
                    break;
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
       // r.setMODE(0);
        int is_str = 0;
        Boolean is_end = false;
        r.createVirtualMachine();
        r.setM();
        while(true) {
            System.out.print(r.getCS() + ":" + r.getIP() + " ");
            String line = input.nextLine();
            String padded = line + ("        ".substring(line.length()));
            String command = padded.substring(0, 4);
            String parameters = padded.substring(4, 8);
            //NėrA $STR VM programos pradzioj
            if(r.getIP().equals("0000")&&r.getMODE()==0){
                if(!command.equals("$STR")){
                    r.setERR("6");
                }
            }
            //Nėra $END
            if(r.getIP().equals("0099")&&r.getMODE()==0&&!is_end){
                if(!command.equals("$END")) {
                    r.setERR("8");
                }
            }
            if(is_str>1&&r.getMODE()==0){
                //kuris err kai antras $str ivestas
                r.setERR("11");
            }
            r.needMemory(Integer.parseInt(r.getIP()));
            switch (command){
                case "$STR":
                    r.commandToM(line);
                    r.INC("IP");
                    is_str++;
                    break;
                case "$END":
                    r.commandToM(line);
                    is_end=true;
                    r.halt();
                    break;
                    ///
                case "ADD ":
                    r.commandToM(line);
                    r.add(Integer.parseInt(parameters));
                    break;
                case "exit":
                    start();
                    break;
            }
        }
    }


    public void exit(){
        System.exit(1);
    }
}
