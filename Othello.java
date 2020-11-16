import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Mbassip2
 */
public class Othello {

    public static final int SQUARESIZE = 60; // Basic dimensions of board
    public static final double PIECERATIO = 0.4; // ration of radius of piece to square size
    public static final int xBOARDpos = 100; // Position of board
    public static final int yBOARDpos = 100; // Position of board
    public static final int xMARGIN = 50; // Position of board
    public static final int yMARGIN = 50; // Position of board
    public static final int searchDepth = 8; // Depth of minimax search

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BoardState initialState = new BoardState();
        initialState.setContents(3, 3, 1);
        initialState.setContents(3, 4, -1);
        initialState.setContents(4, 3, -1);
        initialState.setContents(4, 4, 1);
        initialState.colour = -1; // Black to start

        OthelloDisplay othelloDisplay = new OthelloDisplay();
        othelloDisplay.boardState = initialState;
        othelloDisplay.repaint();
    }

    public static Move chooseMove(BoardState boardState) {

        ArrayList<Move> moves = boardState.getLegalMoves();
        if (moves.isEmpty())
            return null;
        // participant version: replace this line with the following code
        // and provide the routines as directed in the lab exercise script.
        // return moves.get(0);
        // /*
        return minimax(boardState, searchDepth);
        // */
    }

    public static int evaluateBoard(BoardState boardState) {
        int whiteScore = 0;
        int blackScore = 0;
        int[][] values = { { 120, -20, 20, 5, 5, 20, -20, 120 }, { -20, -40, -5, -5, -5, -5, -40, -20 },
                { 20, -5, 15, 3, 3, 15, -5, 20 }, { 5, -5, 3, 3, 3, 3, -5, 5 }, { 5, -5, 3, 3, 3, 3, -5, 5 },
                { 20, -5, 15, 3, 3, 15, -5, 20 }, { -20, -40, -5, -5, -5, -5, -40, -20 },
                { 120, -20, 20, 5, 5, 20, -20, 120 } };

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardState.getContents(i, j) == 1) {
                    whiteScore = whiteScore + values[i][j];
                } else if (boardState.getContents(i, j) == -1) {
                    blackScore = blackScore + values[i][j];
                }
            }
        }

        return (whiteScore - blackScore);
    }

    public static int minimax_val(BoardState boardState, int searchDepth, int alpha, int betha) {
        if (searchDepth == 0) {
            return evaluateBoard(boardState);
        }
        if (boardState.getLegalMoves().size() == 0) {
            boardState.colour = -boardState.colour;
            return minimax_val(boardState, searchDepth - 1, alpha, betha);
        } else if (boardState.colour == 1) {
            alpha = -8000;
            for (int i = 0; i < boardState.getLegalMoves().size() && alpha < betha; i++) {
                BoardState b1 = boardState.deepCopy();
                Move m = b1.getLegalMoves().get(i);
                b1.makeLegalMove(m.x, m.y);
                int val = minimax_val(b1, searchDepth - 1, alpha, betha);
                if (val > alpha) {
                    alpha = val;
                }
            }
            return alpha;
        } else {
            betha = 8000;
            for (int i = 0; i < boardState.getLegalMoves().size() && alpha < betha; i++) {
                BoardState b1 = boardState.deepCopy();
                Move m = b1.getLegalMoves().get(i);
                b1.makeLegalMove(m.x, m.y);
                int val1 = minimax_val(b1, searchDepth - 1, alpha, betha);
                if (val1 < betha) {
                    betha = val1;
                }
            }
            return betha;
        }

    }

    public static Move minimax(BoardState boardState, int searchDepth) {
        if (boardState.getLegalMoves().size() == 0) {
            return null;
        }
        Move bestMove = boardState.getLegalMoves().get(0);
        if (boardState.colour == 1) {
            int alpha = -8000;
            for (Move m : boardState.getLegalMoves()) {
                BoardState b1 = boardState.deepCopy();
                b1.makeLegalMove(m.x, m.y);
                int val = minimax_val(b1, searchDepth - 1, alpha, 8000);
                if (val > alpha) {
                    alpha = val;
                    bestMove = m;
                }
            }
        }
        return bestMove;

    }
}
