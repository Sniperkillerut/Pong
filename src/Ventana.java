
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Ventana extends javax.swing.JPanel {
    public static javax.swing.JPanel jPanel1;
    public static JPanel score;
    public static JFrame f= new JFrame();
    public static JFrame f2= new JFrame();
    public static int esp;
    public static ArrayList<Circulo> circulos = new ArrayList<Circulo>();
    public static ArrayList<Obstaculo> obstaculos = new ArrayList<Obstaculo>();
    public static Paleta p1;
    public static Paleta p2;
    public static String Sarr;
    public static String Saba;
    public static String Sizq;
    public static String Sder;
    public static String Shor;
    public static String Sver;
    public static String Sa30;
    public static String Sa45;
    public static String Sarr2;
    public static String Saba2;
    public static String Sizq2;
    public static String Sder2;
    public static String Shor2;
    public static String Sver2;
    public static String Sa302;
    public static String Sa452;
    public static boolean AI=false;
    public static boolean dif=false;
    public static int name=0;
    public static int tiemp=1;
    public static int puntaje1=0;
    public static int puntaje2=0;
    public static int kien=0;
    public static Thread h = new Thread(new Runnable() {
        public void run() {
            try{   
                
                while(true){
                
                    Image im = jPanel1.createImage(jPanel1.getWidth(), jPanel1.getHeight());
                    Graphics g = im.getGraphics();

                    g.setColor(new Color(0,255,0));
                    g.fill3DRect(0, 0, jPanel1.getWidth()/3, jPanel1.getHeight(), true);
                    g.setColor(Color.cyan);
                    g.fill3DRect(jPanel1.getWidth()/3,0, jPanel1.getWidth()/3,jPanel1.getHeight(), true);
                    g.setColor(new Color(0,255,0));
                    g.fill3DRect(2*(jPanel1.getWidth()/3),0,  jPanel1.getWidth()/3, jPanel1.getHeight(), true);
                     
                Image im2 = score.createImage(score.getWidth(), score.getHeight());
                Graphics g2 = im2.getGraphics();
                g2.setColor(Color.BLACK);
                g2.fill3DRect(0, 0, score.getWidth(), score.getHeight(), true);
                g2.setColor(Color.WHITE);
                g2.drawString("Angulo:",score.getWidth()/16,(score.getHeight()/2)-5);
                g2.drawString("Puntaje:",score.getWidth()/16,(score.getHeight()/2)+10);
                g2.drawString(String.valueOf(p1.angulo),score.getWidth()/4,(score.getHeight()/2)-5);
                g2.drawString(String.valueOf(p2.angulo),(score.getWidth()/2)+(score.getWidth()/4),(score.getHeight()/2)-5);
                g2.drawString(String.valueOf(puntaje1),score.getWidth()/4,(score.getHeight()/2)+10);
                g2.drawString(String.valueOf(puntaje2),(score.getWidth()/2)+(score.getWidth()/4),(score.getHeight()/2)+10);
                score.getGraphics().drawImage(im2, 0, 0, null);
                
                if(puntaje1>=20){
                    JOptionPane.showMessageDialog(null,"Gano el jugador 1");
                    System.exit(0);
                }
                if(puntaje2>=20){
                    JOptionPane.showMessageDialog(null,"Gano el jugador 2");
                    System.exit(0);
                }
                if(AI){
                    if(dif){//si esta dificil se mueve en ambos tiempos, sino solo en tiempo 1
                            //los tiempos son para mover las pelotas con velocidades (2,1),(-2,1)
                        AI();
                    }else{
                        if(tiemp==1){
                                AI();
                        }
                    }
                }
                
               synchronized(circulos){
                    for(Circulo c:circulos){
                         
                         if(tiemp==2){
                             if(Math.abs(c.dx)==2){
                                 c.mover(Integer.signum(c.dx)*1);
                                 rebotar(c);
                                 golpearpaleta(p1,c);
                                 golpearpaleta(p2,c);
                             }
                         }else{
                             c.mover(Integer.signum(c.dx)*1); 
                             rebotar(c);
                             golpearpaleta(p1,c);
                             golpearpaleta(p2,c);
                         }
                         c.pintar(g);
                            
                     }//fin for
               
                     boolean salio=true;
                     while(salio){
                        salio=false;
                        Circulo borr=circulos.get(0);
                        for(Circulo c:circulos){
                             if(c.x+c.d>=c.w){
                                 salio=true;
                                 borr=c;
                                 break;
                             }
                             if(c.x<=0){
                                 salio=true;
                                 borr=c;
                                 break;
                             }
                         }
                         if(salio){
                             circulos.remove(borr);
                             if(circulos.size()<1){
                                if(kien==1){
                                    try {
                                        circulos.add(new Circulo(p1.x+p1.w,p1.y+(p1.h/2),15,jPanel1.getWidth(),jPanel1.getHeight(),name,0,0));
                                    }catch(Exception e){System.out.println(e.getMessage());}
                                }else{
                                    try {
                                        circulos.add(new Circulo(p2.x-15,p2.y+(p2.h/2),15,jPanel1.getWidth(),jPanel1.getHeight(),name,0,0));
                                    }catch(Exception e){System.out.println(e.getMessage());}
                                }
                                 name++;
                             }
                        }
                     }
               }
                    golpearcaja();
                    synchronized(obstaculos){
                     for(Obstaculo o:obstaculos){
                         o.pintar(g);
                     }
                    }
                     p1.pintar(g);
                     p2.pintar(g);
                     jPanel1.getGraphics().drawImage(im, 0, 0, null);

                if(tiemp==1){tiemp=2;}
                else{tiemp=1;}
                    h.sleep(1);
            }
            }catch(InterruptedException ex){
                System.out.println(ex.getMessage());
            } 
        }
            });
    public static Thread h2=new Thread(new Runnable(){
     public void run(){
     esp=0;
     boolean sw=true;
     while(true){
        if(esp<2){
            sw=true;
            esp++;
        }else{
            sw=false;
        }
         crearObstaculos(sw);     
     }
   }
   });
    public static void rebotar(Circulo c1){
           // int d = 15;
            boolean sw=false;
           // for(Circulo c1:circulos){
              //  int x1 = c1.x;
              //  int y1 = c1.y;
              synchronized(circulos){
                for(Circulo c2:circulos){
                //    int x2 = c2.x;
                //    int y2 = c2.y;
                 /*  if(c1.name!= c2.name && x2 >= x1 &&x2 <= x1+d && y2 >= y1 && y2<=y1+d){
                        c1.dx = c1.dx*-1;
                        c1.dy = c1.dy*-1;
                        c2.dx = c2.dx*-1;
                        c2.dy = c2.dy*-1;
                    }*/
                    
                    if(c1.name!= c2.name){
                        //si se estrella por arriba   
                        if(c1.y+c1.d==c2.y && (c1.x>c2.x-c1.d && c1.x<c2.x+c2.d)){
                            c1.dy = c1.dy*-1;
                            c2.dy = c2.dy*-1;
                            if(c1.dy==0){
                                c1.dy=c2.dy*-1;
                            }
                            if(c2.dy==0){
                                c2.dy=c1.dy*-1;
                            }
                            sw=true;
                            //break;
                        }else{
                        //si se estrella por abajo
                        if(c1.y==c2.y+c2.d && (c1.x>c2.x-c1.d && c1.x<c2.x+c2.d)){
                             c1.dy = c1.dy*-1;
                             c2.dy = c2.dy*-1;
                             if(c1.dy==0){
                                 c1.dy=c2.dy*-1;
                             }
                             if(c2.dy==0){
                                 c2.dy=c1.dy*-1;
                             }
                             sw=true;
                             //break;
                         }else{
                        //si se estrella por la izquierda
                        if(c1.x+c1.d==c2.x && (c1.y>c2.y-c1.d-1 && c1.y<c2.y+c2.d+1)){
                            c1.dx = c1.dx*-1;
                            c2.dx = c2.dx*-1;
                            if(c1.dx==0){
                                c1.dx=c2.dx*-1;
                            }
                            if(c2.dy==0){
                                c2.dx=c1.dx*-1;
                            }
                            sw=true;
                            //break;
                        }else{
                        //si se estrella por la derecha
                        if(c1.x==c2.x+c2.d && (c1.y>c2.y-c1.d-1 && c1.y<c2.y+c2.d+1)){
                              c1.dx = c1.dx*-1;
                              c2.dx = c2.dx*-1;
                              if(c1.dx==0){
                                  c1.dx=c2.dx*-1;
                              }
                              if(c2.dy==0){
                                  c2.dx=c1.dx*-1;
                              }
                              sw=true;
                              //break;
                          } 
                    }}}}
                }
              }
              // if(sw){break;}
            //}
        }
    public static void golpearcaja(){
    boolean sw=true;
    synchronized(circulos){
    synchronized(obstaculos){
        for(Circulo c:circulos){
            for(int i=0;i<obstaculos.size()&&sw;i++){
                Obstaculo o=obstaculos.get(i);
                     //si se estrella por arriba   
                     if(c.y+c.d==o.y && (c.x>o.x-c.d && c.x<o.x+o.d)){
                       c.dy=c.dy*-1;
                       c.color=o.color;
                       if(o.especial){
                            try {
                                circulos.add(new Circulo(o.x+7,o.y+7,15,jPanel1.getWidth(),jPanel1.getHeight(),name,c.dx,c.dy*-1));
                                name++;
                                esp--;
                                sw=false;
                            } catch (Exception e) {System.out.println(e.getMessage());}
                        }
                       obstaculos.remove(i);
                       i--;
                     }else{
                     //si se estrella por abajo
                     if(c.y==o.y+o.d && (c.x>o.x-c.d && c.x<o.x+o.d)){
                        c.dy=c.dy*-1;
                        c.color=o.color;
                        if(o.especial){
                              try {
                                  circulos.add(new Circulo(o.x+7,o.y+7,15,jPanel1.getWidth(),jPanel1.getHeight(),name,c.dx,c.dy*-1));
                                  name++;
                                  esp--;
                                  sw=false;
                              } catch (Exception e) {System.out.println(e.getMessage());}
                          }
                        obstaculos.remove(i);
                         i--;
                     }else{
                     //si se estrella por la izquierda
                     if(c.x+c.d==o.x && (c.y>o.y-c.d-1 && c.y<o.y+o.d+1)){
                         c.dx=c.dx*-1;
                         c.color=o.color;
                        if(o.especial){
                              try {
                                    circulos.add(new Circulo(o.x+7,o.y+7,15,jPanel1.getWidth(),jPanel1.getHeight(),name,c.dx*-1,c.dy));
                                    name++;
                                    esp--;
                                  sw=false;
                              } catch (Exception e) {System.out.println(e.getMessage());}
                          }
                         obstaculos.remove(i);
                         i--;
                     }else{
                    //si se estrella por la derecha
                     if(c.x==o.x+o.d && (c.y>o.y-c.d-1 && c.y<o.y+o.d+1)){
                           c.dx=c.dx*-1;
                           c.color=o.color;
                          if(o.especial){
                                try {
                                    circulos.add(new Circulo(o.x+7,o.y+7,15,jPanel1.getWidth(),jPanel1.getHeight(),name,c.dx*-1,c.dy));
                                    name++;
                                    esp--;
                                    sw=false;
                                } catch (Exception e) {System.out.println(e.getMessage());}
                            }
                           obstaculos.remove(i);
                           i--;
                       } 
            }}}}
            if(!sw){
                break;
            }
        }
    }}
    }
    public static void golpearpaleta(Paleta p,Circulo c){
        //for(Circulo c:circulos){
                    //si se estrella por arriba   
                     if(c.y+c.d==p.y && (c.x>p.x-c.d && c.x<p.x+p.w)){
                        c.dy=-1;
                        p.color=c.color;
                     }else{
                     //si se estrella por abajo
                     if(c.y==p.y+p.h && (c.x>p.x-c.d && c.x<p.x+p.w)){
                        c.dy=1;
                        p.color=c.color;
                     }else{
                     //si se estrella por la izquierda
                     if(c.x+c.d==p.x && (c.y>p.y-c.d-1 && c.y<p.y+p.h+1)){
                        switch(p.angulo){
                            case(0):        c.dy=0;c.dx=-1;if(c.dx==0){c.dx=-1;}break;
                            case(30):       c.dx=-2;if(c.dy==0){Random r=new Random();if(r.nextBoolean()){c.dy=-1;}else{c.dy=1;}}break;
                            case(45):       c.dx=-1;if(c.dy==0){Random r=new Random();if(r.nextBoolean()){c.dy=-1;}else{c.dy=1;}}break;
                            case(90):       c.dx=0;if(c.dy==0){Random r=new Random();if(r.nextBoolean()){c.dy=-1;}else{c.dy=1;}}break;
                        }
                         p.color=c.color;
                     }else{
                    //si se estrella por la derecha
                     if(c.x==p.x+p.w && (c.y>p.y-c.d-1 && c.y<p.y+p.h+1)){
                           switch(p.angulo){
                               case(0):        c.dy=0;c.dx=1;if(c.dx==0){c.dx=1;}break;
                               case(30):       c.dx=2;if(c.dy==0){Random r=new Random();if(r.nextBoolean()){c.dy=-1;}else{c.dy=1;}}break;
                               case(45):       c.dx=1;if(c.dy==0){Random r=new Random();if(r.nextBoolean()){c.dy=-1;}else{c.dy=1;}}break;
                               case(90):       c.dx=0;if(c.dy==0){Random r=new Random();if(r.nextBoolean()){c.dy=-1;}else{c.dy=1;}}break;
                           }
                           p.color=c.color;
                       }
                     }}}
      //  }
    }
    /*public static void crearCirculos(int name){
            try{
            Random r = new Random();
            synchronized(circulos){
                circulos.add(new Circulo(r.nextInt(jPanel1.getWidth()-15), r.nextInt(jPanel1.getHeight()-15), 15, jPanel1.getWidth(), jPanel1.getHeight(),name,1,1));
            }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
    }*/
    public static void crearObstaculos(boolean sp){
                Random r = new Random();
                int x=0;
                int y=0;
                int d=30;
                int w=(2*(jPanel1.getWidth()/3));
                int h=jPanel1.getHeight();
                boolean sw=false;          
                while(!sw){
                    x=(jPanel1.getWidth()/3)+r.nextInt((jPanel1.getWidth()/3)-30);
                    y=r.nextInt(jPanel1.getHeight()-30);
                    if((!(x<0||(x+d)>w||y<0||(y+d)>h)) && superpuesto(x,y,d)){
                        sw=true;  
                    }
                }
        synchronized(obstaculos){
            obstaculos.add(new Obstaculo(x,y,d,w,h,sp));
        }
        try{h2.sleep(3000);}
        catch(InterruptedException ie){System.out.println(ie.getMessage());}
    }
    public static boolean superpuesto(int x,int y,int d){    
    synchronized(obstaculos){
        for(Obstaculo o:obstaculos){
       // for(int cont=0;cont<obstaculos.size();cont++){
         //Obstaculo o=(Obstaculo)obstaculos.get(cont);
         if((x+d)>=o.x && x<=(o.x+o.d)){
           if((y+d)>=o.y && y<=(o.y+o.d)){
            return false;     
           }   
         }
        }
    }
    synchronized(circulos){
        for(Circulo c:circulos){
            if((x+d)>=c.x && x<=(c.x+c.d)){
              if((y+d)>=c.y && y<=(c.y+c.d)){
               return false;     
              }   
            }
        }
    }
        return true;
        }
    private static void calcularangulo() {
    try{
        Obstaculo spe=obstaculos.get(0);
        synchronized(obstaculos){
            for(Obstaculo o:obstaculos){
                if(o.especial){
                    if(o.x>spe.x){
                        spe=o;
                    }
                }
            }
            if((spe.y>=p2.y)&&(spe.y+spe.d<=p2.y+p2.h)){
                p2.angulo=0;
            }else{
                if(((spe.y>=p2.y-100)&&(spe.y+spe.d<=p2.y))||(spe.y+spe.d<=p2.y+p2.h+100)&&(spe.y>=p2.y+p2.h)){
                    p2.angulo=45;
                }else{
                    p2.angulo=30;
                }
            }
        }
    }catch(Exception e){
        System.out.println(e.getMessage());
        p2.angulo=30;
    }
        
    }
    public static void AI(){
        Circulo xmax=circulos.get(0);
        for(Circulo c:circulos){//obtener circulo mas a la derecha
            if(c.dx>=0){
                if(dif){//solo intentara golpear las pelotas detras de ella si esta dificil
                if((c.x>xmax.x)/*&&(c.x<p2.x-10)*/){
                    xmax=c;
                }
                }else{
                    if((c.x>xmax.x)&&(c.x<p2.x-10)){
                        xmax=c;
                    }
                }
            }
        }
        if(xmax.y>p2.y+(p2.h/2)){//si esta bajo la mitad de la paleta
            for(int i=0;i<5;i++){
                if(p2.y<p2.hmax-p2.h){
                    p2.y=p2.y+1;
                    calcularangulo();
                }          
                 
                for(Circulo c:circulos){
                    golpearpaleta(p2,c);
                }
                
            }
            
        }
        if(xmax.y+xmax.d<p2.y+(p2.h/2)){//si esta sobre la mitad de la paleta
            for(int i=0;i<5;i++){
                if(p2.y>0){
                    p2.y=p2.y-1;
                    calcularangulo();
                }
                 
                for(Circulo c:circulos){
                    golpearpaleta(p2,c);
                }
                
            }
        }
        if(dif){//solo se mueve en horizontal si esta dificil
            if(xmax.x+xmax.d<p2.x){//si esta a la izq de la paleta
                for(int i=0;i<5;i++){
                    if(p2.x>p2.wmax){
                        p2.x=p2.x-1;
                        calcularangulo();
                    }
                     
                    for(Circulo c:circulos){
                        golpearpaleta(p2,c);
                    }
                    
                }
            }
            if(xmax.x+xmax.d>p2.x){//si esta a la der de la paleta
                for(int i=0;i<5;i++){
                    if(p2.x-p2.w<jPanel1.getWidth()){
                        p2.x=p2.x+1;
                        calcularangulo();
                    }
                     
                    for(Circulo c:circulos){
                        golpearpaleta(p2,c);
                    }
                    
                }
            }
        }
    }
    public Ventana() {
        initComponents();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel1.setPreferredSize(new Dimension(500,450));
        jPanel1.setSize(500,450);
        
        score= new JPanel();
        score.setPreferredSize(new Dimension(500,50));
        score.setSize(500,50);
        this.setLayout(new BorderLayout());
        add(jPanel1,BorderLayout.CENTER);
        add(score,BorderLayout.SOUTH);
      }
    public static void main(String args[]) {
            JPanel config= new JPanel();
            config.setLayout(new GridLayout(11,3,5,5));
            
            JLabel ai =new JLabel("Contra AI:");
            JRadioButton si=new JRadioButton("Sì");
            JRadioButton no=new JRadioButton("no");
                ButtonGroup group = new ButtonGroup();
                group.add(si);
                group.add(no);
                si.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        si_stateChanged(e);
                    };
                });
                no.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        si_stateChanged(e);
                    };
                });
                no.setSelected(true);
                
                JLabel difucultad =new JLabel("Dificultad AI:");
                JRadioButton dif=new JRadioButton("Dificil");
                JRadioButton fac=new JRadioButton("Facil");
                    ButtonGroup group2 = new ButtonGroup();
                    group2.add(dif);
                    group2.add(fac);
                    dif.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            dif_stateChanged(e);
                        };
                    });
                    fac.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            dif_stateChanged(e);
                        };
                    });
                    fac.setSelected(true);
            
            JLabel nul =new JLabel("");
            JLabel pri =new JLabel("Primer jugador");
            JLabel seg =new JLabel("segundo jugador");
            JLabel arr =new JLabel("Arriba");
            JLabel aba =new JLabel("Abajo");
            JLabel izq =new JLabel("Izquierda");
            JLabel der =new JLabel("Derecha");
                JLabel hor =new JLabel("Horizontal");
                JLabel ver =new JLabel("Vertical");
                JLabel a30 =new JLabel("30º");
                JLabel a45 =new JLabel("45º");
 
                JTextField tarr =new JTextField(new JTextFieldLimit(1),"w",1);
                JTextField taba =new JTextField(new JTextFieldLimit(1),"s",1);
                JTextField tizq =new JTextField(new JTextFieldLimit(1),"a",1);
                JTextField tder =new JTextField(new JTextFieldLimit(1),"d",1);
                JTextField thor =new JTextField(new JTextFieldLimit(1),"h",1);
                JTextField tver =new JTextField(new JTextFieldLimit(1),"j",1);
                JTextField ta30 =new JTextField(new JTextFieldLimit(1),"k",1);
                JTextField ta45 =new JTextField(new JTextFieldLimit(1),"l",1);
                JTextField tarr2 =new JTextField(new JTextFieldLimit(1),"8",1);
                JTextField taba2 =new JTextField(new JTextFieldLimit(1),"5",1);
                JTextField tizq2 =new JTextField(new JTextFieldLimit(1),"4",1);
                JTextField tder2 =new JTextField(new JTextFieldLimit(1),"6",1);
                JTextField thor2 =new JTextField(new JTextFieldLimit(1),"7",1);
                JTextField tver2 =new JTextField(new JTextFieldLimit(1),"9",1);
                JTextField ta302 =new JTextField(new JTextFieldLimit(1),"1",1);
                JTextField ta452 =new JTextField(new JTextFieldLimit(1),"3",1);
                
                tarr.setName("tarr");
                taba.setName("taba");
                tizq.setName("tizq");
                tder.setName("tder");
                thor.setName("thor");
                tver.setName("tver");
                ta30.setName("ta30");
                ta45.setName("ta45");
                tarr2.setName("tarr2");
                taba2.setName("taba2");
                tizq2.setName("tizq2");
                tder2.setName("tder2");
                thor2.setName("thor2");
                tver2.setName("tver2");
                ta302.setName("ta302");
                ta452.setName("ta452");
                
                
                tarr.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                taba.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                tizq.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                tder.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                thor.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                tver.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                ta30.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                ta45.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                tarr2.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                taba2.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                tizq2.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                tder2.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                thor2.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                tver2.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                ta302.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });
                ta452.addKeyListener(new KeyAdapter() {
                            public void keyPressed(KeyEvent e) {
                                tarr_keyPressed(e);
                            }
                        });

                Sarr=tarr.getText();
                Saba=taba.getText();
                Sizq=tizq.getText();
                Sder=tder.getText();
                Shor=thor.getText();
                Sver=tver.getText();
                Sa30=ta30.getText();
                Sa45=ta45.getText();
                Sarr2=tarr2.getText();
                Saba2=taba2.getText();
                Sizq2=tizq2.getText();
                Sder2=tder2.getText();
                Shor2=thor2.getText();
                Sver2=tver2.getText();
                Sa302=ta302.getText();
                Sa452=ta452.getText();
            config.add(nul);
            config.add(pri);
            config.add(seg);
            config.add(arr);
                config.add(tarr);
                config.add(tarr2);
            config.add(aba);
                config.add(taba);
                config.add(taba2);
            config.add(izq);
                config.add(tizq);
                config.add(tizq2);
            config.add(der);
                config.add(tder);
                config.add(tder2);
            config.add(hor);
                config.add(thor);
                config.add(thor2);
            config.add(ver);
                config.add(tver);
                config.add(tver2);
            config.add(a30);
                config.add(ta30);
                config.add(ta302);
            config.add(a45);
                config.add(ta45);
                config.add(ta452);
            config.add(ai);
                config.add(si);
                config.add(no);
            config.add(difucultad);
                config.add(dif);
                config.add(fac);
            f2.getContentPane().add(config);
            f2.setDefaultCloseOperation(3);
            f2.setBounds(500,0,500,500);
            f2.setVisible(true);
            
            f.getContentPane().add(new Ventana());
            f.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            f.setSize(500,500);
            f.setVisible(true);
            //for(int i=0;i<8;i++){
             p1=new Paleta(20,(jPanel1.getHeight()/3)+25,15,100,(jPanel1.getWidth()/3),jPanel1.getHeight());
             p2=new Paleta((jPanel1.getWidth()-35),(jPanel1.getHeight()/3)+25,15,100,(2*(jPanel1.getWidth()/3)),jPanel1.getHeight());
             synchronized(circulos){
                 Random r=new Random();
                    if(r.nextBoolean()){
                        try {
                            circulos.add(new Circulo(p1.x+p1.w+1,p1.y+(p1.h/2),15,jPanel1.getWidth(),jPanel1.getHeight(),name,0,0));
                        }catch(Exception e){
                            System.out.println(e.getMessage());
                        }
                    }else{
                        try {
                            circulos.add(new Circulo(p2.x-16,p2.y+(p2.h/2),15,jPanel1.getWidth(),jPanel1.getHeight(),name,0,0));
                        }catch(Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                    name++;
             }
            //}
            h2.start();
            h.start();
            }
    private static void tarr_keyPressed(KeyEvent e) {
        JTextField temp =(JTextField)e.getSource();
        temp.setText(String.valueOf(Character.toLowerCase(e.getKeyChar())));
        String name=temp.getName();
        if(name.equals("tarr")){
            Sarr=temp.getText();
        }else{
            if(name.equals("taba")){
                Saba=temp.getText();
            }else{
                if(name.equals("tizq")){
                    Sizq=temp.getText();
                }else{
                    if(name.equals("tder")){
                        Sder=temp.getText();
                    }else{
                        if(name.equals("thor")){
                            Shor=temp.getText();
                        }else{
                            if(name.equals("tver")){
                                Sver=temp.getText();
                            }else{
                                if(name.equals("ta30")){
                                    Sa30=temp.getText();
                                }else{
                                    if(name.equals("tarr2")){
                                        Sarr2=temp.getText();
                                    }else{
                                        if(name.equals("taba2")){
                                            Saba2=temp.getText();
                                        }else{
                                            if(name.equals("tizq2")){
                                                Sizq2=temp.getText();
                                            }else{
                                                if(name.equals("tder2")){
                                                    Sder2=temp.getText();
                                                }else{
                                                    if(name.equals("thor2")){
                                                        Shor2=temp.getText();
                                                    }else{
                                                        if(name.equals("tver2")){
                                                            Sver2=temp.getText();
                                                        }else{
                                                            if(name.equals("ta302")){
                                                                Sa302=temp.getText();
                                                            }else{
                                                                if(name.equals("ta452")){
                                                                    Sa452=temp.getText();
                                                                }else{
                                                                    if(name.equals("ta45")){
                                                                        Sa45=temp.getText();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private static void si_stateChanged(ActionEvent e) {
        JRadioButton temp = (JRadioButton)e.getSource();
        if(temp.getText().equals("no")){
            AI=false;
        }else{
            AI=true;
        }
    }
    private static void dif_stateChanged(ActionEvent e) {
        JRadioButton temp = (JRadioButton)e.getSource();
        if(temp.getText().equals("Dificil")){
            dif=true;
        }else{
            dif=false;
        }
    }
    private void jbInit() throws Exception {
        f.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        f_keyPressed(e);
                    }
                }); 
    }
    private static void f_keyPressed(KeyEvent e) {
            String temp=e.getKeyText(e.getKeyCode()).toLowerCase();
            temp=temp.substring(temp.length()-1);
        if(temp.equals(Sarr)){
            for(int i=0;i<10;i++){
                if(p1.y>0){
                    p1.y=p1.y-1;
                }
                for(Circulo c:circulos){
                    golpearpaleta(p1,c);
                }
                
            }
        }
        if(temp.equals(Saba)){
            for(int i=0;i<10;i++){
                if((p1.y+p1.h)<p1.hmax){
                    p1.y=p1.y+1;
                }
                for(Circulo c:circulos){
                    golpearpaleta(p1,c);
                }
            }
        }
        if(temp.equals(Sizq)){
            for(int i=0;i<10;i++){
                if(p1.x>0){
                    p1.x=p1.x-1;
                }
                for(Circulo c:circulos){
                    golpearpaleta(p1,c);
                }
            }
        }
        if(temp.equals(Sder)){
            for(int i=0;i<10;i++){
                if(p1.x+p1.w<p1.wmax){
                    p1.x=p1.x+1;
                }
                for(Circulo c:circulos){
                    golpearpaleta(p1,c);
                }
            }
        }
        if(temp.equals(Shor)){
            p1.angulo=0;
        }
        if(temp.equals(Sver)){
            p1.angulo=90;
        }
        if(temp.equals(Sa30)){
            p1.angulo=30;
        }
        if(temp.equals(Sa45)){
            p1.angulo=45;
        }
        if(!AI){
                if(temp.equals(Sarr2)){
                    for(int i=0;i<10;i++){
                        if(p2.y>0){
                            p2.y=p2.y-1;
                        }
                        for(Circulo c:circulos){
                            golpearpaleta(p2,c);
                        }
                    }
                }
                if(temp.equals(Saba2)){
                    for(int i=0;i<10;i++){
                        if(p2.y+p2.h<p2.hmax){
                            p2.y=p2.y+1;
                        }
                        for(Circulo c:circulos){
                            golpearpaleta(p2,c);
                        }
                    }
                }
                if(temp.equals(Sizq2)){
                    for(int i=0;i<10;i++){
                        if(p2.x>p2.wmax){
                            p2.x=p2.x-1;
                        }
                        for(Circulo c:circulos){
                            golpearpaleta(p2,c);
                        }
                    }
                }
                if(temp.equals(Sder2)){
                    for(int i=0;i<10;i++){
                        if(p2.x+p2.w<jPanel1.getWidth()){
                            p2.x=p2.x+1;
                        }
                        for(Circulo c:circulos){
                            golpearpaleta(p2,c);
                        }
                    }
                }
                if(temp.equals(Shor2)){
                    p2.angulo=0;
                }
                if(temp.equals(Sver2)){
                    p2.angulo=90;
                }
                if(temp.equals(Sa302)){
                    p2.angulo=30;
                }
                if(temp.equals(Sa452)){
                    p2.angulo=45;
                }
        }
    }
}