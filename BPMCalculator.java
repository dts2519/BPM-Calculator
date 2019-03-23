package bpmcalculator;

import javax.swing.JFrame;

public class BPMCalculator 
{
    public static void main(String[] args) 
    {
        Bpm moira = new Bpm();
        moira.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        moira.setSize(700, 500); //Size of the window
        moira.setVisible(true);
    }
}
