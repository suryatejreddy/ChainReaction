package sample;

import javafx.scene.layout.StackPane;


//class to hold UI of Cell.
public class ExtendedCell
{
    private StackPane cell;
    private int coordX;
    private int coordY;

    public ExtendedCell(StackPane cell, int coordX, int coordY)
    {
        this.cell=cell;
        this.coordX=coordX;
        this.coordY=coordY;
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

    public void setCell(StackPane cell)
    {
        this.cell = cell;
    }

    public void setCoordX(int coordX)
    {
        this.coordX = coordX;
    }

    public void setCoordY(int coordY)
    {
        this.coordY = coordY;
    }
}