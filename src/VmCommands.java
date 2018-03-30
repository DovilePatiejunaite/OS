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
    public void add(int adress){
        String i = m.getFromArray(adress+Integer.parseInt(getCS()));
        int value = Integer.parseInt(i.trim());
        int last = Integer.parseInt(getR())+value;
        overflow(last);
        if(Integer.parseInt(getR()) == 0) {
            setZF(1);
        } else setZF(0);
    //    if(Integer.toBinaryString(last).substring(0,1).equals("1")){
        setSF(0);
        INC("IP");
        timer();
    }
    public void sub(int adress){
        String i = m.getFromArray(adress+Integer.parseInt(getCS()));
        int value = Integer.parseInt(i.trim());
        int last = Integer.parseInt(getR())-value;
        setR(String.valueOf(last));
        if(Integer.parseInt(getR()) == 0){
            setZF(1);
        } else setZF(0);
        if(last<0){
            setSF(1);
        } else setSF(0);
        INC("IP");
        timer();
    }
    public void mul( int adress){
        String i = m.getFromArray(Integer.parseInt(getCS())+adress);
        int value = Integer.parseInt(i.trim());
        int last = Integer.parseInt(getR())*value;
        overflow(last);
        if(Integer.parseInt(getR()) == 0){
            setZF(1);
        } else setZF(0);
        INC("IP");
        timer();
    }
    public void div(int adress){
        String i = m.getFromArray(adress+Integer.parseInt(getCS()));
        int value = Integer.parseInt(i.trim());
        int last = Integer.parseInt(getR())/value;
        setR(String.valueOf(last));
        if(Integer.parseInt(getR()) == 0){
            setZF(1);
        } else setZF(0);
        INC("IP");
        timer();
    }
    public void mod(int adress){
        String i = m.getFromArray(adress+Integer.parseInt(getCS()));
        int value = Integer.parseInt(i.trim());
        int last = Integer.parseInt(getR())%value;
        setR(String.valueOf(last));
        if(Integer.parseInt(getR()) == 0){
            setZF(1);
        } else setZF(0);
        //set carry flag
        INC("IP");
        timer();
    }
    //loginės komandos
    public void and(int adress){
        String i = m.getFromArray(adress+Integer.parseInt(getCS()));
        int value = Integer.parseInt(i.trim());
        int last = Integer.parseInt(getR())&value;
        setR(String.valueOf(last));
        if(Integer.parseInt(getR()) == 0){
            setZF(1);
        } else setZF(0);
        INC("IP");
        timer();
    }
    public void or(int adress){
        String i = m.getFromArray(adress+Integer.parseInt(getCS()));
        int value = Integer.parseInt(i.trim());
        int last = Integer.parseInt(getR())|value;
        setR(String.valueOf(last));
        if(Integer.parseInt(getR()) == 0){
            setZF(1);
        } else setZF(0);
        INC("IP");
        timer();
    }
    public void xor( int adress){
        String i = m.getFromArray(adress+Integer.parseInt(getCS()));
        int value = Integer.parseInt(i.trim());
        int last = Integer.parseInt(getR())^value;
        setR(String.valueOf(last));
        if(Integer.parseInt(getR()) == 0){
            setZF(1);
        } else setZF(0);
        INC("IP");
        timer();
    }
    public void not(){
        int last = ~Integer.parseInt(getR());
        setR(String.valueOf(last));
        if(Integer.parseInt(getR()) == 0){
            setZF(1);
        } else setZF(0);
        timer();
        INC("IP");
    }
    //Duomenims apdoroti skirtos komandos
    public void load(int adress){
        String i = m.getFromArray(adress+Integer.parseInt(getCS()));
        setR(i.trim());
        INC("IP");
        timer();
    }
    public void store(int adress){
        m.setArrayWord(getR(),adress);
        INC("IP");
        timer();
    }
    public void storeString(Memory m, int adress){
        m.setArrayWord(getRS(),adress+Integer.parseInt(getCS()));
        INC("IP");
        timer();
    }
    public void loadString( int adress){
        String i = m.getFromArray(adress+Integer.parseInt(getCS()));
        setRS(i);
        INC("IP");
        timer();
    }
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
    public void loadS(int bytes) {
        String s = getR();
        String first = String.valueOf(bytes);
        String second = s.substring(4,s.length());
        setR(first.concat(second));
        INC("IP");
        timer();
    }
    //desinej 4 baitai
    public void loadStringJ(String bytes) {
        String s = getRS();
        String first = s.substring(0,4);
        setRS(first.concat(bytes));
        INC("IP");
        timer();
    }
    //kairej
    public void loadStringS(String bytes) {
        String s = getRS();
        String second = s.substring(4,s.length());
        setRS(bytes.concat(second));
        INC("IP");
        timer();
    }
    //Palyginimo komandos
    public void cpr(int adress){
        String i = m.getFromArray(adress+Integer.parseInt(getCS()));
        int value = Integer.parseInt(i.trim());
        int last = Integer.parseInt(getR())-value;
        if(last == 0){
            setZF(1);
        } else setZF(0);
        if(last<0){
            setSF(1);
        } else setSF(0);
        //jei ZF = 1, tai reikšmės lygios. Jei SF = 1, tai didesnis adresas, jei SF = 0, didesnis registras.
        INC("IP");
        timer();
    }
    public void cps(int adress){
        String i = m.getFromArray(adress+Integer.parseInt(getCS()));
        if(i.equals(getRS())){
            setZF(1);
        } else setZF(0);
        //Palygina dvi eilutes, jei lygios ZF=1.
        INC("IP");
        timer();
    }
    //Įvedimo/išvedimo komandos.
    public void print(int adress){
        setR(String.valueOf(adress+Integer.parseInt(getCS())));
        setIR(4);
        INC("IP");
        timer();
    }
    public void write(int adress) {
        setR(String.valueOf(adress+Integer.parseInt(getCS())));
        setIR(3);
        INC("IP");
        timer();
    }
    //Valdymo perdavimo komandos
    public void go(String adress){
        setIP(adress);
        timer();
    }
    //vartotojo programos vykdymo pabaiga
    public void halt(){
        setIR(1);
        timer();
    }

    //Sąlyginio valdymo perdavimo komandos
    public void je(String adress){
        if(getZF() == 1){
            setIP(adress);
        }
        timer();
    }
    public void jn(String adress){
        if(getZF() == 0){
            setIP(adress);
        }
        timer();
    }
    public void jl(String adress){
        if(getSF() == 1){
            setIP(adress);
        }
        timer();
    }
    public void jg(String adress){
        if(getSF() == 0){
            setIP(adress);
        }
        timer();
    }
    public void jo(String adress){
        if(getCF() == 1){
            setIP(adress);
        }
        timer();
    }
    //Eilės duomenų struktūros valdymo komandos. FIFO principas
    public void push(){
        m.setArrayWord(getR(),Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        INC("SP");
        INC("IP");
        timer();
    }

    public void pushs(){
        m.setArrayWord(getRS(),Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        INC("SP");
        INC("IP");
        timer();
    }
    public void pop(){
        String i = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        setR(i.trim());
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        DEC("SP");
        INC("IP");
        timer();
    }
    public void pops(){
        String s = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        setRS(s);
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        DEC("SP");
        INC("IP");
        timer();
    }
    public void clears(){
        for(int i=700;i<800;i++){
            m.setArrayWord("        ", i);
        }
        setSP("0000");
        INC("IP");
        timer();
    }
    public void pushm(int adress){
        String s = m.getFromArray(adress);
        m.setArrayWord(s,Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        INC("SP");
        INC("IP");
        timer();
    }
    public void popm(int adress){
        String s = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        m.setArrayWord(s,adress);
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        DEC("SP");
        INC("IP");
        timer();
    }
    //supushina flagu registrus i eilę.
    public void pushf(){
        String whole = String.valueOf(getCF()).concat(String.valueOf(getSF()));
        String last = whole.concat(String.valueOf(getZF()));
        m.setArrayWord(last,Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        INC("SP");
        INC("IP");
        timer();
    }
    public void popf(){
        String whole = m.getFromArray(Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        int cf = Integer.parseInt(whole.substring(4,5));
        int sf = Integer.parseInt(whole.substring(5,6));
        int zf = Integer.parseInt(whole.substring(6,7));
        m.setArrayWord("",Integer.parseInt(getSS())+Integer.parseInt(getSP()));
        setCF(cf);
        setSF(sf);
        setZF(zf);
        DEC("SP");
        INC("IP");
        timer();
    }
    //HDD
    public void readhard(String adress){
        setR(adress);
        setIR(6);
        INC("IP");
        timer();
    }
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
}
