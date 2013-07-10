package lifecounter.model;

import java.io.Serializable;

/**
 * A counnter type describes the base life and base poison counters for a
 * specific counter. Real counters can be produced from a counter type.
 * 
 * @author Mirko
 * 
 */
public class CounterType implements Serializable {

	private static final long serialVersionUID = -5062293116428942246L;

	private int baseLife;
	private int basePoison;

	public final static CounterType DEFAULT = new CounterType(20, 10);

	public CounterType(int baseLife, int basePoison) {
		this.baseLife = baseLife;
		this.basePoison = basePoison;
	}

	public Counter createCounter(Player p) {
		return new Counter(this, p);
	}

	public int getBaseLife() {
		return baseLife;
	}

	public int getBasePoison() {
		return basePoison;
	}

	public Counter[] createCounters(Player... players) {
		Counter[] result = new Counter[players.length];

		for (int i = 0; i < result.length; i++) {
			result[i] = createCounter(players[i]);
		}

		return result;
	}

	protected void resetCounter(Counter c) {
		c.setLife(baseLife);
		c.setPoison(0);
	}
}
