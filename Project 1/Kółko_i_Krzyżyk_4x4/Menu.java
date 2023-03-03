import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu implements MouseListener {
   //deklaracja wszystkich zmiennych:
   Drawing draw = new Drawing();
   ImageIcon beginPic;
   boolean pastBeginning = false;


   //konstruktor
   public Menu() {
      JFrame frame = new JFrame("Menu");
      beginPic = new ImageIcon("background.jpg");
      frame.add(draw);
      draw.addMouseListener(this);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(420, 480);
      frame.setVisible(true);
   }

   class Drawing extends JComponent {
      public void paint(Graphics g) {
         Graphics2D g2 = (Graphics2D) g;

         //wyświetl screen menu
         if (!pastBeginning)
            g.drawImage(beginPic.getImage(),0,0,this);

      }
   }

   // --> implementacja MouseListener - 5 metod
   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
      // znajdź współrzędne
      int row = e.getX();
      int col = e.getY();

      draw.repaint();
      new KiK4x4();
   }

   public void mouseClicked(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }
// koniec implementacji MouseListener  <---

   public static void main(String[] args) {
      new Menu();
   } //główna metoda
}