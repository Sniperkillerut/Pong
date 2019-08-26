import java.awt.Color;

import java.awt.Graphics;

import java.util.Random;

public class Obstaculo{
    
    public int x;
    public int y;
    public int d;
    public int w;
    public int h;
    public Color color;
    public boolean especial=false;
    public Obstaculo(int x, int y, int d, int w, int h, boolean es) {
    
        this.x=x;
        this.y=y;
        this.d=d;
        this.w=w;
        this.h=h;
        this.especial=es;
        Random r= new Random();
        color=new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
    }
    
    public void pintar(Graphics g){
        if(especial){
            Random r= new Random();
            g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
            g.fillRect(x,y,d,d);
        }else{
            g.setColor(color);
            g.fillRect(x,y,d,d);
        }
    }
}