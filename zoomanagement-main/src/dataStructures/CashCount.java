package dataStructures;
import java.util.LinkedHashMap;

public class CashCount implements ICashCount {

    public LinkedHashMap<Integer, Integer> poundsToCountMap = new LinkedHashMap<>();
    public LinkedHashMap<Integer, Integer> penceToCountMap = new LinkedHashMap<>();

    public CashCount(){
        poundsToCountMap.put(20, 0);
        poundsToCountMap.put(10, 0);
        poundsToCountMap.put(5, 0);
        poundsToCountMap.put(2, 0);
        poundsToCountMap.put(1, 0);

        penceToCountMap.put(50, 0);
        penceToCountMap.put(20, 0);
        penceToCountMap.put(10, 0);

    }

    /**
     * Copies the information about denomination from ICashCount object, directly to the CashCount object
     * @param cashCount ICashCount instance
     */
    public void fromICashCountToCashCount(ICashCount cashCount){
        setNrNotes_20pounds(cashCount.getNrNotes_20pounds());
        setNrNotes_10pounds(cashCount.getNrNotes_10pounds());
        setNrNotes_5pounds(cashCount.getNrNotes_5pounds());
        setNrCoins_2pounds(cashCount.getNrCoins_2pounds());
        setNrCoins_1pound(cashCount.getNrCoins_1pound());

        setNrCoins_50p(cashCount.getNrCoins_50p());
        setNrCoins_20p(cashCount.getNrCoins_20p());
        setNrCoins_10p(cashCount.getNrCoins_10p());
    }


    /**
     * Converts ICashCount to pounds
     * @param poundsInserted ICashCount instance
     * @return Returns pounds sum
     */
    public static int toPoundsFromICash(ICashCount poundsInserted){
        int twentyPounds = poundsInserted.getNrNotes_20pounds();
        int tenPounds = poundsInserted.getNrNotes_10pounds();
        int fivePounds = poundsInserted.getNrNotes_5pounds();
        int twoPounds = poundsInserted.getNrCoins_2pounds();
        int onePound = poundsInserted.getNrCoins_1pound();

        return (twentyPounds*20) + (tenPounds*10) + (fivePounds*5) + (twoPounds*2) + (onePound);
    }

    /**
     * Converts ICashCount to pence
     * @param penceInserted ICashCount instance
     * @return Returns pence sum
     */
    public static int toPenceFromICash(ICashCount penceInserted){
        int fiftyPence = penceInserted.getNrCoins_50p();
        int twentyPence = penceInserted.getNrCoins_20p();
        int tenPence = penceInserted.getNrCoins_10p();

        return (fiftyPence*50) + (twentyPence*20) + (tenPence*10);
    }



    /**
     * Adds cash to a cash supply
     * @param cash The denomination type
     * @param cashCount Number of coins/notes
     * @param forPounds Indication if pence must be added or pounds
     */
    public static void addCashToSupply(ICashCount toSupply, int cash, int cashCount, boolean forPounds) {
        if(forPounds) {
            switch (cash) {
                case 20:
                    toSupply.setNrNotes_20pounds(toSupply.getNrNotes_20pounds() + cashCount);
                    break;
                case 10:
                    toSupply.setNrNotes_10pounds(toSupply.getNrNotes_10pounds() + cashCount);
                    break;
                case 5:
                    toSupply.setNrNotes_5pounds(toSupply.getNrNotes_5pounds() + cashCount);
                    break;
                case 2:
                    toSupply.setNrCoins_2pounds(toSupply.getNrCoins_2pounds() + cashCount);
                    break;
                case 1:
                    toSupply.setNrCoins_1pound(toSupply.getNrCoins_1pound() + cashCount);
                    break;
            }
        }
        else{
            switch (cash){
                case 50:
                    toSupply.setNrCoins_50p(toSupply.getNrCoins_50p() + cashCount);
                    break;
                case 20:
                    toSupply.setNrCoins_20p(toSupply.getNrCoins_20p() + cashCount);
                    break;
                case 10:
                    toSupply.setNrCoins_10p(toSupply.getNrCoins_10p() + cashCount);
                    break;
            }
        }
    }


    /**
     * Takes cash from a cash supply
     * @param fromSupply cash supply from which the cash is taken
     * @param cash The denomination type
     * @param cashCount Number of coins/notes
     * @param forPounds Indication if pence must be added or pounds
     */
    public static void takeCashFromSupply(ICashCount fromSupply ,int cash, int cashCount, boolean forPounds){
        if(forPounds) {
            switch (cash) {
                case 20:
                    fromSupply.setNrNotes_20pounds(fromSupply.getNrNotes_20pounds() - cashCount);
                    break;
                case 10:
                    fromSupply.setNrNotes_10pounds(fromSupply.getNrNotes_10pounds() - cashCount);
                    break;
                case 5:
                    fromSupply.setNrNotes_5pounds(fromSupply.getNrNotes_5pounds() - cashCount);
                    break;
                case 2:
                    fromSupply.setNrCoins_2pounds(fromSupply.getNrCoins_2pounds() - cashCount);
                    break;
                case 1:
                    fromSupply.setNrCoins_1pound(fromSupply.getNrCoins_1pound() - cashCount);
                    break;
            }
        }
        else{
            switch (cash) {
                case 50:
                    fromSupply.setNrCoins_50p(fromSupply.getNrCoins_50p() - cashCount);
                    break;
                case 20:
                    fromSupply.setNrCoins_20p(fromSupply.getNrCoins_20p() - cashCount);
                    break;
                case 10:
                    fromSupply.setNrCoins_10p(fromSupply.getNrCoins_10p() - cashCount);
                    break;
            }
        }
    }


    /**
     * Copy cash from one cash supply and add it to another
     * @param fromSupply ICashCount instance of the cash from which cash must be copied
     * @param toSupply ICashCount instance of the cash supply where cash will be added
     */
    public static void addAllDenCash(ICashCount fromSupply, ICashCount toSupply){
        addCashToSupply(toSupply,20, fromSupply.getNrNotes_20pounds(), true);
        addCashToSupply(toSupply,10, fromSupply.getNrNotes_10pounds(), true);
        addCashToSupply(toSupply,5, fromSupply.getNrNotes_5pounds(), true);
        addCashToSupply(toSupply,2, fromSupply.getNrCoins_2pounds(), true);
        addCashToSupply(toSupply,1, fromSupply.getNrCoins_1pound(), true);

        addCashToSupply(toSupply, 50, fromSupply.getNrCoins_50p(), false);
        addCashToSupply(toSupply, 20, fromSupply.getNrCoins_20p(), false);
        addCashToSupply(toSupply, 10, fromSupply.getNrCoins_10p(), false);
    }


    /**
     * Checks if some amount of cash is available in the cash supply
     * @param cashSupply ICashCount instance of the cash supply to be checked
     * @param cash The denomination type
     * @param countNeeded Number of coins/notes
     * @param forPounds Indication if pence must be removed or pounds
     * @return Returns true if the cash is available, false otherwise
     */

    public static boolean isCashAvailable(ICashCount cashSupply, int cash, int countNeeded, boolean forPounds){
        int cashCount = 0;
        boolean available;
        if(forPounds) {
            switch (cash) {
                case 20:
                    cashCount = cashSupply.getNrNotes_20pounds();
                    break;
                case 10:
                    cashCount = cashSupply.getNrNotes_10pounds();
                    break;
                case 5:
                    cashCount = cashSupply.getNrNotes_5pounds();
                    break;
                case 2:
                    cashCount = cashSupply.getNrCoins_2pounds();
                    break;
                case 1:
                    cashCount = cashSupply.getNrCoins_1pound();
                    break;
            }
        }
        else{
            switch(cash){
                case 50:
                    cashCount = cashSupply.getNrCoins_50p();
                    break;
                case 20:
                    cashCount = cashSupply.getNrNotes_20pounds();
                    break;
                case 10:
                    cashCount = cashSupply.getNrCoins_10p();
                    break;
            }
        }
        available = cashCount >= countNeeded;
        return available;
    }


    @Override
    public void setNrNotes_20pounds(int noteCount) {
        poundsToCountMap.put(20, noteCount);
    }

    @Override
    public void setNrNotes_10pounds(int noteCount) {
        poundsToCountMap.put(10, noteCount);
    }

    @Override
    public void setNrNotes_5pounds(int noteCount) {
        poundsToCountMap.put(5, noteCount);
    }

    @Override
    public void setNrCoins_2pounds(int coinCount) { poundsToCountMap.put(2, coinCount); }

    @Override
    public void setNrCoins_1pound(int coinCount) {
        poundsToCountMap.put(1, coinCount);
    }

    @Override
    public void setNrCoins_50p(int coinCount) {
        penceToCountMap.put(50, coinCount);
    }

    @Override
    public void setNrCoins_20p(int coinCount) {
        penceToCountMap.put(20, coinCount);
    }

    @Override
    public void setNrCoins_10p(int coinCount) {
        penceToCountMap.put(10, coinCount);
    }

    @Override
    public int getNrNotes_20pounds() { return poundsToCountMap.get(20); }

    @Override
    public int getNrNotes_10pounds() {
        return poundsToCountMap.get(10);
    }

    @Override
    public int getNrNotes_5pounds() {
        return poundsToCountMap.get(5);
    }

    @Override
    public int getNrCoins_2pounds() { return poundsToCountMap.get(2);}

    @Override
    public int getNrCoins_1pound() {
        return poundsToCountMap.get(1);
    }

    @Override
    public int getNrCoins_50p() {
        return penceToCountMap.get(50);
    }

    @Override
    public int getNrCoins_20p() {
        return penceToCountMap.get(20);
    }

    @Override
    public int getNrCoins_10p() { return penceToCountMap.get(10); }
}
