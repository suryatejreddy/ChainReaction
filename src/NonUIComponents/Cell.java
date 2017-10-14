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
        //TODO change color of balls for UI
    }

    public void emptyCell(){ //to be called when the cell bursts
        this.numberOfBallsPresent = 0;
        this.playerOccupiedBy.removeCell(this);
        this.playerOccupiedBy = null;
        this.cellIsOccupied = false;

    }

    public void addBall(Player curPlayer)
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
          if (this.numberOfBallsPresent == this.getCriticalMass()){

                this.emptyCell();
                try
                {
                    //TODO add multi threading here
                    this.neighbouringCells.forEach((Cell neighbour) -> {
                        neighbour.addBall(curPlayer);
                    });
                }
                catch(StackOverflowError e)
                {
                    System.out.println("Yayyyy.. You won.");
                }
          }

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

    public String toString()
    {
        String s = "(" + this.coordinateX + "," + this.coordinateY + ")" + " " ;
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
