package org.firstinspires.ftc.teamcode;

/**
 * Created by Mikko on 3/2/17
 */

public class DoubleScale
{
    public double inmin, inmax, outmin, outmax;

    double scale;

    public DoubleScale(double inmin, double inmax, double outmin, double outmax)
    {
        this.inmin = inmin;
        this.inmax = inmax;
        this.outmin = outmin;
        this.outmax = outmax;

        calculateValues();
    }

    void calculateValues()
    {
        scale = (outmax - outmin) / (inmax - inmin);
    }

    public double scale(double in)
    {
        return (in - inmin) * scale + outmin;
    }
}
