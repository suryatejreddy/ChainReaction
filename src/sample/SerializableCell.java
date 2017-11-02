package sample;

import java.io.Serializable;

public class SerializableCell implements Serializable
{
    public static final int VIOLET=0;
    public static final int BLUE=1;
    public static final int GREEN=2;
    public static final int YELLOW=3;
    public static final int ORANGE=4;
    public static final int RED=5;
    public static final int BROWN=6;
    public static final int WHITE=7;


    int x;
    int y;
    int playerColor;
    int numberOFBallsPresent;

    public SerializableCell(int x, int y, int playerColor, int numberOFBallsPresent)
    {
        this.x = x;
        this.y = y;
        this.playerColor = playerColor;
        this.numberOFBallsPresent = numberOFBallsPresent;
    }

    @SuppressWarnings("Duplicates")
    public String getColorByString()
    {
        switch(this.playerColor)
        {
            case VIOLET:    return "Violet";
            case BLUE:      return "Blue";
            case GREEN:     return "Green";
            case YELLOW:    return "Yellow";
            case ORANGE:    return "Orange";
            case RED:       return "Red";
            case BROWN:     return "Brown";
            case WHITE:     return "White";
        }
        return null;
    }


    public String toString()
    {
        String s1 =  "(" + this.x + "," + this.y + ")";
        if (playerColor != -1)
        {
            s1 += getColorByString().substring(0,1);
            s1 +=  this.numberOFBallsPresent;
        }
        else{
            s1 += " E";
        }
        return  s1;
    }
}
