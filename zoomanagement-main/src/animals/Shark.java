package animals;
import areas.Area.AreaType;

public class Shark extends Animal{

    public Shark(String nickName){
        super(nickName);
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        return !(animal instanceof Seal);
    }

    @Override
    public AreaType compatibleAreaType(Animal animal) {
        return AreaType.AQUARIUM;
    }
}
