package areas;

public class  Cage extends Habitat {

    public static final int MAXSIZE = 6;

    public Cage(int maxHabitants) {
        super(maxHabitants);
    }

    @Override
    public int getAreaTypeCode() {
        return AreaCodes.CAGE;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.CAGE;
    }
}
