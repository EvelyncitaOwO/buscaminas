package com.company;

import java.util.Objects;
import java.util.Scanner;

public class Buscaminas {

    private int rows;
    private int cols;
    private String[][] tauler;
    private int[][] tauler2;
    private int minas;
    private boolean acabat=false;
    private int CasellesGirades;
    private int CasellesTotals;
    private final Scanner leer = new Scanner(System.in);

    public void initBuscaminas(){

        System.out.println("Seleccione el numero de filas (1-9)");
        rows= leer.nextInt();
        if (rows<1|rows>9){
            System.out.println("error");
            initBuscaminas();
        }

        System.out.println("Seleccione el numero de columnas (1-9)");
        cols= leer.nextInt();
        if (cols<1|cols>9){
            System.out.println("error");
            initBuscaminas();
        }

        System.out.println("Seleccione el numero de minas (menor al numero de casillas)");
        minas= leer.nextInt();
        if (minas<=0|minas>=(rows*cols)){
            System.out.println("error");
            initBuscaminas();
        }
        tauler=new String[rows][cols];
        tauler2=new int[rows][cols];
        CasellesTotals=(rows*cols)-minas;

        CrearTauler();
        ColocarMinas();
        CalcularNumero();
        Jugar();
    }

    public void CrearTauler(){

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tauler[i][j] = " ";
            }
        }
    }

    public void DibuixarTauler(){

        for (int j = 0; j < cols+1; j++) {
            System.out.print("|---");
        }
        System.out.println("|");

        for (int i = 0; i < rows; i++) {
            System.out.print("| "+(rows-i)+" ");
            for (int j = 0; j < cols; j++) {
                System.out.print("| ");

                switch (tauler[i][j]){

                    case "X":System.out.print("\033[35m"+tauler[i][j]+"\u001B[0m");break;

                    case "0":System.out.print("\033[37m"+tauler[i][j]+"\u001B[0m");break;

                    case "1":System.out.print("\033[34m"+tauler[i][j]+"\u001B[0m");break;

                    case "2":System.out.print("\033[32m"+tauler[i][j]+"\u001B[0m");break;

                    case " ":System.out.print(tauler[i][j]);break;

                    default: System.out.print("\033[31m"+tauler[i][j]+"\u001B[0m");break;
                }


                System.out.print(" ");
            }
            System.out.println("|");
            for (int j = 0; j < cols+1; j++) {
                System.out.print("|---");
            }
            System.out.println("|");

        }
        for (int i=0;i<cols+1;i++){

            System.out.print("| "+i+" ");
        }
        System.out.println("|");
        System.out.println();

        System.out.println("Minas: "+minas);
        System.out.println("Casillas restantes: "+(CasellesTotals-CasellesGirades));


    }

    public void ColocarMinas(){

        int contador=0;

        while (contador<minas) {

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


    public void Jugar(){

        String error="";

        while (!acabat){
            DibuixarTauler();

            System.out.println(error);
            error="";

            System.out.println("Introduzca la posición x:");
            int x = leer.nextInt();
            x--;
            System.out.println("Introduzca la posición y:");
            int y = leer.nextInt();
            y=rows-y;

            if (0<=x && x<cols && 0<=y && y<rows){
                if (Objects.equals(tauler[y][x], " ")){
                    Girar(x,y);
                }
            }else{
                error="Error, seleccione una casilla válida";
            }
        }
    }

    public void Girar(int x, int y){

        if (Objects.equals(tauler[y][x], " ")){
            if (tauler2[y][x]!=-1){
                tauler[y][x]=String.valueOf(tauler2[y][x]);
                CasellesGirades++;
            }else{

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        if (tauler2[i][j]==-1){
                            tauler[i][j]= "X";
                            acabat=true;
                        }
                    }
                }

                //tauler[y][x]= "X";
                //acabat=true;
                DibuixarTauler();
                System.out.print("\033[31m"+"Derrota"+"\u001B[0m");
            }
            if (tauler2[y][x]==0){
                Desbloquear(x,y);
            }
        }

        ComprovarGuanyar();
    }

    public void Desbloquear(int j, int i){
        if (i+1<rows && j-1>=0 ){
            Girar(j-1,i+1);
        }
        if (j-1>=0){
           Girar(j-1,i);
        }
        if (i-1>=0 && j-1>=0){
            Girar(j-1,i-1);
        }
        if (i+1<rows){
            Girar(j,i+1);
        }
        if (i-1>=0){
            Girar(j,i-1);
        }
        if (i+1<rows && j+1<cols){
            Girar(j+1,i+1);
        }
        if (i-1>=0 && j+1<cols){
            Girar(j+1,i-1);
        }
        if (j+1<cols){
            Girar(j+1,i);
        }
    }

    public void ComprovarGuanyar(){
        if (CasellesGirades>=CasellesTotals){
            DibuixarTauler();
            System.out.print("\033[32m"+"Victoria"+"\u001B[0m");
            acabat=true;
        }
    }
}
