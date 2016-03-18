import java.io.FileWriter;

public class PrisonersTournament extends FitnessFunction
{
    PrisonersTournament()
    {
        super("Iterated Prisoner's Dilemma");
    }
    
    public void doRawFitness(Chromo X)
    {
        X.rawFitness = 0;

        // Create player strategy with chromosome
        StrategyMixed player = new StrategyMixed(iterationsRemembered);
        player.setStrategy(X.chromo);
        
        Strategy opponent = null;
        
        // TODO: Switch what type of strat to play
        opponent = new StrategyTitForTat();
        
        X.rawFitness = getIteratedPrisonersDilemmaScore(player, opponent);
    }

    private int getIteratedPrisonersDilemmaScore(Strategy player, Strategy opponent)
    {
        // Play IPD With Player1 and Player2
        IteratedPD game = new IteratedPD(player1, player2);
        
        // TODO: Decide on Fitness evaluation from IPD
        return ipd.player1Score();
    }

}
