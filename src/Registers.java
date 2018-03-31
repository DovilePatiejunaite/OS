import java.util.*;
public class Registers {
    //Registrai inicializuojami rm pradiniais registrais.
    private int  IR = 0, CHST1 = 0, CHST2 = 0, CHST3 = 0, MODE = 1, CF = 0, ZF = 0,SF = 0;
    private String RS = "        ";
    private String PTR = "0000";
    private String R = "00000000";
    private String P = "00000000";
    private String IP = "0000";
    private String CS = "0060";
    private String SS = "0700";
    private String SP = "0000";
    private String TI = "00";
    private String ERR = "00";
    private String RE = "00";
    //paskutinėst dierbusios mašinos sp
    private String RMB = "0000";


//SETERIAI
    protected Memory m;

    Registers(Memory m){
        this.m = m;
    }
    //idet patikrinima
    //registrai kurie string pavidalo laiko vien integerius, kaip patikrinti, kad neatsirastu raides? - idet error - DONE
    public void setRMB(String RMB) {
        if(RMB.length()>8 && isNumeric(RMB)!=false){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        }else if(isNumeric(RMB)==false){
            if(getMODE()==1) {
                setRE("9");
            }else{
                setERR("9");
            }
        }else {
            this.RMB = String.format("%04d",Integer.parseInt(RMB));
        }
    }

    public String getRMB() {
        return RMB;
    }

    public void setRS(String RS) {
        if(RS.length()>8){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        } else {
            this.RS = String.format("%4s",RS);
        }
    }

    public void setP(String P) {
        if(P.length()>8){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        } else {
            this.P = String.format("%08d",Integer.parseInt(P));
        }
    }

    public void setCF(int CF) {
        if(CF>9||(CF!=0&&CF!=1)){
            if(getMODE()==1){
                setRE("7");
            }else{
                setERR("7");
            }
        } else {
            this.CF = CF;
        }
    }

    public void setZF(int ZF) {
        if(ZF>9||(ZF!=0&&ZF!=1)){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        } else {
            this.ZF = ZF;
        }
    }

    public int getCF() {
        return CF;
    }

    public int getZF() {
        return ZF;
    }

    public void setCS(String CS) {
        if(CS.length()>4 && isNumeric(CS)!=false){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        }else if(isNumeric(CS)==false){
            if(getMODE()==1) {
                setRE("9");
            }else{
                setERR("9");
            }
        }else {
            this.CS = String.format("%04d",Integer.parseInt(CS));
        }
    }
    public void setIP(String IP) {
        if(IP.length()>4 && isNumeric(IP)!=false){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        }else if(isNumeric(IP)==false){
            if(getMODE()==1) {
                setRE("9");
            }else{
                setERR("9");
            }
        }else {
            this.IP = String.format("%04d",Integer.parseInt(IP));
        }
    }
    public void setPTR(String PTR) {
        if(PTR.length()>4 && isNumeric(PTR)!=false){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        }else if(isNumeric(PTR)==false){
            if(getMODE()==1) {
                setRE("9");
            }else{
                setERR("9");
            }
        }else {
            this.PTR = String.format("%04d",Integer.parseInt(PTR));
        }
    }
    public void setR(String R) {
        if(R.length()>8 && isNumeric(R)!=false){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        }else if(isNumeric(R)==false){
            if(getMODE()==1) {
                setRE("9");
            }else{
                setERR("9");
            }
        }else {
            this.R = String.format("%08d",Integer.parseInt(R));
        }
    }

    public void setSS(String SS) {
        if(SS.length()>4 && isNumeric(SS)!=false){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        }else if(isNumeric(SS)==false){
            if(getMODE()==1) {
                setRE("9");
            }else{
                setERR("9");
            }
        }else {
            this.SS = String.format("%04d",Integer.parseInt(SS));
        }
    }
    public void setSP(String SP) {
        if(SP.length()>4 && isNumeric(SP)!=false){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        }else if(isNumeric(SP)==false){
            if(getMODE()==1) {
                setRE("9");
            }else{
                setERR("9");
            }
        }else {
            this.SP = String.format("%04d",Integer.parseInt(SP));
        }
    }

    //
    //INTERUPTO APDOROJIMAS, nusistacius IR registrui!
    //
    public void setIR(int IR) {
        if(IR>99){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        } else {
            this.IR = IR;
        }
        setMODE(1);
        Interrupt();
    }
    public void setTI(String TI) {
        if(TI.length()>2 && isNumeric(TI)!=false){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        }else if(isNumeric(TI)==false){
            if(getMODE()==1) {
                setRE("9");
            }else{
                setERR("9");
            }
        }else {
            this.TI = String.format("%02d",Integer.parseInt(TI));
        }
        //pertraukimas jei TI ==0??
    }
    public void setRE(String RE) {
        if(RE.length()>2 && isNumeric(RE)!=false){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        }else if(isNumeric(RE)==false){
            if(getMODE()==1) {
                setRE("9");
            }else{
                setERR("9");
            }
        }else {
            this.RE = String.format("%02d",Integer.parseInt(RE));
        }
    }
    public void setERR(String ERR) {
        if(ERR.length()>2 && isNumeric(ERR)!=false){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        }else if(isNumeric(ERR)==false){
            if(getMODE()==1) {
                setRE("9");
            }else{
                setERR("9");
            }
        }else {
            this.ERR = String.format("%02d",Integer.parseInt(ERR));
            if(!this.ERR.equals("00")) {
                setIR(1); // PIRMAS interruptas?
            }
        }
    }
    public void setSF(int SF) {
        if(SF>9||(SF!=0&&SF!=1)){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        } else {
            this.SF = SF;
        }
    }
    public void setCHST1(int CHST1) {
        if(CHST1>9||(CHST1!=0&&CHST1!=1)){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        } else {
            this.CHST1 = CHST1;
        }
    }
    public void setCHST2(int CHST2) {
        if(CHST2>9||(CHST2!=0&&CHST2!=1)){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        } else {
            this.CHST2 = CHST2;
        }
    }
    public void setCHST3(int CHST3) {
        if(CHST3>9||(CHST3!=0&&CHST3!=1)){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        } else {
            this.CHST3 = CHST3;
        }
    }
    public void setMODE(int MODE) {
        if(MODE>9||(MODE!=0&&MODE!=1)){
            if(getMODE()==1) {
                setRE("7");
            }else{
                setERR("7");
            }
        } else {
            this.MODE = MODE;
        }
    }

    //GETERIAI


    public String getRS() {
        return RS;
    }
    public int getCHST1() {
        return CHST1;
    }

    public String getCS() {
        return CS;
    }

    public int getCHST2() {
        return CHST2;
    }

    public String getIP() {
        return IP;
    }

    public int getCHST3() {
        return CHST3;
    }

    public int getIR() {
        return IR;
    }

    public String getPTR() {
        return PTR;
    }

    public int getMODE() {
        return MODE;
    }

    public String getR() {
        return R;
    }

    public String getRE() {
        return RE;
    }


    public String getSP() {
        return SP;
    }

    public String getSS() {
        return SS;
    }

    public String getTI() {
        return TI;
    }

    public String getERR() {
        return ERR;
    }

    public int getSF() {
        return SF;
    }

    public String getP() {
        return P;
    }

    public void SETCH(int register){
        if(register==1){
            setCHST1(1);
        } else if (register==2){
            setCHST2(1);
        } else if (register==3){
            setCHST3(1);
        } else {
            setERR("6");
        }
    }
    public void CLRCH(int register){
        if(register==1){
            setCHST1(0);
        } else if (register==2){
            setCHST2(0);
        } else if (register==3){
            setCHST3(0);
        } else {
            setERR("6");
        }
    }
    public void INC(String register){
        //switch
        if(register.equals("TI")){
            setTI(String.valueOf(Integer.parseInt(getTI())+1));
        } else if(register.equals("SP")){
            setSP(String.valueOf(Integer.parseInt(getSP())+1));
        } else if(register.equals("IP")){
            setIP(String.valueOf(Integer.parseInt(getIP())+1));
        } else {
            setERR("5");
        }
    }
    public void DEC(String register){
        if(register.equals("TI")){
            setTI(String.valueOf(Integer.parseInt(getTI())-1));
        } else if(register.equals("SP")){
            setSP(String.valueOf(Integer.parseInt(getSP())-1));
        } else if(register.equals("IP")){
            setIP(String.valueOf(Integer.parseInt(getIP())-1));
        } else {
            setERR("5");
        }
    }
    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    public String String() {
       return  "MODE: "+MODE
               +"\nPTR: "+PTR+" CS: "+CS+" IP: "+IP+"\nR: "+R+" P: "+P+" RS: "+ RS + " SS: "+ SS+" SP:"+SP
               +"\nTI: "+TI+" IR:"+IR+" RE: "+RE+" ERR: "+ERR+" RMB: "+RMB
               +"\nCHST1: "+ CHST1+" CHST2: "+ CHST2+" CHST13 "+ CHST3
               +"\nCF: "+CF+" SF: "+SF+" ZF: "+ZF+"\n";
    }

    private void Interrupt(){
        String last_sp = getSP();
        setSP(getRMB());
        String last_ss = getSS();
        //VIENAI VIRTUALIAI MASINAI, steko struktūroje(ji prasideda nuo 0700 ir iki 0799)
        //PTR,IP, R, P, RS, SS, SP, ERR, (CF, SF, ZF);
        m.setArrayWord(getPTR(),700+Integer.parseInt(getSP()));
        INC("SP");
        m.setArrayWord(getIP(),700+Integer.parseInt(getSP()));
        INC("SP");
        m.setArrayWord(getR(),700+Integer.parseInt(getSP()));
        INC("SP");
        m.setArrayWord(getP(),700+Integer.parseInt(getSP()));
        INC("SP");
        m.setArrayWord(getRS(),700+Integer.parseInt(getSP()));
        INC("SP");
        m.setArrayWord(last_ss,700+Integer.parseInt(getSP()));
        INC("SP");
        m.setArrayWord(last_sp,700+Integer.parseInt(getSP()));
        INC("SP");
        m.setArrayWord(getERR(),700+Integer.parseInt(getSP()));
        INC("SP");
        String flags = String.valueOf(getCF())+getSF()+getZF();
        m.setArrayWord(flags,700+Integer.parseInt(getSP()));
        INC("SP");
        String intadress = m.getFromArray(getIR());
        String ip = intadress.substring(4,8);
        if(ip.trim().equals("")){
            ip = "0000";
        }
        setCS("0060");
        setIP(ip);
        setSS("0700");
    }

}
