
import java.util.*;
import java.io.*;
public class VmCommands extends Registers{
    VmCommands(Memory m){
        super(m);
        System.out.println("VM KONSTR");
    }
    //konstruktorius
    //IP+CS!!!
    //QS+QP!!!
    //funkcija tikrinanti ar neperžiangiami registrų rėžiai
    private void overflow(int last) {
        if(last>99999999){
            String str = String.valueOf(last);
            String first = str.substring(0,4);
            String second = str.substring(4,str.length());
            setR(Integer.parseInt(first));
            setP(Integer.parseInt(second));
            setCF(1);
        } else {
            setR(last);
            setCF(0);
        }
        timer();
    }

    //aritmetinės komandos
    public void add(int adress){
        String i = m.getFromArray(adress+getCS());
        int value = Integer.parseInt(i);
        int last = getR()+value;
        overflow(last);
        if(getR() == 0) {
            setZF(1);
        } else setZF(0);
    //    if(Integer.toBinaryString(last).substring(0,1).equals("1")){
        setSF(0);
        INC("IP");
        timer();
    }
    public void sub(int adress){
        String i = m.getFromArray(adress+getCS());
        int value = Integer.parseInt(i);
        int last = getR()-value;
        setR(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
        if(last<0){
            setSF(1);
        } else setSF(0);
        INC("IP");
        timer();
    }
    public void mul( int adress){
        String i = m.getFromArray(getCS()+adress);
        int value = Integer.parseInt(i);
        int last = getR()*value;
        overflow(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
        INC("IP");
        timer();
    }
    public void div(int adress){
        String i = m.getFromArray(adress+getCS());
        int value = Integer.parseInt(i);
        int last = getR()/value;
        setR(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
        INC("IP");
        timer();
    }
    public void mod(int adress){
        String i = m.getFromArray(adress+getCS());
        int value = Integer.parseInt(i);
        int last = getR()%value;
        setR(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
        //set carry flag
        INC("IP");
        timer();
    }
    //loginės komandos
    public void and(int adress){
        String i = m.getFromArray(adress+getCS());
        int value = Integer.parseInt(i);
        int last = getR()&value;
        setR(last);
        if(getR() == 0){
            setZF(1);            String str = String.valueOf(last);
            String first = str.substring(0,4);
            String second = str.substring(4,str.length());
            setR(Integer.parseInt(first));
            setP(Integer.parseInt(second));
        } else setZF(0);
        INC("IP");
        timer();
    }
    public void or(int adress){
        String i = m.getFromArray(adress+getCS());
        int value = Integer.parseInt(i);
        int last = getR()|value;
        setR(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
        INC("IP");
        timer();
    }
    public void xor( int adress){
        String i = m.getFromArray(adress+getCS());
        int value = Integer.parseInt(i);
        int last = getR()^value;
        setR(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
        INC("IP");
        timer();
    }
    public void not(){
        int last = ~getR();
        setR(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
        timer();
        INC("IP");
    }
    //Duomenims apdoroti skirtos komandos
    public void load(int adress){
        String i = m.getFromArray(adress+getCS());
        int value = Integer.parseInt(i);
        setR(value);
        INC("IP");
        timer();
    }
    public void store(int adress){
        String str = String.valueOf(getR()+getCS());
        m.setArrayWord(str,adress);
        INC("IP");
        timer();
    }
    public void storeString(Memory m, int adress){
        m.setArrayWord(getRS(),adress+getCS());
        INC("IP");
        timer();
    }
    public void loadString( int adress){
        String i = m.getFromArray(adress+getCS());
        setRS(i);
        INC("IP");
        timer();
    }
    //desinej 4 baitai
    public void loadJ(int bytes) {
        String s = String.format("%08d", getR());
        String first = s.substring(0,4);
        String second = String.valueOf(bytes);
        setR(Integer.parseInt(first.concat(second)));
        INC("IP");
        timer();
    }
    //kairej
    public void loadS(int bytes) {
        String s = String.format("%08d", getR());
        String first = String.valueOf(bytes);
        String second = s.substring(4,s.length());
        setR(Integer.parseInt(first.concat(second)));
        INC("IP");
        timer();
    }
    //desinej 4 baitai
    public void loadStringJ(String bytes) {
        String s = String.format("%8s", getRS());
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
        String i = m.getFromArray(adress+getCS());
        int value = Integer.parseInt(i);
        int last = getR()-value;
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
        String i = m.getFromArray(adress+getCS());
        if(i.equals(getRS())){
            setZF(1);
        } else setZF(0);
        //Palygina dvi eilutes, jei lygios ZF=1.
        INC("IP");
        timer();
    }
    //Įvedimo/išvedimo komandos.
    public void print(int adress){
        setR(adress+getCS());
        setIR(4);
        INC("IP");
        timer();
    }
    public void write(int adress) {
        setR(adress+getCS());
        setIR(3);
        INC("IP");
        timer();
    }
    //Valdymo perdavimo komandos
    public void go(int adress){
        setIP(adress);
        timer();
    }
    //vartotojo programos vykdymo pabaiga
    public void halt(String command){
        setIR(1);
        //sukelia pertraukima 1
        //grazina i realia masina kaip buvo pries VM isijungiant
        timer();
    }

    //Sąlyginio valdymo perdavimo komandos
    public void je(int adress){
        if(getZF() == 1){
            setIP(adress);
        }
        timer();
    }
    public void jn(int adress){
        if(getZF() == 0){
            setIP(adress);
        }
        timer();
    }
    public void jl(int adress){
        if(getSF() == 1){
            setIP(adress);
        }
        timer();
    }
    public void jg(int adress){
        if(getSF() == 0){
            setIP(adress);
        }
        timer();
    }
    public void jo(int adress){
        if(getCF() == 1){
            setIP(adress);
        }
        timer();
    }
    //Eilės duomenų struktūros valdymo komandos. FIFO principas
    public void push(){
        String s = String.valueOf(getR());
        m.setArrayWord(s,getQS()+getQP());
        INC("QP");
        INC("IP");
        timer();
    }

    public void pushs(){
        m.setArrayWord(getRS(),getQS()+getQP());
        INC("QP");
        INC("IP");
        timer();
    }
    public void pop(){
        int i = Integer.parseInt(m.getFromArray(getQS()+getQP()));
        setR(i);
        m.setArrayWord("",getQS()+getQP());
        DEC("QP");
        INC("IP");
        timer();
    }
    public void pops(){
        String s = m.getFromArray(getQS()+getQP());
        setRS(s);
        m.setArrayWord("",getQS()+getQP());
        DEC("QP");
        INC("IP");
        timer();
    }
    public void clears(){
        for(int i=700;i<800;i++){
            m.setArrayWord("        ", i);
        }
        setQP(0000);
        INC("IP");
        timer();
    }
    public void pushm(int adress){
        String s = m.getFromArray(adress);
        m.setArrayWord(s,getQP()+getQS());
        INC("QP");
        INC("IP");
        timer();
    }
    public void popm(int adress){
        String s = m.getFromArray(getQS()+getQP());
        m.setArrayWord(s,adress);
        m.setArrayWord("",getQS()+getQP());
        DEC("QP");
        INC("IP");
        timer();
    }
    //supushina flagu registrus i eilę.
    public void pushf(){
        String whole = String.valueOf(getCF()).concat(String.valueOf(getSF()));
        String last = whole.concat(String.valueOf(getZF()));
        m.setArrayWord(last,getQS()+getQP());
        INC("QP");
        INC("IP");
        timer();
    }
    public void popf(){
        String whole = m.getFromArray(getQS()+getQP());
        int cf = Integer.parseInt(whole.substring(4,5));
        int sf = Integer.parseInt(whole.substring(5,6));
        int zf = Integer.parseInt(whole.substring(6,7));
        m.setArrayWord("",getQS()+getQP());
        setCF(cf);
        setSF(sf);
        setZF(zf);
        DEC("QP");
        INC("IP");
        timer();
    }
    //HDD
    public void readhard(){
        setIR(6);
        INC("IP");
        timer();
    }
    public void writehard(){
        setIR(5);
        INC("IP");
        timer();
    }

    public void setmQS(int adress){
        setQS(adress);
        INC("IP");
    }

    public void setmQP(int adress){
        setQP(adress);
        INC("IP");
    }

    //taimerio paleidimas ar sustabdymas priklausomai nuo mode.esant timeriui 0 user mode nustatomas pertraukimas
    private void timer(){
        if(getMODE()==0){
            setTI(getTI()-1);
            if(getTI()==0){
                setIR(2);
            }
        }
    }
}
