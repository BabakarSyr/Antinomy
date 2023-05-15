package Modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Coup {
    public int carte;
    public int position;
    public int score;
    Stack<Coup>historiques;


    public Coup(int carte, int position) {
        this.carte = carte;
        this.position = position;
        this.score = 0;
        historiques=new Stack<>();
        
    }



    public boolean peutannnuler(){
        return !historiques.isEmpty();
    }

    public void annuler(){
        
        if(peutannnuler()){
            Coup c=historiques.pop();
            carte=c.carte;
            position=c.position;
         
        }
    }









}
