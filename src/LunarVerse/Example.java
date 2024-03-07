package LunarVerse;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class Example {

	JFrame frame=new JFrame();
    public Example(String name) throws IOException
    {
        BufferedImage img=ImageIO.read(new File(name));
        ImageIcon icon=new ImageIcon(img);
        frame.setLayout(new FlowLayout());
        frame.setSize(375,550);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void close() {
    	frame.setVisible(false);
    }
    
    public void open() {
    	frame.setVisible(true);
    }
    
}