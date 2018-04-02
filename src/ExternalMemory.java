import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ExternalMemory extends AdrMechanism{
    private String[] array = new String[2000];
    private int last_read = 0;
    private int last_write = 0;
    public ExternalMemory(){
        //read file externalmemory.txt
        for(int i=0;i<2000;i++){
            array[i] = "        ";
        }
    }

    public void setArray(String[] array) {
        this.array = array;
    }
    public void setArrayWord(String word, int adress) {
        this.array[adress] = String.format("%8s", word);
    }

    public void writeArrayWord(String word){
        try{
            FileWriter fw = new FileWriter("/sysroot/home/dovile/IdeaProjects/OS/src/externalmemory.txt", true);
            fw.write(word+"\n");
            fw.close();
        }
        catch (IOException io){
            System.err.println("IO EXEPTION: "+io.getMessage());
        }
    }

    public void setLast_write(int last_write) {
        this.last_write = last_write;
    }

    public int getLast_write() {
        return last_write;
    }

    public String[]getArray(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("/sysroot/home/dovile/IdeaProjects/OS/src/externalmemory.txt"));
            String line = br.readLine();
            int i =0;
            while(line!=null){
                setArrayWord(line,i);
                i++;
                line = br.readLine();
            }
            setLast_write(i+1);
            br.close();
        } catch (IOException io){
            System.err.println("IO EXEPTION: "+io.getMessage());
        }

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

    public int getLast_free_space() {
        return findFreeSpace(getArray(),0,2000)*10;
    }
}
