package areas;

public class Entrance extends HumanArea{

    public static final int MAXSIZE = 1;

    public Entrance(){
    }


    @Override
    public int getAreaTypeCode() {
        return AreaCodes.ENTRANCE;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.ENTRANCE;
    }


}
