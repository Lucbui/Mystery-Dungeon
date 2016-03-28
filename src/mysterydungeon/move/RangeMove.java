/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysterydungeon.move;

import mysterydungeon.dungeon.Dungeon;
import mysterydungeon.dungeon.Node;
import mysterydungeon.entity.Entity;
import mysterydungeon.MysteryDungeon;

/**
 * A standard long-range move.
 * This move affects entities the attacker is facing, but may be some distance
 * away. The farther the enemy is, the weaker the attack: It is at 100% power
 * at one tile away, 75% power at two tiles away, 56.25% power at three tiles away,
 * and so on. These moves have a 15% chance of missing, and no chance of doing
 * increased damage.
 * @author Justis
 */
public class RangeMove implements Move
{

    /**
     * The value multiplied by this base power with each tile traveled.
     */
    public static final double MULTIPLIER = 0.75;
    
    private final int power;
    private final int range;
    private int currentPower;
    
    /**
     * Creates a ranged move.
     * @param power The standard damage this attack will do, at one tile away.
     * @param range The farthest the attack will go before dying.
     */
    public RangeMove(int power, int range)
    {
        this.power = power;
        this.currentPower = power;
        this.range = range;
    }
    
    /**
     * Get the type of move this is.
     * @return The constant Move.RANGE, defined in the Move class.
     */
    @Override
    public int getType()
    {
        return Move.RANGE;
    }
    
    /**
     * Get the name of this move. Not actually used.
     * @return The string "Blast".
     */
    @Override
    public String getName()
    {
        return "Blast";
    }
    
    /**
     * Performs the actual attack.
     * @param dungeon The dungeon this attack occurs in.
     * @param attacker The entity performing the attack.
     * @param defender The entity being attacked.
     */
    @Override
    public void attack(Dungeon dungeon, Entity attacker, Entity defender)
    {
        if(defender == null)
        {
            MysteryDungeon.LOG.append(String.format("%s fired and missed!\n", attacker.getName()));
        }
        else
        {
            if(Dungeon.PRNG.nextInt(100) < 15)
            {
                MysteryDungeon.LOG.append(String.format("%s fired at %s and missed!\n", attacker.getName(), defender.getName()));
            }
            else
            {
                int totalDamage = defender.addHP(-currentPower);
                MysteryDungeon.LOG.append(String.format("%s attacked %s for %dHP of damage!\n", attacker.getName(), defender.getName(), totalDamage));
                if(defender.getCurrentHP() == 0)
                {
                    dungeon.clearEnemy(defender);
                    MysteryDungeon.LOG.append(String.format("%s was destroyed!\n", defender.getName()));
                }
            }
        }
    }
    
    /**
     * Gets the entity affected by this attack.
     * @param dungeon The dungeon this attack occurs in.
     * @param attacker The entity performing the attack.
     * @return The entity receiving the attack, or null if there is nobody.
     */
    @Override
    public Entity getDefender(Dungeon dungeon, Entity attacker)
    {
        Node start = attacker.getCurrentNode();
        int facing = attacker.facing;
        currentPower = power;
        for(int ds = 0; ds < range; ds++)
        {
            start = start.getPath(facing);
            if(start == null){break;}
            for(Entity entity : dungeon.getEntities())
            {
                if(entity.getCurrentNode().equals(start))
                {
                    return entity;
                }
            }
            currentPower = (int)(currentPower * MULTIPLIER); //Reduces by 25% the farther away you go.
        }
        return null;
    }
}
