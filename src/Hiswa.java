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

    private int nrOfVisitors = 0;
    private int nrOfBuyers = 0;
    private int nrOfBuyersInside = 0;
    private int previousBuyers = 0;

    private Condition allowMoreVisitors;
    private Condition allowMoreBuyers;
    private Condition noMoreVisitors;

    Lock lock;

    public Hiswa()
    {
        lock = new ReentrantLock();
        allowMoreVisitors = lock.newCondition();
        allowMoreBuyers = lock.newCondition();
        noMoreVisitors = lock.newCondition();
    }

    public void buyerEnterHiswa() throws InterruptedException
    {
        lock.lock();

        try {
            nrOfBuyers++;

            while (isBuyerInside())
                allowMoreBuyers.await();

            System.out.println("[HISWA] A buyer entered, waiting for everyone to leave");
            nrOfBuyers--;
            nrOfBuyersInside++;
            previousBuyers++;

        } finally {
            lock.unlock();
        }
    }

    public void enterHiswa() throws InterruptedException
    {
        lock.lock();

        try {
            while (hiswaIsFull() || isBuyerWaiting() || isBuyerInside())
                allowMoreVisitors.await();

            nrOfVisitors++;
            System.out.println("[HISWA] A visitor entered, " +
                    "total visitors: " + nrOfVisitors + ", " +
                    "total buyers: " + nrOfBuyers);

        } finally {
            lock.unlock();
        }
    }

    public void leaveHiswa() throws InterruptedException
    {
        lock.lock();

        try {
            if (Thread.currentThread() instanceof Kijker)
                nrOfVisitors--;
            else if (Thread.currentThread() instanceof Koper)
                nrOfBuyersInside--;

            if (!isVisitorInside())
                noMoreVisitors.signal();

            if (isBuyerWaiting() && previousBuyers < MAX_BUYERS)
                allowMoreBuyers.signal();
            else
            {
                previousBuyers = 0;
                allowMoreVisitors.signalAll();
            }

            System.out.println("[HISWA] " + Thread.currentThread().getName() + " left, " +
                    "remaining visitors: " + nrOfVisitors + ", " +
                    "remaining buyers: " + nrOfBuyers);

        } finally {
            lock.unlock();
        }
    }

    public void buyBoat() throws InterruptedException
    {
        lock.lock();

        try {
            while(isVisitorInside())
                noMoreVisitors.await();

            System.out.println("[HISWA] " + Thread.currentThread().getName() + " bought a boat");

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

    private boolean isBuyerInside()
    {
        return nrOfBuyersInside > 0;
    }

    private boolean isVisitorInside()
    {
        return nrOfVisitors > 0;
    }
}
