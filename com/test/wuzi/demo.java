import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

public class demo {
    private JFrame jf= new JFrame();
    private chessboard cb= new chessboard();
    private int owner = 1;//1 for human, 0 for null, -1 for AI
    public void init(){
        jf.setTitle("gobang");
        jf.setSize(518, 508);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(cb);
        jf.setVisible(true);
        cb.addMouseListener(new MouseInputAdapter(){//mouse detect
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                play(e);
            }
        });
        
    }
    
    /**
     * 
     * @param e
     */
    public void play(MouseEvent e){
        int blocksize= cb.getblocksize();
        int x =(e.getX()-5)/blocksize;//make sure put chess on the cross
        int y =(e.getY()-5)/blocksize;
        boolean restart = true;
        if (cb.checkchess(x, y)){
            cb.putchess(new chess(x, y, owner));
            if (cb.winorloss(x, y, owner)){
                JOptionPane.showMessageDialog(jf, "YOU WIN!!!", "Finished", JOptionPane.PLAIN_MESSAGE);// alert
                cb.clear();
                restart = false;
            }
            if(restart){
                chess loc = cb.searchlocation();
                cb.putchess(loc);
                if (cb.winorloss(loc.getX(), loc.getY(), loc.getState())){
                    JOptionPane.showMessageDialog(jf, "YOU LOSS!!!", "Finished", JOptionPane.PLAIN_MESSAGE);// alert
                    cb.clear();
            }
        }
            
        }
        
    }
    public static void main(String[] args) {
        new demo().init();
    }
    
}