public class Interrupts extends Registers{
    private int interrupt;
    Interrupts(Memory m, int interrupt){
        super(m);
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

        setInterrupt(interrupt);
        String intadress = m.getFromArray(getInterrupt());
        String ip = intadress.substring(4,8);
        setCS("0040");
        setIP(ip);
        setSS("0700");
    }

    public void setInterrupt(int interrupt) {
        this.interrupt = interrupt;
    }

    public int getInterrupt() {
        return interrupt;
    }
}
