/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createdecisiontree;

import java.util.ArrayList;

/**
 *
 * @author Oluwatobi
 */
public class Record {
    private String className;
    private ArrayList <Float> valueSet;
    
    Record(String className, ArrayList <Float> valueSet){
        setClassName(className);
        setValueSet(valueSet);
    }
    
    public void addValue(Float value){
        valueSet.add(value);
    }
    
    public void setClassName(String className){
        this.className = className;
    }
    
    public void setValueSet(ArrayList <Float>  valueSet){
        this.valueSet = valueSet;
    }
    
    public String getClassName(){
        return className;
    }
    
    public ArrayList <Float> getValueSet(){
        return valueSet;
    }
    
    public float getValue(int index){
        return valueSet.get(index);
    }
    
      
}
