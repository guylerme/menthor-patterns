package net.menthor.editor.v2.ui.patternRecognition;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import net.menthor.patternRecognition.PatternOccurrence;

/**
 * @author Guylerme Figueiredo
 *
 */

public class PatternResultFilter extends ViewerFilter {

	private String searchString;
	
	public void setSearchText(String s) 
	{     
		this.searchString = ".*" + s + ".*"; // ensure that the value can be used for matching
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) 
	{
	  if (searchString == null || searchString.length() == 0) return true;
	  
	  PatternOccurrence occurrence = (PatternOccurrence) element;
	  
	  if (occurrence.getShortName().matches(searchString)) return true;
	  
//	  if (occurrence.getAntiPatternType().getAntipatternInfo().getAcronym().matches(searchString)) {
//	    return true;
//	  }
	  
	   return false;
	}
}
