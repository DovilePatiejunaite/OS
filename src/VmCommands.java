import java.util.*;
public class VmCommands extends Registers{
    VmCommands(Memory m){
        super(m);
        System.out.println("VM KONSTR");
    }
    //VM pradiniai cs=0;ip=0,SS=90, SP=0
    //cs nekeičiamas
    //IP+CS!!!
    //SS+SP!!!
    //funkcija tikrinanti ar neperžiangiami registrų rėžiai ir nereikia atsakymo perkelinėti į papildomą registrą P
    private void overflow(int last) {
        if(last>99999999){
            String str = String.valueOf(last);
            String first = str.substring(0,4);
            String second = str.substring(4,str.length());
            setR(first);
            setP(second);
            setCF(1);
        } else {
            setR(String.valueOf(last));
            setCF(0);
        }
        timer();
    }

    //aritmetinės komandos
    //imamas dabartinis CS, nes beveik visos komandos gali būti naudojamos ir VM ir RM, kuriose CS registras skiriasi.
    //ADD 1234
    public void add(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            String i = m.getFromArray(adress);
            int value = Integer.parseInt(i.trim());
            int last = Integer.parseInt(getR())+value;
            overflow(last);
            if(Integer.parseInt(getR()) == 0) {
                setZF(1);
            } else setZF(0);
            //if(Integer.toBinaryString(last).substring(0,1).equals("1")){
            setSF(0);
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //SUB 1234
    public void sub(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            String i = m.getFromArray(adress);
            int value = Integer.parseInt(i.trim());
            int last = Integer.parseInt(getR())-value;
            setR(String.valueOf(last));
            if(Integer.parseInt(getR()) == 0){
                setZF(1);
            } else setZF(0);
            if(last<0){
                setSF(1);
            } else setSF(0);;
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //MUL 1234
    public void mul( int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            String i = m.getFromArray(adress);
            int value = Integer.parseInt(i.trim());
            int last = Integer.parseInt(getR())*value;
            overflow(last);
            if(Integer.parseInt(getR()) == 0){
                setZF(1);
            } else setZF(0);
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //DIV 1234
    public void div(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            String i = m.getFromArray(adress);
            int value = Integer.parseInt(i.trim());
            if (value == 0) {
                if(getMODE()==1) {
                    setRE("11");
                }else{
                    setERR("2");
                }
            }else{
                int last = Integer.parseInt(getR())/value;
                setR(String.valueOf(last));
                if(Integer.parseInt(getR()) == 0){
                    setZF(1);
                } else setZF(0);
            }
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //MOD 1234
    public void mod(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            String i = m.getFromArray(adress);
            int value = Integer.parseInt(i.trim());
            int last = Integer.parseInt(getR())%value;
            setR(String.valueOf(last));
            if(Integer.parseInt(getR()) == 0){
                setZF(1);
            } else setZF(0);
            //set carry flag
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //AND 1234
    //loginės komandos
    public void and(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            String i = m.getFromArray(adress);
            int value = Integer.parseInt(i.trim());
            int last = Integer.parseInt(getR())&value;
            setR(String.valueOf(last));
            if(Integer.parseInt(getR()) == 0){
                setZF(1);
            } else setZF(0);
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }

    //OR  1234
    public void or(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            String i = m.getFromArray(adress);
            int value = Integer.parseInt(i.trim());
            int last = Integer.parseInt(getR())|value;
            setR(String.valueOf(last));
            if(Integer.parseInt(getR()) == 0){
                setZF(1);
            } else setZF(0);
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //XOR 1234
    public void xor( int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            String i = m.getFromArray(adress);
            int value = Integer.parseInt(i.trim());
            int last = Integer.parseInt(getR())^value;
            setR(String.valueOf(last));
            if(Integer.parseInt(getR()) == 0){
                setZF(1);
            } else setZF(0);
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //NOT 1234
    public void not(){
        int last = ~Integer.parseInt(getR());
        setR(String.valueOf(last));
        if(Integer.parseInt(getR()) == 0){
            setZF(1);
        } else setZF(0);
        timer();
        INC("IP");
    }
    //LOAD1234
    //Duomenims apdoroti skirtos komandos
    public void load(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            String i = m.getFromArray(adress);
            setR(i.trim());
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //STOR1234
    public void store(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            m.setArrayWord(getR(),adress);
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //STOS1234
    public void storeString(Memory m, int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            m.setArrayWord(getRS(), adress);
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //LODS1234
    public void loadString( int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            String i = m.getFromArray(adress);
            setRS(i);
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //LODJ1234
    //desinej 4 baitai
    public void loadJ(int bytes) {
        String s = getR();
        String first = s.substring(0,4);
        String second = String.valueOf(bytes);
        setR(first.concat(second));
        INC("IP");
        timer();
    }
    //kairej
    //LODL1234
    public void loadS(int bytes) {
        String s = getR();
        String first = String.valueOf(bytes);
        String second = s.substring(4,s.length());
        setR(first.concat(second));
        INC("IP");
        timer();
    }
    //desinej 4 baitai
    //LOSR1234
    public void loadStringJ(String bytes) {
        String s = getRS();
        String first = s.substring(0,4);
        setRS(first.concat(bytes));
        INC("IP");
        timer();
    }
    //kairej
    //LOSL1234
    public void loadStringS(String bytes) {
        String s = getRS();
        String second = s.substring(4,s.length());
        setRS(bytes.concat(second));
        INC("IP");
        timer();

    }
    //WRTP1234
    public void writeP(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            m.setArrayWord(getP(), adress);
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //WRIP1234
    public void writeIP(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            m.setArrayWord(getIP(), adress);
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //WRSP1234
    public void writeSP(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            m.setArrayWord(getSP(), adress);
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //WRSS1234
    public void writeSS(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            m.setArrayWord(getSS(), adress);
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }

    //Palyginimo komandos
    //CPR 1234
    public void cpr(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            String i = m.getFromArray(adress);
            int value = Integer.parseInt(i.trim());
            int last = Integer.parseInt(getR()) - value;
            if (last == 0) {
                setZF(1);
            } else setZF(0);
            if (last < 0) {
                setSF(1);
            } else setSF(0);
            //jei ZF = 1, tai reikšmės lygios. Jei SF = 1, tai didesnis adresas, jei SF = 0, didesnis registras.
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //CPS 1234
    public void cps(int adress){
        needMemory(adress);
        adress= checkMode(adress);
        if(checkAdress(adress)==1){
             String i = m.getFromArray(adress);
            //String i = m.getFromArray(adress);
            if(i.equals(getRS())){
                setZF(1);
            } else setZF(0);
            //Palygina dvi eilutes, jei lygios ZF=1.
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //Įvedimo/išvedimo komandos.
    //PRIN
    public void print(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            setR(String.valueOf(adress));
            setIR(4);
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //WRIT
    public void write(int adress) {
        needMemory(adress);
        adress = checkMode(adress);
        if(checkAdress(adress)==1) {
            setR(String.valueOf(adress));
            setIR(3);
        }else{
            if(getMODE()==1) {
                setRE("3");
            }else{
                setERR("3");
            }
        }
        INC("IP");
        timer();
    }
    //GO  1234
    //Valdymo perdavimo komandos
    public void go(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        setIP(String.valueOf(adress));
        timer();
    }
    //HALT
    //vartotojo programos vykdymo pabaiga
    public void halt(){
        setIR(1);
        timer();
    }
    //JE  1234
    //Sąlyginio valdymo perdavimo komandos
    public void je(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(getZF() == 1){
            setIP(String.valueOf(adress));
        }
        timer();
    }
    //JN  1234
    public void jn(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(getZF() == 0){
            setIP(String.valueOf(adress));
        }
        timer();
    }
    //JL 1234
    public void jl(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(getSF() == 1){
            setIP(String.valueOf(adress));
        }
        timer();
    }
    //JG  1234
    public void jg(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(getSF() == 0){
            setIP(String.valueOf(adress));
        }
        timer();
    }
    //JO  1234
    public void jo(int adress){
        needMemory(adress);
        adress = checkMode(adress);
        if(getCF() == 1){
            setIP(String.valueOf(adress));
        }
        timer();
    }
    //PUSH
    //Stekas
    public void push(){
        int adress = checkMode(Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        m.setArrayWord(getR(),adress);
        INC("SP");
        INC("IP");
        timer();
    }
    //PUSS
    public void pushs(){
        int adress = checkMode(Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        m.setArrayWord(getRS(),adress);
        INC("SP");
        INC("IP");
        timer();
    }
    //POP
    public void pop(){
        if(Integer.parseInt(getSP()) == 0){
            if(getMODE()==1) {
                setRE("13");
            }else{
                setERR("5");
            }
        }else {
            String i = m.getFromArray(Integer.parseInt(getSS()) + Integer.parseInt(getSP()));
            setR(i.trim());
            m.setArrayWord("", Integer.parseInt(getSS()) + Integer.parseInt(getSP()));
            DEC("SP");
            INC("IP");
            timer();
        }
    }
    //POPS
    public void pops(){
        if(Integer.parseInt(getSP()) == 0){
            if(getMODE()==1) {
                setRE("13");
            }else{
                setERR("5");
            }
        }else {
            String s = m.getFromArray(Integer.parseInt(getSS()) + Integer.parseInt(getSP()));
            setRS(s);
            m.setArrayWord("", Integer.parseInt(getSS()) + Integer.parseInt(getSP()));
            DEC("SP");
            INC("IP");
            timer();
        }
    }
    //CLR
    //PAKEISI KAIP REIKIA VM KAD VEIKTU IR REALIAI!!
    public void clears(){
        for(int i=700;i<800;i++){
            m.setArrayWord("        ", i);
        }
        setSP("0000");
        INC("IP");
        timer();
    }
    //PUSM1234
    public void pushm(int adress){
        String s = m.getFromArray(adress);
        m.setArrayWord(s,Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        INC("SP");
        INC("IP");
        timer();
    }
    //POPM1234
    public void popm(int adress){
        if(Integer.parseInt(getSP()) == 0){
            if(getMODE()==1) {
                setRE("13");
            }else{
                setERR("5");
            }
        }else {
            String s = m.getFromArray(Integer.parseInt(getSS()) + Integer.parseInt(getSP()));
            m.setArrayWord(s, adress);
            m.setArrayWord("", Integer.parseInt(getSS()) + Integer.parseInt(getSP()));
            DEC("SP");
            INC("IP");
            timer();
        }
    }
    //PUSF
    //supushina flagu registrus i eilę.
    public void pushf(){
        String whole = String.valueOf(getCF()).concat(String.valueOf(getSF()));
        String last = whole.concat(String.valueOf(getZF()));
        m.setArrayWord(last,Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        INC("SP");
        INC("IP");
        timer();
    }
    //POPF
    public void popf(){
        if(Integer.parseInt(getSP()) == 0){
            if(getMODE()==1) {
                setRE("13");
            }else{
                setERR("5");
            }
        }else {
            String whole = m.getFromArray(Integer.parseInt(getSS()) + Integer.parseInt(getSP()));
            int cf = Integer.parseInt(whole.substring(5, 6));
            int sf = Integer.parseInt(whole.substring(6, 7));
            int zf = Integer.parseInt(whole.substring(7, 8));
            m.setArrayWord("", Integer.parseInt(getSS()) + Integer.parseInt(getSP()));
            setCF(cf);
            setSF(sf);
            setZF(zf);
        }
        DEC("SP");
        INC("IP");
        timer();
    }
    //HDD
    //REHA1234
    public void readhard(String adress){
        setR(adress);
        setIR(6);
        INC("IP");
        timer();
    }
    //WRHA1234
    public void writehard(){
        setIR(5);
        INC("IP");
        timer();
    }

    private void setmSS(String adress){
        setSS(adress);
        INC("IP");
        timer();
    }

    private void setmSP(String adress){
        setSP(adress);
        INC("IP");
        timer();
    }

    //taimerio paleidimas ar sustabdymas priklausomai nuo mode.esant timeriui 0 user mode nustatomas pertraukimas
    private void timer(){
        if(getMODE()==0){
            setTI(String.valueOf(Integer.parseInt(getTI())-1));
            if(Integer.parseInt(getTI())==0){
                setIR(2);
            }
        }
    }
    //Funkcija, kuri patikrina pagal mode ar geras adresas
    public int checkAdress(int adress){
        int availability = 0;
        if(getMODE()==1){
            if(adress > 60 && adress < 700) {
                availability = 1;
            }else{
                availability = 0;
            }
        }else if(getMODE()==0){
            if((adress >= 0) && (adress <= 100)){
                availability = 1;
            }
            else{
                availability = 0;
            }
        }else{
            System.out.println("Neatpažinta klaida");
        }
        return availability;
    }
    public void commandToM(String command){
        int adress;
        if(getMODE()==0){
            adress = m.realWordAdress(m.getArray(),Integer.parseInt(getPTR()),Integer.parseInt(getIP()));
        } else {
            adress = Integer.parseInt(getCS())+Integer.parseInt(getIP());
        }
        m.setArrayWord(command,adress);
    }

    public int checkMode(int adr){
        int adress = adr;
        if(getMODE()==0){
            adress = m.realWordAdress(m.getArray(),Integer.parseInt(getPTR()),adr);
        }
        return adress;
    }
    public void needMemory(int adress){
        if(getMODE()==0){
            int ad = Integer.parseInt(getPTR())%100;
            if(m.getFromArray(ad*10+Integer.parseInt(getIP())/10).trim().equals("-"));
            {
                moreMemoryForVM(adress);
            }
        }
    }
    //issikiriam vietos is vm->rm kur bus irasoma
// IP yra virtualios mašinos (CS=0, adreso nekeičia)
    //nėra komanda!! nedidina IP ir yra puslapiavimo dalis.
    public void moreMemoryForVM(int ip){
        int block =  m.findFreeSpace(m.getArray(),60,700);
        int which_block = ip/10;
        int ptr =  Integer.parseInt(getPTR())%100;
        int adress = ptr*10+which_block;
        m.setArrayWord(String.valueOf(block), adress);
    }
}
