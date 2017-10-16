package sample;

import javafx.scene.layout.GridPane;
import java.util.ArrayList;


//class to hold UI of Grid(Matrix)
public class ExtendedGrid
{
    private ArrayList<ExtendedCell> extendedCells;
    private GridPane gridPane;

    public ExtendedGrid(ArrayList<ExtendedCell> cells, GridPane grid)
    {
        this.extendedCells = cells;
        this.gridPane = grid;
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