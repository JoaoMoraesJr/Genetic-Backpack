/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mochilagenetico;

import java.util.Random;

/**
 *
 * @author joao_
 */
public class Generator {
    
    private int best = 0;
    private int bestGeneration;
    private int bestSolution[];
    private int maxWeight;
    private int nItens;
    private Item itens[];
    private int countItem = 0;
    private int population;
    private int matrixPop[][];
    private int matrixNewPop [][];
    Random random = new Random();
    private int generation = 0;
    private int results[];
    private double probPop[];
    private int probMutation;
    
    public Generator (int maxWeight, int nItens, int population, int mutation) {
        this.maxWeight = maxWeight;
        this.nItens = nItens;
        itens = new Item [nItens];
        this.population = population;
        matrixPop = new int[population][nItens];
        matrixNewPop = new int [population][nItens];
        bestSolution = new int[nItens];
        results = new int [population];
        probPop = new double [population];
        probMutation = mutation;
    }
    
    public void generate () {
        if (generation == 0) {
            generateFirst();
        }
        else{
            calculateProbAcum();
            crossOver();
            for (int i = 0; i<population; i++) {
                for (int j =0; j<nItens; j++) {
                    matrixPop[i][j] = matrixNewPop[i][j];
                }
            }
            mutation();
        }
        generation++;
        verifyBest();
    }
    
    public void generateFirst () {
        for (int i = 0; i < population; i++) {
            do{
                for (int j = 0; j<nItens; j++) {
                        matrixPop[i][j] = random.nextInt(itens[j].getStock()+1);
                        results[i] = getResult(i);
                }
            }while (verifySolution(i, matrixPop) != true);
        }
    }


    public boolean verifySolution (int i, int mat[][]) {
        int weight = 0;
        for (int j = 0; j<nItens; j++) {
            weight += mat[i][j] * itens[j].getWeight();
        }
        //System.out.println (weight);
        if (weight > maxWeight) {
            //System.out.print ("Overweight");
            return false;
        }
        return true;
    }
    
    
    public void verifyBest () {
        for (int i = 0; i<population; i++) {
            int value = getResult(i);
            if (value> best) {
                best = value;
                bestGeneration = generation;
                for (int k = 0; k<nItens; k++){
                    bestSolution[k] = matrixPop[i][k];
                }
            }
        }
    }
    
    public void calculateProbAcum() {
        int total = 0;
        for (int i = 0; i<population; i++) {
            total += getResult(i);
        }
        probPop[0] = ((double)getResult(0) / total) * 100;
        //System.out.println ("total: " + total + " " + getResult(0) + " " + results[0]);
        for (int j = 1; j<population; j++) {
            probPop[j] = (((double)getResult(j) / total) * 100) + probPop[j-1];
        }
    }
    
    public void crossOver(){
        int father1 = 0;
        int father2 = 0;
        for (int k = 0; k<population; k++) {
            do{
                //System.out.println ("cross1");
                int r = random.nextInt(101);
                for (int i = 0; i<population; i++) {
                   if (r <= probPop[i]) {
                        father1 = i;
                        break;
                    }
                }
                do {
                    r = random.nextInt (101);
                    for (int i = 0; i<population; i++) {
                        if (r <= probPop[i]) {
                            //System.out.println (r);
                            father2 = i;
                            break;
                        }
                    }
                    //System.out.println ("f: " + father1 + " f2: " + father2 + " r: " + r);
                }while (father1 == father2);
                //System.out.println("cross2");
                int cross = random.nextInt(nItens-1)+1;
                for (int i = 0; i<cross; i++) {
                    matrixNewPop [k] [i] = matrixPop[father1][i];
                }
                for (int i = cross; i<nItens; i++) {
                    matrixNewPop [k] [i] = matrixPop[father2][i];
                }
                //System.out.println ("cross3");
            }while (verifySolution(k, matrixNewPop) != true);
        }
    }
    
    public void mutation () {
        int weight;
        int mut;
        int index =0;
        for (int i = 0; i<population; i++) {
            int r = random.nextInt(101);
            if (r <= probMutation) {
                boolean aux = false;
                int oldI = 0;
                do{
                    if (aux == true) {
                        matrixPop[i][index] = oldI;
                        aux = false;
                    }
                    index = random.nextInt(nItens);
                    do {
                    mut = random.nextInt (itens[index].getStock()+1);
                    } while (mut == matrixPop[i][index]);
                    oldI = matrixPop[i][index];
                    matrixPop[i][index] = mut;
                    aux = true;
                }while (verifySolution(i, matrixPop) != true);
            }
        }
    }
    //GETTERS AND SETTERS
    
    public boolean setItem (Item i) {
        if (countItem >= maxWeight) return false;
        itens[countItem] = i;
        countItem++;
        return true;
    }
    
    public Item getItem (int n) {
        return itens[n];
    }
    
    public int getResult (int i) {
        int result = 0;
        for (int j = 0; j<nItens; j++) {
            result += matrixPop[i][j] * itens[j].getValue();
        }
        return result;
    }
    
    public int getWeight (int i) {
        int weight = 0;
        for (int j = 0; j<nItens; j++) {
            weight += matrixPop[i][j] * itens[j].getWeight();
        }
        return weight;
    }
    
    public int getBest() {
        return best;
    }
    
    public int getGeneration (){
        return generation;
    }
    
    public int getBestGeneration (){
        return bestGeneration;
    }
    
    
    //TO STRINGS
        public String toStringItem () {
        String s = "";
        for (int i = 0; i != nItens; i++) {
            s+= "Item " + (i+1) + " - " + itens[i].toString() + "\n";
        }
        return s;
    }
        
    public String toStringPop () {
        String s ="";
        s+= "   Geração: " + generation + "\n";
        for (int i = 0; i<population; i++) {
            s+= (i+1) + " - ";
            for (int j = 0; j < nItens; j++) {
                s+= matrixPop[i][j] + " ";
            }
            s+= ": " + getResult(i) + " - " + getWeight(i) + "\n";
        }
        return s;
    }
    
    public String toStringBest () {
        String s = "";
        s+= "Best solution - ";
        for (int i = 0; i<nItens; i++) {
            s+= bestSolution[i] + " ";
        }
        s+= ": " + best + " Best generation - " + bestGeneration;
        return s;
    }

}