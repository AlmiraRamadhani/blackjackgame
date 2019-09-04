/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.ArrayList;

/**
 *
 * @author Rizky
 */
public class Deck {
    
    private ArrayList<Kartu> listKartu;
    private int size;
    private void Shuffle()
    {
        Kartu temp;
    for(int x = 1; x<=1000; x++)
    {
        int ramd1 = (int)(Math.random() * size);
        int ramd2 = (int)(Math.random() * size);
        temp = listKartu.get(ramd1);
        listKartu.set(ramd1, listKartu.get(ramd2));
        listKartu.set(ramd1, temp);
    }

    }
    public Deck(){
        listKartu = new ArrayList<Kartu>();
        listKartu.add(new Kartu("Diamond", "Ace", 11));
        listKartu.add(new Kartu("Diamond", "Two", 2));
        listKartu.add(new Kartu("Diamond", "Three", 3));
        listKartu.add(new Kartu("Diamond", "Four", 4));
        listKartu.add(new Kartu("Diamond", "Five", 5));

        listKartu.add(new Kartu("Diamond", "Six", 6));
        listKartu.add(new Kartu("Diamond", "Seven", 7));
        listKartu.add(new Kartu("Diamond", "Eight", 8));
        listKartu.add(new Kartu("Diamond", "Nine", 9));
        listKartu.add(new Kartu("Diamond", "Ten", 10));

        listKartu.add(new Kartu("Diamond", "Jack", 10));
        listKartu.add(new Kartu("Diamond", "Queen", 10));
        listKartu.add(new Kartu("Diamond", "King", 10));

        listKartu.add(new Kartu("Clubs", "Ace", 11));
        listKartu.add(new Kartu("Clubs", "Two", 2));
        listKartu.add(new Kartu("Clubs", "Three", 3));
        listKartu.add(new Kartu("Clubs", "Four", 4));
        listKartu.add(new Kartu("Clubs", "Five", 5));

        listKartu.add(new Kartu("Clubs", "Six", 6));
        listKartu.add(new Kartu("Clubs", "Seven", 7));
        listKartu.add(new Kartu("Clubs", "Eight", 8));
        listKartu.add(new Kartu("Clubs", "Nine", 9));
        listKartu.add(new Kartu("Clubs", "Ten", 10));

        listKartu.add(new Kartu("Clubs", "Jack", 10));
        listKartu.add(new Kartu("Clubs", "Queen", 10));
        listKartu.add(new Kartu("Clubs", "King", 10));

        listKartu.add(new Kartu("Hearts", "Ace", 11));
        listKartu.add(new Kartu("Hearts", "Two", 2));
        listKartu.add(new Kartu("Hearts", "Three", 3));
        listKartu.add(new Kartu("Hearts", "Four", 4));
        listKartu.add(new Kartu("Hearts", "Five", 5));

        listKartu.add(new Kartu("Hearts", "Six", 6));
        listKartu.add(new Kartu("Hearts", "Seven", 7));
        listKartu.add(new Kartu("Hearts", "Eight", 8));
        listKartu.add(new Kartu("Hearts", "Nine", 9));
        listKartu.add(new Kartu("Hearts", "Ten", 10));

        listKartu.add(new Kartu("Hearts", "Jack", 10));
        listKartu.add(new Kartu("Hearts", "Queen", 10));
        listKartu.add(new Kartu("Hearts", "King", 10));

        listKartu.add(new Kartu("Spades", "Ace", 11));
        listKartu.add(new Kartu("Spades", "Two", 2));
        listKartu.add(new Kartu("Spades", "Three", 3));
        listKartu.add(new Kartu("Spades", "Four", 4));
        listKartu.add(new Kartu("Spades", "Five", 5));

        listKartu.add(new Kartu("Spades", "Six", 6));
        listKartu.add(new Kartu("Spades", "Seven", 7));
        listKartu.add(new Kartu("Spades", "Eight", 8));
        listKartu.add(new Kartu("Spades", "Nine", 9));
        listKartu.add(new Kartu("Spades", "Ten", 10));

        listKartu.add(new Kartu("Spades", "Jack", 10));
        listKartu.add(new Kartu("Spades", "Queen", 10));
        listKartu.add(new Kartu("Spades", "King", 10));
        Shuffle();
    }
    
    public String toString()
{
String out = "";
for(int x = 0; x<=51; x++)
{
out = out + listKartu.get(x).toString()+"\n";
}
return out;
}
    
}
