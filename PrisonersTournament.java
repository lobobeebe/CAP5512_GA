public class PrisonersTournament extends FitnessFunction
{
    private int mNumGenes;
    private int mGeneSize;

    PrisonersTournament(int numGenes, int geneSize)
    {
        super("Iterated Prisoner's Dilemma");

        mNumGenes = numGenes;
        mGeneSize = geneSize;

        // For the IPD, limit possible size vs num
        // TODO: Beef up this explanation when not tired
        if (Math.pow(2, mGeneSize) != mNumGenes * mGeneSize)
        {
            System.err.println("Invalid (NumGenes, GeneSize) pair: (" + mNumGenes + ", " + mGeneSize + ")");
        }
    }
    
    public void doRawFitness(Chromo X)
    {
        // Create player strategy with chromosome
        StrategyMixed player = new StrategyMixed();
        player.decodeChromoToStrategy(X);

        Strategy opponent;

        // TODO: Switch what type of strat to play
        opponent = new StrategyTitForTat();

        if (opponent != null)
        {
            X.setRawFitness(getIteratedPrisonersDilemmaScore(player, opponent));
        }
        else
        {
            System.err.println("Cannot perform IPD against invalid opponent.");
        }
    }

    private int getIteratedPrisonersDilemmaScore(Strategy player, Strategy opponent)
    {
        // Play IPD
        IteratedPD game = new IteratedPD(player, opponent);
        // TODO: Make this configurable
        game.runSteps(100);

        // TODO: Decide on Fitness evaluation from IPD
        return game.player1Score();
    }

}
