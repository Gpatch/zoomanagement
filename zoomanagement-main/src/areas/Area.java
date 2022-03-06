package areas;
import zoo.Zoo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Area implements IArea{

    public enum AreaType{
        ENTRANCE,
        PICNIC_AREA,
        ENCLOSURE,
        CAGE,
        AQUARIUM
    }


    protected Zoo associatedZoo;

    @Override
    public ArrayList<Integer> getAdjacentAreas() {
        int fromId = getID();
        return (ArrayList<Integer>) associatedZoo.getAreaPathMap().get(fromId);
    }

    public int getID(){
        return associatedZoo.getAreaToIDMap().get(this);
    }

    @Override
    public void addAdjacentArea(int toAreaId){
        int fromAreaId = getID();
        List<Integer> valList;

        if(associatedZoo.getAreaPathMap().size() == 0 || !associatedZoo.getAreaPathMap().containsKey(fromAreaId)) {
            valList = new ArrayList<>();
        }
        else{
            valList = associatedZoo.getAreaPathMap().get(fromAreaId);
        }
        valList.add(toAreaId);
        associatedZoo.getAreaPathMap().put(fromAreaId, valList);
    }

    @Override
    public void removeAdjacentArea(int toAreaId){
       int fromAreaId = associatedZoo.getID(this);
        List<Integer> valList = associatedZoo.getAreaPathMap().get(fromAreaId);
        if(valList.size() == 1){
            valList = new ArrayList<>();
        }else {
            valList.remove(toAreaId);
        }
        associatedZoo.getAreaPathMap().put(fromAreaId, valList);
    }



    /**
     * Generates an ID for the area based on the area type, and number of area type occurrences in the zoo
     * @param areaTypeCode The code each area type has, first digit in the id
     * @param areaTypeOrderNum The number of specific area type in the zoo -1, last digit(s) of id
     * @return An ID generated
     */
    public static int idGenerator(int areaTypeCode, int areaTypeOrderNum){
        String sb = String.valueOf(areaTypeCode) + (areaTypeOrderNum);
        return Integer.parseInt(sb);
    }


    /**
     * Counts the number of occurrences of specific area type from collection of areasInMap retrieved from the hashmap of area id's
     * @param area The area type as an enumerator value
     * @param areasInMap Collection of areas stored in the hashmap of area id's
     * @return The number of occurrences of specific area type
     */
    public int areaTypeCounter(AreaType area, Collection<IArea> areasInMap){
        int specificAreaCounter;

        if(area == AreaType.ENTRANCE){
            specificAreaCounter = 0;
            for(IArea areaClass : areasInMap){
                if(areaClass instanceof Entrance){
                    specificAreaCounter++;
                }
            }
            return specificAreaCounter;
        }
        else if (area == AreaType.PICNIC_AREA){
            specificAreaCounter = 0;
            for(IArea areaClass : areasInMap){
                if(areaClass instanceof PicnicArea){
                    specificAreaCounter++;
                }
            }
            return specificAreaCounter;
        }
        else if (area == AreaType.ENCLOSURE){
            specificAreaCounter = 0;
            for(IArea areaClass : areasInMap){
                if(areaClass instanceof Enclosure){
                    specificAreaCounter++;
                }
            }
            return specificAreaCounter;
        }
        else if (area == AreaType.CAGE){
            specificAreaCounter = 0;
            for(IArea areaClass : areasInMap){
                if(areaClass instanceof Cage){
                    specificAreaCounter++;
                }
            }
            return specificAreaCounter;
        }
        else{
            specificAreaCounter = 0;
            for(IArea areaClass : areasInMap){
                if(areaClass instanceof Aquarium){
                    specificAreaCounter++;
                }
            }
            return specificAreaCounter;
        }
    }

    /**
     * Checks if the limit of how many areas of some type can be created is reached
     * @param area The area type as an enumerator value
     * @param areasInMap Collection of areas stored in the hashmap of area id's
     * @return Boolean value based on if the area limit is reached or not
     */
    public boolean isAreaTypeLimitReached(AreaType area, Collection<IArea> areasInMap){
        int specificAreaCounter;

        if(area == AreaType.ENTRANCE){
            specificAreaCounter = 0;
            for(IArea areaClass : areasInMap){
                if(areaClass instanceof Entrance){
                    specificAreaCounter++;
                }
            }
            return specificAreaCounter >= Entrance.MAXSIZE;
        }
        else if(area == AreaType.PICNIC_AREA){
            specificAreaCounter = 0;
            for(IArea areaClass : areasInMap){
                if(areaClass instanceof PicnicArea){
                    specificAreaCounter++;
                }
            }
            return specificAreaCounter >= PicnicArea.MAXSIZE;
        }
        else if(area == AreaType.ENCLOSURE){
            specificAreaCounter = 0;
            for(IArea areaClass : areasInMap){
                if(areaClass instanceof Enclosure){
                    specificAreaCounter++;
                }
            }
            return specificAreaCounter >= Enclosure.MAXSIZE;
        }
        else if(area == AreaType.CAGE){
            specificAreaCounter = 0;
            for(IArea areaClass : areasInMap){
                if(areaClass instanceof Cage){
                    specificAreaCounter++;
                }
            }
            return specificAreaCounter >= Cage.MAXSIZE;
        }
        else{
            specificAreaCounter = 0;
            for(IArea areaClass : areasInMap){
                if(areaClass instanceof Aquarium){
                    specificAreaCounter++;
                }
            }
            return specificAreaCounter >= Aquarium.MAXSIZE;
        }
    }
    /**
     *
     * @param area Area to be checked
     * @return Return true if area is a habitat, false otherwise
     */
    public boolean isHabitat (Area area){
        return area instanceof Habitat;
    }

    /**
     *  Check if the habitat is currently full, based on the maximum habitat size
     * @param area The area which to check
     * @param numOfAnimals Number of animals the habitat currently holds
     * @return Return true of the habitat is full, false otherwise
     */
    public boolean isHabitatFull(Habitat area, int numOfAnimals){
        return numOfAnimals >= area.MAX_HABITANTS;
    }

    /**
     * Gets the code for some specific area type
     * @return The integer code
     */
    public abstract int getAreaTypeCode();

    /**
     * Gets the enumerator value of some specific area
     * @return The enumerator value
     */
    public abstract AreaType getAreaType();


    /**
     * Sets the reference to the correct zoo
     * @param zoo the reference to the zoo
     */
    public void setAssociatedZoo(Zoo zoo){
        associatedZoo = zoo;
    }
}
