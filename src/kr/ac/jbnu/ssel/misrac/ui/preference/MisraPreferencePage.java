package kr.ac.jbnu.ssel.misrac.ui.preference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller.Listener;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPreferencePage;



/**
 * 
 * @author Taeyoung Kim
 */
public class MisraPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private static final String PageDescription = "misra page";
	private Table table;
	private List<Rule> tableData;

	private CheckboxTableViewer tableViewer;
	protected Object[] checkedElements;

	private SourceViewer sourceViewer;
	private IDocument document;
	private SourceViewerConfiguration sourceViewerConfiguration;
	private int VERTICAL_RULER_WIDTH = 40;
	private int styles = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;
	private AnnotationModel annotationModel;
	private String defaultMessage = "please click the column above tihs viewer\n\n\n\n";

	private String[] ruleCategoriesComboList = { "All","Enviroment", "Language Extensions", "Documentation", "Character Sets",
			"Identifiers", "Types", "Constants", "Declarations And Definitions", "Initialization",
			"Arithmetic Type Conversion", "Pointer Type Conversion", "Exprssions", "Control Statement Expressions",
			"Control Flow", "Switch Statements", "Functions", "Pointers And Arrays", "Structures And Unions",
			"Preprocessing Directives", "Standard Libraries", "Runtime Failures" };
	private int defaultIndex = 0;

	private Rules rules;
	private List<Rule> clonedData;
	private MisraUIdataHandler misraUIdataHandler;
	private Combo ruleCategories;

	@Override
	public void init(org.eclipse.ui.IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite parent) {
		misraUIdataHandler = MisraUIdataHandler.getInstance();
		noDefaultAndApplyButton();
		cloneTableData();
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.GRAB_VERTICAL));
		createButtonsAndOthers(composite);
		createTable(composite);
		addTableLister();
		// add to button here!!
		createSourceViewer(composite);
		this.contributeButtons(composite);
		return composite;
	}

	private void createTable(Composite composite) {
		Group tableCmp = new Group(composite, SWT.NONE);
		tableCmp.setLayout(new GridLayout(1, false));
		tableCmp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 2));
		tableCmp.setText("Rule_Viewer");
		// Initializes TableViewer
  
		Table table = new Table(tableCmp,SWT.CHECK | styles);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setRedraw(true);
			
		GridData tableGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		tableGridData.heightHint =270;
		tableGridData.widthHint =180;
		
		table.setLayoutData(tableGridData);
		tableViewer = new CheckboxTableViewer(table);
		TableViewerColumn colFirstName = new TableViewerColumn(tableViewer, SWT.NONE);
		colFirstName.getColumn().setWidth(80);
		colFirstName.getColumn().setText("Rule Num");
		colFirstName.setLabelProvider(new ColumnLabelProvider() {
			@Override 
			public String getText(Object element) {
				 Rule rule = (Rule) element;
				 return "Rule"+rule.getMinerNum();
			}
		});
		TableViewerColumn colSecondName = new TableViewerColumn(tableViewer, SWT.NONE);
		colSecondName.getColumn().setWidth(80);
		colSecondName.getColumn().setText("Rule Type");
		colSecondName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				 Rule rule = (Rule) element;
				 return rule.getType();
			}
		});
		TableViewerColumn colThirdName = new TableViewerColumn(tableViewer, SWT.NONE);
		colThirdName.getColumn().setWidth(400);
		colThirdName.getColumn().setText("rule Description");
		colThirdName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				 Rule rule = (Rule) element;
				 return rule.getDescription();
			}
		});
		tableViewer.setContentProvider(new ArrayContentProvider());

		// must be input Data as Array<Rule>
		tableData = misraUIdataHandler.getInstance().getRules();
		ruleCategories.select(defaultIndex);
		tableViewer.setInput(tableData);
		setChecked(tableViewer);

	}

	private void createSourceViewer(Composite composite) {

		Group sourceViewCmp = new Group(composite, SWT.None);
		sourceViewCmp.setLayout(new GridLayout(1, false));
		sourceViewCmp.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,3,3));
		sourceViewCmp.setText("Rule_CodeViewer");

		CompositeRuler ruler = new CompositeRuler(VERTICAL_RULER_WIDTH);
		sourceViewer = new SourceViewer(sourceViewCmp, ruler, styles);
		sourceViewerConfiguration = new SourceViewerConfiguration();
		sourceViewer.configure(sourceViewerConfiguration);

//		GridData sourceViewerGridData = new GridData(360, 180);
		GridData sourceViewerGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		sourceViewerGridData.heightHint =220;
		sourceViewerGridData.widthHint =180;
		
		// We need to handle how to show the source Code to use tableColumn
		if (document == null) {
			CreateDefaultDocument();
		}
		
		annotationModel = new AnnotationModel();
		annotationModel.connect(document);

		sourceViewer.setDocument(document, annotationModel);
		sourceViewer.getTextWidget().setFont(JFaceResources.getFont(JFaceResources.TEXT_FONT));
		sourceViewer.getControl().setLayoutData(sourceViewerGridData);
		sourceViewer.setEditable(false);
		
	}

	private void CreateDefaultDocument() {
		document = new Document();
		document.set(defaultMessage);
	}

	private void createButtonsAndOthers(Composite composite) {

		Group buttonsCmp = new Group(composite, SWT.None);
		buttonsCmp.setLayout(new GridLayout(4, false));
		buttonsCmp.setLayoutData(new GridData(SWT.HORIZONTAL));

		ruleCategories = new Combo(buttonsCmp, SWT.BORDER | SWT.READ_ONLY);
		for (int i = 0; i < ruleCategoriesComboList.length; i++) {
			ruleCategories.add(ruleCategoriesComboList[i]);
		}
		ruleCategories.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String categoryWithCombo = ((Combo) event.widget).getText();
				if(categoryWithCombo.contains(" "))
					categoryWithCombo = manipulateWhiteSpace(categoryWithCombo);
				try {
					//issue occured
					tableData = misraUIdataHandler.getRules(categoryWithCombo);
					tableViewer.setInput(tableData);
					tableViewer.refresh();
					setChecked(tableViewer);
					setCheckIntoWholeData(tableViewer);
					if(categoryWithCombo.equals("All"))
					{
						tableData = misraUIdataHandler.getRules();
						tableViewer.setInput(tableData);
						setChecked(tableViewer);
						tableViewer.refresh();
					}
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			private void setCheckIntoWholeData(CheckboxTableViewer tableViewer) {
				// TODO Auto-generated method stub
				TableItem[] tableItems = tableViewer.getTable().getItems();
				List<Rule> wholeRule = misraUIdataHandler.getInstance().getRules();
				for (int i = 0; i < tableItems.length; i++) {
					Rule itemRule = (Rule)tableItems[i].getData();
					for (Rule rule : wholeRule) {
						if(rule.equals(itemRule)){
							rule.setShouldCheck(true);
						}
					}
				}
			}

			private String manipulateWhiteSpace(String categoryWithCombo) {
				StringBuffer stringBuffer = new StringBuffer();
				String[] categoryStrArry = categoryWithCombo.split(" ");
				for (int i = 0; i < categoryStrArry.length; i++) {
					stringBuffer.append(categoryStrArry[i]);
				}
				return stringBuffer.toString();
			}
		});

		org.eclipse.swt.widgets.Button allCheckButton = new org.eclipse.swt.widgets.Button(buttonsCmp, SWT.PUSH);
		allCheckButton.setText("All");
		allCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (int i = 0; i < table.getItemCount(); i++) 
				{
					TableItem item = table.getItem(i);
					item.setChecked(true);
					tableData.get(i).setShouldCheck(true);
				}
			}
		});
		
		org.eclipse.swt.widgets.Button allUnCheckButton = new org.eclipse.swt.widgets.Button(buttonsCmp, SWT.PUSH);
		allUnCheckButton.setText("None");
		allUnCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (int i = 0; i < table.getItemCount(); i++) 
				{
					TableItem item = table.getItem(i);
					item.setChecked(false);
					tableData.get(i).setShouldCheck(false);
				}
			}
		});				
	}
	private void addTableLister() {
		table = tableViewer.getTable();		
		table.addSelectionListener(new SelectionListener() {
	
			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				try{
					Object obj = selectionEvent.getSource();
					table = tableViewer.getTable();
					int index = table.getSelectionIndex();
					Object tableItem = table.getItem(index).getData();
					// TODO
					Rule rule = (Rule)tableItem;
					if (document != null) {
						//put Code to use getCode Method
						document.set(rule.getSourceCode());
					} else {
						CreateDefaultDocument();
					}
				}
				catch(IllegalArgumentException exp){
					exp.printStackTrace();
				}
			}
	
			@Override
			public void widgetDefaultSelected(SelectionEvent selectionEvent) {
			}
	
		});
		
		// to do : need to implement handling when user checked checkbox
		tableViewer.addCheckStateListener(new ICheckStateListener() {
			
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				Object element = event.getElement();
				Rule rule = (Rule)element;
				if(rule.shouldCheck==false){
					rule.setShouldCheck(true);
				}else{
					rule.setShouldCheck(false);
				}
			}
		});
		
	}

	@Override
	protected void contributeButtons(Composite parent) {
		GridData gd;
		Composite buttonBar = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.makeColumnsEqualWidth = false;
        buttonBar.setLayout(layout);
        
        gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
        
        buttonBar.setLayoutData(gd);
		
		
		org.eclipse.swt.widgets.Button defaultButton = new org.eclipse.swt.widgets.Button(buttonBar, SWT.PUSH);
		defaultButton.setText("Resotre");
		int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		org.eclipse.swt.graphics.Point minButtonSize = defaultButton.computeSize(SWT.DEFAULT,
				SWT.DEFAULT, true);
		data.widthHint = Math.max(widthHint, minButtonSize.x);
		defaultButton.setLayoutData(data);
		
		defaultButton.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent event) {
				tableViewer.setInput(clonedData);
				tableViewer.refresh();
				setChecked(tableViewer);
				ruleCategories.select(defaultIndex);
				
			}
		});
		org.eclipse.swt.widgets.Button applyButton = new org.eclipse.swt.widgets.Button(buttonBar, SWT.PUSH);
		applyButton.setText("Apply");
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		minButtonSize = applyButton.computeSize(SWT.DEFAULT, SWT.DEFAULT,
				true);
		data.widthHint = Math.max(widthHint, minButtonSize.x);
		applyButton.setLayoutData(data);
		
		applyButton.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {;
				List<Rule> checkedRuleList = getCheckedRule(tableData);
				//need to store Data into xml
				misraUIdataHandler.storeToXml();
				String nil = buildNotImplementedList(checkedRuleList);
				MessageDialog.openInformation(parent.getShell(), "Open MRC preference", nil);
			}
			
			private String buildNotImplementedList(List<Rule> checkedRuleList) {
				StringBuilder sb = new StringBuilder();
				for (Rule rule : checkedRuleList) {
					if(rule.getClassName().contains("notImplement")){
						sb.append("Rule:"+rule.minerNum +", ");
					}
				}
				sb.append(".. are not Implemented yet");
				return sb.toString();
			}

			private List<Rule> getCheckedRule(List<Rule> tableData) {
				List<Rule> checkedRuleList = new ArrayList<Rule>();
				for (Rule rule : tableData) {
					if(rule.isShouldCheck())
						checkedRuleList.add(rule);
				}
				return checkedRuleList;
			}
		});
		super.contributeButtons(parent);
	}
	
	private void cloneTableData() {
		try {
			Object clonedObj = misraUIdataHandler.clone();
			MisraUIdataHandler muh = (MisraUIdataHandler)clonedObj;
			clonedData = muh.getInstance().getRules();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setChecked(CheckboxTableViewer tableViewer){
		List<Rule> rules = misraUIdataHandler.getRules();
		table = tableViewer.getTable();
		TableItem[] tableItems = table.getItems();
		for (int i = 0; i < tableItems.length; i++) 
		{
			Object tableItem = tableItems[i].getData();
			Rule itemRule = (Rule)tableItem;
			for (Rule rule : rules) 
			{
				if(rule.equals(itemRule)&&itemRule.isShouldCheck())
					table.getItem(i).setChecked(true);
			}
		}
	}
}
