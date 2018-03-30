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
        //PTR
        //VIENAI VIRTUALIAI MASINAI, eilės duomenų struktūroje(ji prasideda nuo 0700 ir iki 0799)
        //PTR,IP, R, P, RS, QS, QP, ERR, (CF, SF, ZF);
        m.setArrayWord(String.valueOf(getPTR()),Integer.parseInt(getQS())+Integer.parseInt(getQP()));
        INC("QP");
        m.setArrayWord(String.valueOf(getIP()),Integer.parseInt(getQS())+Integer.parseInt(getQP()));
        INC("QP");
        m.setArrayWord(String.valueOf(getR()),Integer.parseInt(getQS())+Integer.parseInt(getQP()));
        INC("QP");
        m.setArrayWord(String.valueOf(getP()),Integer.parseInt(getQS())+Integer.parseInt(getQP()));
        INC("QP");
        m.setArrayWord(String.valueOf(getRS()),Integer.parseInt(getQS())+Integer.parseInt(getQP()));
        INC("QP");
        m.setArrayWord(String.valueOf(getQS()),Integer.parseInt(getQS())+Integer.parseInt(getQP()));
        INC("QP");
        m.setArrayWord(String.valueOf(getQP()),Integer.parseInt(getQS())+Integer.parseInt(getQP()));
        INC("QP");
        m.setArrayWord(String.valueOf(getERR()),Integer.parseInt(getQS())+Integer.parseInt(getQP()));
        INC("QP");
        String flags = String.valueOf(getCF())+getSF()+getZF();
        m.setArrayWord(flags,Integer.parseInt(getQS())+Integer.parseInt(getQP()));
        INC("QP");
    }

    public void setInterrupt(int interrupt) {
        this.interrupt = interrupt;
    }

    public int getInterrupt() {
        return interrupt;
    }
}
