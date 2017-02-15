package kr.ac.jbnu.ssel.castparser.view;

import java.awt.Button;
import java.io.File;
import java.rmi.activation.Activator;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Element;

import kr.ac.jbnu.ssel.castparser.preference.Rule;
import kr.ac.jbnu.ssel.castparser.preference.Rules;
import kr.ac.jbnu.ssel.castparser.viewHandler.MisraUIdataHandler;

import org.eclipse.cdt.core.templateengine.TemplateEngineHelper;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.persistence.TemplatePersistenceData;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.internal.ViewSite;

public class MisraPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private static final String PageDescription = "misra page";
	private Table table;
	private List<Rule> tableData;

	private TableViewer tableViewer;
	protected Object[] checkedElements;

	private SourceViewer sourceViewer;
	private IDocument document;
	private SourceViewerConfiguration sourceViewerConfiguration;
	private int VERTICAL_RULER_WIDTH = 40;
	private int styles = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;
	private AnnotationModel annotationModel;
	private String defaultMessage = "please click the column above tihs viewer\n\n\n\n";

	private String[] ruleCategoriesComboList = { "All", "Language Extensions", "Documentation", "Character Sets",
			"Identifiers", "Types", "Constants", "Declarations And Definitions", "Initialization",
			"Arithmetic Type Conversion", "Pointer Type Conversion", "Exprssions", "Control Statement Expressions",
			"Control Flow", "Switch Statements", "Functions", "Pointers And Arrays", "Structures And Unions",
			"Preprocessing Directives", "Standard Libraries", "Runtime Failures" };
	private int defaultIndex = 0;

	private Rules rules;

	private MisraUIdataHandler misraUIdataHandler;
	
	@Override
	public void init(org.eclipse.ui.IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite parent) {
		misraUIdataHandler = new MisraUIdataHandler();
			
		noDefaultAndApplyButton();
		noDefaultButton();
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.GRAB_VERTICAL));
		createButtonsAndOthers(composite);
		createTable(composite);
		addTableLister();
		// add to button here!!
		createSourceViewer(composite);

		return composite;
	}

	private void addTableLister() {
		table = tableViewer.getTable();
		// to do : need to implement handling when user checked checkbox
		table.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {
					int index = table.getSelectionIndex();
					String ruleNumber = table.getItem(index).getText();
					setChecked(ruleNumber, tableData);
				}
			}

			private void setChecked(String ruleNumber, List<Rule> tableData) {
				String[] ruleNumArry = ruleNumber.split("Rule");
				String minerNum = ruleNumArry[1];
				for (Rule rule : tableData) 
				{
					if(minerNum.equals(rule.getMinerNum()))
					{
						rule.setShouldCheck(true);
						break;
					}
				}
				
			}
		});
		
		table.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				int index = table.getSelectionIndex();
				String ruleNumber = table.getItem(index).getText();
				misraUIdataHandler.getCode(ruleNumber);
				if (document != null) {
					//put Code to use getCode Method
					document.set(ruleNumber);
				} else {
					CreateDefaultDocument();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent selectionEvent) {
			}

		});
		
	}

	private void createTable(Composite composite) {
		Group tableCmp = new Group(composite, SWT.None);
		tableCmp.setLayout(new GridLayout(1, false));
		tableCmp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 2));
		tableCmp.setText("Rule_Viewer");
		// Initializes TableViewer
		tableViewer = new TableViewer(tableCmp,
				SWT.CHECK | SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		//
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		    
		table.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true));
		TableViewerColumn colFirstName = new TableViewerColumn(tableViewer, SWT.NONE);
		colFirstName.getColumn().setWidth(70);
		colFirstName.getColumn().setText("Rule Num");
		colFirstName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				 Rule rule = (Rule) element;
				 return "Rule"+rule.getMinerNum();
			}
		});
		TableViewerColumn colSecondName = new TableViewerColumn(tableViewer, SWT.NONE);
		colSecondName.getColumn().setWidth(70);
		colSecondName.getColumn().setText("Rule Type");
		colSecondName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				 Rule rule = (Rule) element;
				 return rule.getType();
			}
		});
		TableViewerColumn colThirdName = new TableViewerColumn(tableViewer, SWT.NONE);
		colThirdName.getColumn().setWidth(230);
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
		tableViewer.setInput(tableData);

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

		// We need to handle how to show the source Code to use tableColumn
		if (document == null) {
			CreateDefaultDocument();
		}

		annotationModel = new AnnotationModel();
		annotationModel.connect(document);

		sourceViewer.setDocument(document, annotationModel);
		sourceViewer.getTextWidget().setFont(JFaceResources.getFont(JFaceResources.TEXT_FONT));
		sourceViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
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

		Combo ruleCategories = new Combo(buttonsCmp, SWT.BORDER | SWT.READ_ONLY);
		for (int i = 0; i < ruleCategoriesComboList.length; i++) {
			ruleCategories.add(ruleCategoriesComboList[i]);
		}
		ruleCategories.select(defaultIndex);
		try {
		tableData = misraUIdataHandler.allDataLoad().getRule();
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ruleCategories.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String categoryWithCombo = ((Combo) event.widget).getText();
				if(categoryWithCombo.contains(" "))
					categoryWithCombo = manipulateWhiteSpace(categoryWithCombo);
				try {
					tableData = misraUIdataHandler.DataLoad(categoryWithCombo);
					tableViewer.setInput(tableData);
					tableViewer.refresh();
					if(categoryWithCombo.equals("All"))
					{
						tableData = misraUIdataHandler.allDataLoad().getRule();
						tableViewer.setInput(tableData);
						tableViewer.refresh();
					}
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

		org.eclipse.swt.widgets.Button setRuleButton = new org.eclipse.swt.widgets.Button(buttonsCmp, SWT.PUSH);
		setRuleButton.setText("Set_Rules");
		setRuleButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<Rule> checkedRuleList = getCheckedRule(tableData);
				//need to Manipulate to excute rules depend on the checked rule.
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
}
