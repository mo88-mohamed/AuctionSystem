package Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Countdown {
    private long startTime;
    private long endTime;
    private long timeLeft;
    private Timer timer;
    private ActionListener counter;
    public Countdown(JLabel countdown ,long startTime,long endTime){

        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.calculateLeftTime();

         counter=new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                timeLeft -=100;
                SimpleDateFormat df=new SimpleDateFormat("dd HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("GMT"));
                countdown.setText(df.format(timeLeft));
//                System.out.println(df.format(timeLeft));
                if(timeLeft <=0)
                {
                    timer.stop();
                    countdown.setText("Sold");
                    countdown.setForeground(Color.red);
                }
            }
        };

    }

    public void calculateLeftTime() {
//        this.timeLeft = endTime-startTime;
        this.timeLeft = endTime - System.currentTimeMillis();
    }

    public long getTimeLeft() {
        return this.timeLeft;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getStartTime() {
        return startTime;
    }
    public void startCountdown(){
        timer =new Timer(100,counter);
        timer.start();
    }


}
