package sample;

import javafx.scene.layout.GridPane;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Class that holds the UI and NonUI components of the Game Matrix.
 *
 * @author Vishaal
 */

public class ExtendedGrid implements Serializable
{
    private ArrayList<ExtendedCell> extendedCells;
    private transient GridPane gridPane;
    private int sideLengthX;
    private int sideLengthY;

    public static final long serialVersionUID=1266852L;

    /**
     * Parameterized Constructor
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-23
     * @param cells
     * @param grid
     * @param sideLengthX
     * @param sideLengthY
     */

    public ExtendedGrid(ArrayList<ExtendedCell> cells, GridPane grid, int sideLengthX, int sideLengthY)
    {
        this.extendedCells = cells;
        this.gridPane = grid;
        this.sideLengthX=sideLengthX;
        this.sideLengthY=sideLengthY;
    }

    /**
     * Return the cell at passed paramters' coordinates.
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-25
     * @param row
     * @param column
     * @return Extended Cell object present at passed parameters' coordinates in Game Matrix
     */

    public ExtendedCell getCellFromCoordinate(int row, int column)
    {
        return this.getCurrentExtendedCell(row, column);
    }


    /**
     *Method to set the critical mass and neighbouring cells for an extended cell of the extended grid.
     *
     * @author Vishaal
     * @version 1.1
     * @since 2017-10-28
     */

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


    /**
     * Method to return cell at (i, j) coordinates.
     *
     * @author Vishaal
     * @version 1.8
     * @since 2017-10-25
     * @param i
     * @param j
     * @return cell at (i, j) coordinates
     */

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


    /**
     * Returns index of (i, j) cell in the cells arraylist.
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-29
     * @param i
     * @param j
     * @return Index of (i, j) cell in the cells arraylist
     */

    public int returnIndexOfCell(int i, int j)
    {
        int index = 0;
        for(ExtendedCell c: extendedCells)
        {
            if(c.getCoordX()==j && c.getCoordY()==i)
                return index;
            index += 1;
        }
        return -1;
    }


    /**
     * Method to initialize the cells in the Game Matrix.
     *
     * @author Vishaal
     * @version 1.2
     * @since 2017-10-28
     */

    public void initCells()
    {
        getExtendedCells().forEach(e ->
        {
            e.getGroup().getChildren().clear();
        });
    }


    /**
     * Method to set the neighbouringCells for Normal cells.(not corner/edge cells)
     *
     * @author Vishaal
     * @version 1.5
     * @since 2017-10-28
     * @param i
     * @param j
     */

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


    /**
     * Method to set the neighbouringCells for Edge cells.
     *
     * @author Vishaal
     * @version 1.2
     * @since 2017-10-28
     * @param i
     * @param j
     */

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


    /**
     * Method to set the neighbouringCells for Corner cells.
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-28
     * @param i
     * @param j
     */

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


    /**
     * Checks if cell is an Edge cell or not.
     *
     * @author Vishaal
     * @version 1.2
     * @since 2017-10-28
     * @param i
     * @param j
     * @return boolean that checks if cell is an edge cell.
     */

    @SuppressWarnings("Duplicates")
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


    /**
     * hecks if cell is a Corner cell or not.
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-28
     * @param i
     * @param j
     * @return boolean that checks if cell is a corner cell.
     */

    @SuppressWarnings("Duplicates")
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


    /**
     * Getter for Extended cell arraylist
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-27
     * @return Extended cells arraylist
     */

    public ArrayList<ExtendedCell> getExtendedCells()
    {
        return extendedCells;
    }


    /**
     * Getter for gridPane
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-27
     * @return gridPane
     */

    public GridPane getGridPane()
    {
        return gridPane;
    }


    /**
     * Setter for Extended Cells arraylist
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-27
     * @param extendedCells
     */

    public void setExtendedCells(ArrayList<ExtendedCell> extendedCells)
    {
        this.extendedCells = extendedCells;
    }

    /**
     * Setter for gridPane
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-27
     * @param gridPane
     */

    public void setGridPane(GridPane gridPane)
    {
        this.gridPane = gridPane;
    }

    /**
     * Getter for sideLengthX
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-27
     * @return sideLengthX
     */

    public int getSideLengthX()
    {
        return this.sideLengthX;
    }


    /**
     * Getter for sideLengthY
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-27
     * @return sideLengthY
     */

    public int getSideLengthY()
    {
        return this.sideLengthY;
    }
}