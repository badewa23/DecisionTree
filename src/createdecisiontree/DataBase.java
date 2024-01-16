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
public class DataBase {
    private ArrayList <Record> recordSet = new ArrayList ();
    
    DataBase(){
    }
    
    public void addRecord(Record record){
        recordSet.add(record);
    }
    
     public void setRecordSet(ArrayList <Record> recordSet){
         this.recordSet = recordSet ;
    }
     
     public void setRecord(int index, Record record){
         recordSet.set(index, record);
     }
    
    public ArrayList <Record> getRecordSet(){
         return recordSet;
    }
    
    public Record getRecord(int index){
        return recordSet.get(index);
    }
    
    public int size(){
        return recordSet.size();
    }

}
