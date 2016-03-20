/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysterydungeon.entity;

import mysterydungeon.dungeon.Dungeon;
import mysterydungeon.dungeon.Node;

/**
 *
 * @author Justis
 */
public class FollowState extends EntityState
{
        @Override
	public void doState(Entity e, Dungeon d)
	{
		Entity player = d.getEntities().get(0);
		Node target = player.getCurrentNode();
		Node start = e.getCurrentNode();
		Node next = nextNode(e, d, start, target);
		if(!target.equals(next))
		{
			e.setCurrentNode(next);
		}
	}

        @Override
	public int isState()
	{
		return 1;
	}
}