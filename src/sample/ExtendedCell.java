package sample;

import NonUIComponents.Cell;
import NonUIComponents.Player;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;


//class to hold UI of Cell.
public class ExtendedCell
{
    public static int TYPE_CORNER=2;
    public static int TYPE_EDGE=3;
    public static int TYPE_NORMAL=4;

    private StackPane cell;
    private int coordX;
    private int coordY;
    private ExtendedPlayer playerOccupiedBy;
    private int criticalMass;
    private int numberOfBallsPresent;
    private ArrayList<ExtendedCell> neighbouringCells;
    private boolean cellIsOccupied;

    public ExtendedCell(StackPane cell, int coordX, int coordY, ExtendedPlayer player, ArrayList<ExtendedCell> neighbouringCells)
    {
        this.cell=cell;
        this.coordX=coordX;
        this.coordY=coordY;
        this.playerOccupiedBy=player;
        this.cellIsOccupied=false;
        this.criticalMass=-1;
        this.numberOfBallsPresent=0;
        this.neighbouringCells=neighbouringCells;
    }

    public ExtendedPlayer getPlayer()
    {
        return playerOccupiedBy;
    }

    public int getCoordX()
    {
        return coordX;
    }

    public int getCoordY()
    {
        return coordY;
    }

    public StackPane getCell()
    {
        return cell;
    }

    public ArrayList<ExtendedCell> getNeighbouringCells()
    {
        return neighbouringCells;
    }

    public boolean isCellOccupied()
    {
        return cellIsOccupied;
    }

    public int getNumberOfBallsPresent()
    {
        return numberOfBallsPresent;
    }


    public int getCriticalMass()
    {
        return criticalMass;
    }

    public void setCell(StackPane cell)
    {
        this.cell = cell;
    }

    public void setCriticalMass(int criticalMass)
    {
        this.criticalMass = criticalMass;
    }

    public void setCoordX(int coordX)
    {
        this.coordX = coordX;
    }

    public void setCoordY(int coordY)
    {
        this.coordY = coordY;
    }

    public void setPlayer(ExtendedPlayer player)
    {
        this.playerOccupiedBy = player;
    }

    public void setNumberOfBallsPresent(int numberOfBallsPresent)
    {
        this.numberOfBallsPresent = numberOfBallsPresent;
    }

    public void setNeighbouringCells(ArrayList<ExtendedCell> neighbouringCells)
    {
        this.neighbouringCells = neighbouringCells;
    }

    public void setCellIsOccupied(boolean cellIsOccupied)
    {
        this.cellIsOccupied = cellIsOccupied;
    }

    public void setPlayerOccupiedBy(ExtendedPlayer playerOccupiedBy)
    {
        //what should happen here?
        playerOccupiedBy.addCell(this);
        this.playerOccupiedBy = playerOccupiedBy;
        //TODO change color of balls for UI
    }

    public void emptyCell()
    { //to be called when the cell bursts
        this.numberOfBallsPresent = 0;
        this.playerOccupiedBy.removeCell(this);
        this.playerOccupiedBy = null;
        this.cellIsOccupied = false;

    }

    public void addBall(ExtendedPlayer curPlayer)
    {
        curPlayer.setTakenFirstMove(true);

        if (this.cellIsOccupied && this.playerOccupiedBy != curPlayer)  //cell is occupied by someone else
        {
            //TODO change color of the ball for UI
            this.playerOccupiedBy.removeCell(this); //removing it from his list

        }
        this.setCellIsOccupied(true);
        this.setPlayerOccupiedBy(curPlayer);
        this.numberOfBallsPresent += 1;
        if (this.numberOfBallsPresent == this.getCriticalMass())
        {

            this.emptyCell();
            try
            {
                //TODO add multi threading here
                this.neighbouringCells.forEach((ExtendedCell neighbour) ->
                {
                    neighbour.addBall(curPlayer);
                });
            }
            catch(StackOverflowError e)
            {
                System.out.println("Yayyyy.. You won.");
            }
        }
    }

    public String toString()
    {
        String s = "(" + this.coordX + "," + this.coordY + ")" + " " ;
        if (this.playerOccupiedBy != null)
        {
            s += this.playerOccupiedBy.getPlayerColourByString().substring(0,1) + "" +  this.getNumberOfBallsPresent();
        }
        else
        {
            s += "E ";
        }
        return s;
    }
}