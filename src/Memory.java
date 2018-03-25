public class Memory {
    private double[][] array = new double[800][8];

    //Konstruktorius
    public Memory(){
        System.out.print("Inicializuojama atmintis");
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = 0;
                //komentaras

            }
        }
    }
}
