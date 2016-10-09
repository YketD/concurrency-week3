/**
 * Created by yketd on 29-9-2016.
 */
public class Koper extends Thread
{
    private Hiswa hiswa;

    public Koper(String name, Hiswa hiswa)
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

                System.out.println("[" + getName() + "] Decides to go to HISWA");
                hiswa.buyerEnterHiswa();

                System.out.println("[" + getName() + "] Is purchasing a boat");
                buyABoat();

                hiswa.leaveHiswa();

                break;
            } catch (InterruptedException e){}
        }
    }

    private void justLive()
    {
        try {
            Thread.sleep((int)(Math.random() * 60000) + 10000);
        } catch (InterruptedException e) {}
    }

    private void buyABoat()
    {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {}
    }
}
