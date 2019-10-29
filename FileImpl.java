import java.io.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class FileImpl extends UnicastRemoteObject implements FileInterface
{
    //name of the file to be transferred
      private String name;
    //Constructor for the Implementation class.
      public FileImpl(String s)throws RemoteException
    {
        super();
        name=s;
    }
    //Reads the file selected for transfer
    public byte[] downloadFile(String fileName)
    {
        try
            {
                File file=new File("Down/"+fileName);
                //Defines buffer in which the file will be read
                byte buffer[]=new byte[(int)file.length()];
                BufferedInputStream inputFileStream=new BufferedInputStream( new FileInputStream("Down/"+fileName));
                //Reads the file into buffer
                inputFileStream.read(buffer,0,buffer.length);
                inputFileStream.close();
                return(buffer);
           }
           catch(Exception e)
           {
                System.out.println("FileImpl:"+e.getMessage());
                e.printStackTrace();
                return(null);                   
           }
    }
    //Gets the list of files in the Server
    public String[] getFiles()
    {
        //Folder name in which the files should be stored
        String dirname="Down";
        File serverDir=new File(dirname);
        String file[]=serverDir.list();
        return file;
    } 
}