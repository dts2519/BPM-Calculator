//The purpose of this file is to create the window containing the calculator.

package bpmcalculator;

import javax.swing.JFrame;

public class BPMCalculator 
{
    public static void main(String[] args) 
    {
        Bpm calculator = new Bpm();
        calculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calculator.setSize(700, 500); //Size of the window
        calculator.setVisible(true);
    }
}
