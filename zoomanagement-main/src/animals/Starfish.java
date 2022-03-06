package animals;
import areas.Area.AreaType;

public class Starfish extends Animal{

    public Starfish(String nickName){
        super(nickName);
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        return true;
    }

    @Override
    public AreaType compatibleAreaType(Animal animal) {
        return AreaType.AQUARIUM;
    }
}
