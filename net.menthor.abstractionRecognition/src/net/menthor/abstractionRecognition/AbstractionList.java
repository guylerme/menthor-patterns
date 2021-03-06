package net.menthor.abstractionRecognition;

/**
 * ============================================================================================
 * Menthor Editor -- Copyright (c) 2015 
 *
 * This file is part of Menthor Editor. Menthor Editor is based on TinyUML and as so it is 
 * distributed under the same license terms.
 *
 * Menthor Editor is free software; you can redistribute it and/or modify it under the terms 
 * of the GNU General Public License as published by the Free Software Foundation; either 
 * version 2 of the License, or (at your option) any later version.
 *
 * Menthor Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Menthor Editor; 
 * if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, 
 * MA  02110-1301  USA
 * ============================================================================================
 */

import java.util.ArrayList;

import net.menthor.abstractionRecognition.nonSortalAbstraction.NonSortalAbstraction;
import net.menthor.abstractionRecognition.relatorAbstraction.RelatorAbstraction;

/**
 * 
 * This class represents a Pattern List Model.
 * 
 * @author Guylerme Figueiredo
 */

public class AbstractionList {

	private RelatorAbstraction relatorAbstraction;
	private NonSortalAbstraction nonSortalAbstraction;

	public AbstractionList(RelatorAbstraction relatorAbstraction, NonSortalAbstraction nonSortalAbstraction) {

		this.relatorAbstraction = relatorAbstraction;
		this.nonSortalAbstraction = nonSortalAbstraction;

	}

	public AbstractionList() {

	}

	public ArrayList<AbstractionOccurrence> getAll() {
		ArrayList<AbstractionOccurrence> result = new ArrayList<AbstractionOccurrence>();
		result.addAll(relatorAbstraction.getOccurrences());
		result.addAll(nonSortalAbstraction.getOccurrences());

		return result;
	}

	public RelatorAbstraction getRelatorAbstraction() {
		return relatorAbstraction;
	}

	public void setRelatorAbstraction(RelatorAbstraction relatorAbstraction) {
		this.relatorAbstraction = relatorAbstraction;
	}

	public NonSortalAbstraction getNonSortalAbstraction() {
		return nonSortalAbstraction;
	}

	public void setNonSortalAbstraction(NonSortalAbstraction nonSortalAbstraction) {
		this.nonSortalAbstraction = nonSortalAbstraction;
	}

}
