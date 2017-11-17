package sample;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Class that holds all the Player data.
 *
 * @author Vishaal and Suryatej
 */

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


    /**
     * Parameterized Constructor
     *
     * @author Vishaal
     * @version 1.1
     * @since 2017-10-27
     *
     * @param playerColour
     * @param isAlive
     * @param p
     */

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

    /**
     * Getter for Player Colour
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-27
     * @return Player Colour
     */

    public Color getPlayerColour()
    {
        return playerColor;
    }

    /**
     * Getter for takenFirstMove boolean.
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-27
     * @return takenFirstMove
     */

    public boolean hasTakenFirstMove()
    {
        return takenFirstMove;
    }

    /**
     * Getter for Player Colour in String format.
     *
     * @author Vishaal
     * @version 1.1
     * @since 2017-10-27
     * @return Player Colour in String
     */

    public String getPlayerColourByString()
    {
        return playerColor.toString();
    }


    /**
     * Getter for isAlive boolean
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-27
     * @return isAlive
     */

    public boolean isAlive()
    {
        return isAlive;
    }

    /**
     * Setter for isAlive boolean
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-27
     * @param alive
     */

    public void setAlive(boolean alive)
    {
        isAlive = alive;
    }

    /**
     * Setter for takenFirstMove boolean
     *
     * @author Vishaal
     * @since 2017-10-27
     * @param takenFirstMove
     */

    public void setTakenFirstMove(boolean takenFirstMove)
    {
        this.takenFirstMove = takenFirstMove;
    }


    /**
     * Adds passed cell to the arraylist of cells controlled by the player currently.
     *
     * @author Vishaal
     * @version 1.6
     * @since 2017-10-28
     * @param curCell
     */

    public void addCell(ExtendedCell curCell)
    {
        currentCells.add(curCell);
    }

    /**
     * Removes passed cell from the arraylist of cells controlled by the player currently.
     *
     * @author Vishaal
     * @version 1.2
     * @since 2017-10-28
     * @param curCell
     */

    public void removeCell(ExtendedCell curCell)
    {
        currentCells.remove(curCell);
    }


    /**
     * Updates if the player is dead or alive
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-02
     */

    public void checkPlayerStatus()
    {
        if (this.currentCells.size() == 0)
        {
            this.setAlive(false);
        }
    }

    /**
     * Returns isAlive in String format
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-01
     * @return isAlive in String
     */

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


    /**
     * Overriden toString() method
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-01
     * @return Player details
     */

    @Override
    public String toString()
    {
        return ("Player " +  this.playerNumber  + " " + this.isAliveString() + " in " + this.currentCells.size() +  " cells");
    }

    /**
     * Returns colour of ith player.
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-16
     * @param i
     * @return colour of ith player
     */

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


    /**
     * Creates a clone of the current player's color object and returns it.
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-16
     * @return Clone of current player's color
     */

    public Color makeNewColor()
    {
        return Color.color(this.r,this.g,this.b);
    }
}