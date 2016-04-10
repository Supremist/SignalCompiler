package compiler.lexan;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by supremist on 3/23/16.
 */
public class Complex {
    public double real;
    public double imagine;

    public Complex(){
        this.real = 0;
        this.imagine = 0;
    }

    public Complex init (double real, double imagine){
        this.real = real;
        this.imagine = imagine;
        return this;
    }

    public Complex init (double real){
        this.real = real;
        this.imagine = 0;
        return this;
    }

    public Complex fromExp(double ro, double phi){
        real = ro*cos(phi);
        imagine = ro*sin(phi);
        return this;
    }

    public Complex readFromStr(String str){
        String[] values = str.trim().split(" ");
        if (values.length == 2) {
            real = Double.parseDouble(values[0]);
            imagine = Double.parseDouble(values[1]);
        }
        return this;
    }

    public String toString(){
        return String.valueOf(real)+" "+String.valueOf(imagine);
    }

}
