package sample;

import NonUIComponents.Cell;
import NonUIComponents.Game;
import NonUIComponents.Matrix;
import javafx.scene.paint.Color;

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

    public static  Color colorOfPlayer1 = Color.VIOLET;
    public static  Color colorOfPlayer2 = Color.BLUE;
    public static  Color colorOfPlayer3 = Color.GREEN;
    public static  Color colorOfPlayer4 = Color.YELLOW;
    public static  Color colorOfPlayer5 = Color.ORANGE;
    public static  Color colorOfPlayer6 = Color.RED;
    public static  Color colorOfPlayer7 = Color.BROWN;
    public static  Color colorOfPlayer8 = Color.WHITE;



    public static final long serialVersionUID=6898952L;

    public transient Color playerColor;

    public double r , g , b;

    public int playerNumber;




    private HashSet<ExtendedCell> currentCells;

    private boolean isAlive;
    private boolean takenFirstMove;

    public ExtendedPlayer(Color playerColour,boolean isAlive,int p)
    {
        this.isAlive=isAlive;
        this.playerColor=playerColour;
        this.takenFirstMove=false;
        this.currentCells = new HashSet<ExtendedCell>();
        this.r = playerColour.getRed();
        this.g = playerColour.getGreen();
        this.b = playerColour.getBlue();
        this.playerNumber = p;
    }

    public Color getPlayerColour()
    {
        return playerColor;
    }

    public boolean hasTakenFirstMove()
    {
        return takenFirstMove;
    }

    public String getPlayerColourByString()
    {
        return playerColor.toString();
    }


    public boolean isAlive()
    {
        return isAlive;
    }

    public void setAlive(boolean alive)
    {
        isAlive = alive;
    }

    public void setPlayerColour(Color playerColour)
    {
        this.playerColor = playerColour;
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
        return ("Player " +  this.playerNumber  + " " + this.isAliveString() + " in " + this.currentCells.size() +  " cells");
    }

    public static Color returnColorOfPlayer(int i)
    {
        switch (i){
            case 0:
                return colorOfPlayer1;
            case 1:
                return colorOfPlayer2;
            case 2:
                return colorOfPlayer3;
            case 3:
                return  colorOfPlayer4;
            case 4:
                return colorOfPlayer5;
            case 5:
                return colorOfPlayer6;
            case 6:
                return  colorOfPlayer7;
            case 7:
                return colorOfPlayer8;
        }
        return null;
    }


    public void setCurrentCells(HashSet<ExtendedCell> currentCells)
    {
        this.currentCells=currentCells;
    }

    public HashSet<ExtendedCell> getCurrentCells()
    {
        return currentCells;
    }

    public Color makeNewColor()
    {
        return Color.color(this.r,this.g,this.b);
    }
}