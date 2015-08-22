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
package org.worldgrower.deity;

import java.io.ObjectStreamException;
import java.util.Arrays;
import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.condition.VampireUtils;
import org.worldgrower.goal.GroupPropertyUtils;
import org.worldgrower.profession.Professions;

public class Dionysus implements Deity {

	@Override
	public String getName() {
		return "Dionysus";
	}

	@Override
	public String getExplanation() {
		return getName() + " is the God of wine, parties and festivals, madness, chaos, drunkenness, drugs, and ecstasy.";
	}

	public Object readResolve() throws ObjectStreamException {
		return readResolveImpl();
	}
	
	@Override
	public List<String> getReasons() {
		return Arrays.asList(
				getName() + " is the God of wine and festivals, he is important for keeping up morale in our community"
				);
	}

	@Override
	public int getReasonIndex(WorldObject performer, World world) {
		if (performer.getProperty(Constants.PROFESSION) == Professions.PRIEST_PROFESSION) {
			return 0;
		}
		return -1;
	}
	
	@Override
	public void onTurn(World world) {
		int currentTurn = world.getCurrentTurn().getValue();
		int totalNumberOfWorshippers = DeityPropertyUtils.getTotalNumberOfWorshippers(world);
		
		if ((currentTurn % 1000 == 0) && (totalNumberOfWorshippers > 10) && (VampireUtils.getVampireCount(world) == 0)) {
			List<WorldObject> targets = DeityPropertyUtils.getWorshippersFor(this, world);
			final WorldObject target;
			if (targets.size() > 0) {
				target = targets.get(0);
			} else {
				List<WorldObject> allWorshippers = DeityPropertyUtils.getAllWorshippers(world);
				
				int indexOfChosenTarget = (int) (Math.random() * allWorshippers.size());
				target = allWorshippers.get(indexOfChosenTarget);
			}
			
			VampireUtils.vampirizePerson(target);
		}
	}
}