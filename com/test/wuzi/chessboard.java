import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import  java.awt.Graphics2D;
import javax.swing.JPanel;



public class chessboard extends JPanel{
    private static final int screensize = 15;
    private static final int margin = 20;
    private List<chess> chesslist= new ArrayList<chess>();//store each object in to a list
    private int[][] location = new int[screensize][screensize];//store the location already has chess
    private int[][] scores = new int[screensize][screensize];//store the score at each location
    /**
     * 
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawboard(g);
        drawchess(g);
    }

    /**
     * 
     * @param g
     */
    private void drawboard(Graphics g) {
        
        for (int i = 0; i < screensize; i++) {
            g.drawLine(margin, margin+getblocksize()*i, getWidth()-margin, margin+getblocksize()*i);//horizontal line 
            g.drawLine(margin+getblocksize()*i, margin, margin+getblocksize()*i, getHeight()-margin);//vertial line
        }
    }
    public void drawchess(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        float[] f = new float[]{0.1f, 0.9f};
        Color[][] c = new Color[][]{{Color.WHITE, Color.GRAY},{Color.BLACK, Color.LIGHT_GRAY}};
        for (int i = 0; i < chesslist.size(); i++) {
            chess ch = chesslist.get(i);//get chess object
            if (ch.getState() == 1) {//human
                RadialGradientPaint Paint1 = new RadialGradientPaint(ch.getX()*getblocksize()+margin, ch.getY()*getblocksize()+margin, getblocksize(), f, c[1]);
                g2.setPaint(Paint1);
            }else{
                RadialGradientPaint Paint2 = new RadialGradientPaint(ch.getX()*getblocksize()+margin, ch.getY()*getblocksize()+margin, getblocksize(), f, c[0]);
                g2.setPaint(Paint2);
        
            }
            
            g2.fillOval(ch.getX()*getblocksize()+margin-getblocksize()/2, ch.getY()*getblocksize()+margin-getblocksize()/2, getblocksize(), getblocksize());
        }
    }
    public int getblocksize(){
        return ((getWidth()-2*margin)/(screensize-1));
    }
    public void putchess(chess chesses){
        chesslist.add(chesses);
        location[chesses.getX()][chesses.getY()]=chesses.getState();
        repaint();
    }
    public boolean checkchess (int x, int y){//check whether there is a chess at location x and y
        if (x>=0 && x<=screensize && y>=0 && y<=screensize && location[x][y]==0){
            return true;
        }else{
            return false;
        }
    }
    public boolean winorloss(int x, int y, int owner){//more efficient than check way of win in whole board
        int sum = 0;
        for (int i = x-1; i >=0; i--) {//horizontal left
            if (location[i][y] == owner){
                sum++;
            }else{
                break;
            }
        }
        for (int i = x+1; i <=screensize; i++) {//horizontal right
            if (location[i][y] == owner){
                sum++;
            }else{
                break;
            }
        }
        if(sum>=4){
            return true;
        }
        sum=0;
        for (int i = y-1; i >= 0; i--) {//vertial up
            if(location[x][i] == owner){
                sum++;
            }else{
                break;
            }
        }
        for (int i = y+1; i <= screensize; i++) {//vertial down
            if(location[x][i] == owner){
                sum++;
            }else{
                break;
            }
        }
        if(sum>=4){
            return true;
        }
        sum=0;
        for (int i = x-1, j = y-1; i >= 0 && j >= 0; i--, j--) {//left up
            if(location[i][j] == owner){
                sum++;
            }else{
                break;
            }
        }
        for (int i = x+1, j = y+1; i <= screensize && j <= screensize; i++, j++) {//right down
            if(location[i][j] == owner){
                sum++;
            }else{
                break;
            }
        }
        if(sum>=4){
            return true;
        }
        sum=0; 
        for (int i = x+1, j = y-1; i <= screensize && j >= 0; i++, j--) {//left down
            if(location[i][j] == owner){
                sum++;
            }else{
                break;
            }
        }
        for (int i = x-1, j = y+1; i >= 0 && j <= screensize; i--, j++) {//right up
            if(location[i][j] == owner){
                sum++;
            }else{
                break;
            }
        }
        if(sum>=4){
            return true;
        }
        sum=0;
        return false;
    }
    public int calculatescore(int humanscore, int AIscore){//score map
        if (humanscore > 0 && AIscore > 0){
            return 0;
        }
        if(humanscore == 0 && AIscore == 0){//no chess in each win way
            return 7;
        } 
        if(AIscore == 1){
            return 35;
        }
        if(AIscore == 2){
            return 800;
        } 
        if(AIscore == 3){
            return 15000;
        }
        if(AIscore == 4){
            return 800000;
        }
        if(humanscore == 1){
            return 15;
        }
        if(humanscore == 2){
            return 400;
        }
        if(humanscore == 3){
            return 1800;
        }
        if(humanscore == 4){
            return 100000;
        }
        return -1; //fore extreme case, almost impossible
    }
    public chess searchlocation(){
        for (int i = 0; i < screensize; i++) {
            for (int j = 0; j < screensize; j++) {
                scores[i][j] = 0;
            }
        }
        int humanchessnum = 0;
        int AIchessnum = 0;
        long changescore = 0; //temporary score

        int goalx = -1;
        int goaly = -1;
        int maxS = -1;//meaximum score
        for (int i = 0; i < screensize; i++) {//vertial score set
            for (int j = 0; j < screensize-4; j++) {
                for (int k = j; k < j+5; k++) {
                    if (location[i][k] == -1) {
                        AIchessnum++;
                    }else if(location[i][k] == 1){
                        humanchessnum++;
                    } 
                }
                changescore = calculatescore(humanchessnum, AIchessnum);
                for (int j2 = j; j2 < j+5; j2++) {
                    scores[i][j2] += changescore;
                }
                humanchessnum = 0;
                AIchessnum = 0;
                changescore = 0;
            }
            
        }
        for (int i = 0; i < screensize; i++) {//horizontal score set
            for (int j = 0; j < screensize-4; j++) {
                for (int k = j; k < j+5; k++) {
                    if (location[k][i] == -1) {
                        AIchessnum++;
                    }else if(location[k][i] == 1){
                        humanchessnum++;
                    } 
                }
                changescore = calculatescore(humanchessnum, AIchessnum);
                for (int j2 = j; j2 < j+5; j2++) {
                    scores[j2][i] += changescore;
                }
                humanchessnum = 0;
                AIchessnum = 0;
                changescore = 0;
            }
            
        }
        for (int i = screensize-1; i >= 4; i--) {//leftdown upside
            for (int k = i, j = 0; j < screensize && k >= 4; j++, k--) {
                for(int m=k, n=j; m>=k-4; m--, n++){//five chess
                    if (location[m][n] == -1) {
                        AIchessnum++;
                    }else if (location[m][n] == 1){
                        humanchessnum++;
                    }
                }
                changescore = calculatescore(humanchessnum, AIchessnum);
                for (int m = k, n = j; m >= k-4; m--, n++) {
                    scores[m][n] += changescore;
                }
                humanchessnum = 0;
                AIchessnum = 0;
                changescore = 0;
            }
        }
        for (int i = 1; i <= screensize-1; i++) {//leftdown downside
            for (int k = i, j = screensize-1; j >= 0 && k < screensize-4; j--, k++) {
                for(int m=j, n=k; m>=j-4; m--, n++){//five chess
                    if (location[m][n] == -1) {
                        AIchessnum++;
                    }else if (location[m][n] == 1){
                        humanchessnum++;
                    }
                }
                changescore = calculatescore(humanchessnum, AIchessnum);
                for (int m=j, n=k; m>=j-4; m--, n++) {
                    scores[m][n] += changescore;
                }
                humanchessnum = 0;
                AIchessnum = 0;
                changescore = 0;
            }
        }
        for (int i = 0; i <= screensize-4; i++) {//leftup upside
            for (int k = i, j = 0; j < screensize && k < screensize-4; j++, k++) {
                for(int m=k, n=j; m<=k+4; m++, n++){//five chess
                    if (location[m][n] == -1) {
                        AIchessnum++;
                    }else if (location[m][n] == 1){
                        humanchessnum++;
                    }
                }
                changescore = calculatescore(humanchessnum, AIchessnum);
                for (int m=k, n=j; m<=k+4; m++, n++) {
                    scores[m][n] += changescore;
                }
                humanchessnum = 0;
                AIchessnum = 0;
                changescore = 0;
            }
        }
        for (int i = 1; i <= screensize-5; i++) {//leftup downside
            for (int k = i, j = 0; j < screensize && k < screensize-4; j++, k++) {
                for(int m=j, n=k; n<=k+4; m++, n++){//five chess
                    if (location[m][n] == -1) {
                        AIchessnum++;
                    }else if (location[m][n] == 1){
                        humanchessnum++;
                    }
                }
                changescore = calculatescore(humanchessnum, AIchessnum);
                for (int m=j, n=k; n<=k+4; m++, n++) {
                    scores[m][n] += changescore;
                }
                humanchessnum = 0;
                AIchessnum = 0;
                changescore = 0;
            }
        }
        for (int i = 0; i < screensize; i++) {// find max score at null location
            for (int j = 0; j < screensize; j++) {
                if (location[i][j] == 0 && scores[i][j] > maxS) {
                    goalx = i;
                    goaly = j;
                    maxS = scores[i][j];
                }
            }
        }
        return new chess(goalx, goaly, -1);

    }
    public void clear(){
        chesslist = new ArrayList<chess>();
        location = new int[screensize][screensize];//store the location already has chess
        scores = new int[screensize][screensize];
        repaint();
    }
}
