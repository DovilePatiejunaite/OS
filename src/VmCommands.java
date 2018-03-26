public class VmCommands extends Registers{

    //funkcija tikrinanti ar neperžiangiami registrų rėžiai
    private void overflow(int last) {
        if(last>9999){
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
            setZF(1);
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

}
