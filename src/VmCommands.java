public class VmCommands extends Registers{
    public void add(Memory m, int adress){
        String i = m.getFromArray(adress);
        int value = Integer.parseInt(i);
        int last = getR()+value;
        setR(last);
        if(getR() == 0){
            setZF(1);
        } else setZF(0);
        if(Integer.toBinaryString(last).substring(0,1).equals("1")){
            setSF(1);
        } else setSF(0);
        INC("IP");
    }

}
