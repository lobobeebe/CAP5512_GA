/**
 * Class containing iterated Prisoner's Dilemma (IPD).
 * @author	081028AW
 */
public class IteratedPD extends Object
{
    /**
     * Iterated Prisoner's Dilemma.
     */
    int verbose = 0;

    PrisonersDilemma pd;
    Strategy p1, p2;
    double p1Score;
    double p2Score;

    public IteratedPD(Strategy player1, Strategy player2)
    {
        this.p1 = player1;
        this.p2 = player2;

        pd = new PrisonersDilemma(p1, p2);
        p1Score = 0;
        p2Score = 0;

        if(verbose == 1) {
            System.out.printf(" Player 1 is %s, Player 2 is %s\n",
                    p1.getName(), p2.getName());
        }
    }  /* IteratedPD */

    public void runSteps(int maxSteps)
    {
        int i;

        for (i=0; i<maxSteps; i++)
        {
            pd.playPD();
            p1Score += pd.getPlayer1Payoff();
            p2Score += pd.getPlayer2Payoff();
        }

        p1Score = p1Score / (double) maxSteps;
        p2Score = p2Score / (double) maxSteps;
    }

    public double player1Score()
    {
        return p1Score;
    }

}  /* class IteratedPD */

