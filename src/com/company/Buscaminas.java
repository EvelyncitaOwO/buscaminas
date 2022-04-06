package com.company;

public class Buscaminas {

    private int rows=9;
    private int cols=9;
    private String[][] tauler=new String[rows][cols];
    private int[][] tauler2=new int[rows][cols];
    private int minas;

    public void initBuscaminas(){
        CrearTauler();
        DibuixarTauler();
        ColocarMinas();
        CalcularNumero();
    }

    public void CrearTauler(){

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tauler[i][j] = " ";
            }
        }
    }

    public void DibuixarTauler(){

        for (int i=1;i<cols+1;i++){

            System.out.print("| "+i+" ");
        }
        System.out.println("|");
        for (int j = 0; j < cols; j++) {
            System.out.print("|---");
        }
        System.out.println("|");

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("|"+tauler[i][j]+" ");
                System.out.print(" ");
            }
            System.out.println("|");
            for (int j = 0; j < cols; j++) {
                System.out.print("|---");
            }
            System.out.println("|");

        }
    }

    public void ColocarMinas(){

        int contador=0;

        while (contador<=minas) {

            int x = (int) (Math.random()*rows);
            int y = (int) (Math.random()*cols);

            if (tauler2[x][y] == 0) {
                tauler2[x][y] = -1;
                contador++;
            }
        }
    }

    private void CalcularNumero(){

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (tauler2[i][j]<0){
                    if (i+1<rows && j-1>=0 && tauler2[i+1][j-1]!=-1){
                        tauler2[i+1][j-1]++;
                    }
                    if (j-1>=0 && tauler2[i][j-1]!=-1){
                        tauler2[i][j-1]++;
                    }
                    if (i-1>=0 && j-1>=0 && tauler2[i-1][j-1]!=-1){
                        tauler2[i-1][j-1]++;
                    }
                    if (i+1<rows && tauler2[i+1][j]!=-1){
                        tauler2[i+1][j]++;
                    }
                    if (i-1>=0 && tauler2[i-1][j]!=-1){
                        tauler2[i-1][j]++;
                    }
                    if (i+1<rows && j+1<cols && tauler2[i+1][j+1]!=-1){
                        tauler2[i+1][j+1]++;
                    }
                    if (i-1>=0 && j+1<cols && tauler2[i-1][j+1]!=-1){
                        tauler2[i-1][j+1]++;
                    }
                    if (j+1<cols && tauler2[i][j+1]!=-1){
                        tauler2[i][j+1]++;
                    }
                }
            }
        }

    }
}
