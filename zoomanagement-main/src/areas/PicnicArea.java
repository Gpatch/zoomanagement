package areas;

public class PicnicArea extends HumanArea{

    public static final int MAXSIZE = 4;

    public PicnicArea(){
    }


    @Override
    public int getAreaTypeCode() {
        return AreaCodes.PICNIC;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.PICNIC_AREA;
    }

}
