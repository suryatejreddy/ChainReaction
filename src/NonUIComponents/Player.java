package NonUIComponents;


import java.util.ArrayList;

public class Player
{
    public static int VIOLET=0;
    public static int BLUE=1;
    public static int GREEN=2;
    public static int YELLOW=3;
    public static int ORANGE=4;
    public static int RED=5;
    public static int BROWN=6;
    public static int BLACK=7;

    private int playerColour;
    private ArrayList<Cell> currentOccupiedCells;
    private boolean isAlive;

    public Player(int playerColour, ArrayList<Cell> currentOccupiedCells, boolean isAlive)
    {
        this.currentOccupiedCells=currentOccupiedCells;
        this.isAlive=isAlive;
        this.playerColour=playerColour;
    }

    public int getPlayerColour()
    {
        return playerColour;
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
}
