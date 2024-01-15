package counting;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Data {

    public int r = 0; //number of destroys - liczba uszkodzeń
    public Map<Float, Integer> reparingTimeMap = new HashMap<Float, Integer>(); //tablica chyba 10.12 albo 10.15 z książki
    public Map<Integer,Float> statisticChiSquareMap01 = new HashMap<Integer,Float>(); //dla alpha = 0,1
    public Map<Integer,Float> statisticChiSquareMap005 = new HashMap<Integer,Float>(); //dla alpha = 0,1
    public Map<Integer,Float> statisticChiSquareMap095 = new HashMap<Integer,Float>(); //dla alpha = 0,1
    public float Tn1 = 0; // maxAverageReparingTime - maksymalny dopuszczalny średni czas naprawy
    public float Tn = 0; // requiredReparingTime - wymagany średni czas naprawy
    public float Tns = 0; // sum of reparingTimeArray - łączny czas wszystkich napraw
    public float TnE = 0; // TnE --> Tn estimated //szacowany średni czas naprawy
    public float Tng = 0;
    public float Tnd = 0;
    public float alpha = 0; // level of significance - poziom istotności alfa
    public float beta = 0; // risk of Recipient - ryzyko obdiorcy beta
    public float quotient = 0; // pattern will be shown in the body function
    public boolean isConditionMet = false;
    public Data(List<Float> anotherTimesForAllData, float maxAverageReparingTime, float requiredReparingTime,float alpha, float beta) {
        this.Tn1 = maxAverageReparingTime;
        this.Tn = requiredReparingTime;
        this.alpha = alpha;
        this.beta = beta;

        fillReparingTimeMap();
        fillStatisticChiSquareMap01();
        fillStatisticChiSquareMap005();
        fillStatisticChiSquareMap095();
    }
    public Map<Float, Integer> getReparingTimeMap() {
        return reparingTimeMap;
    }
    public void setReparingTimeMap(Map<Float, Integer> reparingTimeMap) {
        this.reparingTimeMap = reparingTimeMap;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public float getTn1() {
        return Tn1;
    }
    public void setTn1(float tn1) {
        Tn1 = tn1;
    }
    public float getTn() {
        return Tn;
    }
    public void setTn(float Tn) {
        Tn = Tn;
    }
    public float getAlpha() {
        return alpha;
    }
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    public float getBeta() {
        return beta;
    }
    public void setBeta(float beta) {
        this.beta = beta;
    }
    public float getQuotient() {
        return quotient;
    }
    public void setQuotient(float quotient) {
        this.quotient = quotient;
    }
    public float getTng() {
        return Tng;
    }
    public void setTng(float tng) {
        Tng = tng;
    }
    public float getTnd() {
        return Tnd;
    }
    public void setTnd(float tnd) {
        Tnd = tnd;
    }
    public void fillReparingTimeMap() {
        reparingTimeMap.put(1.5f, 41);
        reparingTimeMap.put(1.6f, 30);
        reparingTimeMap.put(1.7f, 24);
        reparingTimeMap.put(1.8f, 20);
        reparingTimeMap.put(1.9f, 16);
        reparingTimeMap.put(2.0f, 14);
//        reparingTimeMap.put(1.5f, 50);
//        reparingTimeMap.put(1.6f, 50);
//        reparingTimeMap.put(1.7f, 48);
//        reparingTimeMap.put(1.8f, 40);
//        reparingTimeMap.put(1.9f, 32);
//        reparingTimeMap.put(2.0f, 28);

    }//uzupełnienie tablicy czasu naprawy o rozkładzie wykładniczym
    public void displayMap(Map<Float, Integer> map ){

        for (Map.Entry<Float, Integer> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

    }
    public void fillStatisticChiSquareMap01(){
        statisticChiSquareMap01.put(14,7.79f);
        statisticChiSquareMap01.put(16,9.31f);
        statisticChiSquareMap01.put(20,12.4f);
        statisticChiSquareMap01.put(24,15.7f);
        statisticChiSquareMap01.put(30,20.6f);
        statisticChiSquareMap01.put(40,29.1f);
    }//uzupełnienie tablicy z kwantylami statystyki
    public void fillStatisticChiSquareMap005(){
        statisticChiSquareMap005.put(14,6.57f);
        statisticChiSquareMap005.put(16,7.96f);
        statisticChiSquareMap005.put(20,10.9f);
        statisticChiSquareMap005.put(24,13.8f);
        statisticChiSquareMap005.put(30,18.5f);
        statisticChiSquareMap005.put(41,27.3f);
    }
    public void fillStatisticChiSquareMap095(){
        statisticChiSquareMap095.put(14,23.7f);
        statisticChiSquareMap095.put(16,26.3f);
        statisticChiSquareMap095.put(20,31.4f);
        statisticChiSquareMap095.put(24,36.4f);
        statisticChiSquareMap095.put(30,43.8f);
        statisticChiSquareMap095.put(41,56.9f);
    }

    //START COUNTING PART
    public void countData(List<Float> concreteTimesList){
        sumTimeForAllData(concreteTimesList);
        estimatedAverageReparingTime();
        isConditionMet();
        countLimes();
    }

    public float numberOfDestroys(){

        if(Tn != 0) {
            quotient = Tn1/Tn;
            quotient *= 10;
            quotient = Math.round(quotient);
            quotient /= 10;
        }

        r = reparingTimeMap.get(quotient); // na podstawie ilorazu quotient(Tn1/Tn) odczytujemy r (liczbę uszkodzeń)
        System.out.println("r = " + r);

        return r;
    } // @ToDo na podstawie r0w generujemy tyle pól ile potrzeba ...
    public float sumTimeForAllData(List<Float> anotherTimesForAllData){
        float sum = 0;

        for(float time : anotherTimesForAllData) {
            sum += time;
        }

        Tns = sum;

        return Tns;
    } // zsumowanie wszystkich czasów

    public void estimatedAverageReparingTime(){
        if(r != 0){
            TnE = Tns / r;
        }
    }
    public boolean isConditionMet(){ // spełnienie wymagania dla średniego czasu naprawy

        float statisticChiSquare = statisticChiSquareMap01.get(r);

        float expression = statisticChiSquare * statisticChiSquare / 2 * r;

        System.out.println("Tne = " + TnE + " Tn = " + Tn );

        if(TnE >= (Tn * expression)){
            isConditionMet = false;
            return false;
        }

        isConditionMet = true;
        return true;
    }
    public void countLimes(){

        float statisticChiSquare = statisticChiSquareMap005.get(r);
        Tnd = 2 * Tns / (statisticChiSquare * statisticChiSquare);

        float statisticChiSquare2 = statisticChiSquareMap095.get(r);
        Tng = 2 * Tns / (statisticChiSquare2 * statisticChiSquare2);
    }

    @Override
    public String toString() {
        return  "\n Liczba uszkodzeń = " + r +
                "\n Maks. dopuszczalny czas naprawy  = " + Tn1 +
                "\n Wymagany czas naprawy = " + Tn +
                "\n Sumaryczny czas naprawy = " + Tns +
                "\n Szacowany średni czas naprawy = " + TnE +
                "\n Tng = " + Tng +
                "\n Tnd = " + Tnd +
                "\n Poziom istotności alfa = " + alpha +
                "\n Ryzyko odbiorcy beta = " + beta;
    }

}
