public class ExternalMemory {
    private String[] array = new String[2000];
    private int last_read = 0;
    private int last_free_space = 0;
    public ExternalMemory(){
        for (int i = 0; i < array.length; i++){
                array[i] = "        ";
                //komentaras
        }
    }

    public void setArray(String[] array) {
        this.array = array;
    }
    public void setArrayWord(String word, int adress) {
        this.array[adress] = String.format("%8s", word);
    }
    public String[]getArray() {
        return array;
    }

    public String getFromArray(int adress){
        return array[adress];
    }

    public void setLast_read(int last_read) {
        this.last_read = last_read;
    }

    public int getLast_read() {
        return last_read;
    }

    public void setLast_free_space(int last_free_space) {
        this.last_free_space = last_free_space;
    }

    public int getLast_free_space() {
        return last_free_space;
    }
}
