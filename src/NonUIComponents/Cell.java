package NonUIComponents;

import java.util.ArrayList;

public class Cell
{
    public static int TYPE_CORNER=2;
    public static int TYPE_EDGE=3;
    public static int TYPE_NORMAL=4;

    private boolean cellIsOccupied;
    private int criticalMass;
    private int numberOfBallsPresent;
    private ArrayList<Cell> neighbouringCells;
    private Player playerOccupiedBy;

    public Cell(int criticalMass, ArrayList<Cell> neighbouringCells, int numberOfBallsPresent, Player player)
    {
        this.cellIsOccupied=false;
        this.criticalMass=criticalMass;
        this.numberOfBallsPresent=numberOfBallsPresent;
        this.neighbouringCells=neighbouringCells;
        this.playerOccupiedBy=player;
    }

    public int getCriticalMass()
    {
        return criticalMass;
    }

    public ArrayList<Cell> getNeighbouringCells()
    {
        return neighbouringCells;
    }

    public boolean isCellIsOccupied()
    {
        return cellIsOccupied;
    }

    public int getNumberOfBallsPresent()
    {
        return numberOfBallsPresent;
    }

    public Player getPlayerOccupiedBy()
    {
        return playerOccupiedBy;
    }

    public void setCellIsOccupied(boolean cellIsOccupied)
    {
        this.cellIsOccupied = cellIsOccupied;
    }

    public void setCriticalMass(int criticalMass)
    {
        this.criticalMass = criticalMass;
    }

    public void setNeighbouringCells(ArrayList<Cell> neighbouringCells)
    {
        this.neighbouringCells = neighbouringCells;
    }

    public void setNumberOfBallsPresent(int numberOfBallsPresent)
    {
        this.numberOfBallsPresent = numberOfBallsPresent;
    }

    public void setPlayerOccupiedBy(Player playerOccupiedBy)
    {
        this.playerOccupiedBy = playerOccupiedBy;
    }
}
