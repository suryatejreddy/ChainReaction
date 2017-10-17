package sample;

import NonUIComponents.Cell;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;


//class to hold UI of Grid(Matrix)
public class ExtendedGrid
{
    private ArrayList<ExtendedCell> extendedCells;
    private GridPane gridPane;
    private int sideLengthX;
    private int sideLengthY;

    public ExtendedGrid(ArrayList<ExtendedCell> cells, GridPane grid, int sideLengthX, int sideLengthY)
    {
        this.extendedCells = cells;
        this.gridPane = grid;
        this.sideLengthX=sideLengthX;
        this.sideLengthY=sideLengthY;
    }

    public ExtendedCell getCellFromCoordinate(int row, int column)
    {
        return this.getCurrentExtendedCell(row, column);
    }

    //method to set the critical mass and neighbouring cells for an extended cell of the extended grid.
    public void setCellDetails()
    {
        for(int i=0;i<sideLengthY;i++)
        {
            for(int j=0;j<sideLengthX;j++)
            {
                if(cellIsACornerCell(i, j))
                {
                    getCurrentExtendedCell(i, j).setCriticalMass(ExtendedCell.TYPE_CORNER);
                    setNeighbouringCellsForCorner(i, j);
                }
                else if(cellIsAnEdgeCell(i, j))
                {
                    getCurrentExtendedCell(i, j).setCriticalMass(ExtendedCell.TYPE_EDGE);
                    setNeighbouringCellsForEdge(i, j);
                }
                else
                {
                    getCurrentExtendedCell(i, j).setCriticalMass(ExtendedCell.TYPE_NORMAL);
                    setNeighbouringCellsForNormal(i, j);
                }
            }
        }
    }

    private ExtendedCell getCurrentExtendedCell(int i, int j)
    {
        ExtendedCell extendedCell=extendedCells.get(0);
        for(ExtendedCell c: extendedCells)
        {
            if(c.getCoordX()==j && c.getCoordY()==i)
                extendedCell=c;
        }

        return extendedCell;
    }

    private void setNeighbouringCellsForNormal(int i, int j)
    {
        ExtendedCell cell1=getCurrentExtendedCell(i, j+1);
        ExtendedCell cell2=getCurrentExtendedCell(i-1, j);
        ExtendedCell cell3=getCurrentExtendedCell(i, j-1);
        ExtendedCell cell4=getCurrentExtendedCell(i+1, j);
        ArrayList<ExtendedCell> cellArrayList=new ArrayList<ExtendedCell>();
        cellArrayList.add(cell1);
        cellArrayList.add(cell2);
        cellArrayList.add(cell3);
        cellArrayList.add(cell4);
        getCurrentExtendedCell(i, j).setNeighbouringCells(cellArrayList);
    }

    private void setNeighbouringCellsForEdge(int i, int j)
    {
        if(i==0)
        {
            ExtendedCell cell1=getCurrentExtendedCell(i, j+1);
            ExtendedCell cell2=getCurrentExtendedCell(i+1, j);
            ExtendedCell cell3=getCurrentExtendedCell(i, j-1);
            ArrayList<ExtendedCell> cellArrayList=new ArrayList<ExtendedCell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cellArrayList.add(cell3);
            getCurrentExtendedCell(i, j).setNeighbouringCells(cellArrayList);
        }
        else if(i==sideLengthY-1)
        {
            ExtendedCell cell1=getCurrentExtendedCell(i, j+1);
            ExtendedCell cell2=getCurrentExtendedCell(i-1, j);
            ExtendedCell cell3=getCurrentExtendedCell(i, j-1);
            ArrayList<ExtendedCell> cellArrayList=new ArrayList<ExtendedCell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cellArrayList.add(cell3);
            getCurrentExtendedCell(i, j).setNeighbouringCells(cellArrayList);
        }
        else if(j==0)
        {
            ExtendedCell cell1=getCurrentExtendedCell(i, j+1);
            ExtendedCell cell2=getCurrentExtendedCell(i-1, j);
            ExtendedCell cell3=getCurrentExtendedCell(i+1, j);
            ArrayList<ExtendedCell> cellArrayList=new ArrayList<ExtendedCell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cellArrayList.add(cell3);
            getCurrentExtendedCell(i, j).setNeighbouringCells(cellArrayList);
        }
        else if(j==sideLengthX-1)
        {
            ExtendedCell cell1=getCurrentExtendedCell(i, j-1);
            ExtendedCell cell2=getCurrentExtendedCell(i-1, j);
            ExtendedCell cell3=getCurrentExtendedCell(i+1, j);
            ArrayList<ExtendedCell> cellArrayList=new ArrayList<ExtendedCell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cellArrayList.add(cell3);
            getCurrentExtendedCell(i, j).setNeighbouringCells(cellArrayList);
        }
    }

    private void setNeighbouringCellsForCorner(int i, int j)
    {
        if(i==0 && j==0)
        {
            ExtendedCell cell1=getCurrentExtendedCell(i, j+1);
            ExtendedCell cell2=getCurrentExtendedCell(i+1, j);
            ArrayList<ExtendedCell> cellArrayList=new ArrayList<ExtendedCell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            getCurrentExtendedCell(i, j).setNeighbouringCells(cellArrayList);
        }
        else if(i==0 && j==sideLengthX-1)
        {
            ExtendedCell cell1=getCurrentExtendedCell(i, j-1);
            ExtendedCell cell2=getCurrentExtendedCell(i+1, j);
            ArrayList<ExtendedCell> cellArrayList=new ArrayList<ExtendedCell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            getCurrentExtendedCell(i, j).setNeighbouringCells(cellArrayList);
        }
        else if(i==sideLengthY-1 && j==0)
        {
            ExtendedCell cell1=getCurrentExtendedCell(i, j+1);
            ExtendedCell cell2=getCurrentExtendedCell(i-1, j);
            ArrayList<ExtendedCell> cellArrayList=new ArrayList<ExtendedCell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            getCurrentExtendedCell(i, j).setNeighbouringCells(cellArrayList);
        }
        else if(i==sideLengthY-1 && j==sideLengthX-1)
        {
            ExtendedCell cell1=getCurrentExtendedCell(i, j-1);
            ExtendedCell cell2=getCurrentExtendedCell(i-1, j);
            ArrayList<ExtendedCell> cellArrayList=new ArrayList<ExtendedCell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            getCurrentExtendedCell(i, j).setNeighbouringCells(cellArrayList);
        }
    }

    private boolean cellIsAnEdgeCell(int i, int j)
    {
        if(!cellIsACornerCell(i,j))
        {
            if(i==0 || i==sideLengthY-1)
                return true;
            if(j==0 || j==sideLengthX-1)
                return true;
            return false;
        }

        return false;
    }

    private boolean cellIsACornerCell(int i, int j)
    {
        if(i==0 && j==0)
            return true;
        if(i==sideLengthY-1 && j==0)
            return true;
        if(i==0 && j==sideLengthX-1)
            return true;
        if(i==sideLengthY-1 && j==sideLengthX-1)
            return true;
        return false;
    }

    public ArrayList<ExtendedCell> getExtendedCells()
    {
        return extendedCells;
    }

    public GridPane getGridPane()
    {
        return gridPane;
    }

    public void setExtendedCells(ArrayList<ExtendedCell> extendedCells)
    {
        this.extendedCells = extendedCells;
    }

    public void setGridPane(GridPane gridPane)
    {
        this.gridPane = gridPane;
    }

    public void addExtendedCell(ExtendedCell cell)
    {
        this.extendedCells.add(cell);
    }
}