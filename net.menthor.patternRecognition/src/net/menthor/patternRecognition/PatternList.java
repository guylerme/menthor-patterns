package net.menthor.patternRecognition;

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

import net.menthor.patternRecognition.kindPattern.KindPattern;
import net.menthor.patternRecognition.nonSortalPattern.NonSortalPattern;
import net.menthor.patternRecognition.parthoodStructurePattern.ParthoodStructurePattern;
import net.menthor.patternRecognition.phasePattern.PhasePattern;
import net.menthor.patternRecognition.relatorPattern.RelatorPattern;
import net.menthor.patternRecognition.rolePattern.RolePattern;
import net.menthor.patternRecognition.subKindPattern.SubKindPattern;
import net.menthor.patternRecognition.substanceSortalPattern.SubstanceSortalPattern;

/**
 * 
 * This class represents a Pattern List Model.
 * 
 * @author Guylerme Figueiredo
 */

public class PatternList {
	/** TODO Colocar aqui os padroes */
	private KindPattern kindPattern;
	private SubstanceSortalPattern substanceSortalPattern;
	private SubKindPattern subKindPattern;
	private PhasePattern phasePattern;
	private RolePattern rolePattern;
	private ParthoodStructurePattern parthoodStructurePattern;
	private RelatorPattern relatorPattern;
	private NonSortalPattern nonSortalPattern;

	public PatternList(KindPattern kindPattern, SubstanceSortalPattern substanceSortalPattern,
			SubKindPattern subKindPattern, PhasePattern phasePattern, RolePattern rolePattern,
			ParthoodStructurePattern parthoodStructurePattern, RelatorPattern relatorPattern,
			NonSortalPattern nonSortalPattern) {

		this.kindPattern = kindPattern;
		this.substanceSortalPattern = substanceSortalPattern;
		this.subKindPattern = subKindPattern;
		this.phasePattern = phasePattern;
		this.rolePattern = rolePattern;
		this.parthoodStructurePattern = parthoodStructurePattern;
		this.relatorPattern = relatorPattern;
		this.nonSortalPattern = nonSortalPattern;

	}

	public PatternList() {

	}

	public ArrayList<PatternOccurrence> getAll() {
		ArrayList<PatternOccurrence> result = new ArrayList<PatternOccurrence>();
		result.addAll(kindPattern.getOccurrences());
		result.addAll(substanceSortalPattern.getOccurrences());
		result.addAll(subKindPattern.getOccurrences());
		result.addAll(phasePattern.getOccurrences());
		result.addAll(rolePattern.getOccurrences());
		result.addAll(parthoodStructurePattern.getOccurrences());
		result.addAll(relatorPattern.getOccurrences());
		result.addAll(nonSortalPattern.getOccurrences());

		return result;
	}

	public KindPattern getKindPattern() {
		return kindPattern;
	}

	public void setKindPattern(KindPattern kindPattern) {
		this.kindPattern = kindPattern;
	}

	public SubstanceSortalPattern getSubstanceSortalPattern() {
		return substanceSortalPattern;
	}

	public void setSubstanceSortalPattern(SubstanceSortalPattern substanceSortalPattern) {
		this.substanceSortalPattern = substanceSortalPattern;
	}

	public SubKindPattern getSubKindPattern() {
		return subKindPattern;
	}

	public void setSubKindPattern(SubKindPattern subKindPattern) {
		this.subKindPattern = subKindPattern;
	}

	public PhasePattern getPhasePattern() {
		return phasePattern;
	}

	public void setPhasePattern(PhasePattern phasePattern) {
		this.phasePattern = phasePattern;
	}

	public RolePattern getRolePattern() {
		return rolePattern;
	}

	public void setRolePattern(RolePattern rolePattern) {
		this.rolePattern = rolePattern;
	}

	public ParthoodStructurePattern getParthoodStructurePattern() {
		return parthoodStructurePattern;
	}

	public void setParthoodStructurePattern(ParthoodStructurePattern parthoodStructurePattern) {
		this.parthoodStructurePattern = parthoodStructurePattern;
	}

	public RelatorPattern getRelatorPattern() {
		return relatorPattern;
	}

	public void setRelatorPattern(RelatorPattern relatorPattern) {
		this.relatorPattern = relatorPattern;
	}

	public NonSortalPattern getNonSortalPattern() {
		return nonSortalPattern;
	}

	public void setNonSortalPattern(NonSortalPattern nonSortalPattern) {
		this.nonSortalPattern = nonSortalPattern;
	}

}
