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

                hiswa.buyBoat();
                hiswa.leaveHiswa();
            } catch (InterruptedException e){}
        }
    }

    private void justLive()
    {
        try {
            Thread.sleep((int)(Math.random() * 30000) + 10000);
        } catch (InterruptedException e) {}
    }
}
