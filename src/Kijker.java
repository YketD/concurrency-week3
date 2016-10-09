/**
 * Created by yketd on 29-9-2016.
 */
public class Kijker extends Thread
{
    private Hiswa hiswa;

    public Kijker(String name, Hiswa hiswa)
    {
        super(name);
        this.hiswa = hiswa;
    }

    @Override
    public void run()
    {
        while (true) {
            try{
                justLive();

                // Let's get the visitor waiting
                System.out.println("[" + getName() + "] Decides to go to HISWA");
                hiswa.enterHiswa();
                Thread.sleep(1000);

                // Give visitor some time to look at boats
                System.out.println("[" + getName() + "] Is looking at boats");
                lookAtBoats();

                // Let him leave
                hiswa.leaveHiswa();
            } catch (InterruptedException e){}
        }
    }

    private void justLive()
    {
        try {
            Thread.sleep((int)(Math.random() * 10000));
        } catch (InterruptedException e) {}
    }

    private void lookAtBoats()
    {
        try {
            Thread.sleep((int)(Math.random() * 20000));
        } catch (InterruptedException e) {}
    }
}
