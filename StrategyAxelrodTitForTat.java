import java.util.Random;

public class StrategyAxelrodTitForTat extends StrategyAxelrod
{
    public StrategyAxelrodProbabilistic(Random randomizer, ConfigManager configManager)
    {
        super(randomizer, configManager);
        name = "Axelrod Probabilistic";

        mMaxValue = mConfigManager.getIntParameter("MaxDnaValue");
    }

    protected int getMoveFromStrategyValue(int strategyValue)
    {
        int move;

        // Play tit for tat if the value is less than 0.
        if (strategyValue < 0)
        {
            move = getOpponentLastMove();
        }
        else
        {
            move = strategyValue;
        }

        return move;
    }
}
