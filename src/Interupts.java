public class Interupts extends RmCommands{
    private int interrupt;
    Interupts(int interrupt){

        this.interrupt = interrupt;

    }

    public void setInterrupt(int interrupt) {
        this.interrupt = interrupt;
    }

    public int getInterrupt() {
        return interrupt;
    }
}
