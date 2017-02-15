package kr.ac.jbnu.ssel.castparser.view.TableViewColumns;

import java.sql.Time;
import java.util.TimeZone;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;

import kr.ac.jbnu.ssel.castparser.view.Constant;


public class MisraTableViewRuleNumColumn extends MisraTableViewColumn {

	private IResource file;
	
	@Override
	public String getText(Object element) {
		if(element instanceof TimeZone)
		{
			return ((TimeZone) element).getID();
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return Constant.ruleNum;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 100;
	}
	

}
