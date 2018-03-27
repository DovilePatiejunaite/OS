
import java.util.*;
import java.io.*;
public class VmCommands extends Registers{
    VmCommands(Memory m){
        super(m);
    }
    //konstruktorius
    //IP+CS!!!
    //SS+SP!!!
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
        String i = m.getFromArray(adress);
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
        String i = m.getFromArray(adress);
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
        String i = m.getFromArray(adress);
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
        String i = m.getFromArray(adress);
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
        String i = m.getFromArray(adress);
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
        String i = m.getFromArray(adress);
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
        String i = m.getFromArray(adress);
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
        String i = m.getFromArray(adress);
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
        String i = m.getFromArray(adress);
        int value = Integer.parseInt(i);
        setR(value);
        INC("IP");
        timer();
    }
    public void store(int adress){
        String str = String.valueOf(getR());
        m.setArrayWord(str,adress);
        INC("IP");
        timer();
    }
    public void storeString(Memory m, int adress){
        m.setArrayWord(getRS(),adress);
        INC("IP");
        timer();
    }
    public void loadString( int adress){
        String i = m.getFromArray(adress);
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
        String i = m.getFromArray(adress);
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
        String i = m.getFromArray(adress);
        if(i.equals(getRS())){
            setZF(1);
        } else setZF(0);
        //Palygina dvi eilutes, jei lygios ZF=1.
        INC("IP");
        timer();
    }
    //Įvedimo/išvedimo komandos.
    public void print(){
        setIR(4);
        INC("IP");
        timer();
    }
    public void write() {
        setIR(3);
        INC("IP");
        timer();
    }
    //Valdymo perdavimo komandos
    public void go(int adress){
        setIP(adress);
        timer();
    }
    //vartotojo programos vykdymo programa
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
    //Steko valdymo komandos
    //flagai??
    public void push(){
        String s = String.valueOf(getR());
        m.setArrayWord(s,getSS()+getSP());
        DEC("SP");
        INC("IP");
        timer();
    }

    public void pushs(){
        m.setArrayWord(getRS(),getSS()+getSP());
        DEC("SP");
        INC("IP");
        timer();
    }
    public void pop(){
        int i = Integer.parseInt(m.getFromArray(getSS()+getSP()));
        setR(i);
        INC("SP");
        INC("IP");
        timer();
    }
    public void pops(){
        String s = m.getFromArray(getSS()+getSP());
        setRS(s);
        INC("SP");
        INC("IP");
        timer();
    }
    public void clears(){
        for(int i=700;i<800;i++){
            m.setArrayWord("        ", i);
        }
        setSP(0000);
        INC("IP");
        timer();
    }
    public void pushm(int adress){
        String s = m.getFromArray(adress);
        m.setArrayWord(s,getSP()+getSS());
        DEC("SP");
        INC("IP");
        timer();
    }
    public void popm(int adress){
        String s = m.getFromArray(getSS()+getSP());
        m.setArrayWord(s,adress);
        INC("SP");
        INC("IP");
        timer();
    }
    //supushina flagu registrus i steką tokia tvarka: CF,SF,ZF
    public void pushf(){
        m.setArrayWord(String.valueOf(getCF()),getSS()+getSP());
        DEC("SP");
        m.setArrayWord(String.valueOf(getSF()),getSS()+getSP());
        DEC("SP");
        m.setArrayWord(String.valueOf(getZF()),getSS()+getSP());
        DEC("SP");
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
    //taimerio paleidimas ar sustabdymas priklausomai nuo mode.esant timeriui 0 user mode nustatomas pertraukimas
    public void timer(){
        if(getMODE()==0){
            setTI(getTI()-1);
            if(getTI()==0){
                setIR(2);
            }
        }

    }
}
