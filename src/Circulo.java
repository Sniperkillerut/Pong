import java.awt.Color;
import java.awt.Graphics;

public class Circulo{
        public int name;
        public int x;
        public int y;
        public int d;
        public int dx;
        public int dy;
        public int w;
        public int h;
        public Color color;
    public Circulo(int x, int y, int d, int w, int h, int name, int dx, int dy) throws Exception{
        if(x<0||(x+d)>w||y<0||(y+d)>h)
                throw new Exception("Circulo no puede ser creado");
        
        this.x=x;
        this.y=y;
        this.d=d;
        this.w=w;
        this.h=h;
        this.dx=dx;
        this.dy=dy;
        this.name=name;
        color=Color.ORANGE;
    }
    public void mover(int tx){
        x=x+tx;
        y=y+dy;
        if((x+d)>=w){
            Ventana.puntaje1++;
            Ventana.kien=1;
        }
        if(x<=0){
            Ventana.puntaje2++;
            Ventana.kien=2;
        }
        if((y+d)>=h||y<=0){
            dy=dy*-1;
        }
    }
    public void pintar(Graphics g){
        g.setColor(color);
        g.fillOval(x,y,d,d);
    }
}