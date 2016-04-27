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
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.BuildingGenerator;
import org.worldgrower.generator.Item;
import org.worldgrower.generator.PlantGenerator;

public class UTestCreateWineGoal {

	private CreateWineGoal goal = Goals.CREATE_WINE_GOAL;
	
	@Test
	public void testCalculateGoalNull() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject performer = createPerformer();
		
		assertEquals(null, goal.calculateGoal(performer, world));
	}
	
	@Test
	public void testCalculateGoalPlantGrapeVinePlant() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		assertEquals(Actions.PLANT_GRAPE_VINE_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalHarvestGrape() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		int cottonId = PlantGenerator.generateGrapeVine(5, 5, world);
		WorldObject cottonPlant = world.findWorldObject(Constants.ID, cottonId);
		cottonPlant.setProperty(Constants.GRAPE_SOURCE, 100);
		
		assertEquals(Actions.HARVEST_GRAPES_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalBuildBrewery() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.GRAPES.generate(1f), 20);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.WOOD.generate(1f), 20);
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.STONE.generate(1f), 20);
		
		assertEquals(Actions.BUILD_BREWERY_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testCalculateGoalBrewWine() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.GRAPES.generate(1f), 20);
		
		int breweryId = BuildingGenerator.generateBrewery(0, 0, world);
		performer.setProperty(Constants.BREWERY_ID, breweryId);
		
		assertEquals(Actions.BREW_WINE_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
	}
	
	@Test
	public void testIsGoalMet() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer();
		
		assertEquals(false, goal.isGoalMet(performer, world));
		
		performer.getProperty(Constants.INVENTORY).addQuantity(Item.WINE.generate(1f), 10);
		assertEquals(true, goal.isGoalMet(performer, world));
	}

	private WorldObject createPerformer() {
		WorldObject performer = TestUtils.createSkilledWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.X, 0);
		performer.setProperty(Constants.Y, 0);
		performer.setProperty(Constants.WIDTH, 1);
		performer.setProperty(Constants.HEIGHT, 1);
		return performer;
	}
}