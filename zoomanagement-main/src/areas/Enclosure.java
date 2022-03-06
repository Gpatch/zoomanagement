package areas;

public class Enclosure extends Habitat {

    public static final int MAXSIZE = 4;

    public Enclosure(int maxHabitants) {
        super(maxHabitants);
    }

    @Override
    public int getAreaTypeCode() {
        return AreaCodes.ENCLOSURE;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.ENCLOSURE;
    }

}
