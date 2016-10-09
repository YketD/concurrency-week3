/**
 * Created by yketd on 29-9-2016.
 */
public class App
{
    private static final int VISITOR_POPULATION = 2; //15
    private static final int BUYER_POPULATION = 4; //8

    public static void main(String[] args)
    {
        System.out.println("Initializing HISWA");

        Hiswa hiswa = new Hiswa();
        Thread[] kijker = new Thread[VISITOR_POPULATION];
        Thread[] koper = new Thread[BUYER_POPULATION];

        System.out.println("Starting threads");

        for(int i = 0; i < VISITOR_POPULATION; i++)
        {
            kijker[i] = new Kijker("Visitor " + i, hiswa);
            kijker[i].start();
        }

        for(int i = 0; i < BUYER_POPULATION; i++)
        {
            koper[i] = new Koper("Buyer " + i, hiswa);
            koper[i].start();
        }
    }
}
