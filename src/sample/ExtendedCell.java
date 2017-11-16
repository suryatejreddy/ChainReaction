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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


//class to hold UI of Cell.
public class ExtendedCell implements Serializable
{
    public static int TYPE_CORNER=2;
    public static int TYPE_EDGE=3;
    public static int TYPE_NORMAL=4;

    public static final long serialVersionUID=12625252L;

    private transient StackPane cell;
    private int coordX;
    private int coordY;
    private ExtendedPlayer playerOccupiedBy;
    private int criticalMass;
    private int numberOfBallsPresent;
    private ArrayList<ExtendedCell> neighbouringCells;
    private boolean cellIsOccupied;
    private transient Group group;

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

        double rotateTime = (500.0 * this.criticalMass)/this.numberOfBallsPresent;
        RotateTransition rot = new RotateTransition(Duration.millis(rotateTime + this.coordX + this.coordY),getGroup());
        rot.setFromAngle(0);
        rot.setByAngle(360);
        rot.setInterpolator(Interpolator.LINEAR);
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
        if (this.numberOfBallsPresent >= this.criticalMass)
        {
            this.numberOfBallsPresent = this.numberOfBallsPresent - criticalMass;
        }
        else
        {
            this.numberOfBallsPresent = 0;
        }
        if (this.playerOccupiedBy != null)
        {
            this.playerOccupiedBy.removeCell(this);
        }
        this.playerOccupiedBy = null;
        this.cellIsOccupied = false;

        for(int i=0;i<numberOfBallsPresent;i++)
        {
            getGroup().getChildren().add(getNewSphere(Main.getColor(this.playerOccupiedBy)));
        }
        cell.getChildren().clear();
        cell.getChildren().add(getGroup());
    }

    public static Sphere staticGetSphere(Color color)
    {
        Sphere s=new Sphere(10);
        PhongMaterial phongMaterial=new PhongMaterial();
        phongMaterial.setDiffuseColor(color);
        phongMaterial.setSpecularColor(Color.BLACK);
        s.setMaterial(phongMaterial);
        return s;
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



    public void addBall(ExtendedPlayer curPlayer, boolean addBallInUI, boolean callFromMain)
    {
        curPlayer.setTakenFirstMove(true);

        if (this.cellIsOccupied && this.playerOccupiedBy != curPlayer)  //cell is occupied by someone else
        {

            //TODO change color of the ball for UI
            //NON_UI_PART
            this.playerOccupiedBy.removeCell(this); //removing it from his list
            //UI_PART
            int groupSize = getGroup().getChildren().size();
            if (groupSize != this.criticalMass - 1)
            {
                getGroup().getChildren().clear();
                for(int i=0;i<groupSize;i++)
                {
                    Sphere sphere = getNewSphere(curPlayer.getPlayerColour());
                    setTranslationToSphere(sphere);
                    getGroup().getChildren().add(sphere);
                }

//                cell.getChildren().clear();
//                cell.getChildren().add(getGroup());
            }
            else{
//                addBallInUI = false;
            }
            curPlayer.addCell(this);
        }

        //NON_UI_PART
        this.setCellIsOccupied(true);
        this.setPlayerOccupiedBy(curPlayer);
        this.numberOfBallsPresent+=1;


        //UI_PART, add a new Sphere
        Sphere sphere = getNewSphere(curPlayer.getPlayerColour());
        setTranslationToSphere(sphere);
        getGroup().getChildren().add(sphere);
            //Add the group in case it was not added
        if(getCell()!=null && getCell().getChildren().size()==0)
        {
             getCell().getChildren().add(group);
        }


        this.startRotation();


        if (this.numberOfBallsPresent >= this.getCriticalMass())
        {

//            try
//            {
//                Main.playOnRecurse();
//            }
//            catch(IOException e)
//            {
//                e.printStackTrace();
//            }

            //NON_UI Part
            this.emptyCell();


            ArrayList<Sphere> allSpheres = getAllSpheres(curPlayer.getPlayerColour(),this.getCriticalMass());
            ParallelTransition mainTransition = new ParallelTransition();
            for (int i=0;i<this.neighbouringCells.size();i++)
            {
                Sphere curSphere = allSpheres.get(i);
                ExtendedCell neighbour = this.neighbouringCells.get(i);
                TranslateTransition move = new TranslateTransition();
                move.setDuration(Duration.seconds(0.5));
                move.setNode(curSphere);
                int moveX = neighbour.coordX - this.coordX;
                int moveY = neighbour.coordY - this.coordY;
                if (this.criticalMass != 4)
                {
                    moveY = 0 - moveY;
                }
                move.setToX(moveX*60);
                move.setToY(moveY*60);
                this.cell.getChildren().add(curSphere);
                mainTransition.getChildren().add(move);
                this.cell.toFront();
            }
            mainTransition.play();

            mainTransition.setOnFinished(e ->
            {
                try {
                    Main.playOnClick();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                this.cell.getChildren().clear();

                getGroup().getChildren().clear();
                for(int i=0;i<numberOfBallsPresent;i++)
                {
                    Sphere newSphere = getNewSphere(curPlayer.getPlayerColour());
                    setTranslationToSphere(newSphere);
                    getGroup().getChildren().add(newSphere);
                }
                this.cell.getChildren().add(getGroup());


                boolean flag = true;
                for (int i=0;i < this.neighbouringCells.size() ; i ++)
                {
                    ExtendedCell neighbour = this.neighbouringCells.get(i);
                    if (neighbour.numberOfBallsPresent == neighbour.criticalMass - 1)
                    {
                        flag = false;
                    }
                    try
                    {
                        neighbour.addBall(curPlayer,true,false);
                    }
                    catch (StackOverflowError s1)
                    {
                        System.out.println("Yayy... You won.");
                    }

                }
                if (flag)
                {
                    System.out.println(this + " is lastt ");
                    Main.onAnimationCompleted(curPlayer);
                }
            });
        }
        else
        {
//            try
//            {
//                Main.playOnClick();
//            }
//            catch(IOException e)
//            {
//                e.printStackTrace();
//            }
            if (callFromMain)
            {
                Main.onAnimationCompleted(curPlayer);
            }
        }

    }

    public void setGroup(Group group)
    {
        this.group = group;
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