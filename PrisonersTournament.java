import java.util.ArrayList;
import java.util.Random;

public class PrisonersTournament extends FitnessFunction
{
    private static final String AXELROD_ORIGINAL_STRATEGY = "AXELROD_ORIGINAL";
    private static final String AXELROD_PROBABILITY_STRATEGY = "AXELROD_PROBABILISTIC";
    private static final String AXELROD_TIT_FOR_TAT_STRATEGY = "AXELROD_TIT_FOR_TAT";
    private static final String TIT_FOR_TAT_STRATEGY = "TIT_FOR_TAT";
    private static final String TIT_FOR_TWO_TATS_STRATEGY = "TIT_FOR_TWO_TATS";
    private static final String ALWAYS_COOPERATE_STRATEGY = "ALWAYS_COOPERATE";
    private static final String ALWAYS_DEFECT_STRATEGY = "ALWAYS_DEFECT";
    private static final String COEVOLUTIONARY_STRATEGY = "COEVOLUTIONARY";

    private String mStrategyName;
    private String[] mOpponentStrategyNames;
    private int mCoEvolutionaryIterations;
    private int mIpdIterations;
    private Random mRandomizer;

    private ConfigManager mConfigManager;

    PrisonersTournament(ConfigManager configManager, Random randomizer)
    {
        super("Iterated Prisoner's Dilemma");

        mRandomizer = randomizer;
        mConfigManager = configManager;

        mStrategyName = mConfigManager.getStringParameter("TournamentStrategy");
        mOpponentStrategyNames = mConfigManager.getCsvParameter("TournamentSuite");
        mCoEvolutionaryIterations = mConfigManager.getIntParameter("CoevolutionaryIterations");
        mIpdIterations = mConfigManager.getIntParameter("IpdIterations");
    }

    public void doRawFitness(Chromo X, Chromo[] population)
    {
        // Create player strategy with chromosome
        Strategy player;

        if (mStrategyName.equalsIgnoreCase(AXELROD_ORIGINAL_STRATEGY))
        {
            player = new StrategyAxelrod(mRandomizer, mConfigManager);
        }
        else if (mStrategyName.equalsIgnoreCase(AXELROD_PROBABILITY_STRATEGY))
        {
            player = new StrategyAxelrodProbabilistic(mRandomizer, mConfigManager);
        }
        else if (mStrategyName.equalsIgnoreCase(AXELROD_TIT_FOR_TAT_STRATEGY))
        {
            player = new StrategyAxelrodTitForTat(mRandomizer, mConfigManager);
        }
        else
        {
            System.err.println("Cannot create invalid strategy : " + mStrategyName);
            return;
        }

        player.decodeChromoToStrategy(X);

        double sumFitness = 0;

        // Create the opponent suite
        ArrayList<Strategy> opponentSuite = new ArrayList<>();

        for (String strategy : mOpponentStrategyNames)
        {
            if (strategy.equalsIgnoreCase(TIT_FOR_TAT_STRATEGY))
            {
                opponentSuite.add(new StrategyTitForTat());
            }
            else if(strategy.equalsIgnoreCase(TIT_FOR_TWO_TATS_STRATEGY))
            {
                opponentSuite.add(new StrategyTitForTwoTats());
            }
            else if (strategy.equalsIgnoreCase(ALWAYS_COOPERATE_STRATEGY))
            {
                opponentSuite.add(new StrategyAlwaysCooperate());
            }
            else if (strategy.equalsIgnoreCase(ALWAYS_DEFECT_STRATEGY))
            {
                opponentSuite.add(new StrategyAlwaysDefect());
            }
            else if (strategy.equalsIgnoreCase(COEVOLUTIONARY_STRATEGY))
            {
                for (int coIterations = 0; coIterations < mCoEvolutionaryIterations; coIterations++)
                {
                    Chromo other = population[mRandomizer.nextInt(population.length)];

                    Strategy opponent;

                    if (mStrategyName.equalsIgnoreCase(AXELROD_ORIGINAL_STRATEGY))
                    {
                        opponent = new StrategyAxelrod(mRandomizer, mConfigManager);
                    }
                    else if (mStrategyName.equalsIgnoreCase(AXELROD_PROBABILITY_STRATEGY))
                    {
                        opponent = new StrategyAxelrodProbabilistic(mRandomizer, mConfigManager);
                    }
                    else if (mStrategyName.equalsIgnoreCase(AXELROD_TIT_FOR_TAT_STRATEGY))
                    {
                        opponent = new StrategyAxelrodTitForTat(mRandomizer, mConfigManager);
                    }
                    else
                    {
                        // Can never reach here
                        return;
                    }

                    opponent.decodeChromoToStrategy(other);

                    opponentSuite.add(opponent);
                }
            }
            else
            {
                System.err.println("Cannot perform IPD against invalid strategy : " + strategy);
            }
        }

        for (Strategy opponent : opponentSuite)
        {
            sumFitness += getIteratedPrisonersDilemmaScore(player, opponent);
        }

        if (opponentSuite.size() > 1)
        {
            sumFitness /= opponentSuite.size();
        }

        X.setRawFitness(sumFitness);
    }

    private double getIteratedPrisonersDilemmaScore(Strategy player, Strategy opponent)
    {
        // Play IPD
        IteratedPD game = new IteratedPD(player, opponent);
        game.runSteps(mIpdIterations);

        return game.player1Score();// / (double) mIpdIterations;
    }

}
