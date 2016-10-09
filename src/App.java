/**
 * Created by yketd on 29-9-2016.
 */
public class App {
    static int KOPER_AMT = 8;
    static int KIJKER_AMT = 20;
    public static void main(String[] args) {
        new App().run();
    }
    Thread koper[];
    Thread kijker[];
    Hiswa hiswa;


    public void run(){
        koper = new Thread[KOPER_AMT];
        kijker = new Thread[KIJKER_AMT];
        hiswa = new Hiswa();
        hiswa.start();

        for (int i = 0; i < KOPER_AMT ; i++){
            koper[i] = new Koper(hiswa);
            koper[i].start();
        }

        for (int i = 0; i < KIJKER_AMT ; i++){
            kijker[i] = new Kijker(hiswa);
            kijker[i].start();
        }
    }
}
