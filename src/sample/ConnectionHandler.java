package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Class to handle server/client requests
 *
 * @author Vishaal
 */

public class ConnectionHandler implements Runnable
{
    Socket connection;
    public static int mainX;
    public static int mainY;

    /**
     * Parameterized Constructor
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-16
     * @param connection
     */

    public ConnectionHandler(Socket connection)
    {
        this.connection=connection;
    }

    /**
     * Overriden function from Runnable interface.
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-16
     */

    @Override
    public void run()
    {
        Main.isMultiplayer=true;
        Main.isServer = true;
        try
        {
            DataOutputStream out=new DataOutputStream(connection.getOutputStream());
            DataInputStream in=new DataInputStream(connection.getInputStream());
            out.writeUTF("Accepted");
            Main.launchGame(2, 9, 6);
            Main.liveSocket=connection;
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        while(true)
                        {
                            String i=in.readUTF();

                            int x=Integer.parseInt(i.substring(0,1));
                            int y=Integer.parseInt(i.substring(2));
                            if (x == mainX && y == mainY)
                            {
                                continue;
                            }
                            mainX = x;
                            mainY = y;
                            Platform.runLater(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    try
                                    {
                                        Main.multiplayerReceivedCell(x,y);
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                            });
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
