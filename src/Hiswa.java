import javax.management.monitor.Monitor;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yketd on 29-9-2016.
 */
public class Hiswa extends Monitor {
    Lock lock;

    private static int kijkerTickets = 8;
    private static int kijkersBinnen = 0;

    private Queue<Koper> kopersWaiting;
    private Queue<Kijker> kijkersWaiting;

    private boolean koperWaiting = false;
    private  int kopersBinnen = 0;

    private Condition hiswaEmpty, kijkersToegang;

    public Hiswa(){
        lock = new ReentrantLock();
        hiswaEmpty = lock.newCondition();
    }
    @Override
    public void start() {

    }

    public boolean enter(int entree) {
        if (entree == 250000) {
            try{
                koperApplies();}
            catch (InterruptedException ie){
                System.err.println(ie.getMessage());
            }
        }
        else{
            try {
                kijkerApplies();
            }catch (InterruptedException ie){
                System.err.println(ie.getMessage());
            }
        }
        return true;
    }


    public void kijkerApplies() throws InterruptedException {
        while (koperIsWaiting())
            kijkersToegang.await();
        System.out.println("Kijkers mogen naar binnen");
        kijkersBinnen++;
    }



    public void koperApplies() throws InterruptedException{
        if (!hiswaIsEmpty()) {
            hiswaEmpty.await();
        }


            kopersBinnen --;
            if (kopersWaiting.isEmpty() && hiswaIsEmpty() ) {
                kijkersToegang.signal();
            }

    }


    @Override
    public void stop() {
    }
    public boolean hiswaIsEmpty(){
        return kijkersBinnen == 0 && kopersBinnen == 0;
    }

    public boolean koperIsWaiting(){
        return !kopersWaiting.isEmpty();
    }


    public void koperLeaves(){
        kopersBinnen --;
        if (kopersWaiting.isEmpty() && hiswaIsEmpty() ) {
            kijkersToegang.signal();
        }
    }
    public void leave(){
        kijkersBinnen --;
    }
}
