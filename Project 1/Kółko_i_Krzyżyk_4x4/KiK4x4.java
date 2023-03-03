import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KiK4x4 implements MouseListener
{
   //deklaruje wszystkie mozliwosci:
   Drawing draw = new Drawing();
   int x, y;
   int [][] board = new int [4][4];
   boolean xTurn = true;
   boolean win = false;
   JFrame frame;
   JLabel message = new JLabel("###--- Ruch Gracza 1 ---###");
   ImageIcon[] boardPictures = new ImageIcon[3];
   int countClick = 0;          //liczy ile razy gracz kliknal myszka po wygranej, dzieki temu program wie kiedy się zresetowac

   public KiK4x4()      //konstruktor
   {
      for (int i = 0; i < boardPictures.length; i++)
         this.boardPictures[i] = new ImageIcon(i + ".jpg");
      this.frame = new JFrame("Kółko i krzyżyk 4x4");
      this.frame.add(draw);
      this.draw.addMouseListener(this);
      this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      //jezeli gracz zamknie okno w javie, zamyka sie tylko biezaca rozgrywka, nie cala gra

      this.frame.setSize(420, 465);
      this.message.setFont(new Font("Dialog",Font.BOLD,20));
      this.message.setForeground(Color.black);
      this.message.setHorizontalAlignment(SwingConstants.CENTER);
      this.frame.add(message, "North");
      this.frame.setVisible(true);
   }

   //metoda ktora sprawdza czy plansza jest pelna czy pusta
   public boolean fullBoard()
   {
      int countBlank=0; //inicjalizuje ilosc pustych miejsc na planszy do zera
      //przeglada cala plansze i zlicza ilosc pustych miejsc
      for(int r=0;r<4;r++)
      {
         for(int col=0;col<4;col++)
         {
            if(this.board[r][col]==0)
               countBlank++;
         }
      }
      //jezeli plansza nie ma pustych miejsc to znaczy, ze jest pelna
      //zwraca true, jeśli plansza ma 0 pustych miejsc, a false w przeciwnym razie
      return(countBlank==0);
   }

   //metoda ktora sprawdza czy ktorys z graczy juz wygral
   public boolean checkForWin()
   {
      int count;  //zlicza ilosc par tych samych znakow w kazdym wierszu, kolumnie i przekatnej

      //sprawdza kazdy z wierszy planszy
      for (int r=0; r < 4; r++)
      {
         count=0; //inicjalizuje licznik na 0, gdy sprawdza nowy wiersz
         for (int c=0; c < 3; c++)
         {
            //sprawdza czy miejsce w wierszu ma taki sam znak na miejscu w tym wierszu obok
            //konczy sprawdzac jesli [row,0] == [row,1] && [row,1] == [row,2] && [row,2] == [row,3]
            if (this.board[r][c] == this.board[r][c+1] && this.board[r][c] != 0)
               count++;
         }
         //jeśli 3 pary miejsc w tym rzędzie są równe, to znaczy,
         //że wszystkie znaczniki w tym rzędzie są takie same, co oznacza, że ktoś wygrał,
         //a następnie zmienia wartość boolean win na true
         if (count == 3)
            this.win = true;
      }

      //sprawdza kazda kolumne na planszy
      for (int c=0; c < 4; c++)
      {
         count=0; //inicjalizuje licznik na 0, gdy sprawdza nową kolumnę
         for (int r=0; r < 3; r++)
         {
            //sprawdza, czy miejsce w kolumnie jest równe następnemu miejscu w tej kolumnie i
            // kończy sprawdzanie jesli
            //[0, column] == [1,column] && [1,column] == [2,column] && [2,column] == [3,column]
            if (this.board[r][c] == this.board[r+1][c] && this.board[r][c] != 0)
               count++;
         }
         //jeśli 3 pary miejsc w tej kolumnie są równe, to znaczy, że wszystkie znaczniki w tej kolumnie
         //są takie same, czyli ktoś wygrał i następnie zmienia wartość boolean win na true
         if (count == 3)
            this.win = true;

      }

      //sprawdza, czy każdy znacznik na przekątnej góra - prawo - dół - lewo jest równy jeśli są,
      //to zmienia wartość booleanu win na true
      if (this.board[0][3] == this.board[1][2] && this.board[1][2] == this.board[2][1] && this.board[2][1] == this.board[3][0] && this.board[3][0] != 0)
         this.win = true;

      //sprawdza, czy każdy znacznik na przekątnej góra lewo - dół prawo jest równy jeśli są,
      //to zmienia wartość booleanu win na true
      if (this.board[0][0] == this.board[1][1] && this.board[1][1] == this.board[2][2] && this.board[2][2] == this.board[3][3] && this.board[3][3] != 0)
         this.win = true;

      //jeśli win jest prawdziwe, to wypisz odpowiednią wiadomość o wygranej
      if (this.win)
      {
         //kiedy gdy gracz odejdzie, a boolean xTurn zmieni wartość, to jeśli xTurn = false,
         //X wygrywa, a jeśli xTurn = true, 0 wygrywa
         if (this.xTurn)
            this.message.setText("###--- Gracz 2 wygrywa ---###");
         else
            this.message.setText("###--- Gracz 1 wygrywa ---###");
      }
      //zwraca wartość booleanu win
      return(this.win);
   }
   // umieszcza znacznik aktualnego gracza na wybranym polu, jeśli to możliwe sprawdza,
   //czy gra została wygrana i jeśli gra jeszcze się nie zakończyła informuje drugiego gracza,
   //że nadeszła jego kolej
   public void markSquare(int row, int col)
   {
      if (!(checkForWin()))         //wypuścić gracza, jeśli nikt jeszcze nie wygrał
      {
         if (this.board[row][col] == 0)  //pozwala graczowi wybrać miejsce tylko wtedy, gdy jest ono puste
         {
            if (this.xTurn)              //jeśli nadeszła kolej X:
            {
               this.board[row][col] = 1; //zmienia wartość tablicy na 1
               this.xTurn = false;       //xTurn staje się fałszywa, więc to jest kolej O.

               //jeśli nikt jeszcze nie wygrał, zmień wiadomość o grze z powrotem na turę O's
               if (!(checkForWin()))
                  this.message.setText("###--- Ruch Gracza 2 ---###");
            }
            else                    //jeśli przyjdzie kolej na O:
            {
               this.board[row][col] = 2; //zmienia wartość tablicy na 2
               this.xTurn = true;        //xTurn staje się ponownie prawdą, więc to jest kolej X.
               //jeśli nikt jeszcze nie wygrał, zmień wiadomość o grze z powrotem na turę X'a
               if (!(checkForWin()))
                  this.message.setText("###--- Ruch Gracza 1 ---###");
            }
         }
      } //metoda markSquare

      //jeśli plansza jest pełna, ale nikt nie wygrał, to wypisywany jest komunikat o remisie
      if (fullBoard())
      {
         if (!(checkForWin())) {
            this.message.setText("###--- Remis ---###");
            win = true;
         }
      }

   }

   class Drawing extends JComponent
   {
      public void paint(Graphics g)
      {
         Graphics2D g2 = (Graphics2D) g;
         // losuje zawartość planszy (odświeża się co turę)
         for (int row = 0; row < 4; row++)
            for (int col = 0; col < 4; col++)
               g.drawImage(boardPictures[board[row][col]].getImage(),col * 100, row * 100,100,100,this);
         // rysuje siatkę

         g2.setStroke(new BasicStroke(10));
         for (int i = 1; i < 4; i++) {
            g.fillRect(i*100,5,5,395);
            g.fillRect(5,i*100,395,5);
         }

         //Jeżeli wszystkie wartości wiersza/kolumny/diagonali są równe, a co najmniej 1 nie jest równe 0, rysuje linię
         //przez ten rząd/kolumnę/diagonę, wykazuje wygraną
         for (int i = 0; i < 4; i++){
            if(board[i][0] != 0){ // Sprawdzamy poziom
               for(int j = 1; j < 4; j++) {
                  if (board[i][0] != board[i][j])
                     break;
                  if (j == 3)
                     g.drawLine(0, 50+i*100, 395, 50+i*100);
               }
            }
            if(board[0][i] != 0){ // Sprawdzamy pion
               for(int j = 1; j < 4; j++) {
                  if (board[0][i] != board[j][i])
                     break;
                  if (j == 3)
                     g.drawLine(50+i*100,0 , 50+i*100,395);
               }
            }
         }
         if(board[0][0] != 0){ // Sprawdzamy ukośny prawy
            for (int i = 1; i < 4; i++) {
               if(board[0][0] != board[i][i])
                  break;
               if(i==3)
                  g.drawLine(0,0,395,395);
            }
         }
         if(board[3][0] != 0){ // Sprawdzamy ukośny lewy
            for (int i = 1; i < 4; i++) {
               if(board[3][0] != board[3-i][i])
                  break;
               if(i==3)
                  g.drawLine(10,395,395,10);
            }
         }
      }
   }

   // --> implementuje MouseListener - 5 metod
   public void mousePressed(MouseEvent e)
   {
   }

   //zajmuje się wszystkimi kliknięciami myszki
   public void mouseReleased(MouseEvent e)
   {
      if (!this.win)
      {
         // znajduje współrzędne kliknięcia myszką
         int row = e.getY()/100;
         int col = e.getX()/100;
         // obsługiwanie ruchu, który gracz wykonał na planszy gry
         markSquare(row, col);
         //uzyskuje punkt, który zostanie wywołany w celu odzwierciedlenia kliknięcia myszą
         this.draw.repaint();
      } else {
         this.frame.dispose();
      }
   }

   public void mouseClicked(MouseEvent e)
   {
   }

   public void mouseEntered(MouseEvent e)
   {
   }

   public void mouseExited(MouseEvent e)
   {
   }
   //kończy implementacje MouseListener  <---
   public static void main(String[] args)
   {
      new KiK4x4();
   }
}//klasa KiK4x4