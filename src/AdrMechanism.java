public class AdrMechanism{
    public int findFreeSpace(String[] array, String c, String q) {
        int cs = Integer.parseInt(c);
        int qs = Integer.parseInt(q);
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

    public int realWordAdress(String[] array, String p, String i, String c){
        int cs = Integer.parseInt(c);
        int ip = Integer.parseInt(i);
        int ptr = Integer.parseInt(p);
        int block_number = ptr%100;
        int whole = cs+ip;
        int block = whole/10;
        int word = whole%10;
        int real_adress = ((Integer.parseInt(array[block_number*10]))+block)*10+word;
        return real_adress;
    }
}
