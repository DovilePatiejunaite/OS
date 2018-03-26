
import java.util.*;
import java.io.*;
public class VmCommands extends Registers{
    //konstruktorius

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
    }

    //aritmetinės komandos
    public void add(Memory m, int adress){
        String i = m.getFromArray(adress);
        int value = Integer.parseInt(i);
        int last = getR()+value;
        overflow(last);
        if(getR() == 0) {
            setZF(1);
        } else setZF(0);
    //    if(Integer.toBinaryString(last).substring(0,1).equals("1")){
        setSF(0);
        //set carry flag
        INC("IP");
    }
    public void sub(Memory m, int adress){
        String i = m.getFromArray(adress);
        int value = Integer.parseInt(i);
        int last = getR()-value;
        setR(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
   //     if(Integer.toBinaryString(last).substring(0,1).equals("1")){
        if(last<0){
            setSF(1);
        } else setSF(0);
        INC("IP");
    }
    public void mul(Memory m, int adress){
        String i = m.getFromArray(adress);
        int value = Integer.parseInt(i);
        int last = getR()*value;
        overflow(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
        INC("IP");
    }
    public void div(Memory m, int adress){
        String i = m.getFromArray(adress);
        int value = Integer.parseInt(i);
        int last = getR()/value;
        setR(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
        INC("IP");
    }
    public void mod(Memory m, int adress){
        String i = m.getFromArray(adress);
        int value = Integer.parseInt(i);
        int last = getR()%value;
        setR(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
        //set carry flag
        INC("IP");
    }
    //loginės komandos
    public void and(Memory m, int adress){
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
    }
    public void or(Memory m, int adress){
        String i = m.getFromArray(adress);
        int value = Integer.parseInt(i);
        int last = getR()|value;
        setR(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
        INC("IP");
    }
    public void xor(Memory m, int adress){
        String i = m.getFromArray(adress);
        int value = Integer.parseInt(i);
        int last = getR()^value;
        setR(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
        INC("IP");
    }
    public void not(){
        int last = ~getR();
        setR(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
        INC("IP");
    }
    //Duomenims apdoroti skirtos komandos
    public void load(Memory m, int adress){
        String i = m.getFromArray(adress);
        int value = Integer.parseInt(i);
        setR(value);
        INC("IP");
    }
    public void store(Memory m, int adress){
        String str = String.valueOf(getR());
        m.setArrayWord(str,adress);
        INC("IP");
    }
    public void storeString(Memory m, int adress){
        m.setArrayWord(getRS(),adress);
        INC("IP");
    }
    public void loadString(Memory m, int adress){
        String i = m.getFromArray(adress);
        setRS(i);
        INC("IP");
    }
    //desinej 4 baitai
    public void loadJ(int bytes) {
        String s = String.format("%08d", getR());
        String first = s.substring(0,4);
        String second = String.valueOf(bytes);
        setR(Integer.parseInt(first.concat(second)));
        INC("IP");
    }
    //kairej
    public void loadS(int bytes) {
        String s = String.format("%08d", getR());
        String first = String.valueOf(bytes);
        String second = s.substring(4,s.length());
        setR(Integer.parseInt(first.concat(second)));
        INC("IP");
    }
    //desinej 4 baitai
    public void loadStringJ(String bytes) {
        String s = String.format("%8s", getRS());
        String first = s.substring(0,4);
        setRS(first.concat(bytes));
        INC("IP");
    }
    //kairej
    public void loadStringS(String bytes) {
        String s = getRS();
        String second = s.substring(4,s.length());
        setRS(bytes.concat(second));
        INC("IP");
    }
    //Palyginimo komandos
    public void cpr(Memory m, int adress){
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
    }
    public void cps(Memory m, int adress){
        String i = m.getFromArray(adress);
        if(i.equals(getRS())){
            setZF(1);
        } else setZF(0);
        //Palygina dvi eilutes, jei lygios ZF=1.
        INC("IP");
    }
    //Įvedimo/išvedimo komandos.
    public void print(){
        INC("IP");
        setIR(4);
    }
    public void write() {
        INC("IP");
        setIR(3);

    }
    //Valdymo perdavimo komandos
    public void go(int adress){
        setIP(adress);
    }
    //vartotojo programos vykdymo programa
    public void halt(String command){
        //sukelia pertraukima 1
        //grazina i realia masina kaip buvo pries VM isijungiant
    }

    //Sąlyginio valdymo perdavimo komandos
    public void je(int adress){
        if(getZF() == 1){
            setIP(adress);
        }
    }
    public void jn(int adress){
        if(getZF() == 0){
            setIP(adress);
        }
    }
    public void jl(int adress){
        if(getSF() == 1){
            setIP(adress);
        }
    }
    public void jg(int adress){
        if(getSF() == 0){
            setIP(adress);
        }
    }
    public void jo(int adress){
        if(getCF() == 1){
            setIP(adress);
        }
    }
    //Steko valdymo komandos
    //flagai??
    public void push(Memory memory){
        String s = String.valueOf(getR());
        memory.setArrayWord(s,getSS()+getSP());
        DEC("SP");
        INC("IP");
    }

    public void pushs(Memory memory){
        memory.setArrayWord(getRS(),getSS()+getSP());
        DEC("SP");
        INC("IP");
    }
    public void pop(Memory memory){
        int i = Integer.parseInt(memory.getFromArray(getSS()+getSP()));
        setR(i);
        INC("SP");
        INC("IP");
    }
    public void pops(Memory memory){
        String s = memory.getFromArray(getSS()+getSP());
        setRS(s);
        INC("SP");
        INC("IP");
    }
    public void clears(Memory memory){
        for(int i=700;i<800;i++){
            memory.setArrayWord("        ", i);
        }
        setSP(0000);
        INC("IP");
    }
    public void pushm(Memory memory, int adress){
        String m = memory.getFromArray(adress);
        memory.setArrayWord(m,getSP()+getSS());
        DEC("SP");
        INC("IP");
    }
    public void popm(Memory memory, int adress){
        String s = memory.getFromArray(getSS()+getSP());
        memory.setArrayWord(s,adress);
        INC("SP");
        INC("IP");
    }

    //HDD
    public void readhard(){
        INC("IP");
        setIR(6);
    }
    public void writehard(){
        INC("IP");
        setIR(5);
    }
}
