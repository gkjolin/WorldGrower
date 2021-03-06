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

import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.Knowledge;
import org.worldgrower.attribute.KnowledgeMap;

public class KnowledgePropertyUtils {

	public static boolean performerKnowsMoreThanTarget(WorldObject performer, WorldObject target) {
		return getPerformerOnlyKnowledge(performer, target).hasKnowledge();
	}
	
	public static KnowledgeMap getPerformerOnlyKnowledge(WorldObject performer, WorldObject target) {
		if (!performer.equals(target)) {
			KnowledgeMap performerKnowledge = performer.getProperty(Constants.KNOWLEDGE_MAP);
			KnowledgeMap targetKnowledge = target.getProperty(Constants.KNOWLEDGE_MAP);
			
			return performerKnowledge.subtract(targetKnowledge);
		} else {
			return new KnowledgeMap();
		}
	}
	
	public static Knowledge find(List<Knowledge> knowledgeList, int id) {
		for(Knowledge knowledge : knowledgeList) {
			if (knowledge.getId() == id) {
				return knowledge;
			}
		}
		throw new IllegalStateException("Id " + id + " not found in " + knowledgeList);
	}
	
	public static int[] createArgs(List<Knowledge> sortedKnowledge, int count) {
		int[] result = new int[count];
		for(int i=0; i<count; i++) {
			result[i] = sortedKnowledge.get(i).getId();
		}
		return result;
	}

	public static boolean exists(List<Knowledge> knowledgeList, int id) {
		for(Knowledge knowledge : knowledgeList) {
			if (knowledge.getId() == id) {
				return true;
			}
		}
		return false;
	}
}
