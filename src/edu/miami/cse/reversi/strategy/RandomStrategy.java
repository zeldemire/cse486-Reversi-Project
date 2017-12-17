package edu.miami.cse.reversi.strategy;

import edu.miami.cse.reversi.Board;
import edu.miami.cse.reversi.Square;
import edu.miami.cse.reversi.Strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * A simple strategy that just chooses randomly from the squares available to
 * the current player.
 */
public class RandomStrategy implements Strategy {

    /**
     * A simple utility method for selecting a random item from a set. Not
     * particularly efficient.
     *
     * @param itemSet The set of items from which to select.
     * @return A random item from the set.
     */
    private static <T> T chooseOne(Set<T> itemSet) {
        List<T> itemList = new ArrayList<>(itemSet);
        return itemList.get(new Random().nextInt(itemList.size()));
    }

    @Override
    public Square chooseSquare(Board board) {
        return chooseOne(board.getCurrentPossibleSquares());
    }
}
