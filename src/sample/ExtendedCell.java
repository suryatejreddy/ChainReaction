package sample;

import NonUIComponents.Cell;
import NonUIComponents.Player;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

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
    private Group group;

    public ExtendedCell(Group group, StackPane cell, int coordX, int coordY, ExtendedPlayer player, ArrayList<ExtendedCell> neighbouringCells)
    {
        this.group=group;
        this.cell=cell;
        this.coordX=coordX;
        this.coordY=coordY;
        this.playerOccupiedBy=player;
        this.cellIsOccupied=false;
        this.criticalMass=-1;
        this.numberOfBallsPresent=0;
        this.neighbouringCells=neighbouringCells;

    }

    public void addAnimation()
    {
        double rotateTime = (500.0 * this.criticalMass)/(this.numberOfBallsPresent);
        RotateTransition rot = new RotateTransition(Duration.millis(rotateTime + this.coordX + this.coordY),getGroup());
        rot.setFromAngle(0);
        rot.setToAngle(360);
        rot.setInterpolator(Interpolator.EASE_BOTH);
        rot.setCycleCount(RotateTransition.INDEFINITE);
        rot.setAxis(Rotate.Z_AXIS);
        rot.setAutoReverse(false);
        rot.play();

    }

    public void startRotation()
    {
        this.addAnimation();
    }

    public ExtendedPlayer getPlayerOccupiedBy()
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

    public Group getGroup()
    {
        return group;
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

//        for (int i = 0; i < numberOfBallsPresent; i++)
//        {
//            getGroup().getChildren().remove(i);
//        }
        getGroup().getChildren().clear();
        this.numberOfBallsPresent = 0;
        this.playerOccupiedBy.removeCell(this);
        this.playerOccupiedBy = null;
        this.cellIsOccupied = false;

    }

    public Sphere getNewSphere(Color color)
    {
        Sphere s1 = new Sphere(10);
        PhongMaterial phongMaterial2 = new PhongMaterial();
        phongMaterial2.setDiffuseColor(color);
        phongMaterial2.setSpecularColor(Color.BLACK);
        s1.setMaterial(phongMaterial2);
        return s1;
    }

    public ArrayList<Sphere> getAllSpheres(Color color,int numberOfSpheres)
    {
        ArrayList<Sphere> temp = new ArrayList<Sphere>();
        for (int i=0;i<numberOfSpheres;i++)
        {
            temp.add(getNewSphere(color));
        }
        return temp;
    }

    public void setTranslationToSphere(Sphere sphere)
    {
        switch (getGroup().getChildren().size())
        {
            case 0:
                sphere.setTranslateX(0);
                break;

            case 1:
                sphere.setTranslateX(10);
                break;

            case 2:
                sphere.setTranslateX(5);
                sphere.setTranslateY(10);
                break;

            default:
                break;
        }
    }

    public void addBall(ExtendedPlayer curPlayer, boolean addBallInUI)
    {
        curPlayer.setTakenFirstMove(true);


        if (this.cellIsOccupied && this.playerOccupiedBy != curPlayer)  //cell is occupied by someone else
        {

            //TODO change color of the ball for UI
            //NON_UI_PART
            this.playerOccupiedBy.removeCell(this); //removing it from his list

            //UI_PART
            int groupSize = getGroup().getChildren().size();
            if (groupSize != 3)
            {
                getGroup().getChildren().clear();
                for(int i=0;i<groupSize;i++)
                {
                    Sphere sphere = getNewSphere(Main.getColor(curPlayer));
                    setTranslationToSphere(sphere);
                    getGroup().getChildren().add(sphere);
                }
            }
            else{
//                addBallInUI = false;
            }
        }
        //NON_UI_PART
        this.setCellIsOccupied(true);
        this.setPlayerOccupiedBy(curPlayer);
        this.numberOfBallsPresent+=1;


        //UI_PART, add a new Sphere
        if (addBallInUI)
        {
            Sphere sphere = getNewSphere(Main.getColor(curPlayer));
            setTranslationToSphere(sphere);
            getGroup().getChildren().add(sphere);

            //Add the group in case it was not added
            if(getCell().getChildren().size()==0)
            {
                getCell().getChildren().add(group);
            }
        }


        this.startRotation();


        if (this.numberOfBallsPresent == this.getCriticalMass())
        {
            //NON_UI Part
            this.emptyCell();


            //part where i add animation
            this.getGroup().getChildren().clear();
            ArrayList<Sphere> allSpheres = getAllSpheres(Main.getColor(curPlayer),4);
            ParallelTransition mainTransition = new ParallelTransition();
            for (int i=0;i<this.neighbouringCells.size();i++)
            {
                Sphere curSphere = allSpheres.get(i);
                ExtendedCell neighbour = this.neighbouringCells.get(i);
                TranslateTransition move = new TranslateTransition();
                move.setDuration(Duration.seconds(1));
                move.setNode(curSphere);
                int moveX = neighbour.coordX - this.coordX;
                int moveY = neighbour.coordY - this.coordY;
                move.setToX(moveX*60);
                move.setToY(moveY*60);
                this.cell.getChildren().add(curSphere);
                mainTransition.getChildren().add(move);
                this.cell.toFront();
            }
            mainTransition.play();

            mainTransition.setOnFinished(e -> {
                this.cell.getChildren().clear();
            });
            try
            {
                this.neighbouringCells.forEach((ExtendedCell neighbour) ->
                {
                    neighbour.addBall(curPlayer,false);
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