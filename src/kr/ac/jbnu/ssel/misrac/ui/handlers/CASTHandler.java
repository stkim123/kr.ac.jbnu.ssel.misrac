package kr.ac.jbnu.ssel.misrac.ui.handlers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.ui.CDTUITools;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

import kr.ac.jbnu.ssel.misrac.rule.RuleLocation;
import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MiaraCRuleException;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;
import kr.ac.jbnu.ssel.misrac.ui.EclipseUtil;
import kr.ac.jbnu.ssel.misrac.ui.preference.MisraUIdataHandler;
import kr.ac.jbnu.ssel.misrac.ui.preference.Rule;
import kr.ac.jbnu.ssel.misrac.ui.view.ViolationMessageView;

/**
 * 
 * @author "STKIM"
 */
public class CASTHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

		processMisraRuleChecking();

		MessageDialog.openInformation(window.getShell(), "MISRA-C Rule Checker", "Checking MISRA-C Rules .. Done!!");

		return null;
	}

	private void processMisraRuleChecking() {
		try {
			IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (!(editor instanceof ITextEditor))
				return;

			// Access translation unit of the editor.
			ITranslationUnit tu = (ITranslationUnit) CDTUITools.getEditorInputCElement(editor.getEditorInput());

			ICProject[] allProjects = CoreModel.getDefault().getCModel().getCProjects();
			IIndex index = CCorePlugin.getIndexManager().getIndex(allProjects);

			// Index-based AST: IASTTranslationUnit for a workspace file
			IASTTranslationUnit ast = null;

			try {

				index.acquireReadLock(); // we need a read-lock on the index

				ast = tu.getAST(index, ITranslationUnit.AST_SKIP_INDEXED_HEADERS);

				String fileURLAsString = EclipseUtil.getEclipsePackageDirOfClass(RuleLocation.class);
				File ruleDicFile = new File(fileURLAsString);
				String[] ruleFiles = ruleDicFile.list();
				
				// filter out unselected rules.
				HashSet<Rule> shouldCheckRules = MisraUIdataHandler.getInstance().getShouldCheckRules();
				HashSet<String> shouldCheckRulesAsString = new HashSet<String>();
				
				for (Rule rule : shouldCheckRules) {
					shouldCheckRulesAsString.add(rule.getClassName());
				}
				
				///////////////////////////
				ArrayList<ViolationMessage> violationMessages = new ArrayList<ViolationMessage>();
				for (String ruleClass : ruleFiles) {
					if(shouldCheckRulesAsString.contains(ruleClass))
					{
						callRule(ast, ruleClass, violationMessages);	
					}
					
				}

				openMessageView(violationMessages);
			} finally {
				index.releaseReadLock();
				ast = null; // don't use the ast after releasing the read-lock
			}

		} catch (MiaraCRuleException e) {
			e.printStackTrace();
		} catch (CModelException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void openMessageView(ArrayList<ViolationMessage> violationMessages) {
		try {
			ViolationMessageView vmv = (ViolationMessageView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(ViolationMessageView.ID);
			vmv.setViolationMessage(violationMessages);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	private void callRule(IASTTranslationUnit ast, String ruleClass, ArrayList<ViolationMessage> violationMessages)
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException, MiaraCRuleException {

		System.out.println("ruleClass:"+ ruleClass);
		String ruleClassWithPackage = RuleLocation.class.getPackage().getName() + "."
				+ ruleClass.substring(0, ruleClass.indexOf("."));

		System.out.println("ruleClassWithPackage:" + ruleClassWithPackage);
		if (!ruleClassWithPackage.equals(RuleLocation.class.getName())) // except
		// R.class
		{
			AbstractMisraCRule rule = (AbstractMisraCRule) Class.forName(ruleClassWithPackage)
					.getConstructor(IASTTranslationUnit.class).newInstance(ast);
			rule.checkRule();
			if (rule.isViolated()) {
				ViolationMessage[] violationMsgs = rule.getViolationMessages();
				violationMessages.addAll(Arrays.asList(violationMsgs));

				for (ViolationMessage violationMessage : violationMsgs) {
					System.out.println("violationMessage:" + violationMessage);
				}
			}
		}
	}
}
