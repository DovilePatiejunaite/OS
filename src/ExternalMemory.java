public class ExternalMemory {
    private String[][] array = new String[2000][1];
    private int last_readed = 0;
    private int last_free_space = 0;
    public ExternalMemory(){
        System.out.println("Inicializuojama atmintis");
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

    public void setLast_readed(int last_readed) {
        this.last_readed = last_readed;
    }

    public int getLast_readed() {
        return last_readed;
    }

    public void setLast_free_space(int last_free_space) {
        this.last_free_space = last_free_space;
    }

    public int getLast_free_space() {
        return last_free_space;
    }
}
