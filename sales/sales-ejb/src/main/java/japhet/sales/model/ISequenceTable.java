package japhet.sales.model;

/**
 * This interface is used to define the methods that are
 * required to implement an entity that uses an in memory
 * sequence value as PK.
 * 
 * @author David
 *
 */
public interface ISequenceTable {

	/**
	 * Obtains the current value to use as PK in this entity
	 * @return
	 */
	public long getCurrentSequenceValue();
	
	/**
	 * Obtains the current value to use as PK in this entity
	 * and then increases its value by 1.
	 * @return
	 */
	public long getNewSequenceValue();
	
	/**
	 * Increases by 1 the current PK sequence value.
	 */
	public void increaseSequenceValue();
	
	/**
	 * Defines the value that is going to be used in 
	 * the sequence generator.
	 */
	public void setSequenceValue(long sequenceValue);
}
