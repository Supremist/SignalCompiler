package compiler.SyntacticalAnalizer.Declarations.Constant;

import compiler.SyntacticalAnalizer.CompileException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import static java.lang.Double.NaN;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by supremist on 3/23/16.
 */
public class ConstantValue {
    public double real;
    public double imagine;

    public ConstantValue(){
        this.real = 0;
        this.imagine = 0;
    }

    public ConstantValue (double real, double imagine){
        this.real = real;
        this.imagine = imagine;
    }

    public ConstantValue (double real){
        this.real = real;
        this.imagine = 0;
    }

    public static ConstantValue fromExp(double ro, double phi){
        return new ConstantValue(ro*cos(phi), ro*sin(phi));
    }

    public ConstantValue add (ConstantValue value){
        return new ConstantValue(this.real + value.real, this.imagine + value.imagine);
    }

    public ConstantValue subrtact (ConstantValue value){
        return new ConstantValue(this.real - value.real, this.imagine - value.imagine);
    }

    public ConstantValue multiply (ConstantValue value){
        double new_real = this.real * value.real - this.imagine * value.imagine;
        double new_imagine = this.real * value.imagine + this.imagine * value.real;
        return new ConstantValue(new_real, new_imagine);
    }

    public ConstantValue divide (ConstantValue value){
        double divider = Math.pow(value.real, 2) + Math.pow(value.imagine, 2);
        double new_real = this.real * value.real + this.imagine * value.imagine;
        double new_imagine = this.imagine * value.real - this.real * value.imagine;
        return new ConstantValue(new_real/divider, new_imagine/divider);
    }

    public boolean isInteger(){
        return imagine == 0 && real != NaN && !Double.isInfinite(real)
                && real == Math.floor(real) && real < Integer.MAX_VALUE;
    }

    public boolean isFloat(){
        return imagine == 0;
    }

    public int getInteger(){
        return (int) real;
    }

    public int bitOr(ConstantValue value) throws IllegalArgumentException{
        if(this.isInteger() && value.isInteger()){
            return this.getInteger() | value.getInteger();
        }
        else{
            throw new IllegalArgumentException("Int type expected");
        }
    }

    public int bitAnd(ConstantValue value) throws IllegalArgumentException{
        if(this.isInteger() && value.isInteger()){
            return this.getInteger() & value.getInteger();
        }
        else{
            throw new IllegalArgumentException("Int type expected");
        }
    }

    public ConstantValue unaryMinus(){
        return new ConstantValue(-this.real, -this.imagine);
    }

    public double mod(ConstantValue value) throws IllegalArgumentException{
        if(this.isFloat() && value.isInteger()){
            return this.real % value.getInteger();
        }
        else{
            throw new IllegalArgumentException("Int type expected");
        }
    }

    public ConstantValue readFromStr(String str){
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
