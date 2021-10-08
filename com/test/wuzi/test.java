public class test {
    private static final int screensize = 15;
    public static void main(String[] args) {
        for (int i = 1; i <= screensize-1; i++) {//leftdown downside
            for (int k = i, j = screensize-1; j >= 0 && k < screensize-4; j--, k++) {
                for(int m=j, n=k; m>=j-4; m--, n++){
                    System.out.println(m+"__"+n);
                };
                System.out.println("_______________");
            };
        };
        

    }
}