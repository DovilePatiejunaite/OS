import java.util.Scanner;

public class RmCommands extends VmCommands{
    RmCommands(Memory m){
        super(m);
    }

    //Virtualios mašinos klaidų kodai
    private String[] errorsVM = {
            "OK",
            "Netelpa duomenys į registro rėžius",
            "Dalyba iš nulio",
            "Neteisingas adresas",
            "Kanalas neegzistuoja",
            "Vykdyta POP komanda, tačiau stekas tuščias",
            "Programos pradžioje nėra $STR",
            "Neteisinga registro reikšmė",
            "Programos pabaigoje nėra $END",
            "Registre yra ne skaitinė reikšmė",
            "Neteisinga įvestis iš klavietūros",
            "$STR panaudotas daugiau nei 1 kartą",
            "Neteisingi funkcijos parametrai",
            "Bandoma vykdyti komandą be supervizorinių teisių",
            "Neatpažinta komanda"
    };
    //Realios mašinos klaidų kodai
    private String[] errorsRM = {
            "OK",
            "Nėra laisvos atminties",
            "Komandos INC arba DEC registrai nėra nei TI, nei SP ar IP",
            "Neteisingas adresas",
            "Netelpa duomenys į registro rėžius",
            "Registras neegzistuoja INC arba DEC komandai",
            "Kanalas neegzistuoja",
            "Neteisinga registro reikšmė",
            "",
            "Registre yra ne skaitinė reikšmė",
            "Neteisinga įvestis iš klavietūros",
            "Dalyba iš nulio",
            "Neteisingi funkcijos parametrai",
            "Vykdyta POP komanda, tačiau stekas tuščias",
            "Bandoma rašyti, bet kanalas užimtas",
            "Netinkamas duomenų rašyti į atmintį dydis"

    };
    //(nuo 0000 iki 0039 - pertraukimų vektorių lentelė)CS = 0060 SS = 0700 - nekeičiamas.
    //vektorių lentelei pasiekti ir rašyti - atskiros komandos
    //PR  1234
    //chanel 1 - isvestis
    //chanel 2 - ivestis
    //chanel 3 - hardas
    public void prt(int adress) {
        executable();
        if(checkChnannel(1)){
            setRE("15");
        } else {
            setCHST1(1);
            String i = m.getFromArray(adress);
            System.out.println(i);
            INC("IP");
        }
    }
    //WRT 1234
    public void wrt(int adress) {
        executable();
        if(checkChnannel(2)){
            setRE("15");
        } else {
            setCHST2(1);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Įveskite iki 8 simbolių, įvedimui į atmintį:");
            String input = "";
            while (scanner.hasNextLine()) {
                input = scanner.nextLine();
                if (input.length() < 9) {
                    break;
                } else {
                    if (getMODE() == 1) {
                        setRE("10");
                    } else {
                        setERR("10");
                    }
                }
            }
            m.setArrayWord(input, adress);
            INC("IP");
        }

    }
    //setMODE 0 - USER, 1 - SUPERVISOR
    //JEI MODE 0 - VM ISIJUNGIA IR PUSLAPIAVMIMAS VYKDOMAS
    //jei nauja vm masina paleidziama : createNewVirtualMashine - nustatomas PTR registras naujas - tuomet kvieciama setM();
    //jei norima grizti po pertraukimo i tam tikra realia masina visu pirma su komanda IRET yra susigrazinami reikalingi
    //registrai ir tada nustomas setM();
    //cs - nesikeičia visais atvejais
    //SETM
    public void setM(){
        executable();
        setMODE(0);
        setCS("0000");
       //patikriname ar nauja virtuali mašina, o ne backupas iš steko - jei nepaimtas backupas - SS bus vis dar
        //0700.
       if(getSS().equals("0700")){
           setRMB(getSP());
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
           //Inicializuojamas stekas
           needMemory(90);
           for(int i=0;i<10;i++){
               m.setArrayWord("-",m.realWordAdress(m.getArray(),Integer.parseInt(getPTR()),90)+i);
           }
       }

    }
    //INT 1
    public void inter(int number){
        executable();
        setIR(number);
        INC("IP");
    }

    //INC 12
    public void increg(String register){
        executable();
        INC(register);
        INC("IP");
    }
    //DEC 12
    public void decreg(String register){
        executable();
        DEC(register);
        INC("IP");
    }
    //1 bloko skaitymas iš disko. 10 žodžių
    //REDH1234
    public void readh(ExternalMemory em, int adress){
        executable();
        if(checkChnannel(3)){
            setRE("15");
        } else
        {
            setCHST3(1);
            int last = em.getLast_read();
            em.getArray();
            for (int i = last; i < last + 10; i++) {
                String external = em.getFromArray(i);
                if(external.length()>8){
                    setRE("16");
                }else {
                    m.setArrayWord(external, adress);
                    adress++;
                }
            }
            em.setLast_read(em.getLast_read() + 10);
            if(!getRE().equals("16")){
                INC("IP");
            }
        }
    }
    //1 bloko rašymas į diską.
    //WRTH1234
    //NUSETINA P - I KURIA VIETA EXTERNAL MEMORY MES IRASEM
    public void writeh(ExternalMemory em, int adress){
        executable();
        if(checkChnannel(3)){
            setRE("15");
        } else {
            setCHST3(1);
            for (int i = 0; i < 10; i++) {
                em.writeArrayWord(m.getFromArray(adress+i));
            }
            em.getArray();
            int last = em.getLast_write();
            System.out.println("paskutine eilute - "+last);
            setP(String.valueOf(last-10));
            INC("IP");
        }
    }
    //IRET - grįžimo į VM funkcija.
    //Prieš pertraukimo apdorojimą viskas sudėta buvo tokia tvarka PTR,IP, R, P, RS, SS, SP, ERR-negrazinamas, (CF, SF, ZF);
    //IRET
    public void iret(){
        executable();
        //FLAGAI
        DEC("SP");
        String whole = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        int cf = Integer.parseInt(whole.substring(5,6));
        int sf = Integer.parseInt(whole.substring(6,7));
        int zf = Integer.parseInt(whole.substring(7,8));

        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        setCF(cf);
        setSF(sf);
        setZF(zf);
        //ERR
        DEC("SP");
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        //SP
        DEC("SP");
        String last_sp = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));

        //SS
        DEC("SP");
        String last_ss = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        //RS
        DEC("SP");
        String i = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP())).trim();
        setRS(i);
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));

        //P
        DEC("SP");
        i = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP())).trim();
        setP(i.trim());
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));

        //R
        DEC("SP");
        i = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP())).trim();
        setR(i.trim());
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));

        //IP
        DEC("SP");
        i = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP())).trim();
        setIP(i.trim());
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));

        //PTR
        DEC("SP");
        i = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP())).trim();
        setPTR(i.trim());
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        setRMB(getSP());
        setSP(last_sp.trim());
        setSS(last_ss.trim());
        setIR(0);
        setERR("00");
    }
    //Timeris
    //SETT 12
    public void setTimer(String ti){
        executable();
        setTI(ti);
        INC("IP");
    }
    //SCH 1
    public void setChnannel(int r){
        executable();
        SETCH(r);
        INC("IP");
    }
    //CLCH 1
    public void clearChnannel(int r){
        executable();
        CLRCH(r);
        INC("IP");
    }
    public boolean checkChnannel(int r){
        int chanel = 0;
        if(r==1){
            chanel = getCHST1();
        }
        if(r==2){
            chanel = getCHST2();
        }
        if(r==3) {
            chanel = getCHST3();
        }
        if(chanel==1){
            return true;
        } else {
            return false;
        }
    }
    //SPTR1234
    public void ptr(String value){
        executable();
        setPTR(value);
        INC("IP");
    }

    //VMCR
    public void createVirtualMachine(){
        executable();
        int table_block = m.findFreeSpace(m.getArray(),10,60);
        setPTR("10"+String.format("%02d",table_block));
        int block_adress = table_block*10;
        for(int i=0;i<10;i++){
            m.setArrayWord("-",block_adress+i);
        }
        INC("IP");
    }

    //PRMB1234
    public void RMBtoMemory(int adress){
        executable();
        m.setArrayWord(getRMB(), adress);
        INC("IP");
    }
    //MIV|1|234
    public void makeIntreruptVector(int adress, int interrupt){
        executable();
        m.setArrayWord("0060"+String.format("%04d",adress), interrupt);
        INC("IP");
    }
    //LIV 1
    public void loadInterruptVector(int interrupt){
        executable();
        setR(m.getFromArray(interrupt));
        INC("IP");
    }
    //
    //WTI 1234
    public void writeTI(int adress){
        executable();
        if(checkAdress(adress)==1) {
            m.setArrayWord(getTI(), adress);
            INC("IP");
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
    }
    //WPTR1234
    public void writePTR(int adress){
        executable();
        if(checkAdress(adress)==1) {
            m.setArrayWord(getPTR(), adress);
            INC("IP");
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
    }

    //PRER
    public void printERR(){
        executable();
        System.out.println(getERR()+" "+errorsVM[Integer.parseInt(getERR())]);
        INC("IP");
    }
    //PRRE
    public void printRE(){
        executable();
        System.out.println(getRE()+" "+errorsRM[Integer.parseInt(getRE())]);
        INC("IP");
    }
    public void executable(){
        if(getMODE()==1){
        } else {
            setERR("14");
            DEC("IP");
        }

    }
    //kanalų valdymas
}
