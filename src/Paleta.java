import java.awt.Color;

import java.awt.Graphics;

import java.util.Random;

public class Paleta{
    
    public int x;
    public int y;
    public int w;
    public int h;
    public int hmax;
    public int wmax;
    public Color color;
    public int angulo=45;
    public Paleta(int x, int y,  int w, int h, int wmax, int hmax) {
        
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        this.wmax=wmax;
        this.hmax=hmax;
        Random r= new Random();
        color=new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
    }
    
    public void pintar(Graphics g){
        g.setColor(color);
        g.fillRect(x,y,w,h);
    }
}