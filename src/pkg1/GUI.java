package pkg1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import pkg1.MkpInstanceFileReader.InvalidFileTypeException;
import pkg1.MkpInstanceFileReader.InvalidInstanceException;

@SuppressWarnings("serial")
public class GUI extends JFrame{
	int choice;
	JScrollPane sp = new JScrollPane();
	JScrollPane sp2 = new JScrollPane();
	JScrollPane sp3 = new JScrollPane();
	Mkp mkp;
	String[][] objData ;
	String[][] sacsData ;
	State initialState;
	int nbObj;
	int nbSacs;
	int value;
	 
	public GUI() {
		ImageIcon image = new ImageIcon("src\\logo.png");
		this.setIconImage(image.getImage());
		
		this.setTitle("Multiple Knapsack Problem Solver");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(950,650);
		//colors
		Color sidePanelTextColor=new Color(49, 54, 63);
		Color sidePanelBgColor=new Color(118, 171, 174);
		Color topPanelBgColor=new Color(34, 40, 49);
		Color titleColor=new Color(238, 238, 238);
		//fonts
		Font textFont=new Font("Roboto",Font.PLAIN,11);
		Font titleFont=new Font("Roboto Black",Font.BOLD,22);
		Font labelsFont= new Font("Roboto",Font.BOLD,15);
		
		//panels
		JPanel sidePanel=new JPanel();
		sidePanel.setBorder(new EmptyBorder(20,0,0,0));
		sidePanel.setPreferredSize(new Dimension(200,500));
		sidePanel.setBackground(sidePanelBgColor);
		
		
		JPanel topPanel=new JPanel();
		topPanel.setPreferredSize(new Dimension(750,70));
		topPanel.setBackground(topPanelBgColor);

		
		JPanel mainPanel=new JPanel();
		mainPanel.setPreferredSize(new Dimension(550,300));
		mainPanel.setBackground(titleColor);
		mainPanel.setLayout(null);
		JPanel bottomPanel=new JPanel();
		
		bottomPanel.setPreferredSize(new Dimension(550,60));
		bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		mainPanel.setLayout(new BorderLayout());
		
		mainPanel.add(bottomPanel,BorderLayout.SOUTH);
		JPanel tablesContainer=new JPanel();
		tablesContainer.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        mainPanel.add(tablesContainer);
		
        
        JPanel solutionDescription=new JPanel();
		
		solutionDescription.setPreferredSize(new Dimension(700,180));
		solutionDescription.setLayout(new GridLayout(5,1));
		
		JLabel nbObjectsLabel=new JLabel();
		nbObjectsLabel.setForeground(sidePanelTextColor);
		JLabel nbSacsLabel=new JLabel();
		nbSacsLabel.setForeground(sidePanelTextColor);
		nbSacsLabel.setBorder(new EmptyBorder(10,0,0,0));
		
		
		
		JLabel minValueLabel=new JLabel("MIN VALUE");
		minValueLabel.setForeground(sidePanelTextColor);
		minValueLabel.setBorder(new EmptyBorder(10,0,0,0));
		JLabel titleLabel=new JLabel("Multiple Knapsack Problem Solver");
		JLabel solutionLabel=new JLabel("SEARCHING ..");
		solutionLabel.setFont(labelsFont);
		JLabel solDescLabel=new JLabel("Description: ");
		solDescLabel.setFont(new Font("Roboto",Font.PLAIN,14));

		JLabel exectutionTimeLabel=new JLabel("Executed in: ");
		exectutionTimeLabel.setFont(new Font("Roboto",Font.PLAIN,14));
		JLabel nbNodesLabel=new JLabel("Number of developped nodes: ");
		nbNodesLabel.setFont(new Font("Roboto",Font.PLAIN,14));
		
		JLabel nbNodes=new JLabel();
		nbNodes.setFont(new Font("Roboto Black",Font.BOLD,14));
		nbNodes.setForeground(Color.black);
	
		JLabel executionTime=new JLabel();
		executionTime.setFont(new Font("Roboto Black",Font.BOLD,14));
		executionTime.setForeground(Color.black);
		JLabel totalValueLabel=new JLabel("Total Value: ");
		totalValueLabel.setFont(new Font("Roboto",Font.PLAIN,14));
		
		JLabel totalValue=new JLabel();
		totalValue.setFont(new Font("Roboto Black",Font.BOLD,14));
		totalValue.setForeground(Color.black);
		
		JLabel totalWeightLabel=new JLabel("Total Weight: ");
		totalWeightLabel.setFont(new Font("Roboto",Font.PLAIN,14));
		
		JLabel totalWeight=new JLabel();
		totalWeight.setFont(new Font("Roboto Black",Font.BOLD,14));
		totalWeight.setForeground(Color.black);
		
		JLabel description=new JLabel();
		description.setFont(new Font("Roboto Black",Font.PLAIN,14));
		description.setVerticalAlignment(JLabel.TOP);
		JScrollPane descScrollPane=new JScrollPane();
		
		descScrollPane.setViewportView(description);
		descScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		descScrollPane.setBorder(new EmptyBorder(0,0,0,0));
		
		//textInputs
		JTextField valueInp=new JTextField();
		valueInp.setFont(textFont);
		valueInp.setPreferredSize(new Dimension(180,30));
		valueInp.setBorder(BorderFactory.createLoweredBevelBorder());
		valueInp.setHorizontalAlignment(JTextField.CENTER);;
		valueInp.setText(String.valueOf(10));
		
		//sliders
		JSlider objectsSlider =new JSlider(0,100,5);
		JSlider sacsSlider =new JSlider(0,100,3);
				
		objectsSlider.setPreferredSize(new Dimension(180,50));
		objectsSlider.setPaintTicks(true);
		objectsSlider.setMinorTickSpacing(5);
		objectsSlider.setMajorTickSpacing(20);
		objectsSlider.setPaintLabels(true);
		objectsSlider.setBackground(null);
		objectsSlider.setFont(new Font("Roboto",Font.PLAIN,11));
		
		sacsSlider.setPreferredSize(new Dimension(180,50));
		sacsSlider.setPaintTicks(true);
		sacsSlider.setMinorTickSpacing(5);
		sacsSlider.setMajorTickSpacing(20);
		sacsSlider.setPaintLabels(true);
		sacsSlider.setBackground(null);
		sacsSlider.setFont(new Font("Roboto",Font.PLAIN,11));
		
		//Radio buttons
		JRadioButton b1=new JRadioButton("DFS");
		JRadioButton b2=new JRadioButton("BFS");
		JRadioButton b3=new JRadioButton("A-STAR");
		b1.setFont(labelsFont);
		b2.setFont(labelsFont);
		b3.setFont(labelsFont);
		b1.setVisible(false);
		b2.setVisible(false);
		b3.setVisible(false);
		b1.setBorder(new EmptyBorder(20,0,0,0));
		b2.setBorder(new EmptyBorder(20,0,0,0));
		b3.setBorder(new EmptyBorder(20,0,0,0));
		b1.setBackground(null);
		b2.setBackground(null);
		b3.setBackground(null);
		ButtonGroup group=new ButtonGroup();
	
		group.add(b1);
		group.add(b2);
		group.add(b3);
		b1.addActionListener(e->{
			choice=1;
		});
		b2.addActionListener(e->{
			choice=2;
		});
		b3.addActionListener(e->{
			choice=3;
		});
		
		
	
		//buttons
		JButton generateBtn=new JButton("GENERATE");
		JButton searchBtn=new JButton("SEARCH");
		JButton selectBtn=new JButton("LOAD");
		JButton saveBtn=new JButton("SAVE");
		
		saveBtn.setFocusable(false);
		saveBtn.setPreferredSize(new Dimension(120,40));
		saveBtn.setBackground(Color.LIGHT_GRAY);
		selectBtn.setForeground(titleColor);
		saveBtn.setEnabled(false);
		saveBtn.addActionListener(e->{
				MkpInstanceSaver saver=new MkpInstanceSaver();
				saver.saveInstance(mkp);
		});
		
		selectBtn.setFocusable(false);
		selectBtn.setPreferredSize(new Dimension(120,40));
		selectBtn.setBackground(topPanelBgColor);
		selectBtn.setForeground(titleColor);
		selectBtn.addActionListener(e->{
			
			JFileChooser chooser=new JFileChooser();
			chooser.setCurrentDirectory(new File("./Instances"));
			int response=chooser.showOpenDialog(this);
			if(response==JFileChooser.APPROVE_OPTION) {
				
				saveBtn.setBackground(Color.LIGHT_GRAY);
				saveBtn.setEnabled(false);
				
				File file = new File(chooser.getSelectedFile().getAbsolutePath());
				searchBtn.setBackground(topPanelBgColor);
				searchBtn.setEnabled(true);
				b1.setVisible(true);
				b2.setVisible(true);
				b3.setVisible(true);
				try {
				    mkp = MkpInstanceFileReader.readFromFile(file.getAbsolutePath());
					System.out.println(mkp);
				    nbSacs=mkp.getNbKnapsacs();
					value=mkp.getMinValue();
					nbObj=mkp.getNbObjects();
					initialState=new State(nbSacs,nbObj);
					
					
					valueInp.setText(String.valueOf(value));
				    sacsSlider.setValue(nbSacs);
				    objectsSlider.setValue(nbObj);
			
					objData = mkp.stringifyObjects();
					sacsData = mkp.stringifyKnapsacks();
					// Column Names
			        String[] objColumnNames = { "Object", "Value($)", "Weight(KG)" };
			        String[] sacsColumnNames = { "Knapsack", "Maximum Capacity(KG)"};
				    
			        JTable objs = new JTable(objData, objColumnNames );
			        JTable sacs = new JTable(sacsData, sacsColumnNames);
			        
			        objs.setFont(new Font("Roboto",Font.PLAIN,14));
			        sacs.setFont(new Font("Roboto",Font.PLAIN,14));     
			        objs.setForeground(sidePanelTextColor);
			        sacs.setForeground(sidePanelTextColor);
			        objs.getTableHeader().setBackground(topPanelBgColor);
			        objs.getTableHeader().setForeground(titleColor);
			        objs.getTableHeader().setFont(labelsFont);
			        sacs.getTableHeader().setBackground(topPanelBgColor);
			        sacs.getTableHeader().setForeground(titleColor);
			        sacs.getTableHeader().setFont((new Font("Roboto",Font.BOLD,15)));
			       
			        sp.setViewportView(objs);
			        sp.setPreferredSize(new Dimension(350,130));
			        sp.setBorder(BorderFactory.createLineBorder(titleColor));
			        sp2.setBorder(BorderFactory.createLineBorder(titleColor));
			        sp2.setPreferredSize(new Dimension(350,130));
			        sp2.setViewportView(sacs);
			        
			   
					
					solutionDescription.setVisible(false);
			        solutionLabel.setVisible(false);
			        sp3.setVisible(false);
			        
			        solutionDescription.add(exectutionTimeLabel);
					solutionDescription.add(executionTime);
					solutionDescription.add(nbNodesLabel);
					solutionDescription.add(nbNodes);
					solutionDescription.add(totalValueLabel);
					solutionDescription.add(totalValue);
					solutionDescription.add(totalWeightLabel);
					solutionDescription.add(totalWeight);
					solutionDescription.add(solDescLabel);
					solutionDescription.add(descScrollPane);
					tablesContainer.add(sp);
			        tablesContainer.add(sp2);
					tablesContainer.add(solutionLabel);
					
					
					this.revalidate();
				    
				} catch (IOException | InvalidInstanceException | InvalidFileTypeException e2) {
				    e2.printStackTrace();
				    JOptionPane.showMessageDialog(this, e2.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
				      
				}
			}
			
		});
		generateBtn.setFocusable(false);
		generateBtn.setPreferredSize(new Dimension(120,40));
		generateBtn.setBackground(topPanelBgColor);
		generateBtn.setForeground(titleColor);
		generateBtn.addActionListener(e->{
	
			 String text = valueInp.getText().trim().toString(); 
			 nbObj=objectsSlider.getValue();
        	 nbSacs=sacsSlider.getValue();
        	 
             if (text.isEmpty()||!isInteger(text)|| nbSacs==0||nbObj==0) {
     			JOptionPane.showMessageDialog(this,"Invalid Input","error",JOptionPane.ERROR_MESSAGE);
             }
             else {
            	 value= Integer.valueOf(text);
            	if(nbObj>5 && nbSacs>2 && value>200)JOptionPane.showMessageDialog(this, "The number of objects/knapsacks is too heigh the search may take a long  time and exhauste your machine memory\ndon't panic if the app freezes it's just still searching :)","warning",JOptionPane.WARNING_MESSAGE);
			      
            	 
            	 mkp=new Mkp(nbSacs,nbObj,value);
            	 initialState=new State(nbSacs,nbObj);
            	 
            	 //JOptionPane.showMessageDialog(this,value+" "+nbObj+" "+nbSacs,"title",JOptionPane.PLAIN_MESSAGE);
            		
    			searchBtn.setBackground(topPanelBgColor);
    			searchBtn.setEnabled(true);

			
				b1.setVisible(true);
				b2.setVisible(true);
				b3.setVisible(true);
				objData = mkp.stringifyObjects();
			            
				
				sacsData = mkp.stringifyKnapsacks();
				// Column Names
		        String[] objColumnNames = { "Object", "Value($)", "Weight(KG)" };
		        String[] sacsColumnNames = { "Knapsack", "Maximum Capacity(KG)"};
			    
		        JTable objs = new JTable(objData, objColumnNames );
		        JTable sacs = new JTable(sacsData, sacsColumnNames);
		        
		        objs.setFont(new Font("Roboto",Font.PLAIN,14));
		        sacs.setFont(new Font("Roboto",Font.PLAIN,14));     
		        objs.setForeground(sidePanelTextColor);
		        sacs.setForeground(sidePanelTextColor);
		        objs.getTableHeader().setBackground(topPanelBgColor);
		        objs.getTableHeader().setForeground(titleColor);
		        objs.getTableHeader().setFont(labelsFont);
		        sacs.getTableHeader().setBackground(topPanelBgColor);
		        sacs.getTableHeader().setForeground(titleColor);
		        sacs.getTableHeader().setFont((new Font("Roboto",Font.BOLD,15)));
		       
		        sp.setViewportView(objs);
		        sp.setPreferredSize(new Dimension(350,130));
		        sp.setBorder(BorderFactory.createLineBorder(titleColor));
		        sp2.setBorder(BorderFactory.createLineBorder(titleColor));
		        sp2.setPreferredSize(new Dimension(350,130));
		        sp2.setViewportView(sacs);
		        
		   
				
				solutionDescription.setVisible(false);
		        solutionLabel.setVisible(false);
		        sp3.setVisible(false);
		        
		    	saveBtn.setBackground(topPanelBgColor);
				saveBtn.setForeground(titleColor);
				saveBtn.setEnabled(true);
				
		        solutionDescription.add(exectutionTimeLabel);
				solutionDescription.add(executionTime);
				solutionDescription.add(nbNodesLabel);
				solutionDescription.add(nbNodes);
				solutionDescription.add(totalValueLabel);
				solutionDescription.add(totalValue);
				solutionDescription.add(totalWeightLabel);
				solutionDescription.add(totalWeight);
				solutionDescription.add(solDescLabel);
				solutionDescription.add(descScrollPane);
				tablesContainer.add(sp);
		        tablesContainer.add(sp2);
				tablesContainer.add(solutionLabel);
				
				
				this.revalidate();
             }
			
		});
		
		searchBtn.setFocusable(false);
		searchBtn.setPreferredSize(new Dimension(100,40));
		searchBtn.setForeground(titleColor);
		searchBtn.setEnabled(false);
		searchBtn.setBackground(Color.LIGHT_GRAY);
		searchBtn.addActionListener(e->{
			if(choice==0)
				JOptionPane.showMessageDialog(this,"Choose a search algorithm","warning",JOptionPane.WARNING_MESSAGE);
			
			else{
				generateBtn.setBackground(Color.LIGHT_GRAY);
				generateBtn.setEnabled(false);
			
				selectBtn.setBackground(Color.LIGHT_GRAY);
				selectBtn.setEnabled(false);
				
				saveBtn.setBackground(Color.LIGHT_GRAY);
				saveBtn.setEnabled(false);
				
				solutionDescription.setVisible(true);
				solutionLabel.setVisible(true);
				long startTime;
				long endTime;
				State solution;
				String text = valueInp.getText().trim().toString(); 
				if (text.isEmpty()||!isInteger(text)) {
					JOptionPane.showMessageDialog(this,"Invalid Input","error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					value=Integer.valueOf(text);
					mkp.setMinValue(value);
					
					//solutionLabel.setText("SEARCHING ..");
					try {
						
					startTime= System.currentTimeMillis();	    
					if(choice==1) {
						solution=mkp.DFS(initialState);
						solutionLabel.setText("SOLUTION (DFS)");
					}
					else if(choice ==2) {
						solution=mkp.BFS(initialState);
						solutionLabel.setText("SOLUTION (BFS)");
					}
					else {
						solution=mkp.AStar(initialState);
						solutionLabel.setText("SOLUTION (A-STAR)");
					}
					endTime= System.currentTimeMillis();
					
				
					String time=String.valueOf(endTime-startTime)+"ms";
					nbNodes.setText(String.valueOf(solution.getNbDeveloppedNodes())+" nodes");
					executionTime.setText(time);
					System.out.println(solution);
					
					
					if(solution!=initialState) {
						
						String [][]solutionMatrix=mkp.stringifySolution(solution);
						
		
						description.setText(solution.toString()+"\n");
						totalValue.setText(String.valueOf(mkp.totalValue(solution))+" $");
						totalWeight.setText(String.valueOf(mkp.totalWeight(solution))+" KG");
						
						String[] solColumnNames = mkp.getObjectNames();
					      
				        JTable solutionTable = new JTable(solutionMatrix,solColumnNames);
		
				        solutionTable.setFont(new Font("Roboto",Font.PLAIN,14));
				        solutionTable.setForeground(sidePanelTextColor);
				        solutionTable.getTableHeader().setBackground(topPanelBgColor);
				        solutionTable.getTableHeader().setForeground(titleColor);
				        solutionTable.getTableHeader().setFont(labelsFont);		        
				        solutionTable.getColumnModel().getColumn(0).setPreferredWidth(100);
				        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			            for (int columnIndex = 0; columnIndex < solutionTable.getColumnCount(); columnIndex++) {
			            	solutionTable.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
			            }
				        
				        
				        sp3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				        sp3.setVisible(true);
				        sp3.setViewportView(solutionTable);
				        sp3.setPreferredSize(new Dimension(700,100));
				        if(solutionTable.getColumnCount()>8)solutionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				        sp3.setBorder(BorderFactory.createLineBorder(titleColor));
				       
				        tablesContainer.add(sp3);
				
		
					}
					
					else  {
						//no solution
						solutionLabel.setText("NO SOLUTION FOR THIS VALUE");
						description.setText("-");
						totalValue.setText("-");
						totalWeight.setText("-");
						tablesContainer.remove(sp3);
						this.revalidate();
	
					}
					
					}catch(OutOfMemoryError e1) {
						//out of memory
						solutionLabel.setText("ERROR: OUT OF MEMORY");
						nbNodes.setText("-");
						executionTime.setText("-");
						description.setText("-");
						totalValue.setText("-");
						totalWeight.setText("-");
						tablesContainer.remove(sp3);
						this.revalidate();
					}
					generateBtn.setBackground(topPanelBgColor);
					generateBtn.setEnabled(true);
					selectBtn.setBackground(topPanelBgColor);
					selectBtn.setEnabled(true);
					saveBtn.setForeground(titleColor);
					saveBtn.setBackground(topPanelBgColor);
					saveBtn.setEnabled(true);
					tablesContainer.add(solutionDescription);
					this.revalidate();
				 
				}
			}
		});
		sidePanel.add(nbObjectsLabel);
		sidePanel.add(objectsSlider);
		sidePanel.add(nbSacsLabel);
		sidePanel.add(sacsSlider);
		sidePanel.add(minValueLabel);
		sidePanel.add(valueInp);
		
		sidePanel.add(b1);
		sidePanel.add(b1);
		sidePanel.add(b2);
		sidePanel.add(b3);
		
		bottomPanel.add(saveBtn);
		bottomPanel.add(selectBtn);
		bottomPanel.add(generateBtn);
		bottomPanel.add(searchBtn);
		
		topPanel.setLayout(new BorderLayout());
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(titleColor);
		topPanel.add(titleLabel);
		
		
		nbObjectsLabel.setText("OBJECTS : "+objectsSlider.getValue());
		nbSacsLabel.setText("KNAPSACKS : "+sacsSlider.getValue());
		objectsSlider.addChangeListener(e->{
			nbObjectsLabel.setText("OBJECTS : "+objectsSlider.getValue());	
		});
		sacsSlider.addChangeListener(e->{
			nbSacsLabel.setText("KNAPSACKS : "+sacsSlider.getValue());	
		});
		
		
		setFontForComponents(sidePanel,labelsFont);
		setFontForComponents(bottomPanel,labelsFont);
		setFontForComponents(topPanel,titleFont);;
		this.add(topPanel,BorderLayout.NORTH);
		this.add(sidePanel,BorderLayout.WEST);
		this.add(mainPanel,BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	public static void setFontForComponents(Container container, Font font) {
        Component[] components = container.getComponents();
        for (Component component : components) {
        	 switch (component.getClass().getSimpleName()) {
             case "JLabel":
                 JLabel label = (JLabel) component;
                 label.setFont(font);
                 break;
            case "JTextField":
                 JTextField textField = (JTextField) component;
                 textField.setFont(font);
                 break;
            case "JButton":
                JButton btn = (JButton) component;
                btn.setFont(font);
                break;
         }
        }
    }
	
	  public static boolean isInteger(String input) {
	        try {
	            Integer.parseInt(input);
	            return true; 
	        } catch (NumberFormatException e) {
	            return false; 
	        }
	    }
}
