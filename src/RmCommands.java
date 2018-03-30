import java.util.Scanner;

public class RmCommands extends VmCommands{
    RmCommands(Memory m){
        super(m);
        System.out.println("RM KONSTR");
    }
    //(nuo 0000 iki 0039 - pertraukimų vektorių lentelė)CS = 0040 SS = 0700 - nekeičiamas.
    //vektorių lentelei pasiekti ir rašyti - atskiros komandos
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
    //JEI MODE 0 - VM ISIJUNGIA IR PUSLAPIAVMIMAS VYKDOMAS
    //jei nauja vm masina paleidziama : createNewVirtualMashine - nustatomas PTR registras naujas - tuomet kvieciama setM();
    //jei norima grizti po pertraukimo i tam tikra realia masina visu pirma su komanda IRET yra susigrazinami reikalingi
    //registrai ir tada nustomas setM();
    //cs - nesikeičia visais atvejais
    public void setM(){
        setRMB(getCS()+getSP());
        setMODE(0);
       setCS("0000");
       //patikriname ar nauja virtuali mašina, o ne backupas iš steko - jei nepaimtas backupas - SS bus vis dar
        //0700.
       if(!getSS().equals("0700")){
           //jei nauja mašina - visi jos registrai nustatomi pradinėmis reikšmėmis.
           setSS("0090");
           setSP("0000");
           setIP("0000");
           setR("00000000");
           setP("00000000");
           setRS("00000000");
           setERR("00");
           setCF(0);
           setZF(0);
           setSF(0);
       }
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
    //IRET - grįžimo į VM funkcija.
    //Prieš pertraukimo apdorojimą viskas sudėta buvo tokia tvarka PTR,IP, R, P, RS, SS, SP, ERR, (CF, SF, ZF);
    public void iret(){




        String i = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        setR(String.format("%08d",i.trim()));
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        DEC("SP");
        INC("IP");
    }
    //Timeris
    public void setTimer(String ti){
        setTI(ti);
        INC("IP");
    }

    public void setChnannel(int r){
        SETCH(r);
        INC("IP");
    }
    public void clearChnannel(int r){
        CLRCH(r);
        INC("IP");
    }

    public void checkChnannel(int r){
        setCF(getCHST1());
        INC("IP");
    }

    public void ptr(String value){
        setPTR(value);
        INC("IP");
    }

    public void createVirtualMachine(){
        int table_block = m.findFreeSpace(m.getArray());
        setPTR("10"+String.format("%02d",table_block));
        int block_adress = table_block*10;
        for(int i=0;i<10;i++){
            m.setArrayWord("-",block_adress+i);
        }
        INC("IP");
    }


//issikiriam vietos is vm->rm kur bus irasoma
// IP yra virtualios mašinos (CS=0, adreso nekeičia)
    //nėra komanda!! nedidina IP ir yra puslapiavimo dalis.
    public void moreMemoryForVM(int ip){
        int block =  m.findFreeSpace(m.getArray());
        int which_block = ip/10;
        m.setArrayWord(String.valueOf(block), Integer.parseInt(getPTR().substring(1,3))+which_block);
    }

    //Išskirti atminti VM.+
    //paleisti, nustatyt ptr+
    //iret

    public void RMBtoMemory(int adress){
        m.setArrayWord(getRMB(), adress);
        INC("IP");
    }

    public void makeIntreruptVector(int adress, int interrupt){
        m.setArrayWord("0040"+String.format("%04d",adress), interrupt);
        INC("IP");
    }

    public void loadInterruptVector(int interrupt){
        setR(m.getFromArray(interrupt));
        INC("IP");
    }

    //kanalų valdymas
}
