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

}
