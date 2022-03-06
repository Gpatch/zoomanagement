package zoo;

import animals.Animal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import areas.IArea;
import areas.Area;
import areas.Habitat;
import areas.Entrance;

import dataStructures.CashCount;
import dataStructures.ICashCount;

public class Zoo implements IZoo{

    /**
     * The hashmap where area ID as integer is key and area class as IArea is value.
     * areaToIDMap hashmap is the reverse of the idToAreaMap. Key and value are swapped here.
     * This is done to imitate a 1 to 1 relationship.
     */
    private final HashMap<Integer, IArea> idToAreaMap = new HashMap<>();
    private final HashMap<IArea, Integer> areaToIDMap = new HashMap<>();

    /**
     * The hashmap where starting area ID is the key, and the destination area ID is the value.
     */
    private final HashMap<Integer, List<Integer>> areaPathMap = new HashMap<>();

    /**
     * The hashmap where area ID as integer is key and animals as a list of animal classes is value.
     */
    private final HashMap <Integer, List<Animal>> areaIDToAnimalsMap = new HashMap<>();

    //ZOO ENTRANCE FEE
    public int entranceFeePounds;
    public int entranceFeePence;
    //ZOO TICKET MACHINE
    public ICashCount cashSupply;


    //CLASS CONSTRUCTOR
    public Zoo() {
        addArea(new Entrance());
        cashSupply = new CashCount();
    }

    /**
     * Inserts area ID with area class to idToAreaMap hashmap, and inserts areaID with an empty animal list if habitat type area is used.
     * @param area The area to be inserted into hashmap
     * @param id The idea to be inserted into hashmap which will be associated with the area.
     */
    private void areaMapInsertion(IArea area, int id){
        areaToIDMap.put(area, id);
        idToAreaMap.put(id, area);
        areaPathMap.put(id, new ArrayList<>());
        if(area instanceof Habitat){
            areaIDToAnimalsMap.put(id, new ArrayList<>());
        }
    }

    @Override
    public int addArea(IArea area) {
        Area areaType = (Area)area;
        //CHECK IF AREA IS ALREADY IN THIS ZOO
        for(IArea a : idToAreaMap.values()){
            if(area == a){
                System.out.println("ERROR CODE: -1. Area has already been added!");
                return -1;
            }
        }
        //CHECK IF AREA TYPE LIMIT IS REACHED CHECK
        if(areaType.isAreaTypeLimitReached(areaType.getAreaType(), idToAreaMap.values())) {
            System.out.println("ERROR CODE: -2. " + areaType.toString() + " area type is full!");
            return -2;
        }
        areaType.setAssociatedZoo(this);
        //GENERATE AN ID FOR THE AREA
        int id = Area.idGenerator(areaType.getAreaTypeCode(), areaType.areaTypeCounter(areaType.getAreaType(), idToAreaMap.values()));
        //STORES THE AREA
        areaMapInsertion(area, id);
        return id;
    }

    /**
     * Removes any information about the area from the the hashmaps it was stored
     * @param id ID of the area
     */
    public void areaMapDeletion(int id){
        areaToIDMap.remove(idToAreaMap.get(id));
        idToAreaMap.remove(id);
        areaIDToAnimalsMap.remove(id);
        areaPathMap.remove(id);
    }

    @Override
    public void removeArea(int areaId) {
        //CHECK IF AREA ID PASSED IS THE ID OF THE ZOO ENTRANCE
        if(areaId == 0){
            System.out.println("Can not remove an entrance!");
        }
        else {
            //DELETES THE AREA FROM STORAGE
            areaMapDeletion(areaId);
            //CHECKS IF ANY OF THE AREAS HAVE CONNECTION TO THE REMOVED AREA
            //IF THEY DO, THEN REMOVE TEH CONNECTION TO THE REMOVED AREA
            for(int hArea : areaPathMap.keySet()){
                IArea area = getArea(hArea);
                if(areaPathMap.get(hArea).contains(areaId)){
                    area.removeAdjacentArea(areaId);
                }
            }
        }
    }

    @Override
    public IArea getArea(int areaId) {
        IArea hMapValue = null;
        //CHECK IF HASHMAP STORES THE AREA ID THAT IS PASSED
        for (int id : idToAreaMap.keySet()) {
            if (id == areaId) {
                hMapValue = idToAreaMap.get(id);
                break;
            }
        }
        return hMapValue;
    }


    /**
     * Retrieves a collection of animals from some area
     * @param areaId The id of an area from which animals to be fetched
     * @return Returns a collection of animals
     */
    public Collection<Animal> getAnimalsFromArea(int areaId){
        Collection <Animal> animals = null;
        for(int id : areaIDToAnimalsMap.keySet()){
            if(id == areaId){
                animals = areaIDToAnimalsMap.get(id);
                break;
            }
        }
        return animals;
    }

    /**
     * Check if animal is compatible with the habitat
     * @param areaId The area ID to be fetched from the hashmap
     * @param animal Animal which habitat compatibility to be checked
     * @return Return true of animal can be placed in this habitat, otherwise return false
     */
    private boolean isCorrectHabitatType(int areaId, Animal animal){
        IArea area = null;
        for (int id : idToAreaMap.keySet()){
            if(id == areaId){
                area = getArea(areaId);
                break;
            }
        }
        Area IAreaToArea = (Area) area;
        return  IAreaToArea.getAreaType() == animal.compatibleAreaType(animal);
    }

    @Override
    public byte addAnimal(int areaId, Animal animal) {
        Area area = (Area) getArea(areaId);
        //CHECK IF AN AREA IS A HABITAT
        if(!area.isHabitat(area)){
            return Codes.NOT_A_HABITAT;
        }
        else{
            //CHECK OF THE HABITAT IS A CORRECT TYPE
            if(!isCorrectHabitatType(areaId, animal)){
                return Codes.WRONG_HABITAT;
            }
            else{
                //CHECK IF THE HABITAT IS FULL
                if(area.isHabitatFull((Habitat) area, getAnimalsFromArea(areaId).size())){
                    return Codes.HABITAT_FULL;
                }
                else{
                    //CHECK IF INHABITANTS ARE COMPATIBLE
                    if(!animal.areInhabitantsCompatible(getAnimalsFromArea(areaId))){
                        return Codes.INCOMPATIBLE_INHABITANTS;
                    }
                    else{
                        //ADD ANIMAL INTO THE HABITAT
                        //IF THE AREA HAS NO ANIMALS THEN INSERT A NEW LIST WITH A NEW ANIMAL
                        if(getAnimalsFromArea(areaId).size() == 0){
                            List<Animal> list = new ArrayList<>();
                            list.add(animal);
                            areaIDToAnimalsMap.put(areaId, list);
                        }
                        //OTHERWISE JUST ADD A NEW ANIMAL TO AN ALREADY EXISTING LIST OF ANIMALS
                        else{
                            areaIDToAnimalsMap.get(areaId).add(animal);
                        }
                        return Codes.ANIMAL_ADDED;
                    }
                }
            }
        }
    }

    /**
     * Check if the area id exists
     * @param areaID the id of the area to be checked
     * @return Returns true if the area exist, otherwise return false
     */
    public  boolean doesIdExist(int areaID){
        return idToAreaMap.containsKey(areaID);
    }

    /**
     * Gets the area id
     * @param area Area which id to be fetched
     * @return Returns the area id
     */
    public int getID(IArea area){
        return areaToIDMap.get(area);
    }


    @Override
    public void connectAreas(int fromAreaId, int toAreaId) {
        Area fromArea = (Area) getArea(fromAreaId);
        //CHECK IF BOTH AREA ID'S EXIST
        if(doesIdExist(fromAreaId) && doesIdExist(toAreaId)) {
            fromArea.addAdjacentArea(toAreaId);
        }
    }


    @Override
    public boolean isPathAllowed(ArrayList<Integer> areaIds) {
        boolean isValueOf = false;
        //IF THE PATH INCLUDES ONLY 1 AREA, THEN THE PATH IS ALLOWED BY DEFAULT
        if(areaIds.size() == 1){
            isValueOf = true;
        }
        else {
            for (int i = 1; i < areaIds.size(); i++) {
                //CHECK IF AREA i AND AREA i-1 ARE ADJACENT TO EACH OTHER
                if (getArea(areaIds.get(i-1)).getAdjacentAreas().contains(areaIds.get(i))) {
                    isValueOf = true;
                } else {
                    isValueOf = false;
                    break;
                }
            }
        }
        return isValueOf;
    }

    @Override
    public ArrayList<String> visit(ArrayList<Integer> areaIdsVisited) {
        ArrayList<String> animalsSeen = new ArrayList<>();
        //IF THE PATH IS INVALID THEN NO ANIMALS WERE VISITED
        if(!isPathAllowed(areaIdsVisited)){
            animalsSeen = null;
        }
        else{
            //ADD EVERY ANIMALS NAME VISITED FROM THE HABITAT AREA AND SKIP HUMAN AREA
            for(int area : areaIdsVisited){
                if(getArea(area) instanceof Habitat) {
                    for (Animal animal : areaIDToAnimalsMap.get(area)) {
                        animalsSeen.add(animal.getNickname());
                    }
                }
            }
        }
        return animalsSeen;
    }

    /**
     * Checks if the area is reachable from the entrance, by recursively checking if the previous area is connected to entrance, or an area which leads to an entrance
     * @param id The area id to be checked
     * @return Return -1 if area is unreachable, 0 if is reachable
     */
    private int recReachable(int id){
        int previousArea = -1;
        boolean done = false;

        //Area with id 0 is reachable by default
        if(id == 0){
            return id;
        }

        for(int areaFrom : areaPathMap.keySet()){
            for(int areaTo : areaPathMap.get(areaFrom)){
                //Check if areaFrom connects to id
                if(areaTo == id){
                    previousArea = areaFrom;
                    //Check if areaFrom doesn't connect directly to entrance
                    if(!(previousArea == 0)){
                        //Recursively repeat operation with the new area, which is the area that lead to the 'id' area.
                        previousArea = recReachable(previousArea);
                        //Check if the the final area is entrance
                        if(previousArea == 0 ){
                            //If true then we are done and the area is reachable
                            done = true;
                            break;
                        }
                    }
                    else{
                        done = true;
                        break;
                    }
                }
            }
            if(done){
                break;
            }
        }
        return previousArea;
    }


    @Override
    public ArrayList<Integer> findUnreachableAreas() {
        ArrayList<Integer> idList = new ArrayList<>();

        for(int area : areaPathMap.keySet()){
            if(!(recReachable(area) == 0)){
                idList.add(area);
            }
        }
        return idList;
    }


    @Override
    public void setEntranceFee(int pounds, int pence) {
        entranceFeePounds = pounds;
        entranceFeePence = pence;
    }

    @Override
    public void setCashSupply(ICashCount coins) {
        cashSupply.setNrNotes_20pounds(coins.getNrNotes_20pounds());
        cashSupply.setNrNotes_10pounds(coins.getNrNotes_10pounds());
        cashSupply.setNrNotes_5pounds(coins.getNrNotes_5pounds());

        cashSupply.setNrCoins_2pounds(coins.getNrCoins_2pounds());
        cashSupply.setNrCoins_1pound(coins.getNrCoins_1pound());

        cashSupply.setNrCoins_50p(coins.getNrCoins_50p());
        cashSupply.setNrCoins_20p(coins.getNrCoins_20p());
        cashSupply.setNrCoins_10p(coins.getNrCoins_10p());
    }


    @Override
    public ICashCount getCashSupply() {
        return cashSupply;
    }


    /**
     * Checks if the money inserted is enough to pay for the ticket
     * @param cash ICashCount instance inserted
     * @return Return true if cash inserted is enough, false otherwise
     */
    private boolean isTicketPriceMatched(ICashCount cash){
        int pounds = CashCount.toPoundsFromICash(cash);
        int pence = CashCount.toPenceFromICash(cash);
        int insertedCashAsPence = (pounds * 100) + pence;
        int priceAsPence = (entranceFeePounds * 100) + entranceFeePence;

        return insertedCashAsPence >= priceAsPence;
    }


    /**
     * Calculates change
     * @param moneyInsertedPounds Money inserted in pounds
     * @param moneyInsertedPence Money inserted in pence
     * @param returnPounds Indication if change must be in pounds or pence
     * @return Returns change for pounds if returnPounds is true, returns change for pence otherwise
     */
    private int calculateChange(int moneyInsertedPounds, int moneyInsertedPence, boolean returnPounds){
        int changeInPounds = moneyInsertedPounds - entranceFeePounds;
        int changeInPence = moneyInsertedPence - entranceFeePence;
        int resultChangeInPence = (changeInPounds*100) + (changeInPence);

        if(returnPounds){
            //Convert to whole pounds
            return resultChangeInPence / 100;
        }
        else{
            //Total change in pence minus the change of pounds converted to pence
            return resultChangeInPence - (resultChangeInPence / 100 * 100); //Division by 100 is done to round to an integer
        }
    }



    /**
     * Calculates the change
     * @param supply ICashCount instance of the cash supply
     * @param inserted ICashCount instance of the cash inserted
     * @param previousChangeCash ICashCount instance of the previous change type
     * @param changeOfLeft Change left of pounds/pence
     * @param forPounds Indication if change for pence or pounds must be calculated
     * @return Returns change as ICashCount instance
     */
    private ICashCount calculateChangeDenomination (ICashCount supply, ICashCount inserted, ICashCount previousChangeCash, int changeOfLeft, boolean forPounds){
        CashCount supplyCash = new CashCount();
        supplyCash.fromICashCountToCashCount(supply);

        CashCount insertedCash = new CashCount();
        insertedCash.fromICashCountToCashCount(inserted);

        CashCount change = new CashCount();
        change.fromICashCountToCashCount(previousChangeCash);

        //EITHER GOING TO BE POUNDS OR PENCE
        HashMap<Integer, Integer> denTypeMap;
        if(forPounds){
            denTypeMap = supplyCash.poundsToCountMap;
        }
        else{
            denTypeMap = supplyCash.penceToCountMap;
        }

        //For each note/coin from inserted cash
        for (int denType : denTypeMap.keySet()) {
            if (changeOfLeft == 0) {
                break;
            }
            int denTypeCount = denTypeMap.get(denType);
            //Check if current note/coin count is greater than 0
            if (denTypeCount > 0) {
                //Check if current change is greater than current note/coin
                if (changeOfLeft >= denType) {
                    int currentDenCount = changeOfLeft / denType;
                    //Check if cash supply has the available amount of some note/coin
                    if (CashCount.isCashAvailable(supply, denType, currentDenCount, forPounds)) {
                        CashCount.takeCashFromSupply(supply, denType, currentDenCount, forPounds);
                        CashCount.addCashToSupply(change, denType, currentDenCount, forPounds);
                        changeOfLeft = changeOfLeft - (denType * currentDenCount);
                    }
                }
            }
        }
        if(changeOfLeft != 0){
            return inserted;
        }
        return change;
    }



    @Override
    public ICashCount payEntranceFee(ICashCount cashInserted) {
        ICashCount changePoundsOnly;
        ICashCount finalChange;

        CashCount.addAllDenCash(cashInserted, cashSupply);

        int changePounds = calculateChange(CashCount.toPoundsFromICash(cashInserted), CashCount.toPenceFromICash(cashInserted), true);
        int changePence = calculateChange(CashCount.toPoundsFromICash(cashInserted), CashCount.toPenceFromICash(cashInserted), false);

        if(isTicketPriceMatched(cashInserted)) {
            changePoundsOnly = calculateChangeDenomination(cashSupply, cashInserted, new CashCount(), changePounds, true);
            finalChange = calculateChangeDenomination(cashSupply, cashInserted, changePoundsOnly, changePence, false);
        }
        else{
            return cashInserted;
        }
        return finalChange;
    }

    //---------------------Getters--------------------
    public HashMap<IArea, Integer> getAreaToIDMap(){ return areaToIDMap; }

    public HashMap<Integer, List<Integer>> getAreaPathMap(){ return areaPathMap; }

}

