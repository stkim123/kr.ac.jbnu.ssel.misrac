package kr.ac.jbnu.ssel.misrac.ui.view.tableviewcolumns;

import java.sql.Time;
import java.util.TimeZone;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;

import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;
import kr.ac.jbnu.ssel.misrac.ui.Constant;


public class MisraTableViewRuleNumColumn extends MisraTableViewColumn {

	private IResource file;
	
	@Override
	public String getText(Object element) {
		if(element instanceof ViolationMessage)
		{
			String ruleID = ((ViolationMessage) element).getRule().getRuleID();
			String[] splitedRuleID = ruleID.split("_");
			ruleID = splitedRuleID[0]+"_"+splitedRuleID[1];
			return ruleID;
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
