package animals;
import areas.Area.AreaType;

public class Lion extends Animal{

    public Lion(String nickName){
        super(nickName);
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        return animal instanceof Lion;
    }

    @Override
    public AreaType compatibleAreaType(Animal animal) {
        return AreaType.ENCLOSURE;
    }

}
