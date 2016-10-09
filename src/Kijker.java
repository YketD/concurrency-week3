/**
 * Created by yketd on 29-9-2016.
 */
public class Kijker extends Thread {
    boolean kijkerEntered = false;

    Hiswa hiswa;

    public Kijker(Hiswa hiswa){
        this.hiswa = hiswa;
    }

    public void run(){
        while(true) {
            justLive();
            hiswa.enter(10);
            kijk();
            hiswa.leave();
        }
    }

    public void justLive(){
        try {
            Thread.sleep((int) (Math.random() * 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void kijk(){
        try {
            Thread.sleep((int) (Math.random() * 2000));
            System.out.println("gekeken");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            hiswa.leave();
        }
    }
}
