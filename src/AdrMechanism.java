public class AdrMechanism{
    public int findFreeSpace(String[] array, int cs, int qs) {
        int block = 0;
        int free=0;
        for(int i=cs;i<qs; i++) {
            if(i%10==0){
                free = 0;
                if(array[i].equals("        ")){
                    free++;
                } else {
                    i+=10;
                }
            } else if(free!=0){
                if(array[i].equals("        ")){
                    free++;
                }
            }
            if(free == 10){
                block = i/10;
                break;
            }
        }
        return block;
    }
}
