public class Interrupts extends Registers{
    private int interrupt;
    Interrupts(Memory m, int interrupt){
        super(m);
        setInterrupt(interrupt);
        String intadress = m.getFromArray(getInterrupt());
        String cs = intadress.substring(0,3);
        String ip = intadress.substring(3,7);
        setCS(cs);
        setIP(ip);
        int adress = Integer.parseInt(getQS())+Integer.parseInt(getQP());
        //PTR
        //10 ZODZIU VIENAI VIRTUALIAI MASINAI, eilės duomenų struktūroje(ji prasideda nuo 0700 ir iki 0799)
        //PTR,CS, IP, R, P, RS, QS, QP, ERR, (CF, SF, ZF);
        m.setArrayWord(String.valueOf(getPTR()),adress);
        INC("QP");
        m.setArrayWord(String.valueOf(getCS()),adress);
        INC("QP");
        m.setArrayWord(String.valueOf(getIP()),adress);
        INC("QP");
        m.setArrayWord(String.valueOf(getR()),adress);
        INC("QP");
        m.setArrayWord(String.valueOf(getP()),adress);
        INC("QP");
        m.setArrayWord(String.valueOf(getRS()),adress);
        INC("QP");
        m.setArrayWord(String.valueOf(getQS()),adress);
        INC("QP");
        m.setArrayWord(String.valueOf(getQP()),adress);
        INC("QP");
        m.setArrayWord(String.valueOf(getERR()),adress);
        INC("QP");
        String flags = String.valueOf(getCF())+getSF()+getZF();
        m.setArrayWord(flags,adress);
        INC("QP");
    }

    public void setInterrupt(int interrupt) {
        this.interrupt = interrupt;
    }

    public int getInterrupt() {
        return interrupt;
    }
}
