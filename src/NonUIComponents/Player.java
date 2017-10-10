package NonUIComponents;


import java.util.ArrayList;

public class Player
{
    public static final int VIOLET=0;
    public static final int BLUE=1;
    public static final int GREEN=2;
    public static final int YELLOW=3;
    public static final int ORANGE=4;
    public static final int RED=5;
    public static final int BROWN=6;
    public static final int BLACK=7;

    private int playerColour;
    private ArrayList<Cell> currentOccupiedCells;
    private boolean isAlive;
    private boolean takenFirstMove;

    public Player(int playerColour, ArrayList<Cell> currentOccupiedCells, boolean isAlive)
    {
        this.currentOccupiedCells=currentOccupiedCells;
        this.isAlive=isAlive;
        this.playerColour=playerColour;
        this.takenFirstMove=false;
    }

    public int getPlayerColour()
    {
        return playerColour;
    }

    public boolean hasTakenFirstMove()
    {
        return takenFirstMove;
    }

    public String getPlayerColourByString()
    {
        switch(this.getPlayerColour())
        {
            case VIOLET:    return "Violet";
            case BLUE:      return "Blue";
            case GREEN:     return "Green";
            case YELLOW:    return "Yellow";
            case ORANGE:    return "Orange";
            case RED:       return "Red";
            case BROWN:     return "Brown";
            case BLACK:     return "Black";
        }
        return null;
    }

    public ArrayList<Cell> getCurrentOccupiedCells()
    {
        return currentOccupiedCells;
    }

    public boolean isAlive()
    {
        return isAlive;
    }

    public void setAlive(boolean alive)
    {
        isAlive = alive;
    }

    public void setCurrentOccupiedCells(ArrayList<Cell> currentOccupiedCells)
    {
        this.currentOccupiedCells = currentOccupiedCells;
    }

    public void setPlayerColour(int playerColour)
    {
        this.playerColour = playerColour;
    }

    public void setTakenFirstMove(boolean takenFirstMove)
    {
        this.takenFirstMove = takenFirstMove;
    }

    public void makeMove(int coordX, int coordY, Matrix gameMatrix, Game game, int i, Cell cellChosen, Player currentPlayer)
    {
        ArrayList<Cell> currentRow = gameMatrix.getCells().get(coordY);
        Cell currentCell = currentRow.get(coordX);
        currentCell.addBall(currentPlayer,gameMatrix,game,coordX,coordY,i,cellChosen);
//        gameMatrix.getCells().get(coordY).get(coordX).addBall(currentPlayer, gameMatrix, game, coordX, coordY, i, cellChosen);
    }
}
