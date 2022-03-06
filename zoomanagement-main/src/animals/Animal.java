package animals;
import areas.Area.AreaType;
import java.util.Collection;


public abstract class Animal
{
	protected final String NICKNAME;

	public Animal(String nickname){
		NICKNAME = nickname;
	}
	/**
	 * @return Returns this animal's given name.
	 */
	public String getNickname(){
		return this.NICKNAME;
	}
	
	/**
	 * Check whether two animals can live together.
	 * @param animal The animal for which to check compatibility with this animal.
	 * @return Returns true for compatible animals and false otherwise.
	 */
	public abstract boolean isCompatibleWith(Animal animal);

	/**
	 * Get the compatible area type for specific animal
	 * @param animal The animal for which to set compatible area type
	 * @return Returns the compatible area type for the animal
	 */
	public abstract AreaType compatibleAreaType(Animal animal);


	/**
	 * Checks if the inhabitants in the area are compatible with the animal to be added.
	 * @param animals Animals with which compatibility is checked
	 * @return Boolean value based on if the inhabitants are compatible
	 */
	public boolean areInhabitantsCompatible(Collection<Animal> animals){
		boolean isCompatible = false;
		if(animals.size() == 0){
			return true;
		}
		else{
			for(Animal a : animals){
				if(this.isCompatibleWith(a)){
					isCompatible = true;
				}
				else{
					isCompatible = false;
					break;
				}
			}
			return isCompatible;
		}
	}
}


