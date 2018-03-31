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
                    m.printVM(Integer.parseInt(r.getPTR()));
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
            r.needMemory(Integer.parseInt(r.getIP()));
            System.out.print(r.getCS() + ":" + r.getIP() + " ");
            String line = input.nextLine();
            String padded = line + ("        ".substring(line.length()));
            String command = padded.substring(0, 4);
            String parameters = padded.substring(4, 8);
            //Nėra $STR VM programos pradzioj
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
                case "SUB ":
                    r.commandToM(line);
                    r.sub(Integer.parseInt(parameters));
                    break;
                case "MUL ":
                    r.commandToM(line);
                    r.mul(Integer.parseInt(parameters));
                    break;
                case "DIV ":
                    r.commandToM(line);
                    r.div(Integer.parseInt(parameters));
                    break;
                case "MOD ":
                    r.commandToM(line);
                    r.mod(Integer.parseInt(parameters));
                    break;
                case "AND ":
                    r.commandToM(line);
                    r.and(Integer.parseInt(parameters));
                    break;
                case "OR  ":
                    r.commandToM(line);
                    r.or(Integer.parseInt(parameters));
                    break;
                case "XOR ":
                    r.commandToM(line);
                    r.xor(Integer.parseInt(parameters));
                    break;
                case "NOT ":
                    r.commandToM(line);
                    r.not();
                    break;
                case "LOAD":
                    r.commandToM(line);
                    r.load(Integer.parseInt(parameters));
                    break;
                case "STOR":
                    r.commandToM(line);
                    r.store(Integer.parseInt(parameters));
                    break;
                case "STOS":
                    r.commandToM(line);
                    r.storeString(Integer.parseInt(parameters));
                    break;
                case "LODS":
                    r.commandToM(line);
                    r.loadString(Integer.parseInt(parameters));
                    break;
                case "LODJ":
                    r.commandToM(line);
                    r.loadJ(Integer.parseInt(parameters));
                    break;
                case "LODL":
                    r.commandToM(line);
                    r.loadS(Integer.parseInt(parameters));
                    break;
                case "LOSR":
                    r.commandToM(line);
                    r.loadStringJ(parameters);
                    break;
                case "LOSL":
                    r.commandToM(line);
                    r.loadStringS(parameters);
                    break;
                case "WRTP":
                    r.commandToM(line);
                    r.writeP(Integer.parseInt(parameters));
                    break;
                case "WRIP":
                    r.commandToM(line);
                    r.writeIP(Integer.parseInt(parameters));
                    break;
                case "WRSP":
                    r.commandToM(line);
                    r.writeSP(Integer.parseInt(parameters));
                    break;
                case "WRSS":
                    r.commandToM(line);
                    r.writeSS(Integer.parseInt(parameters));
                    break;
                case "CPR ":
                    r.commandToM(line);
                    r.cpr(Integer.parseInt(parameters));
                    break;
                case "CPS ":
                    r.commandToM(line);
                    r.cps(Integer.parseInt(parameters));
                    break;
                case "PRIN":
                    r.commandToM(line);
                    r.print(Integer.parseInt(parameters));
                    break;
                case "WRIT":
                    r.commandToM(line);
                    r.write(Integer.parseInt(parameters));
                    break;
                case "GO  ":
                    r.commandToM(line);
                    r.go(Integer.parseInt(parameters));
                    break;
                case "JE  ":
                    r.commandToM(line);
                    r.je(Integer.parseInt(parameters));
                    break;
                case "JN  ":
                    r.commandToM(line);
                    r.jn(Integer.parseInt(parameters));
                    break;
                case "JL  ":
                    r.commandToM(line);
                    r.jl(Integer.parseInt(parameters));
                    break;
                case "JG  ":
                    r.commandToM(line);
                    r.jg(Integer.parseInt(parameters));
                    break;
                case "JO  ":
                    r.commandToM(line);
                    r.jo(Integer.parseInt(parameters));
                    break;
                case "PUSH":
                    r.commandToM(line);
                    r.push();
                case "PUSS":
                    r.commandToM(line);
                    r.pushs();
                    break;
                case "POP ":
                    r.commandToM(line);
                    r.pop();
                case "POPS":
                    r.commandToM(line);
                    r.pops();
                case "CLR ":
                    r.commandToM(line);
                    r.clears();
                case "PUSM":
                    r.commandToM(line);
                    r.pushm(Integer.parseInt(parameters));
                    break;
                case "POPM":
                    r.commandToM(line);
                    r.popm(Integer.parseInt(parameters));
                    break;
                case "PUSF":
                    r.commandToM(line);
                    r.pushf();
                    break;
                case "POPF":
                    r.commandToM(line);
                    r.popf();
                    break;
                case "WRHA":
                    r.commandToM(line);
                    r.writehard();
                    break;
                case "REHA":
                    r.commandToM(line);
                    r.readhard(Integer.parseInt(parameters));
                    break;
                    //REALIOS MAŠINOS KOMANDOS
                case "PRT ":
                    r.commandToM(line);
                    r.prt(Integer.parseInt(parameters));
                    break;
                case "WRT ":
                    r.commandToM(line);
                    r.wrt(Integer.parseInt(parameters));
                    break;
                case "SETM":
                    r.commandToM(line);
                    r.setM();
                    break;
                case "INT ":
                    r.commandToM(line);
                    r.inter(Integer.parseInt(parameters));
                    break;
                case "INC ":
                    r.commandToM(line);
                    r.increg(parameters);
                    break;
                case "DEC ":
                    r.commandToM(line);
                    r.decreg(parameters);
                    break;
                case "REDH":
                    r.commandToM(line);
                    r.readh(em,Integer.parseInt(parameters));
                    break;
                case "WRDH":
                    r.commandToM(line);
                    r.writeh(em,Integer.parseInt(parameters));
                    break;
                case "IRET":
                    r.commandToM(line);
                    r.iret();
                    break;
                case "SETT":
                    r.commandToM(line);
                    r.setTimer(parameters);
                    break;
                case "SCH ":
                    r.commandToM(line);
                    r.setChnannel(Integer.parseInt(parameters));
                    break;
                case "CLCH":
                    r.commandToM(line);
                    r.clearChnannel(Integer.parseInt(parameters));
                    break;
                case "CHCH":
                    r.commandToM(line);
                    r.checkChnannel(Integer.parseInt(parameters));
                    break;
                case "SPTR":
                    r.commandToM(line);
                    r.ptr(parameters);
                    break;
                case "VMCR":
                    r.commandToM(line);
                    r.createVirtualMachine();
                    break;
                case "PRMB":
                    r.commandToM(line);
                    r.RMBtoMemory(Integer.parseInt(parameters));
                    break;
                case "MIV ":
                    r.commandToM(line);
                    int interrupt = Integer.parseInt(parameters.substring(0,1));
                    int adress = Integer.parseInt(parameters.substring(1,4));
                    r.makeIntreruptVector(adress, interrupt);
                    break;
                case "LIV ":
                    r.commandToM(line);
                    r.loadInterruptVector(Integer.parseInt(parameters));
                    break;
                case "WTI ":
                    r.commandToM(line);
                    r.writeTI(Integer.parseInt(parameters));
                    break;
                case "WPTR":
                    r.commandToM(line);
                    r.writePTR(Integer.parseInt(parameters));
                    break;
                case "PRER":
                    r.commandToM(line);
                    r.printERR();
                    break;
                case "PRRE":
                    r.commandToM(line);
                    r.printRE();
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
