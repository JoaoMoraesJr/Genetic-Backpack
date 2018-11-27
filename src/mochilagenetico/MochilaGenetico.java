/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mochilagenetico;
import java.util.Scanner;
import java.util.Random;
/**
 *
 * @author joao_
 */
public class MochilaGenetico {

    /**
     * @param args the command line arguments
     */

    
    public static void main(String[] args) {
       /*Random random = new Random ();
       while (true) {
           System.out.println (random.nextInt(4));
           if (false) break;
       }*/
       Scanner read = new Scanner (System.in);
       System.out.println (" ----Problema da Mochila--------");
       System.out.println (" Qual será o tamanho da população?");
       int population = read.nextInt();
       int mutation;
       do {
        System.out.println (" Qual será a probabilidade de mutação?");
        mutation = read.nextInt();
       }while (mutation > 100 || mutation < 0);
       System.out.println (" Quanto peso a mochila comporta? ");
       int maxWeight = read.nextInt();
       System.out.println (" Quantos itens estão a disposição? ");
       int nItens = read.nextInt();
       
       Generator gen = new Generator (maxWeight, nItens, population, mutation);
       
       int weight;
       int value;
       int stock;
       for (int i = 0; i != nItens; i++) {
           System.out.println ("\nItem " + (i+1) + ": ");
           System.out.print ("Peso: ");
           weight = read.nextInt();
           System.out.print ("Valor: ");
           value = read.nextInt();
           System.out.print ("Estoque: ");
           stock = read.nextInt();
           Item item = new Item (weight, value, stock);
           gen.setItem(item);
       }
       System.out.println ("\t Tabela de Itens \n" + gen.toStringItem());
       
       do{
           //System.out.println ("Entrou1");
           gen.generate();
           System.out.println (gen.toStringPop());
           System.out.println (gen.toStringBest());
           //System.out.println ("Entrou2");
           String s = read.nextLine();
           s = read.nextLine();
           //System.out.println ("Entrou3");
        } while (true);
    }
    
}
