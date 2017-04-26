package kr.ac.jbnu.ssel.misrac.ui.view.tableviewcolumns;

import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;
import kr.ac.jbnu.ssel.misrac.ui.Constant;

public class MisraTableViewFilePathColumn extends MisraTableViewColumn {

	@Override
	public String getText(Object element) {
		if(element instanceof ViolationMessage)
		{
			ViolationMessage vmsg = (ViolationMessage) element;
			return vmsg.getCFilePath();
		}
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return Constant.filePath;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 90;
	}

}
