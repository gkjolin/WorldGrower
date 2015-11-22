/*******************************************************************************
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.worldgrower.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.condition.Condition;
import org.worldgrower.condition.Conditions;
import org.worldgrower.generator.PlantGenerator;

public class UTestEatNightShadeAction {

	@Test
	public void testExecuteEat() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject performer = createPerformer(2);
		performer.setProperty(Constants.FOOD, 800);
		
		int nightShadeId = PlantGenerator.generateNightShade(0, 0, world);
		WorldObject nightShade = world.findWorldObject(Constants.ID, nightShadeId);
		nightShade.setProperty(Constants.NIGHT_SHADE_SOURCE, 100);
		
		Actions.EAT_NIGHT_SHADE_ACTION.execute(performer, nightShade, new int[0], world);
		
		assertEquals(810, performer.getProperty(Constants.FOOD).intValue());
		assertEquals(true, performer.getProperty(Constants.CONDITIONS).hasCondition(Condition.POISONED_CONDITION));
		assertEquals(90, nightShade.getProperty(Constants.NIGHT_SHADE_SOURCE).intValue());
	}
	
	private WorldObject createPerformer(int id) {
		WorldObject performer = TestUtils.createSkilledWorldObject(id, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.X, 0);
		performer.setProperty(Constants.Y, 0);
		performer.setProperty(Constants.WIDTH, 1);
		performer.setProperty(Constants.HEIGHT, 1);
		performer.setProperty(Constants.CONDITIONS, new Conditions());
		return performer;
	}
}