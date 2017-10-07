package NonUIComponents;

import java.util.ArrayList;

public class Matrix
{
    private int sideLengthX;
    private int sideLengthY;
    private ArrayList<ArrayList<Cell>> cells;

    public Matrix(int sideLengthX, int sideLengthY)
    {
        this.sideLengthX=sideLengthX;
        this.sideLengthY=sideLengthY;
        cells=new ArrayList<ArrayList<Cell>>();
        cells.forEach(k->
                {
                    k = new ArrayList<Cell>();
                    k.forEach(l->
                            {
                                l=new Cell(-1, new ArrayList<Cell>(), 0, null);
                            }
                    );
                }
        );

        int i,j;
        for(i=0;i<sideLengthX;i++)
        {
            for (j=0;j<sideLengthY;j++)
            {
                cells.get(i).get(j).setCoordinateX(i);
                cells.get(i).get(j).setCoordinateY(j);
                if(cellIsACornerCell(i,j))
                {
                    cells.get(i).get(j).setCriticalMass(Cell.TYPE_CORNER);
                    setNeighbouringCellsForCorner(i,j);
                }
                else if(cellIsAnEdgeCell(i,j))
                {
                    cells.get(i).get(j).setCriticalMass(Cell.TYPE_EDGE);
                    setNeighbouringCellsForEdge(i,j);
                }
                else
                {
                    cells.get(i).get(j).setCriticalMass(Cell.TYPE_NORMAL);
                    setNeighbouringCellsForNormal(i,j);
                }
            }
        }
    }

    private void setNeighbouringCellsForNormal(int i, int j)
    {
        Cell cell1=cells.get(i).get(j+1);
        Cell cell2=cells.get(i-1).get(j);
        Cell cell3=cells.get(i).get(j-1);
        Cell cell4=cells.get(i+1).get(j);
        ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
        cellArrayList.add(cell1);
        cellArrayList.add(cell2);
        cellArrayList.add(cell3);
        cellArrayList.add(cell4);
        cells.get(i).get(j).setNeighbouringCells(cellArrayList);
    }

    private void setNeighbouringCellsForEdge(int i, int j)
    {
        if(i==0)
        {
            Cell cell1=cells.get(i).get(j+1);
            Cell cell2=cells.get(i+1).get(j);
            Cell cell3=cells.get(i).get(j-1);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cellArrayList.add(cell3);
            cells.get(i).get(j).setNeighbouringCells(cellArrayList);
        }
        else if(i==sideLengthY-1)
        {
            Cell cell1=cells.get(i).get(j+1);
            Cell cell2=cells.get(i-1).get(j);
            Cell cell3=cells.get(i).get(j-1);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cellArrayList.add(cell3);
            cells.get(i).get(j).setNeighbouringCells(cellArrayList);
        }
        else if(j==0)
        {
            Cell cell1=cells.get(i).get(j+1);
            Cell cell2=cells.get(i-1).get(j);
            Cell cell3=cells.get(i+1).get(j);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cellArrayList.add(cell3);
            cells.get(i).get(j).setNeighbouringCells(cellArrayList);
        }
        else if(j==sideLengthX-1)
        {
            Cell cell1=cells.get(i).get(j-1);
            Cell cell2=cells.get(i-1).get(j);
            Cell cell3=cells.get(i+1).get(j);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cellArrayList.add(cell3);
            cells.get(i).get(j).setNeighbouringCells(cellArrayList);
        }
    }

    private void setNeighbouringCellsForCorner(int i, int j)
    {
        if(i==0 && j==0)
        {
            Cell cell1=cells.get(i).get(j+1);
            Cell cell2=cells.get(i+1).get(j);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cells.get(i).get(j).setNeighbouringCells(cellArrayList);
        }
        else if(i==0 && j==sideLengthX-1)
        {
            Cell cell1=cells.get(i).get(j-1);
            Cell cell2=cells.get(i+1).get(j);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cells.get(i).get(j).setNeighbouringCells(cellArrayList);
        }
        else if(i==sideLengthY-1 && j==0)
        {
            Cell cell1=cells.get(i).get(j+1);
            Cell cell2=cells.get(i-1).get(j);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cells.get(i).get(j).setNeighbouringCells(cellArrayList);
        }
        else if(i==sideLengthY-1 && j==sideLengthX-1)
        {
            Cell cell1=cells.get(i).get(j-1);
            Cell cell2=cells.get(i-1).get(j);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cells.get(i).get(j).setNeighbouringCells(cellArrayList);
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

    public boolean checkIfCellIsFree(Cell currentCell)
    {
        if(currentCell.isCellOccupied())
            return false;
        return true;
    }

    public ArrayList<ArrayList<Cell>> getCells()
    {
        return cells;
    }

    public int getSideLengthX()
    {
        return sideLengthX;
    }

    public int getSideLengthY()
    {
        return sideLengthY;
    }

    public void setCells(ArrayList<ArrayList<Cell>> cells)
    {
        this.cells = cells;
    }

    public void setSideLengthX(int sideLengthX)
    {
        this.sideLengthX = sideLengthX;
    }

    public void setSideLengthY(int sideLengthY)
    {
        this.sideLengthY = sideLengthY;
    }
}
