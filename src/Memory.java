public class Memory extends AdrMechanism{
    private String[][] array = new String[800][1];
    //Konstruktorius
    //interuptai nuo 0-39
    //duomnys - vm 40-699
    //stekas nuo 700-799
    public Memory(){
        System.out.println("M KONSTR");
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = "        ";
                //komentaras

            }
        }
    }
    public void setArray(String[][] array) {
        this.array = array;
    }
    public void setArrayWord(String word, int adress) {
        this.array[adress][0] = String.format("%8s", word);
    }
    public String[][] getArray() {
        return array;
    }
    public String getFromArray(int adress){
        return array[adress][0];
    }

    public int findFreeSpace(){
        for(){

        }
    }

}
