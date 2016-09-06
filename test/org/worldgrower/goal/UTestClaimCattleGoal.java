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
import org.worldgrower.generator.CreatureGenerator;

public class UTestClaimCattleGoal {

	private ClaimCattleGoal goal = Goals.CLAIM_CATTLE_GOAL;
	
	@Test
	public void testCalculateGoalNull() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		
		assertEquals(null, goal.calculateGoal(performer, world));
	}
	
	@Test
	public void testCalculateGoalClaimCow() {
		World world = new WorldImpl(10, 10, null, null);
		WorldObject performer = createPerformer(2);
		
		WorldObject cow = createCow(world);
		
		assertEquals(Actions.CLAIM_CATTLE_ACTION, goal.calculateGoal(performer, world).getManagedOperation());
		assertEquals(cow, goal.calculateGoal(performer, world).getTarget());
	}

	private WorldObject createCow(World world) {
		WorldObject organization = createVillagersOrganization(world);
		CreatureGenerator creatureGenerator = new CreatureGenerator(organization);
		int cowId = creatureGenerator.generateCow(0, 0, world);
		WorldObject cow = world.findWorldObjectById(cowId);
		return cow;
	}
	
	@Test
	public void testCalculateGoalClaimedCow() {
		World world = new WorldImpl(25, 25, null, null);
		WorldObject performer = createPerformer(2);
		
		WorldObject cow = createCow(world);
		cow.setProperty(Constants.CATTLE_OWNER_ID, 2);
		
		assertEquals(null, goal.calculateGoal(performer, world));
	}
	
	@Test
	public void testIsGoalMet() {
		World world = new WorldImpl(25, 25, null, null);
		WorldObject performer = createPerformer(2);
		
		WorldObject cow = createCow(world);
		
		
		assertEquals(false, goal.isGoalMet(performer, world));
		
		cow.setProperty(Constants.CATTLE_OWNER_ID, 2);
		assertEquals(true, goal.isGoalMet(performer, world));
	}
	
	private WorldObject createVillagersOrganization(World world) {
		WorldObject villagersOrganization = GroupPropertyUtils.createVillagersOrganization(world);
		villagersOrganization.setProperty(Constants.ID, 1);
		world.addWorldObject(villagersOrganization);
		return villagersOrganization;
	}

	private WorldObject createPerformer(int id) {
		WorldObject performer = TestUtils.createSkilledWorldObject(id, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.X, 0);
		performer.setProperty(Constants.Y, 0);
		performer.setProperty(Constants.WIDTH, 1);
		performer.setProperty(Constants.HEIGHT, 1);
		return performer;
	}
}