import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.rmi.*;
import java.io.*;

public class FClient extends JFrame implements ActionListener {
    JList Files;
    JButton Download, Exit;
    String list[];
    int curpointer = 0;
    public static String arg;

    public FClient(String args) {
        Container container = getContentPane();
        container.setLayout(new FlowLayout());
        try {
            String name = "//" + args + "/FServer";
            FileInterface fileInt = (FileInterface) Naming.lookup("//192.168.1.1/FServer");
            list = fileInt.getFiles();
            Files = new JList(fileInt.getFiles());
        } catch (Exception e) {
            System.out.println("FServer Exception:" + e.getMessage());
        }

        Files.setVisibleRowCount(3);
        Files.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Files.addListSelectionListener(
            new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent ev) {
                    curpointer = Files.getSelectedIndex();
                }
            });

        Download = new JButton("Download");
        Exit = new JButton("Exit");

        container.add(Download);
        container.add(Exit);
        container.add(new JScrollPane(Files));

        Download.addActionListener(this);
        Exit.addActionListener(this);
        setTitle("FClient");
        setSize(new Dimension(250, 250));
        setVisible(true);

    }
    public void actionPerformed(ActionEvent ae) {
        String eventlabel = ae.getActionCommand();
        if (eventlabel.equals("Exit")) {
            setVisible(false);
            System.exit(0);
        } else if (eventlabel.equals("Download")) {
            try {
                try {
                    long start = System.currentTimeMillis();

                    String name = "//" + arg + "/FServer";
                    FileInterface fileInt = (FileInterface) Naming.lookup(name);
                    byte[] filedata = fileInt.downloadFile(list[curpointer]);
                    File file = new File(list[curpointer]);
                    BufferedOutputStream outputFile = new BufferedOutputStream(new FileOutputStream(file.getAbsolutePath()));
                    outputFile.write(filedata, 0, filedata.length);
                    outputFile.flush();
                    outputFile.close();
                    JOptionPane.showMessageDialog(FClient.this, list[curpointer] + "Downloaded successfully");
                    long end = System.currentTimeMillis();
                    long diff = end - start;
                    System.out.println("time for transfer is:" + diff + "ms");
                } catch (Exception e) {
                    System.out.println("Got an exception");
                }
            } catch (Exception e) {
                System.out.println("FServer Exception:" + e.getMessage());
            }

        }
    }
    public static void main(String args[]) {
        FClient Fclient = new FClient(args[0]);
        arg = args[0];
        Fclient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}