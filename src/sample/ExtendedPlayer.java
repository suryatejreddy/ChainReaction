package sample;

import NonUIComponents.Cell;
import NonUIComponents.Game;
import NonUIComponents.Matrix;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class ExtendedPlayer implements Serializable
{
    public static final int VIOLET=0;
    public static final int BLUE=1;
    public static final int GREEN=2;
    public static final int YELLOW=3;
    public static final int ORANGE=4;
    public static final int RED=5;
    public static final int BROWN=6;
    public static final int WHITE=7;

    public static final long serialVersionUID=6898952L;


    private int playerColour;

    private HashSet<ExtendedCell> currentCells;

    private boolean isAlive;
    private boolean takenFirstMove;

    public ExtendedPlayer(int playerColour,boolean isAlive)
    {
        this.isAlive=isAlive;
        this.playerColour=playerColour;
        this.takenFirstMove=false;
        this.currentCells = new HashSet<ExtendedCell>();
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
            case WHITE:     return "White";
        }
        return null;
    }


    public boolean isAlive()
    {
        return isAlive;
    }

    public void setAlive(boolean alive)
    {
        isAlive = alive;
    }

    public void setPlayerColour(int playerColour)
    {
        this.playerColour = playerColour;
    }

    public void setTakenFirstMove(boolean takenFirstMove)
    {
        this.takenFirstMove = takenFirstMove;
    }

    public void makeMove(int coordX, int coordY, Matrix gameMatrix, Game game, int i, Cell cellChosen, NonUIComponents.Player currentPlayer)
    {
        ArrayList<Cell> currentRow = gameMatrix.getCells().get(coordY);
        Cell currentCell = currentRow.get(coordX);
//        currentCell.addBall(currentPlayer,gameMatrix,game,coordX,coordY,i,cellChosen);
//        gameMatrix.getCells().get(coordY).get(coordX).addBall(currentPlayer, gameMatrix, game, coordX, coordY, i, cellChosen);
    }


    public void addCell(ExtendedCell curCell)
    {
        currentCells.add(curCell);
    }

    public void removeCell(ExtendedCell curCell)
    {
        currentCells.remove(curCell);
    }


    public void checkPlayerStatus() //updates if the player is dead or alive;
    {
        if (this.currentCells.size() == 0) {
            this.setAlive(false);
        }
    }

    public String isAliveString()
    {
        if (this.isAlive)
        {
            return "is alive";
        }
        else
        {
            return "is dead";
        }
    }



    @Override
    public String toString()
    {
        return (this.getPlayerColourByString() + " " + this.isAliveString() + " in " + this.currentCells.size() +  " cells");
    }

    public void setCurrentCells(HashSet<ExtendedCell> currentCells)
    {
        this.currentCells=currentCells;
    }

    public HashSet<ExtendedCell> getCurrentCells()
    {
        return currentCells;
    }
}