public class Memory extends AdrMechanism{
    private String[] array = new String[800];
    //Konstruktorius
    //interuptai nuo 0-39
    //duomnys - vm 40-699
    //stekas nuo 700-799
    public Memory(){
        System.out.println("M KONSTR");
        for (int i = 0; i < array.length; i++){
                array[i]= "        ";
                //komentaras
        }
    }
    public void setArray(String[] array) {
        this.array = array;
    }
    public void setArrayWord(String word, int adress) {
        this.array[adress] = String.format("%8s", word);
    }
    public String[] getArray() {
        return array;
    }
    public String getFromArray(int adress){
        return array[adress];
    }

    public void printInterruptTable(){
        for(int i=0;i<40;i++){
            System.out.println(i+"-"+array[i]);
        }
    }

    public void printCS(){
        for(int i=40;i<700;i++){
            System.out.println(i+"-"+array[i]);
        }
    }

    public void printSS(){
        for(int i=700;i<800;i++){
            System.out.println(i+"-"+array[i]);
        }
    }

    //paskutinis virtualios masinos ip arba vm programos dydis.

    public void printVM(int PTR, int lastIP){
        for(int i=0;i<lastIP;i++){
            String word = getFromArray(realWordAdress(array,PTR,i));
            System.out.println(i+"-"+word);
        }
    }
}
