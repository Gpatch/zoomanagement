package animals;
import areas.Area.AreaType;

public class Buzzard extends Animal{

    public Buzzard(String nickName){
        super(nickName);
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        return (animal instanceof Buzzard);
    }

    @Override
    public AreaType compatibleAreaType(Animal animal) {
        return AreaType.CAGE;
    }
}
