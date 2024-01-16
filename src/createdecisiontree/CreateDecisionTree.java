/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createdecisiontree;

import java.io.File;
import java.util.*;

/**
 *
 * @author Oluwatobi
 */
public class CreateDecisionTree {

    /**
     * @param args the command line arguments
     */
    
    static enum Attribute {// make it easier to do information gain comparison
        sepalLength,sepalWidth,petalLength,petalWidth;
    }
    
    static class  Node {
        Decision decision = new Decision();
        Node left, right;
        int num;
 
        public Node(Decision decision,int num)
        {
            this.decision = decision;
            left = null;
            right = null;
            this.num = num;
        }
        
        void setLeft(Node node){
            left = node;
        }
                
        void setRight(Node node){
            right = node;
        }
        
        int getNum(){
            return num;
        }
        
        public String toString(){
            return decision.toString();
        }
    }

    static class DecisionTree {
        Node root;
    
        DecisionTree(Decision decision, int num) { 
        root = new Node(decision, num); 
        }
        
        DecisionTree(){
            root = null;
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        DataBase flower = createDataBase("iris.data");// No need to generalize because file will always be iris for project
        int leafeSize =5;
        float purityMin = (float).950;
        DecisionTree tree = new DecisionTree(findBestgain(flower),0);
        tree.root = createDecisionTree(tree.root,leafeSize,purityMin);  
        printDecisionTree(tree.root, "");
    }
    
     public static DataBase createDataBase(String path){// method to add file to DataBase
         DataBase flower = new DataBase();
        try {
            File file=new File(path);   
            Scanner sc = new Scanner(file);     //file to be scanned  
            while (sc.hasNextLine()){ //returns true if and only if scanner has another token
                String line = sc.nextLine();
                if(line.equals("")){
                    break;
                }
                String [] data = line.split(",");// split base on how file is structured can be generalized
                ArrayList <Float> floatSet = new ArrayList <>();
                for (String value: data){
                    try{// making sure float in file
                       floatSet.add(Float.valueOf(value));
                    }
                    catch(Exception e)  {
                        try{// type checking in case of wrong data
                            Record record = new Record(value, floatSet);
                            flower.addRecord(record);
                        }
                        catch(Exception h)  {
                        
                        }
                   }
                }
           }         
        }  
        catch(Exception e){ 
           
        }
        return flower;
    }
     
    public static DataBase sortDatabase(int index, DataBase dataBase){
        ArrayList <Record> recordSet = dataBase.getRecordSet();
        int size = recordSet.size();
        for (int i = 0; i <size - 1; i++){// sort algo
            for (int j = i; j < size -1; j++){
                float value1 = recordSet.get(i).getValue(index);
                float value2 = recordSet.get(j+1).getValue(index);
                if (value1 > value2){
                    Record temp = recordSet.get(i);
                    Record temp2 = recordSet.get(j+1);
                    recordSet.set(i, temp2);
                    recordSet.set(j+1, temp);
                }
            }
        }
        dataBase.setRecordSet(recordSet);
        return dataBase;
    }
    
    public static Decision findBestgain(DataBase dataBase){
        int size = dataBase.size();
        Decision best = new Decision();
        for(Attribute s: Attribute.values()){// Use enum to make the looping easier
            dataBase = sortDatabase(s.ordinal(),dataBase);
            float splitpoint;
            if(size%2==0){// Finding mid point
                Record temp = dataBase.getRecord(size/2);
                Record temp2 = dataBase.getRecord(size/2-1);
                float value = temp.getValue(s.ordinal());
                float value2 = temp2.getValue(s.ordinal()/2);
                splitpoint = (value+value2)/2;
            }
            else
                splitpoint = dataBase.getRecord((size+1)/2-1).getValue(s.ordinal());
            Decision temp = new Decision(dataBase,s.name(),s.ordinal(),splitpoint);
            if(best.getGain() < temp.gain){
                best = temp;
            }
            else if(best.getGain() == temp.gain){
                best = temp;
            }
        }
        return best;
    }
    
    public static Node createDecisionTree (Node node, 
            int leafeSize,float purityMin){
        Decision decision = node.decision;
        int size = decision.getSize();
        float purity = decision.getPurity();
        if(size <= leafeSize || purity >= purityMin){//create leaf Node
            return createLeafNode(node);// stop conditon for recursion;
        }
        //Creating DataBase for Yes and No;
        DataBase dataBaseY = new DataBase();
        DataBase dataBaseN = new DataBase();
        for(Record record: decision.getDataBase().getRecordSet()){
            if(record.getValue(decision.getIndex()) <= decision.splitPoint){
                dataBaseY.addRecord(record);
            }
            else
                dataBaseN.addRecord(record);
        }
        node.setLeft(createDecisionTree(new Node(findBestgain(dataBaseY),node.getNum()+1),
                leafeSize, purityMin));
        node.setRight(createDecisionTree(new Node(findBestgain(dataBaseN),node.getNum()+1),
                leafeSize, purityMin));
        node.left.num = node.num + 1;
        return node;
    }
    public static Node createLeafNode (Node node){
        DataBase dataBaseY = new DataBase();
        DataBase dataBaseN = new DataBase();
        Decision decision = node.decision;
        for(Record record: decision.getDataBase().getRecordSet()){
            if(record.getValue(decision.getIndex()) <= decision.splitPoint){
                dataBaseY.addRecord(record);
            }
            else
                dataBaseN.addRecord(record);
        }
        node.setLeft(new Node(new Decision (dataBaseY), node.getNum()+1));
        node.left.num = node.num + 1;
        node.setRight(new Node(new Decision (dataBaseN), node.getNum()+1));
        node.left.num = node.num + 1;
        return node;
    }
    
    public static void printDecisionTree (Node node, String token){
        System.out.println(token.repeat( node.getNum()) + node);
        if(node.left !=null){
            printDecisionTree(node.left, "\t");
        }
        if(node.right !=null){
            printDecisionTree(node.right, "\t");
        }
        return;
    } 
    
}
