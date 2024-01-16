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
public class Decision {
    String [] classes = {"Iris-setosa","Iris-versicolor","Iris-virginica"}; // already know class so used data for convience
    DataBase dataBase = new DataBase();
    String name;
    String className;
    int index;
    float splitPoint;
    double gain;
    int [] classesSize = new int [3];
    float purity;
    int size;
    boolean leaf = false;
    
    Decision (){
        
    }
    
    Decision (DataBase dataBase){//I am assume all decisions create with just DataBase are leaf node
       createLeaf(dataBase);// has set of dataBase
    }    
    
    Decision (DataBase dataBase, String name, int index){
        setDataBase(dataBase);// has the set of size, classSize, and purity
        setName(name,index);// has set of index
    }
    
    Decision(DataBase dataBase, String name, int index, float splitPoint){
        setDataBase(dataBase);// has the set of size, classSize, and purity
        setName(name, index);// has set of index
        setSplitPoint(splitPoint);// has set of gain
    }
    
    public void setDataBase(DataBase dataBase){
        this.dataBase.setRecordSet(dataBase.getRecordSet());
        setSize();
        setClassSize();
        setPurity();
    }
            
    public void setName (String name,int index){
        this.name = name;
        setIndex(index);
    }
    
    public void setClassName (){
        int maxNum =0;
        int maxIndex =0;
        for(int i =0; i< classesSize.length; i++){
            if(classesSize[i] > maxNum){
                maxIndex =i;
                maxNum = classesSize[i];
            }
        }
        className =  classes[maxIndex];
    }
    
    public void setIndex (int index){
        this.index = index;
    }
            
    public void setSplitPoint(float splitPoint){
        this.splitPoint = splitPoint;
        setGain(); 
    }
            
    public void setGain (){
        DataBase dataBaseY = new DataBase();
        DataBase dataBaseN = new DataBase();
        for(Record record: dataBase.getRecordSet()){
            if(record.getValue(index) <= splitPoint){
                dataBaseY.addRecord(record);
            }
            else
                dataBaseN.addRecord(record);
        }
        double temp= (double)dataBaseY.size()/size * entropy(dataBaseY);
        double temp2= (double)dataBaseN.size()/size * entropy(dataBaseN);
        double temp3 =  entropy(dataBase);
        gain = temp3 -(temp+temp2);  
        int m = 3;
    }
    
    public void setClassSize(){
         for(Record record: dataBase.getRecordSet()){
            for(int i = 0; i < classes.length; i++){
                if(record.getClassName().equals(classes[i])){
                    switch (i){
                        case 0: classesSize[0]+=1;
                                break;
                        case 1: classesSize[1]+=1;
                                break;
                        case 2: classesSize[2]+=1;
                                break;
                    }
                }
            }
        }
    }
    
    public void setPurity(){
        int max =0;
        for(int num: classesSize){
            if (num > max){
                max = num;
            }
        }
        purity = (float)max/size;
    }
    
    public void setSize(){
        size = dataBase.size();
    }
    
    private double entropy(DataBase dataBase){
        double entropy =0;
        int [] classesSize = new int [3];
        for(Record record: dataBase.getRecordSet()){
            for(int i = 0; i < classes.length; i++){
                if(record.getClassName().equals(classes[i])){
                    switch (i){
                        case 0: classesSize[0]+=1;
                                break;
                        case 1: classesSize[1]+=1;
                                break;
                        case 2: classesSize[2]+=1;
                                break;
                    }
                }
            }
        }
        for(int num:classesSize){
            double temp;
            double temp2;
            if(num ==0){// to prevent -infinity
                entropy += 0;
            }
            else{
                temp = num/(double)dataBase.size();
                temp2 = Math.log(temp) / Math.log(2);
                entropy += temp * temp2;
            }
        }
        return -entropy;
    }
    
    public void createLeaf (DataBase dataBase){
        setDataBase(dataBase);// has the set of size, classSize, and purity
        setClassName();
        leaf = true;
    } 
    
    public double getGain(){
        return gain;
    }
    
    public String getName(){
        return name;
    }
    
    public String getClassName(){
        return className;
    }
    
     public int getIndex(){
        return index;
    }
    
    public int getSize(){
        return size;
    }
    
    public float getSplitpoint(){
        return splitPoint;
    }
    
    public float getPurity(){
        return purity;
    }
    
    public boolean getLeaf(){
        return leaf;
    }
    
    public DataBase getDataBase(){
        return dataBase;
    }
    
    
    
    public String toString(){
        if(getLeaf())
            return "Leaf: Label= " + getClassName() +" purity= " + getPurity() 
                    + " size= " + getSize();
        return "Decision: "+ getName() +  " <= " + getSplitpoint() + ", Gain= " 
                + getGain();
    }
}
