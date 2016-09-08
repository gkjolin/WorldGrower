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
package org.worldgrower.goal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.BuildingList;
import org.worldgrower.attribute.BuildingType;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.BuildingGenerator;
import org.worldgrower.generator.Item;

public class UTestMarkUnusedBuildingAsSellableGoal {

	private MarkUnusedBuildingAsSellableGoal goal = Goals.MARK_WORKBENCH_AS_SELLABLE_AFTER_EQUIPING_SCYTHE;
	
	@Test
	public void testCalculateGoalNull() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		assertEquals(null, goal.calculateGoal(performer, world));
	}

	@Test
	public void testCalculateGoalMarkAsSellable() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		performer.setProperty(Constants.LEFT_HAND_EQUIPMENT, Item.SCYTHE.generate(1f));
		int workbenchId = BuildingGenerator.generateWorkbench(0, 0, world, performer);
		performer.getProperty(Constants.BUILDINGS).add(workbenchId, BuildingType.WORKBENCH);
		
		assertEquals(Actions.MARK_AS_SELLABLE_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testIsGoalMet() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		assertEquals(true, goal.isGoalMet(performer, world));
		
		performer.setProperty(Constants.LEFT_HAND_EQUIPMENT, Item.SCYTHE.generate(1f));
		assertEquals(true, goal.isGoalMet(performer, world));
		
		int workbenchId = BuildingGenerator.generateWorkbench(0, 0, world, performer);
		performer.getProperty(Constants.BUILDINGS).add(workbenchId, BuildingType.WORKBENCH);
		assertEquals(false, goal.isGoalMet(performer, world));
	}
	
	private WorldObject createPerformer() {
		WorldObject performer = TestUtils.createSkilledWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.X, 0);
		performer.setProperty(Constants.Y, 0);
		performer.setProperty(Constants.WIDTH, 1);
		performer.setProperty(Constants.HEIGHT, 1);
		performer.setProperty(Constants.BUILDINGS, new BuildingList());
		return performer;
	}
}