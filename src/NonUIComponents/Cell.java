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
    private int coordinateX;
    private int coordinateY;

    public Cell(int criticalMass, ArrayList<Cell> neighbouringCells, int numberOfBallsPresent, Player player,int x,int y)
    {
        this.cellIsOccupied=false;
        this.criticalMass=criticalMass;
        this.numberOfBallsPresent=numberOfBallsPresent;
        this.neighbouringCells=neighbouringCells;
        this.playerOccupiedBy=player;
        this.coordinateX = x;
        this.coordinateY = y;
    }

    public int getCriticalMass()
    {
        return criticalMass;
    }

    public int getCoordinateX()
    {
        return coordinateX;
    }

    public int getCoordinateY()
    {
        return coordinateY;
    }

    public ArrayList<Cell> getNeighbouringCells()
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

    public void setCoordinateX(int coordinateX)
    {
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(int coordinateY)
    {
        this.coordinateY = coordinateY;
    }

    public void setPlayerOccupiedBy(Player playerOccupiedBy)
    {
        //what should happen here?
        playerOccupiedBy.addCell(this);
        this.playerOccupiedBy = playerOccupiedBy;


//        this.getPlayerOccupiedBy().getCurrentOccupiedCells().remove(this);
//        this.getPlayerOccupiedBy().setCurrentOccupiedCells(this.getPlayerOccupiedBy().getCurrentOccupiedCells());
//        this.playerOccupiedBy = playerOccupiedBy;
    }

    public void addBall(Player curPlayer)
    {
          this.setCellIsOccupied(true);
          this.setPlayerOccupiedBy(curPlayer);
//        this.setCellIsOccupied(true);
//        this.setPlayerOccupiedBy(currentPlayer);
//        this.setNumberOfBallsPresent(this.getNumberOfBallsPresent()+1);
//        ArrayList<Cell> occupiedCellsByCurrentPlayer=game.getPlayers().get(i).getCurrentOccupiedCells();
//        occupiedCellsByCurrentPlayer.add(cellChosen);
//        game.getPlayers().get(i).setCurrentOccupiedCells(occupiedCellsByCurrentPlayer);
//        ArrayList<Cell> neighbours=this.getNeighbouringCells();
//        if(this.getNumberOfBallsPresent()==this.getCriticalMass()) {
//            try
//            {
//                neighbours.forEach(k -> k.addBall(currentPlayer, gameMatrix, game, coordX, coordY, i, cellChosen));
//            }
//            catch(StackOverflowError e)
//            {
//                e.printStackTrace();
//                return;
//            }
//        }

    }

    public String toString(){
        String s = "(" + this.coordinateX + "," + this.coordinateY + ")";
        return s;
    }
}
