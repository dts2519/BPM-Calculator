/*
This program has the user tap the beats to a song and calculates the beats per minute (BPM) of the song.
The only two beats that 'matter' per se are the very first and the very last (when the user hits the enter key).
Some songs may not have a standard BPM rate and so the results may be skewed. Generally, they are accurate to within 1 BPM unit.

There are numerous commented sections of code in this program, which come from earlier attempts to 
*/

package bpmcalculator;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.util.Timer;
//import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Bpm extends JFrame
{
    private int hits = 0; //twenths = 0;
    private long startTime, endTime;
    private JButton reset; 
    private JLabel instructions, displayBPM;
    private JLabel hitLabel, hitCount; //twenthsCount;
    
    //private Timer beatTimer;
    private Boolean timerFlag = false; //This
    
    ImageIcon hitIcon = new ImageIcon(getClass().getResource("Hit.PNG"));
    
    public Bpm()
    {
        super("BPM Calculator");
        JPanel p = new JPanel();
        
        p.setLayout(null);
        getContentPane().add(p);
        
        reset = new JButton("Reset");
        reset.setEnabled(false); //By default the reset button will not work
        reset.setBounds(500, 300, 100, 50);
        reset.setFocusable(false); //This makes it so the button never gets focused (only the window, where space/enter are pressed, can be focused)
        p.add(reset);
        
        instructions = new JLabel("Tap space in a rhythm, then press Enter in rhythm at the end of the stream");
        instructions.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        instructions.setBounds(10, 5, 680, 100);
        p.add(instructions);
        
        hitLabel = new JLabel();
        hitLabel.setBounds(150, 150, 300, 200);
        p.add(hitLabel);
        
        displayBPM = new JLabel("BPM: 0.0");
        displayBPM.setFont(new Font("Sans Serif", Font.PLAIN, 36));
        displayBPM.setBounds(250, 380, 400, 80);
        p.add(displayBPM);
        
        hitCount = new JLabel("Hits: 0");
        hitCount.setBounds(5, 400, 100, 20);
        p.add(hitCount);
        
//        twenthsCount = new JLabel("Time: 0"); //Twenths is short for 'twentieths' - it was originally tenths
//        twenthsCount.setBounds(5, 430, 100, 20);
//        p.add(twenthsCount);
        
        HandlerClass handler = new HandlerClass(); //Allows for key presses to move
        addKeyListener(handler);
        
        ButtonHandlerClass handler2 = new ButtonHandlerClass();
        reset.addActionListener(handler2);
    }
    
    
    private class HandlerClass implements KeyListener
    {
        public void keyTyped(KeyEvent ebento) 
        {
            //Nothing
        }

        public void keyPressed(KeyEvent ebento) 
        {
            if (ebento.getKeyCode() == KeyEvent.VK_SPACE)
            {
                reset.setEnabled(true);
                if (! timerFlag)
                {
                    start();
                }
                hitLabel.setIcon(hitIcon);
            }
            else if (ebento.getKeyCode() == KeyEvent.VK_ENTER && hits > 0)
            {
                //beatTimer.cancel();
                calculateBPM();
                instructions.setText("Press Reset to start over");                               
                hits = 0;
            }
        }

        public void keyReleased(KeyEvent ebento) 
        {
            if (ebento.getKeyCode() == KeyEvent.VK_SPACE) //One hit
            {
                hits++;
                hitCount.setText("Hits: " + hits);
                hitLabel.setIcon(null);
            }
        }     
    }
    
    private class ButtonHandlerClass implements ActionListener
    {
        public void actionPerformed(ActionEvent ebento) 
        {
            if (ebento.getSource() == reset)
            {
                //beatTimer.cancel();
                reset.setEnabled(false);
                timerFlag = false;
                displayBPM.setText("BPM: 0.0");
                instructions.setText("Tap space in a rhythm, then press Enter in rhythm at the end of the stream");
                hitCount.setText("Hits: 0");            
                hits = 0;
                //twenthsCount.setText("Time: 0");
                //twenths = 0;
            }
        }
    }
    
//    private class TimeClass extends TimerTask
//    {
//        public void run() 
//        {
//            twenths++;
//            twenthsCount.setText("Time: " + twenths);
//        }
//    }
//    
    
    //Called the first time space bar is pressed after reset
    public void start()
    {
        timerFlag = true;
        startTime = System.nanoTime();

        //beatTimer = new Timer();      
        //beatTimer.schedule(new TimeClass(), 0, 1 * 200);
        //startTime = System.currentTimeMillis();       
    }
    
    public void calculateBPM()
    {
        endTime = System.nanoTime();
        if (hits < 0)
        {
            hits = 0;
        }
        double hitsD = hits; 
        //twenthsD = twenths;
        //endTime = System.currentTimeMillis();
        
        double BPM = hitsD / ((endTime - startTime) / 60_000_000_000.0); //60 billion nanoseconds = 60 seconds = 1 minute
        //double BPM = (hitsD / (twenthsD / 300.00)) * 0.943; - The previous system was not too reliable. Even using milliseconds had some issues.
        
        //This rounds the BPM to the nearest tenth
        int tempBPM = (int)(BPM * 10);
        if (BPM + 0.05 >= BPM + 0.1)
        {
            tempBPM++;
        }
        double newBPM = tempBPM / 10.0;
        
        displayBPM.setText("BPM: " + newBPM); //Puts the calculated BPM on the screen
    }
}
