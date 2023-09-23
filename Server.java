import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // use interface
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;

public class Server    implements ActionListener {
    JTextField msg;
    JPanel  a1;
    static DataOutputStream dout;

     static Box vertical = Box.createVerticalBox();

    static JFrame  f = new JFrame();
   Server(){

         f.setLayout(null);
         JPanel p1 = new JPanel();  // create a penal constructor
         p1.setBackground(new Color(7,94,84));
         p1.setBounds(0,0,450,70);
         p1.setLayout(null);
         f.add(p1);

         ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/aero.png"));
         Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
         ImageIcon i3= new ImageIcon(i2);
         JLabel back = new JLabel(i3);
         back.setBounds(5,20,25,25);
         p1.add(back);

         back.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent ae) {
                 System.exit(0);
             }
         });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/5.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6= new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);


        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9= new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);


        ImageIcon ii1 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image ii2 = ii1.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon ii3= new ImageIcon(ii2);
        JLabel phone = new JLabel(ii3);
        phone.setBounds(360,20,35,30);
        p1.add(phone);


        ImageIcon l = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image l1 = l.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon l2= new ImageIcon(l1);
        JLabel icon = new JLabel(l2);
        icon.setBounds(420,20,10,25);
        p1.add(icon);

        JLabel name = new JLabel("Kuldeep Bhaiya");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(name);


        JLabel status = new JLabel("Online");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,10));
        p1.add(status);

       a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);

        msg = new JTextField();
        msg.setBounds(5, 655 , 310 ,40);
        msg.setFont(new Font("SET_SERIF",Font.PLAIN,16));
        f.add(msg);


        JButton  send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.addActionListener(this);
        send.setFont(new Font("SET_SERIF",Font.PLAIN,16));
        send.setForeground(Color.WHITE);
        f.add(send);









        f.setSize(450,700);
        f.setLocation(200,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);



    }
    public void actionPerformed(ActionEvent ae) {
       try{
        String out = msg.getText();


        JPanel p2 = formatLabel(out);


        a1.setLayout(new BorderLayout());

        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));  // mas space for vertical

        a1.add(vertical, BorderLayout.PAGE_START);

        dout.writeUTF(out);

        msg.setText(""); // msg likhne ke bad msg delete krne ke liye


        f.repaint();
        f.invalidate();
        f.validate();
    }catch (Exception e){
           e.printStackTrace();
       }
    }

    public static  JPanel formatLabel(String out){
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));


     JLabel output = new JLabel("<html><p style=\"width: 150px\">"+out+"</p></html>");
     output.setFont(new Font("Tahoma",Font.PLAIN,16));
     output.setBackground(new Color(37,211,102));
     output.setOpaque(true); // show karne ke liye
         output.setBorder(new EmptyBorder(15,15,15,50));


     panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
    return panel;
    }


    public static void main(String[] args){
        new Server();

        try{
            ServerSocket skt = new ServerSocket(6001);
            while (true){
                Socket  s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                 dout = new DataOutputStream(s.getOutputStream());

                while(true){
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();

                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
