package animals;
import areas.Area.AreaType;

public class Seal extends Animal{

    public Seal(String nickName){
        super(nickName);
    }

    @Override
    public boolean isCompatibleWith(Animal animal){
        return !(animal instanceof Shark);
    }

    @Override
    public AreaType compatibleAreaType(Animal animal) {
        return AreaType.AQUARIUM;
    }
}
