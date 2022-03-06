package animals;
import areas.Area.AreaType;

public class Parrot extends Animal{

    public Parrot(String nickName){
        super(nickName);
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        return (animal instanceof Parrot);
    }

    @Override
    public AreaType compatibleAreaType(Animal animal) {
        return AreaType.CAGE;
    }
}
