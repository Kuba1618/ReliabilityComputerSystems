package counting;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Data {
    public int numberOfData;
    public int r = 0; //liczba uszkodzeń r
    public List<Float> anotherTimesForAllData; //kolejne czasy naprawy
    public Map<Float, Integer> reparingTimeArray; //
    public Map<Integer,Float> statisticChiSquareMap01; //dla alpha = 0,1
    public Map<Integer,Float> statisticChiSquareMap005; //dla alpha = 0,1
    public Map<Integer,Float> statisticChiSquareMap095; //dla alpha = 0,1
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

    public Data(int numberOfData, List<Float> anotherTimesForAllData, float maxAverageReparingTime, float requiredReparingTime) {
        this.numberOfData = numberOfData;
        this.anotherTimesForAllData = anotherTimesForAllData;
        this.Tn1 = maxAverageReparingTime;
        this.Tn = requiredReparingTime;
        fillReparingTimeArray();
        fillStatisticChiSquareMap01();
        fillStatisticChiSquareMap005();
        fillStatisticChiSquareMap095();
    }

    public int getNumberOfData() {
        return numberOfData;
    }

    public void setNumberOfData(int numberOfData) {
        this.numberOfData = numberOfData;
    }

    public Map<Float, Integer> getReparingTimeArray() {
        return reparingTimeArray;
    }

    public void setReparingTimeArray(Map<Float, Integer> reparingTimeArray) {
        this.reparingTimeArray = reparingTimeArray;
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
    public void countData() {
        sumTimeForAllData();
    }
    public float getQuotient() {
        return quotient;
    }
    public void setQuotient(float quotient) {
        this.quotient = quotient;
    }
    public void fillReparingTimeArray() {
        reparingTimeArray.put(1.5f, 41);
        reparingTimeArray.put(1.6f, 30);
        reparingTimeArray.put(1.7f, 24);
        reparingTimeArray.put(1.8f, 20);
        reparingTimeArray.put(1.9f, 16);
        reparingTimeArray.put(2.0f, 14);
    } //uzupełnienie tablicy czasu naprawy o rozkładzie wykładniczym
    public void fillStatisticChiSquareMap01(){
        statisticChiSquareMap01.put(14,7.79f);
        statisticChiSquareMap01.put(16,9.31f);
        statisticChiSquareMap01.put(20,12.4f);
        statisticChiSquareMap01.put(24,15.7f);
        statisticChiSquareMap01.put(30,20.6f);
        statisticChiSquareMap01.put(41,28.9f);
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
    public void counData(){
        numberOfDestroyed();
        sumTimeForAllData();
        estimatedAverageReparingTime();
        isConditionMet();
        countLimes();
    }
    public float numberOfDestroyed(){

        if(Tn != 0){
            quotient = Tn1/Tn;
            quotient *= 10;
            quotient = Math.round(quotient);
            quotient /= 10;
        }

        r = anotherTimesForAllData.indexOf(quotient);
        return r;
    } //na podstawie ilorazu Tn1/Tn odczytujemy r (liczbę uszkodzeń)
    public float sumTimeForAllData(){
        float sum = 0;
        anotherTimesForAllData = new LinkedList<>();

        for(float time : anotherTimesForAllData) {
            sum += time;
        }

        Tns = sum;

        return Tns;
    } //zsumowanie wszystkich czasów
    public void estimatedAverageReparingTime(){
        TnE = sumTimeForAllData()/numberOfDestroyed();
    }
    public void isConditionMet(){

        float statisticChiSquare = statisticChiSquareMap01.get(r);
        float expression = statisticChiSquare * statisticChiSquare / 2 * r;

        if(TnE >= (Tn * expression)){
            isConditionMet = false;
            return;
        }

        isConditionMet = true;
    } //spełnia wymaganie dla średniego czasu naprawy
    public void countLimes(){

        float statisticChiSquare = statisticChiSquareMap005.get(r);
        Tnd = 2 * Tns / (statisticChiSquare * statisticChiSquare);

        float statisticChiSquare2 = statisticChiSquareMap095.get(r);
        Tng = 2 * Tns / (statisticChiSquare2 * statisticChiSquare2);
    }

}
