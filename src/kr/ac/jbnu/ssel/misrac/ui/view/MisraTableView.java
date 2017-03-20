package kr.ac.jbnu.ssel.misrac.ui.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.ui.texteditor.ITextEditor;

import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;
import kr.ac.jbnu.ssel.misrac.ui.view.tableviewcolumns.MisraTableViewRuleTypeColumn;
import kr.ac.jbnu.ssel.misrac.ui.handlers.CASTHandler;
import kr.ac.jbnu.ssel.misrac.ui.view.ViolationMessageView.ViewLabelProvider;
import kr.ac.jbnu.ssel.misrac.ui.view.tableviewcolumns.MisraTableViewErrorMSGColumn;
import kr.ac.jbnu.ssel.misrac.ui.view.tableviewcolumns.MisraTableViewLineColumn;
import kr.ac.jbnu.ssel.misrac.ui.view.tableviewcolumns.MisraTableViewRuleNumColumn;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.layout.Row;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

/**
 * 
 * @author Taeyoung Kim
 */
public class MisraTableView extends ViewPart implements IPropertyChangeListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "kr.ac.jbnu.ssel.misrac.ui.view.MisraTableView";

	private ArrayList<ViolationMessage> violationMsgs = null;

	private TableViewer tableViewer;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	private Composite parent;

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	/**
	 * The constructor.
	 */
	public MisraTableView() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		CASTHandler castHandler = new CASTHandler();
		violationMsgs = castHandler.processMisraRuleChecking0();
		
		this.parent = parent;
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		tableViewer.getTable().setHeaderVisible(true);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(tableViewer.getControl(), "CASTParser.viewer");
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setLabelProvider(new ViewLabelProvider());
		// should make arrayType data;

		// should add some ColumInstance at here
		new MisraTableViewRuleNumColumn().addColumnTo(tableViewer);
		// new MisraTableViewLineColumn().addColumnTo(tableViewer);
		new MisraTableViewRuleTypeColumn().addColumnTo(tableViewer);
		new MisraTableViewErrorMSGColumn().addColumnTo(tableViewer);
		
		tableViewer.setInput(violationMsgs);

		 tableViewer.addDoubleClickListener(new IDoubleClickListener() {
		
		 @Override
		 public void doubleClick(DoubleClickEvent e) {
			 ISelection selection = tableViewer.getSelection();
				Object obj = ((IStructuredSelection) selection).getFirstElement();

				ViolationMessage violationMsg = getViolationMatchedWith(obj);
				if (violationMsg != null) {
					IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
							.getActiveEditor();
					ITextEditor txtEditor = (ITextEditor) editor;
					IDocument doc = txtEditor.getDocumentProvider().getDocument(editor.getEditorInput());

					int offset = violationMsg.getNode().getNodeLocations()[0].getNodeOffset();
					int length = violationMsg.getNode().getNodeLocations()[0].getNodeLength();
					txtEditor.selectAndReveal(offset, length);
				}
		 }
		 });

		getSite().setSelectionProvider(tableViewer);

	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				MisraTableView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(tableViewer.getControl());
		tableViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, tableViewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = tableViewer.getSelection();
				Object obj = ((IStructuredSelection) selection).getFirstElement();
				showMessage("Double-click detected on " + obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(tableViewer.getControl().getShell(), "MISRA-C Rule Check View", message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		tableViewer.getControl().setFocus();
	}

	public void setViolationMessage(ArrayList<ViolationMessage> violationMsgs) {
		this.violationMsgs = violationMsgs;

		// the parameters are dummy
		firePartPropertyChanged("", "", "");
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if (tableViewer != null) {
			tableViewer.setInput(violationMsgs);
		}
	}

	private ViolationMessage getViolationMatchedWith(Object obj) {
		if (violationMsgs != null && violationMsgs.size() != 0) {
			for (ViolationMessage vioMsg : violationMsgs) {
				if (vioMsg.getMessage().equals(((ViolationMessage)obj).getMessage())) {
					return vioMsg;
				}
			}
		}
		return null;
	}
}