package edu.miami.cse.reversi.strategy;

import edu.miami.cse.reversi.Board;
import edu.miami.cse.reversi.Player;
import edu.miami.cse.reversi.Square;
import edu.miami.cse.reversi.Strategy;

public class AlphaBeta implements Strategy{

    private Player color;
    private final int MAX_DEPTH = 4;
    private Square bestMove = null;

    private final int EDGE = 15;
    private final int REGION_4 = -5;
    private final int CORNER = 20;

    @Override
    public Square chooseSquare(Board board) {
        color = board.getCurrentPlayer();
        return chooseOne(board);
    }


    private Square chooseOne(Board board){
        return alphaBeta(board);
    }

    private Square alphaBeta(Board board) {
        maxValue(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, board);
        return bestMove;
    }

    private int maxValue(int alpha, int beta, int depth, Board board) {
        if(depth >= MAX_DEPTH){
            return evaluateMove(board);
        }


        if (board.getCurrentPossibleSquares().isEmpty()) {
            return minValue(alpha, beta, board, depth + 1);
        }

        Square tempMove = null;
        int tempResult = Integer.MIN_VALUE;
        Board newBoard = board;

        for (Square move : newBoard.getCurrentPossibleSquares()) {

            newBoard = board.play( move);


            alpha = minValue(alpha, beta, newBoard, depth + 1);

            if(alpha > tempResult){
                tempMove = move;
                tempResult = alpha;
            }

            if (alpha >= beta) {
                break;
            }

        }

        bestMove = tempMove;
        return alpha;
    }

    private int minValue(int alpha, int beta, Board board, int depth) {
        if(depth >= MAX_DEPTH){
            return evaluateMove(board);
        }

        Board newBoard = board;

        if (board.getCurrentPossibleSquares().isEmpty()) {
            return maxValue(alpha, beta, depth, board);
        }

        for (Square move : newBoard.getCurrentPossibleSquares()) {
            newBoard.play(move);

            beta = Math.min(beta, maxValue(alpha, beta, depth + 1, newBoard));

            if (beta <= alpha) {
                break;
            }

            newBoard = board;
        }
        return beta;
    }

    private int evaluateMove(Board board) {
        int ourScore = board.getPlayerSquareCounts().get(color);
        int opponentScore = board.getPlayerSquareCounts().get(color.opponent());
        int finalScore = ourScore - opponentScore;
        for (int i = 0; i < board.getMoves().size(); i++) {
            if (board.getMoves().get(i).getPlayer().equals(board.getCurrentPlayer())) {
                Square move = board.getMoves().get(i).getSquare();
                if (isCorner(move, board)) {
                    finalScore += CORNER;
                } else if (isRegion4(move, board)) {
                    finalScore += REGION_4;
                } else if (isEdge(move, board)) {
                    finalScore += EDGE;
                }
            }
        }
        return finalScore;
    }

    private boolean isCorner(Square move, final Board board) {
        return move.getRow() == 0 && move.getColumn() == 0 ||
                move.getRow() == 0 && move.getColumn() == board.size() - 1 ||
                move.getRow() == board.size() - 1 && move.getColumn() == 0 ||
                move.getRow() == board.size() - 1 && move.getColumn() == board.size() - 1;
    }

    private boolean isRegion4(Square move, final Board board) {
        return move.getRow() == 1 && move.getColumn() == 0 ||
                move.getRow() == 0 && move.getColumn() == 1 ||
                move.getRow() == 1 && move.getColumn() == 1 ||
                move.getRow() == board.size() - 2 && move.getColumn() == 0 ||
                move.getRow() == board.size() - 1 && move.getColumn() == 1 ||
                move.getRow() == board.size() - 2 && move.getColumn() == 1 ||
                move.getRow() == 0 && move.getColumn() == board.size() - 2 ||
                move.getRow() == 1 && move.getColumn() == board.size() - 1 ||
                move.getRow() == 1 && move.getColumn() == board.size() - 2 ||
                move.getRow() == board.size() - 1 && move.getColumn() == board.size() - 2 ||
                move.getRow() == board.size() - 2 && move.getColumn() == board.size() - 1 ||
                move.getRow() == board.size() - 2 && move.getColumn() == board.size() - 2;
    }

    private boolean isEdge(Square move, final Board board) {
        return move.getRow() == 0 ||
                move.getRow() == board.size() - 1 ||
                move.getColumn() == 0 ||
                move.getColumn() == board.size() - 1;
    }
}
