public class Interupts extends Registers{
    private int interrupt;
    Interupts(Memory m){
        super(m);
        String intadress = m.getFromArray(getInterrupt());
        int cs = Integer.parseInt(intadress.substring(0,3));

        setCS(cs);
        //jei 1 interuptas
    }

    public void setInterrupt(int interrupt) {
        this.interrupt = interrupt;
    }

    public int getInterrupt() {
        return interrupt;
    }
}
