package sample;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionHandler implements Runnable
{
    Socket connection;

    public ConnectionHandler(Socket connection)
    {
        this.connection=connection;
    }

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
