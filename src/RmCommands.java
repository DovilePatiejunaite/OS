import java.util.Scanner;

public class RmCommands extends VmCommands{
    RmCommands(Memory m){
        super(m);
        System.out.println("RM KONSTR");
    }

    //Virtualios mašinos klaidų kodai
    private String[] errorsVM = {
            "Neatpažinta komanda",
            "Netelpa duomenys į registro rėžius",
            "Dalyba iš nulio",
            "Neteisingas adresas",
            "Vykdyta POP komanda, tačiau stekas tuščias",
            "Programos pradžioje nėra $START",
            "Programos pabaigoje nėra $END"
    };
    //Realios mašinos klaidų kodai
    private String[] errorsRM = {
            "Nėra IRET funkcijos grįžti iš pertraukimo",
            "Nėra laisvos atminties",
            "Komandos INC arba DEC registrai nėra nei TI, nei SP ar IP",
            "Neteisingas adresas",
            "Netelpa duomenys į registro rėžius",
            "Registras neegzistuoja INC arba DEC komandai",
            "Kanalas neegzistuoja",
            "Neteisinga registro reikšmė",
            "Nepavyko atstatyti VM būsenos po komandos IRET"
    };
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
    //Prieš pertraukimo apdorojimą viskas sudėta buvo tokia tvarka PTR,IP, R, P, RS, SS, SP, ERR-negrazinamas, (CF, SF, ZF);
    public void iret(){
        //FLAGAI
        String whole = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        int cf = Integer.parseInt(whole.substring(5,6));
        int sf = Integer.parseInt(whole.substring(6,7));
        int zf = Integer.parseInt(whole.substring(7,8));
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        setCF(cf);
        setSF(sf);
        setZF(zf);
        DEC("SP");
        //ERR
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        DEC("SP");
        //SP
        String last_sp = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        DEC("SP");
        //SS
        String last_ss = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        DEC("SP");
        //RS
        String i = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP())).trim();
        setRS(i);
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        DEC("SP");
        //P
        i = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP())).trim();
        setP(i.trim());
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        DEC("SP");
        //R
        i = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP())).trim();
        setR(i.trim());
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        DEC("SP");
        //IP
        i = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP())).trim();
        setIP(i.trim());
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        DEC("SP");
        //PTR
        i = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP())).trim();
        setPTR(i.trim());
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        DEC("SP");
        setSP(last_sp.trim());
        setSS(last_ss.trim());
        setERR("00");
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
        System.out.println(getPTR());
        int ptr =  Integer.parseInt(getPTR())%100;
        int adress = ptr*10+which_block;
        m.setArrayWord(String.valueOf(block), adress);
    }

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
    public void writeTI(int adress){
        if(checkAdress(adress)==1) {
            m.setArrayWord(getTI(), adress);
        }else{
            setERR("3");
        }
        INC("IP");
    }
    public void writePTR(int adress){
        if(checkAdress(adress)==1) {
            m.setArrayWord(getPTR(), adress);
            INC("IP");
        }else{
            setERR("3");
        }
        INC("IP");
    }

    public void printERR(){
        System.out.println(getERR()+" "+errorsVM[Integer.parseInt(getERR())]);
        INC("IP");
    }
    public void printRE(){
        System.out.println(getRE()+" "+errorsVM[Integer.parseInt(getRE())]);
        INC("IP");
    }
    //kanalų valdymas
}
