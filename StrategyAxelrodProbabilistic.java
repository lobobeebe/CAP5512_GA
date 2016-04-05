import java.util.Random;

/**
 * Created by D on 3/7/2016.
 * Uses history of game to index into an array of doubles which represent the probability of cooperation.
 * Plays tit-for-tit if it doesn't have enough history yet.
 * The array of doubles is what the GA has to find.
 */
public class StrategyAxelrodProbabilistic extends StrategyAxelrod
{
    private int mMaxValue;

    public StrategyAxelrodProbabilistic(Random randomizer, ConfigManager configManager)
    {
        super(randomizer, configManager);
        name = "Axelrod Probabilistic";

        mMaxValue = mConfigManager.getIntParameter("MaxDnaValue");
    }

    @Override
    protected int getMoveFromStrategyValue(int strategyValue)
    {
        int randomDraw = mRandomizer.nextInt(mMaxValue);

        int move = (randomDraw <= strategyValue) ? 1 : 0;

        return move;
    }
}
