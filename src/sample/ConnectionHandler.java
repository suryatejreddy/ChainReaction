package sample;

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
        try
        {
            DataOutputStream out=new DataOutputStream(connection.getOutputStream());
            out.writeUTF("Hello C");
            out.close();
            connection.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
