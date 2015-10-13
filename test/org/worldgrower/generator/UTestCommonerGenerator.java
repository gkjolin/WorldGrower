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
package org.worldgrower.generator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.MockCommonerNameGenerator;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.goal.GroupPropertyUtils;
import org.worldgrower.gui.CommonerImageIds;
import org.worldgrower.gui.ImageIds;
import org.worldgrower.gui.start.CharacterAttributes;

public class UTestCommonerGenerator {

	private final CommonerGenerator commonerGenerator = new CommonerGenerator(666, new CommonerImageIds(), new MockCommonerNameGenerator());
	
	@Test
	public void testGeneratePlayerCharacter() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject organization = GroupPropertyUtils.createVillagersOrganization(world);
		
		CharacterAttributes characterAttributes = new CharacterAttributes(10, 10, 10, 10, 10, 10);
		WorldObject playerCharacter = CommonerGenerator.createPlayerCharacter(0, "player", "adventurer" , "female", world, commonerGenerator, organization, characterAttributes, ImageIds.KNIGHT);
		
		assertEquals(false, playerCharacter.isControlledByAI());
		assertEquals(true, playerCharacter.hasIntelligence());
	}
	
	@Test
	public void testGenerateCommoner() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject villagersOrganization = GroupPropertyUtils.createVillagersOrganization(world);
		int commonerId = commonerGenerator.generateCommoner(0, 0, world, villagersOrganization);
		WorldObject commoner = world.findWorldObject(Constants.ID, commonerId);

		assertEquals(true, commoner.isControlledByAI());
		assertEquals(true, commoner.hasIntelligence());
	}
	
	@Test
	public void testGenerateSkeletalRemains() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject originalWorldObject = TestUtils.createSkilledWorldObject(1);
		
		originalWorldObject.setProperty(Constants.X, 5);
		originalWorldObject.setProperty(Constants.Y, 5);
		originalWorldObject.setProperty(Constants.WIDTH, 1);
		originalWorldObject.setProperty(Constants.HEIGHT, 1);
		originalWorldObject.setProperty(Constants.INVENTORY, new WorldObjectContainer());
		originalWorldObject.setProperty(Constants.GOLD, 50);
		originalWorldObject.setProperty(Constants.DEATH_REASON, "dead");
		
		int skeletalRemainsId = CommonerGenerator.generateSkeletalRemains(originalWorldObject, world);
		WorldObject skeletalRemains = world.findWorldObject(Constants.ID, skeletalRemainsId);
		
		assertEquals(true, skeletalRemains.getProperty(Constants.DECEASED_WORLD_OBJECT));
		assertEquals(5, skeletalRemains.getProperty(Constants.X).intValue());
		assertEquals(5, skeletalRemains.getProperty(Constants.Y).intValue());
		assertEquals(50, skeletalRemains.getProperty(Constants.GOLD).intValue());
	}
}