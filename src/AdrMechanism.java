public class AdrMechanism{


//cs,SS - realios mašinos - žinomi išanksto
    public int findFreeSpace(String[] array) {
        int cs = 40;
        int SS = 700;
        int block = 0;
        int free=0;
        for(int i=cs;i<SS; i++) {
            if(i%10==0){
                free = 0;
                if(array[i].equals("        ")){
                    free++;
                } else {
                    i+=9;
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

    //ip, cs(visada 0, neverta apibrėžinėti ieškant adreso)- virtualios mašinos
    public int realWordAdress(String[] array, int ptr, int ip){
        int block_number = ptr%100;
        int block = ip/10;
        int word = ip%10;
        return Integer.parseInt(array[block_number*10+block].trim())*10+word;
    }
}
