public class chess {
    private int x;
    private int y;
    private int state; // 1 for human, -1 for ai, 0 for null

    /**
     * for security, constructor
     * @param x
     * @param y
     * @param state
     */
    public chess(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }
    public int getX() {
        return x;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }

    
    
}
