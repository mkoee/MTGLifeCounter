package lifecounter.model;

import java.io.Serializable;

/**
 * A counter counts the life and poison counters for a player. The counter is
 * produced by a {@link CounterType}.
 * 
 * @author Mirko
 * 
 */
public class Counter implements Serializable {

	private static final long serialVersionUID = 1954415751244879467L;

	private Player player;
	private int life;
	private int poison;

	private CounterType type;

	protected Counter(CounterType type, Player player) {
		this.player = player;
		this.type = type;
		reset();
	}

	public void changeLife(int amount) {
		setLife(life + amount);
	}

	public void changePoison(int amount) {
		setPoison(poison + amount);
	}

	public void setPoison(int poison) {
		this.poison = poison;

		if (this.poison < 0)
			this.poison = 0;

	}

	public void setLife(int life) {
		this.life = life;
		
		if (this.life > 999)
			this.life = 999;
	}

	public Player getPlayer() {
		return player;
	}

	public int getLife() {
		return life;
	}

	public int getPoison() {
		return poison;
	}

	public int getBaseLife() {
		return type.getBaseLife();
	}

	public int getBasePoison() {
		return type.getBasePoison();
	}

	public void reset() {
		type.resetCounter(this);
	}
	
	public double getThreat() {
		double lifeThreat = ((double) getBaseLife() - getLife()) / getBaseLife();
		double poisonThreat = ((double) getPoison()) / getBasePoison();
		
		lifeThreat = lifeThreat < 0 ? 0 : lifeThreat;
		
		double threat = lifeThreat < poisonThreat ? poisonThreat : lifeThreat;
		
		threat = threat < 0 ? 0 : threat;
		threat = threat > 1 ? 1 : threat;
		
		return threat;		
	}
	
	public boolean isDead() {
		return (getLife() == 0) || (getPoison() == getBasePoison());
	}
}
