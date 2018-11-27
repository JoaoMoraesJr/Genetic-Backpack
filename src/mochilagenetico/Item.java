/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mochilagenetico;

/**
 *
 * @author joao_
 */
public class Item {
    
    private int weight;
    private int value;
    private int stock;
    
    public Item (int weight, int value, int stock){
        this.weight = weight;
        this.value = value;
        this.stock = stock;
    }
    
    public int getWeight () {
        return weight;
    }
    
     public int getValue () {
        return value;
    }
    
     public int getStock () {
        return stock;
    }
     
     public String toString () {
         return "Peso: " + weight + " Valor: " + value + " Estoque: " + stock;
     }
    
}
