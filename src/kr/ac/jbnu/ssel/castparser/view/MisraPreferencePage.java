package kr.ac.jbnu.ssel.castparser.view;

import java.awt.Button;
import java.rmi.activation.Activator;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

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
	private List tableData;

	private TableViewer tableViewer;
	protected Object[] checkedElements;

	private SourceViewer sourceViewer;
	private IDocument document;
	private SourceViewerConfiguration sourceViewerConfiguration;
	private int VERTICAL_RULER_WIDTH = 40;
	private int styles = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;
	private AnnotationModel annotationModel;
	private String defaultMessage = "please click the column above tihs viewer\n\n\n\n";

	private String[] ruleCategoriesComboList = { "All", "Language Extention", "Documentation", "Character Sets",
			"Identifiers", "Types", "Constants", "Declarations and Definitions", "Initialization",
			"Arithmetic Type Conversion", "Pointer Type Conversion", "Expressions", "Control Statement Expressions",
			"Control Flow", "Switch Statements", "Functions", "Pointers and Arrays", "Structures and Unions",
			"Preprecessing Directives", "Standard Libraries", "Runtime Failures" };
	private int defaultIndex = 0;

	@Override
	public void init(org.eclipse.ui.IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite parent) {
		tableData = new ArrayList<>();
		tableData.add("aaaaaa");
		tableData.add("bbbbbbb");
		tableData.add("ccccccccc");
		tableData.add("DDDDDDDDDDDD");

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
		tableViewer.getTable().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				int index = table.getSelectionIndex();
				String tableData = table.getItem(index).getText();
				if (document != null) {
					document.set(tableData);
				} else {
					CreateDefaultDocument();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent selectionEvent) {
			}

		});
		// to do : need to implement handling when user checked checkbox
		table.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {

				}
			}
		});

	}

	private void createTable(Composite composite) {
		Group tableCmp = new Group(composite, SWT.None);
		tableCmp.setLayout(new GridLayout(1, false));
		tableCmp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));
		tableCmp.setText("Rule_Viewer");

		// Initializes TableViewer
		tableViewer = new TableViewer(tableCmp,
				SWT.CHECK | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		//
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));

		TableViewerColumn colFirstName = new TableViewerColumn(tableViewer, SWT.NONE);
		colFirstName.getColumn().setWidth(70);
		colFirstName.getColumn().setText("Rule Num");
		colFirstName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				// Rule p = (Rule) element;
				// return p.getFirstName();
				return "aaaa";
			}
		});
		TableViewerColumn colSecondName = new TableViewerColumn(tableViewer, SWT.NONE);
		colSecondName.getColumn().setWidth(70);
		colSecondName.getColumn().setText("Rule Type");
		colSecondName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				// Rule p = (Rule) element;
				// return p.getFirstName();
				return "bbbbb";
			}
		});
		TableViewerColumn colThirdName = new TableViewerColumn(tableViewer, SWT.NONE);
		colThirdName.getColumn().setWidth(230);
		colThirdName.getColumn().setText("rule Description");
		colThirdName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				// Rule p = (Rule) element;
				// return p.getFirstName();
				return "ccccc";
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
		ruleCategories.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				System.out.println(((Combo) event.widget).getText());
			}
		});

		org.eclipse.swt.widgets.Button setRuleButton = new org.eclipse.swt.widgets.Button(buttonsCmp, SWT.PUSH);
		setRuleButton.setText("Set_Rules");
		setRuleButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

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
				}
			}
		});
	}

}
