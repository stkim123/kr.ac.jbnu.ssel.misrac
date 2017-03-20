package kr.ac.jbnu.ssel.misrac.ui.view.tableviewcolumns;

import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;
import kr.ac.jbnu.ssel.misrac.ui.Constant;

public class MisraTableViewRuleTypeColumn extends MisraTableViewColumn {


	@Override
	public String getText(Object element) {
		if(element instanceof ViolationMessage)
		{
			String[] splitedRuleID = ((ViolationMessage) element).getRule().getRuleID().split("_");
			String ruleType = splitedRuleID[2];
			if(ruleType.contains(Constant.requiredType)){
				return Constant.REQUIRED;
			}else if(ruleType.contains(Constant.advisoryType)){
				return Constant.ADVISORY;
			}
		}
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return Constant.errorCode;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 100;
	}

}
