public class Interrupts extends Registers{
    private int interrupt;
    Interrupts(Memory m, int interrupt){
        super(m);
        setInterrupt(interrupt);
        String intadress = m.getFromArray(getInterrupt());
        int cs = Integer.parseInt(intadress.substring(0,3));
        int ip = Integer.parseInt(intadress.substring(3,7));
        setCS(cs);
        setIP(ip);
        //PTR
        //10 ZODZIU VIENAI VIRTUALIAI MASINAI, eilės duomenų struktūroje(ji prasideda nuo 0700 ir iki 0799)
        //PTR,CS, IP, R, P, RS, QS, QP, ERR, (CF, SF, ZF);
        m.setArrayWord(String.valueOf(getPTR()),getQS()+getQP());
        INC("QP");
        m.setArrayWord(String.valueOf(getCS()),getQS()+getQP());
        INC("QP");
        m.setArrayWord(String.valueOf(getIP()),getQS()+getQP());
        INC("QP");
        m.setArrayWord(String.valueOf(getR()),getQS()+getQP());
        INC("QP");
        m.setArrayWord(String.valueOf(getP()),getQS()+getQP());
        INC("QP");
        m.setArrayWord(String.valueOf(getRS()),getQS()+getQP());
        INC("QP");
        m.setArrayWord(String.valueOf(getQS()),getQS()+getQP());
        INC("QP");
        m.setArrayWord(String.valueOf(getQP()),getQS()+getQP());
        INC("QP");
        m.setArrayWord(String.valueOf(getERR()),getQS()+getQP());
        INC("QP");
        String whole = String.valueOf(getCF()).concat(String.valueOf(getSF()));
        String last = whole.concat(String.valueOf(getZF()));
        m.setArrayWord(last,getQS()+getQP());
        INC("QP");
    }

    public void setInterrupt(int interrupt) {
        this.interrupt = interrupt;
    }

    public int getInterrupt() {
        return interrupt;
    }
}
