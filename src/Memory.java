public class Memory extends AdrMechanism{
    private String[] array = new String[800];
    //Konstruktorius
    //interuptai nuo 0-39
    //duomnys - vm 60-699
    //stekas nuo 700-799
    public Memory(){
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
        for(int i=0;i<60;i++){
            System.out.println(i+"-"+array[i]);
        }
    }

    public void printCS(){
        for(int i=60;i<700;i++){
            System.out.println(i+"-"+array[i]);
        }
    }

    public void printSS(){
        for(int i=700;i<800;i++){
            System.out.println(i+"-"+array[i]);
        }
    }

    //paskutinis virtualios masinos ip arba vm programos dydis.

    public void printVM(int PTR){
        int k = 0;
        for(int i=0;i<10;i++){
            String word = "";
            int adress = PTR%100*10+i;
            String w = getFromArray(PTR%100*10+i);
            if(!(w.equals("       -"))){
                for(int j=0;j<10;j++){
                    word = getFromArray(Integer.parseInt(w.trim())*10+j);
                    System.out.println(k+"-"+word);
                    k++;
                }
            } else {
                for(int l=0;l<10;l++) {
                    System.out.println(k + "-" + word);
                    k++;
                }
            }

        }
    }
}
