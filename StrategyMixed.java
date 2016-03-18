import java.util.Random;

/**
 * Created by D on 3/7/2016.
 * Uses history of game to index into an array of doubles which represent the probability of cooperation.
 * Plays tit-for-tit if it doesn't have enough history yet.
 * The array of doubles is what the GA has to find.
 */
public class StrategyMixed extends Strategy
{
    int mCurrentIteration = 0;

    int[] mStrategy;
    int[] mHistory;

    public StrategyMixed()
    {
        name = "Mixed Strategy";

        // 0 = defect, 1 = cooperate
        opponentLastMove = 1;
    }

    // This is what the GA has to find, let the GA call this function to set strategy
    public void decodeChromoToStrategy(Chromo chromo)
    {
        int strategyLength = chromo.getGeneSize() * chromo.getNumGenes();

        mStrategy = new int[strategyLength];

        // TODO: Remove magic number
        mHistory = new int[8];

        // Copy data into strategy
        for(int strategyIndex = 0; strategyIndex < strategyLength; strategyIndex++)
        {
            int geneIndex = strategyIndex / chromo.getGeneSize();
            int dnaIndex = strategyIndex % chromo.getGeneSize();

            mStrategy[strategyIndex] = chromo.getGeneList()[geneIndex][dnaIndex];
        }
    }

    public int nextMove()
    {
        int nextMove;

        // Play Tit-For-Tat if there isn't enough history
        if(mCurrentIteration < mHistory.length / 2)
        {
            nextMove = opponentLastMove;
        }
        else
        {
            // Get strategy index from current history of game
            int index = getIndex();

            nextMove = mStrategy[index];
        }

        mCurrentIteration++;

        return nextMove;
    }

    private int getIndex()
    {
        int index = 0;
        for(int i = 0; i < mHistory.length; i++)
        {
            index += mHistory[i] * Math.pow(2, mHistory.length - 1 - i);
        }
        return index;
    }

    private void recordMoveInHistory(int move)
    {
        for(int i = 0; i < mHistory.length - 1; i++)
        {
            mHistory[i] = mHistory[i + 1];
        }

        mHistory[mHistory.length - 1] = move;
    }

    @Override
    public void saveOpponentMove(int move)
    {
        opponentLastMove = move;

        recordMoveInHistory(move);
    }

    @Override
    public void saveMyMove(int move)
    {
        myLastMove = move;

        recordMoveInHistory(move);
    }
}
