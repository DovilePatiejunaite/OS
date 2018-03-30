import com.sun.deploy.util.StringUtils;

public class Registers {
    //Registrai inicializuojami rm pradiniais registrais.
    private int  IR, CHST1, CHST2, CHST3, MODE, CF, ZF,SF;
    private String RS = "        ";
    private String PTR = "0000";
    private String R = "00000000";
    private String P = "00000000";
    private String IP = "0000";
    private String CS = "0040";
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
    //registrai kurie string pavidalo laiko vien integerius, kaip patikrinti, kad neatsirastu raides? - idet error
    public void setRMB(String RMB) {
        this.RMB = String.format("%04d",Integer.parseInt(RMB));

    }

    public String getRMB() {
        return RMB;
    }

    public void setRS(String RS) {
        if(RS.length()>8){
            System.out.println("Registro RS reikšmė netelpa į rėžius!");
        } else {
            this.RS = String.format("%4s",RS);
        }
    }

    public void setP(String P) {
        if(P.length()>8){
            System.out.println("Registro P reikšmė netelpa į rėžius!");
        } else {
            this.P = String.format("%08d",Integer.parseInt(P));
        }
    }

    public void setCF(int CF) {
        if(CF>9||(CF!=0&&CF!=1)){
            System.out.println("Netinkama registro CF reikšmė!");
        } else {
            this.CF = CF;
        }
    }

    public void setZF(int ZF) {
        if(ZF>9||(ZF!=0&&ZF!=1)){
            System.out.println("Netinkama registro ZF reikšmė!");
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
        if(CS.length()>4){
            System.out.println("Registro CS reikšmė netelpa į rėžius!");
        } else {
            this.CS = String.format("%04d",Integer.parseInt(CS));
        }
    }
    public void setIP(String IP) {
        if(IP.length()>4){
            System.out.println("Registro IP reikšmė netelpa į rėžius!");
        } else {
            this.IP = String.format("%04d",Integer.parseInt(IP));
        }
    }
    public void setPTR(String PTR) {
        if(PTR.length()>4){
            System.out.println("Registro PTR reikšmė netelpa į rėžius!");
        } else {
            this.PTR = String.format("%04d",Integer.parseInt(PTR));
        }
    }
    public void setR(String R) {
        if(R.length()>8){
            System.out.println("Registro R reikšmė netelpa į rėžius!");
        } else {
            this.R = String.format("%08d",Integer.parseInt(R));
        }
    }

    public void setSS(String SS) {
        if(SS.length()>4){
            System.out.println("Registro SS reikšmė netelpa į rėžius!");
        } else {
            this.SS = String.format("%04d",Integer.parseInt(SS));
        }
    }
    public void setSP(String SP) {
        if(SP.length()>4){
            System.out.println("Registro SP reikšmė netelpa į rėžius!");
        } else {
            this.SP = String.format("%04d",Integer.parseInt(SP));
        }
    }

    //
    //INTERUPTO APDOROJIMAS, nusistacius IR registrui!
    //
    public void setIR(int IR) {
        if(IR>99){
            System.out.println("Registro IR reikšmė netelpa į rėžius!");
        } else {
            this.IR = IR;
        }
        setMODE(1);
        Interrupts interrupt = new Interrupts(m,IR);
    }
    public void setTI(String TI) {
        if(TI.length()>2){
            System.out.println("Registro TI reikšmė netelpa į rėžius!");
        } else {
            this.TI = String.format("%02d",Integer.parseInt(TI));
        }
        //pertraukimas jei TI ==0??
    }
    public void setRE(String RE) {
        if(RE.length()>2){
            System.out.println("Registro RE reikšmė netelpa į rėžius!");
        } else {
            this.RE = String.format("%02d",Integer.parseInt(RE));
        }
    }
    public void setERR(String ERR) {
        if(ERR.length()>2){
            System.out.println("Registro ERR reikšmė netelpa į rėžius!");
        } else {
            this.ERR = String.format("%02d",Integer.parseInt(ERR));
        }
    }
    public void setSF(int SF) {
        if(SF>9||(SF!=0&&SF!=1)){
            System.out.println("Netinkama registro SF reikšmė!");
        } else {
            this.SF = SF;
        }
    }
    public void setCHST1(int CHST1) {
        if(CHST1>9||(CHST1!=0&&CHST1!=1)){
            System.out.println("Netinkama registro CHST1 reikšmė!");
        } else {
            this.CHST1 = CHST1;
        }
    }
    public void setCHST2(int CHST2) {
        if(CHST2>9||(CHST2!=0&&CHST2!=1)){
            System.out.println("Netinkama registro CHST2 reikšmė!");
        } else {
            this.CHST2 = CHST2;
        }
    }
    public void setCHST3(int CHST3) {
        if(CHST3>9||(CHST3!=0&&CHST3!=1)){
            System.out.println("Netinkama registro CHST3 reikšmė!");
        } else {
            this.CHST3 = CHST3;
        }
    }
    public void setMODE(int MODE) {
        if(MODE>9||(MODE!=0&&MODE!=1)){
            System.out.println("Netinkama registro MODE reikšmė!");
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
            System.out.println("Neegzistuoja toks kanalas!");
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
            System.out.println("Neegzistuoja toks kanalas!");
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
            System.out.println("Neegzistuoja tos registras!");
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
            System.out.println("Neegzistuoja tos registras!");
        }
    }
}
