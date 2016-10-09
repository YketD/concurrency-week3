import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yketd on 29-9-2016.
 */
public class Hiswa
{
    private static final int MAX_VISITORS = 10;
    private static final int MAX_BUYERS = 4;
    private int BUYERS_ENTERED = 0;

    private int nrOfVisitors = 0;
    private int nrOfBuyers = 0;

    private int amtOfVisitorsWaiting = 0;

    private Condition allowMoreVisitors;
    private Condition hiswaIsEmpty;

    Lock lock;

    public Hiswa()
    {
        lock = new ReentrantLock();
        allowMoreVisitors = lock.newCondition();
        hiswaIsEmpty = lock.newCondition();
    }


    public void buyerEnterHiswa() throws InterruptedException
    {
        lock.lock();

        try {
            System.out.println("[HISWA] A buyer entered, waiting for everyone to leave");
            nrOfBuyers++;
            hiswaIsEmpty.await();
        } finally {
            lock.unlock();
        }
    }

    public void enterHiswa() throws InterruptedException
    {
        lock.lock();

        try {
            while (hiswaIsFull() || isBuyerWaiting() && BUYERS_ENTERED !=4) {
                allowMoreVisitors.await();
            }

            nrOfVisitors++;
            System.out.println("[HISWA] A visitor entered, total visitors: " + nrOfVisitors);


        } finally {
            lock.unlock();
        }
    }

    public void leaveHiswa() throws InterruptedException
    {
        lock.lock();

        try {
            if (Thread.currentThread() instanceof Kijker){
                nrOfVisitors--;
                if (nrOfVisitors == 0){hiswaIsEmpty.signal();}
                System.out.println("[HISWA] " + Thread.currentThread().getName() + " left, remaining visitors: " + nrOfVisitors);
            }
            else if (Thread.currentThread() instanceof Koper) {
                nrOfBuyers--;
                BUYERS_ENTERED++;
                System.out.println("[HISWA] " + Thread.currentThread().getName() + " left, amt of buyers visited: " + BUYERS_ENTERED);
                if (BUYERS_ENTERED == MAX_BUYERS) {
                    System.out.println("max amt of buyers have bought a boat, letting visitors in");
                    for (int i = 0 ; i < MAX_VISITORS ; ){
                        allowMoreVisitors.signal();
                    }
                    BUYERS_ENTERED = 0;
                }   else if(nrOfBuyers > 1){
                    hiswaIsEmpty.signal();
                }
            }





        } finally {
            lock.unlock();
        }
    }

    private boolean hiswaIsFull()
    {
        return nrOfVisitors == MAX_VISITORS;
    }

    private boolean isBuyerWaiting()
    {
        return nrOfBuyers > 0;
    }
}
