package areas;

public class Aquarium extends Habitat {

    public static final int MAXSIZE = 8;

    public Aquarium(int maxHabitants) {
        super(maxHabitants);
    }

    @Override
    public int getAreaTypeCode() {
        return AreaCodes.AQUARIUM;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.AQUARIUM;
    }
}
