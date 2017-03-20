package kr.ac.jbnu.ssel.misrac.ui.view.tableviewcolumns;

import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;
import kr.ac.jbnu.ssel.misrac.ui.Constant;

public class MisraTableViewErrorMSGColumn extends MisraTableViewColumn {

	@Override
	public String getText(Object element) {
		if(element instanceof ViolationMessage)
		{
			ViolationMessage vmsg = (ViolationMessage) element;
			return vmsg.getMessage();
		}
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return Constant.errorMessage;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 450;
	}

}
