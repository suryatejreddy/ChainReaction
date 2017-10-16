package NonUIComponents;

import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Matrix
{
    private int sideLengthX;
    private int sideLengthY;
    private ArrayList<ArrayList<Cell>> allCells;

    public Matrix(int sideLengthX, int sideLengthY)

    {

        //matrix builds like a 2d graph, i.e, (0,0) is lower left corner
        this.sideLengthX=sideLengthX;
        this.sideLengthY=sideLengthY;
        allCells=new ArrayList<ArrayList<Cell>>();
        for (int i=0;i<sideLengthY;i++)
        {
            ArrayList<Cell> rowI = new ArrayList<Cell>();
            for (int j=0;j<sideLengthX;j++)
            {
                Cell newCell = new Cell(-1,new ArrayList<Cell>(), 0,null,j,i);
                rowI.add(newCell);
            }
            allCells.add(rowI);
        }

        for (int i=0;i<sideLengthY;i++)
        {
            for (int j=0;j<sideLengthX;j++)
            {
                if (cellIsACornerCell(i,j))
                {
                    allCells.get(i).get(j).setCriticalMass(Cell.TYPE_CORNER);
                    setNeighbouringCellsForCorner(i,j);
                }
                else if (cellIsAnEdgeCell(i,j))
                {
                    allCells.get(i).get(j).setCriticalMass(Cell.TYPE_EDGE);
                    setNeighbouringCellsForEdge(i,j);
                }
                else
                {
                    allCells.get(i).get(j).setCriticalMass(Cell.TYPE_NORMAL);
                    setNeighbouringCellsForNormal(i,j);
                }
            }
        }
    }

    private void setNeighbouringCellsForNormal(int i, int j)
    {
        Cell cell1=allCells.get(i).get(j+1);
        Cell cell2=allCells.get(i-1).get(j);
        Cell cell3=allCells.get(i).get(j-1);
        Cell cell4=allCells.get(i+1).get(j);
        ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
        cellArrayList.add(cell1);
        cellArrayList.add(cell2);
        cellArrayList.add(cell3);
        cellArrayList.add(cell4);
        allCells.get(i).get(j).setNeighbouringCells(cellArrayList);
    }

    private void setNeighbouringCellsForEdge(int i, int j)
    {
        if(i==0)
        {
            Cell cell1=allCells.get(i).get(j+1);
            Cell cell2=allCells.get(i+1).get(j);
            Cell cell3=allCells.get(i).get(j-1);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cellArrayList.add(cell3);
            allCells.get(i).get(j).setNeighbouringCells(cellArrayList);
        }
        else if(i==sideLengthY-1)
        {
            Cell cell1=allCells.get(i).get(j+1);
            Cell cell2=allCells.get(i-1).get(j);
            Cell cell3=allCells.get(i).get(j-1);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cellArrayList.add(cell3);
            allCells.get(i).get(j).setNeighbouringCells(cellArrayList);
        }
        else if(j==0)
        {
            Cell cell1=allCells.get(i).get(j+1);
            Cell cell2=allCells.get(i-1).get(j);
            Cell cell3=allCells.get(i+1).get(j);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cellArrayList.add(cell3);
            allCells.get(i).get(j).setNeighbouringCells(cellArrayList);
        }
        else if(j==sideLengthX-1)
        {
            Cell cell1=allCells.get(i).get(j-1);
            Cell cell2=allCells.get(i-1).get(j);
            Cell cell3=allCells.get(i+1).get(j);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            cellArrayList.add(cell3);
            allCells.get(i).get(j).setNeighbouringCells(cellArrayList);
        }
    }

    private void setNeighbouringCellsForCorner(int i, int j)
    {
        if(i==0 && j==0)
        {
            Cell cell1=allCells.get(i).get(j+1);
            Cell cell2=allCells.get(i+1).get(j);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            allCells.get(i).get(j).setNeighbouringCells(cellArrayList);
        }
        else if(i==0 && j==sideLengthX-1)
        {
            Cell cell1=allCells.get(i).get(j-1);
            Cell cell2=allCells.get(i+1).get(j);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            allCells.get(i).get(j).setNeighbouringCells(cellArrayList);
        }
        else if(i==sideLengthY-1 && j==0)
        {
            Cell cell1=allCells.get(i).get(j+1);
            Cell cell2=allCells.get(i-1).get(j);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            allCells.get(i).get(j).setNeighbouringCells(cellArrayList);
        }
        else if(i==sideLengthY-1 && j==sideLengthX-1)
        {
            Cell cell1=allCells.get(i).get(j-1);
            Cell cell2=allCells.get(i-1).get(j);
            ArrayList<Cell> cellArrayList=new ArrayList<Cell>();
            cellArrayList.add(cell1);
            cellArrayList.add(cell2);
            allCells.get(i).get(j).setNeighbouringCells(cellArrayList);
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
        return allCells;
    }

    public int getSideLengthX()
    {
        return sideLengthX;
    }

    public int getSideLengthY()
    {
        return sideLengthY;
    }

    public void setCells(ArrayList<ArrayList<Cell>> allCells)
    {
        this.allCells = allCells;
    }

    public void setSideLengthX(int sideLengthX)
    {
        this.sideLengthX = sideLengthX;
    }

    public void setSideLengthY(int sideLengthY)
    {
        this.sideLengthY = sideLengthY;
    }

    public Cell getCellFromCoordinate(int row, int column){
        return this.allCells.get(row).get(column);
    }

    public void printMatrix()
    {
        for (int rowNum = allCells.size() - 1 ; rowNum >= 0 ; rowNum --)
        {
            ArrayList<Cell> curRow = allCells.get(rowNum);
            for (int colNum =0; colNum < curRow.size() ; colNum ++)
            {
                System.out.print(curRow.get(colNum) + "\t\t\t");
            }
            System.out.println("");
        }
    }
}
