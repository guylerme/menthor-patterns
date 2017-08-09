package net.menthor.patternRecognition.wizard.kindPattern;

import java.text.Normalizer;

import net.menthor.patternRecognition.kindPattern.KindOccurrence;
import net.menthor.patternRecognition.wizard.PatternAction;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Association;

public class KindPatternAction extends PatternAction<KindOccurrence> {

	public Association assoc;

	public KindPatternAction(KindOccurrence ap) {
		super(ap);
	}

	public enum Action {
		DERIVE_ONE_ASSOCIATION, CYCLE_FORBIDDEN, CYCLE_MANDATORY
	}

	@Override
	public void run() {

	}

	public static String getStereotype(EObject element) {
		String type = element.getClass().toString().replaceAll("class RefOntoUML.impl.", "");
		type = type.replaceAll("Impl", "");
		type = Normalizer.normalize(type, Normalizer.Form.NFD);
		if (!type.equalsIgnoreCase("association"))
			type = type.replace("Association", "");
		return type;
	}

	public void setDeriveAssociation(Association assoc) {
		code = Action.DERIVE_ONE_ASSOCIATION;
		this.assoc = assoc;
	}

	public void setCycleForbidden() {
		code = Action.CYCLE_FORBIDDEN;
	}

	public void setCycleMandatory() {
		code = Action.CYCLE_MANDATORY;
	}

	@Override
	public String toString() {
		String result = new String();

		if (code == Action.DERIVE_ONE_ASSOCIATION) {
			result += "Create OCL derivation for association " + getStereotype(assoc) + " " + (assoc).getName() + ": "
					+ (assoc).getMemberEnd().get(0).getType().getName() + "->"
					+ (assoc).getMemberEnd().get(1).getType().getName();
		}
		if (code == Action.CYCLE_FORBIDDEN) {
			result += "Create OCL invariant forbidding instance level cycle";
		}
		if (code == Action.CYCLE_MANDATORY) {
			result += "Create OCL invariant enforcing instance level cycle";
		}
		return result;
	}
}
