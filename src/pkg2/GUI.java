package pkg2;
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
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.border.EmptyBorder;
import pkg1.MkpInstanceFileReader.InvalidFileTypeException;
import pkg1.MkpInstanceFileReader.InvalidInstanceException;
import pkg1.Mkp;
import pkg1.State;
import pkg1.MkpInstanceFileReader;
@SuppressWarnings("serial")
public class GUI extends JFrame{
	int choice;
	JScrollPane sp = new JScrollPane();
	JScrollPane sp2 = new JScrollPane();
	JScrollPane sp3 = new JScrollPane();
	JScrollPane sp4 = new JScrollPane();
	JPanel ParamsPanel=new JPanel();
	
	Mkp mkp;
	GeneticAlgorithm GA=null;
	String[][] objData ;
	String[][] sacsData ;
	State initialState;
	int nbObj;
	int nbSacs;
	int value;
	JCheckBox checkbox = new JCheckBox("Show individuals descriptions");  
	LinkedList<State>population;
	String[] populationMatrixColNames= {"Individual","Fitness"};
	String [][]initialPopulationMatrix;
	JTable initPopulationTable;
	//colors
	Color sidePanelTextColor=new Color(49, 54, 63);
	Color sidePanelBgColor=new Color(118, 171, 174);
	Color topPanelBgColor=new Color(34, 40, 49);
	Color titleColor=new Color(238, 238, 238);
	//fonts
	Font textFont=new Font("Roboto",Font.PLAIN,11);
	Font titleFont=new Font("Roboto Black",Font.BOLD,22);
	Font labelsFont= new Font("Roboto",Font.BOLD,15);
	public GUI() {
		ImageIcon image = new ImageIcon("src\\logo.png");
		this.setIconImage(image.getImage());
		
		this.setTitle("Multiple Knapsack Problem Solver (part2)");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(950,650);
		//panels
		JPanel sidePanel=new JPanel();
		sidePanel.setBorder(new EmptyBorder(0,0,0,0));
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
		tablesContainer.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        mainPanel.add(tablesContainer);
		
        
        JPanel solutionDescription=new JPanel();
		
		solutionDescription.setPreferredSize(new Dimension(700,130));
		solutionDescription.setLayout(new GridLayout(4,2,0,0));
	
		
		JLabel nbObjectsLabel=new JLabel();
		nbObjectsLabel.setForeground(sidePanelTextColor);
		nbObjectsLabel.setPreferredSize(new Dimension(180,30));
		nbObjectsLabel.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel nbSacsLabel=new JLabel();
		nbSacsLabel.setForeground(sidePanelTextColor);
		nbSacsLabel.setPreferredSize(new Dimension(180,30));
		nbSacsLabel.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel minValueLabel=new JLabel();
		minValueLabel.setForeground(sidePanelTextColor);
		minValueLabel.setPreferredSize(new Dimension(180,30));
		minValueLabel.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel titleLabel=new JLabel("Multiple Knapsack Problem Solver -Part 2-");
		JLabel solutionLabel=new JLabel();
		solutionLabel.setFont(labelsFont);
		JLabel solDescLabel=new JLabel("Best Solution:  ");
		solDescLabel.setFont(new Font("Roboto",Font.PLAIN,12));

		JLabel exectutionTimeLabel=new JLabel("Executed in: ");
		exectutionTimeLabel.setFont(new Font("Roboto",Font.PLAIN,12));
	
		
		
		JLabel executionTime=new JLabel();
		executionTime.setFont(new Font("Roboto Black",Font.BOLD,12));
		executionTime.setForeground(Color.black);
		JLabel totalValueLabel=new JLabel("Total Value: ");
		totalValueLabel.setFont(new Font("Roboto",Font.PLAIN,12));
		
		JLabel totalValue=new JLabel();
		totalValue.setFont(new Font("Roboto Black",Font.BOLD,12));
		totalValue.setForeground(Color.black);
		
		JLabel totalWeightLabel=new JLabel("Total Weight: ");
		totalWeightLabel.setFont(new Font("Roboto",Font.PLAIN,12));
		
		JLabel totalWeight=new JLabel();
		totalWeight.setFont(new Font("Roboto Black",Font.BOLD,12));
		totalWeight.setForeground(Color.black);
		
		JLabel description=new JLabel();
		description.setFont(new Font("Roboto Black",Font.PLAIN,12));
		description.setVerticalAlignment(JLabel.CENTER);
		
		
		JLabel initialPopulationLabel=new JLabel("***Initial Population***");
        initialPopulationLabel.setHorizontalAlignment(JLabel.CENTER);
        initialPopulationLabel.setPreferredSize(new Dimension(700,15));
        JLabel finalPopulationLabel=new JLabel("***Final Population***");
        finalPopulationLabel.setHorizontalAlignment(JLabel.CENTER);
        finalPopulationLabel.setPreferredSize(new Dimension(700,15));
		
		JScrollPane descScrollPane=new JScrollPane();
		descScrollPane.setPreferredSize(new Dimension(350,20));
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
		
		//Radio buttons
		JRadioButton b1=new JRadioButton("GA ");
		JRadioButton b2=new JRadioButton("BSO");
		b1.setFont(labelsFont);
		b2.setFont(labelsFont);
		b1.setVisible(false);
		b2.setVisible(false);
		b1.setBackground(null);
		b2.setBackground(null);
		ButtonGroup group=new ButtonGroup();
		
		group.add(b1);
		group.add(b2);
		
		
		b1.addActionListener(e->{
			solutionLabel.setText("GENETIC ALGORITHM");
			GAParamsPanel();
			revalidate();
			choice=1;

		});
		b2.addActionListener(e->{
			solutionLabel.setText("BEE SWARM OPTIMIZATION");
			BSOParamsPanel();
			revalidate();
			choice=2;

		});

		
		

		//buttons
		JButton searchBtn=new JButton("SEARCH");
		JButton selectBtn=new JButton("LOAD");
		JButton generateBtn=new JButton("GENERATE");
		
		selectBtn.setFocusable(false);
		selectBtn.setPreferredSize(new Dimension(120,40));
		selectBtn.setBackground(topPanelBgColor);
		selectBtn.setForeground(titleColor);
		selectBtn.addActionListener(e->{
			
			JFileChooser chooser=new JFileChooser();
			chooser.setCurrentDirectory(new File("./Instances"));
			int response=chooser.showOpenDialog(this);
			if(response==JFileChooser.APPROVE_OPTION) {
				
		
				File file = new File(chooser.getSelectedFile().getAbsolutePath());
			
				generateBtn.setBackground(topPanelBgColor);
				generateBtn.setEnabled(true);
				searchBtn.setBackground(Color.LIGHT_GRAY);
				searchBtn.setEnabled(false);
				b1.setVisible(true);
				b2.setVisible(true);
				try {
				    mkp = MkpInstanceFileReader.readFromFile(file.getAbsolutePath());
				    GA=new GeneticAlgorithm(mkp);
					System.out.println(mkp);
				    nbSacs=mkp.getNbKnapsacs();
					value=mkp.getMinValue();
					nbObj=mkp.getNbObjects();
					initialState=new State(nbSacs,nbObj);
					
					
					
					nbObjectsLabel.setText("OBJECTS : "+String.valueOf((nbObj)));
					nbSacsLabel.setText("KNAPSACKS : "+String.valueOf(nbSacs));
					minValueLabel.setText("MIN VALUE : "+String.valueOf(value));
				    //sacsSlider.setValue(nbSacs);
				    //objectsSlider.setValue(nbObj);
					
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
			        sp.setPreferredSize(new Dimension(350,78));
			        sp.setBorder(BorderFactory.createLineBorder(titleColor));
			        sp2.setBorder(BorderFactory.createLineBorder(titleColor));
			        sp2.setPreferredSize(new Dimension(350,78));
			        sp2.setViewportView(sacs);
			        
			   
					
					solutionDescription.setVisible(false);
			        solutionLabel.setVisible(false);
			        sp3.setVisible(false);
			        sp4.setVisible(false);
			        initialPopulationLabel.setVisible(false);
			        finalPopulationLabel.setVisible(false);
			        solutionDescription.add(solDescLabel);
			        solutionDescription.add(descScrollPane);
			        solutionDescription.add(exectutionTimeLabel);
					solutionDescription.add(executionTime);
					solutionDescription.add(totalValueLabel);
					solutionDescription.add(totalValue);
					solutionDescription.add(totalWeightLabel);
					solutionDescription.add(totalWeight);
					tablesContainer.add(sp);
			        tablesContainer.add(sp2);
					tablesContainer.add(solutionLabel);
					if(choice==1)GAParamsPanel();
					else BSOParamsPanel();
					revalidate();
				    
				} catch (IOException | InvalidInstanceException | InvalidFileTypeException e2) {
				    e2.printStackTrace();
				    JOptionPane.showMessageDialog(this, e2.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
				      
				}
			}
			
		});
	
		//generate btn
		generateBtn.setFocusable(false);
		generateBtn.setPreferredSize(new Dimension(120,40));
		generateBtn.setBackground(topPanelBgColor);
		generateBtn.setForeground(titleColor);
		generateBtn.setEnabled(false);
		generateBtn.setBackground(Color.LIGHT_GRAY);		
		generateBtn.addActionListener(e->{
			
			if(choice==0)
				JOptionPane.showMessageDialog(this,"Choose an algorithm","warning",JOptionPane.WARNING_MESSAGE);
			
			else {
				
			String initPopInp=initPopulationInp.getText().trim().toString();
			if(initPopInp.isEmpty()||!isInteger(initPopInp)) {
				JOptionPane.showMessageDialog(this,"Invalid Input","error",JOptionPane.ERROR_MESSAGE);
			}
			else {
				searchBtn.setBackground(topPanelBgColor);
				searchBtn.setEnabled(true);
				int initPop=Integer.valueOf(initPopInp);
				population=GA.generatePopulation(initPop);	
				if(checkbox.isSelected())initialPopulationMatrix=GA.stringifyPopulation(population);
				else initialPopulationMatrix=GA.stringifyPopulation2(population);
				
				JTable initPopulationTable= new JTable(initialPopulationMatrix,populationMatrixColNames);
					
				initPopulationTable.setFont(new Font("Roboto",Font.PLAIN,14));
				initPopulationTable.setForeground(sidePanelTextColor);
				initPopulationTable.getTableHeader().setBackground(topPanelBgColor);
				initPopulationTable.getTableHeader().setForeground(titleColor);
				initPopulationTable.getTableHeader().setFont(labelsFont);		     
				initPopulationTable.getColumnModel().getColumn(0).setPreferredWidth(600);
				initPopulationTable.getColumnModel().getColumn(1).setPreferredWidth(98);
				initPopulationTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				sp3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				initialPopulationLabel.setVisible(true);
				sp3.setVisible(true);
		        sp3.setViewportView(initPopulationTable);
		        sp3.setPreferredSize(new Dimension(700,95));
		        sp3.setBorder(BorderFactory.createLineBorder(titleColor));
		        
		    	
				solutionDescription.setVisible(false);
				solutionLabel.setVisible(true);
				sp4.setVisible(false);
				finalPopulationLabel.setVisible(false);
		        
		        tablesContainer.add(initialPopulationLabel);
		        tablesContainer.add(sp3);
			
		    	tablesContainer.add(solutionDescription);
				searchBtn.setBackground(topPanelBgColor);
				searchBtn.setEnabled(true);
				this.revalidate();
			}
			}
		});
		searchBtn.setFocusable(false);
		searchBtn.setPreferredSize(new Dimension(100,40));
		searchBtn.setForeground(titleColor);
		searchBtn.setEnabled(false);
		searchBtn.setBackground(Color.LIGHT_GRAY);
		searchBtn.addActionListener(e->{
			if(choice==0)
				JOptionPane.showMessageDialog(this,"Choose an algorithm","warning",JOptionPane.WARNING_MESSAGE);
			
			else{
				
				selectBtn.setBackground(Color.LIGHT_GRAY);
				selectBtn.setEnabled(false);
				
				solutionDescription.setVisible(true);
				
				
				long startTime;
				long endTime;
				if(choice ==1) {
					//GENETIC ALGORITHM
					String maxIterInput=maxIterInp.getText().trim().toString();
					
					int crossoverPoint1=point1Slider.getValue();
					int crossoverPoint2=-1;
					if(point2Slider.isEnabled()) crossoverPoint2=point2Slider.getValue();
					if (maxIterInput.isEmpty()||!isInteger(maxIterInput)||(point2Slider.isEnabled()&&crossoverPoint1>crossoverPoint2)) {
						JOptionPane.showMessageDialog(this,"Invalid Input","error",JOptionPane.ERROR_MESSAGE);
					}
					else {
						
						int maxIter=Integer.valueOf(maxIterInput);
						
						double  mutationRate=(double)mutationSlider.getValue()/100;
						//start GA
						System.out.println(crossoverPoint1+"  "+crossoverPoint2);
						startTime= System.currentTimeMillis();	    
						LinkedList<State>finalPopulation=GA.Execute(population, maxIter, crossoverPoint1,crossoverPoint2, mutationRate);
						//after n iterations
						if(checkbox.isSelected())initialPopulationMatrix=GA.stringifyPopulation(finalPopulation);
						else initialPopulationMatrix=GA.stringifyPopulation2(finalPopulation);
						
						JTable finalPopulationTable= new JTable(initialPopulationMatrix,populationMatrixColNames);
						State bestSol=GA.getBestSol(finalPopulation);
						
						finalPopulationTable.setFont(new Font("Roboto",Font.PLAIN,14));
						finalPopulationTable.setForeground(sidePanelTextColor);
						finalPopulationTable.getTableHeader().setBackground(topPanelBgColor);
						finalPopulationTable.getTableHeader().setForeground(titleColor);
						finalPopulationTable.getTableHeader().setFont(labelsFont);		     
						finalPopulationTable.getColumnModel().getColumn(0).setPreferredWidth(600);
						finalPopulationTable.getColumnModel().getColumn(1).setPreferredWidth(98);
						finalPopulationTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
						finalPopulationLabel.setVisible(true);
		
						sp4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						sp4.setVisible(true);
				        sp4.setViewportView(finalPopulationTable);
				        sp4.setPreferredSize(new Dimension(700,100));
				        sp4.setBorder(BorderFactory.createLineBorder(titleColor));
				        tablesContainer.add(finalPopulationLabel);
				        tablesContainer.add(sp4);
				    	System.out.println(bestSol+" "+ GA.fitness(bestSol));
					
				        //end GA
						endTime= System.currentTimeMillis();
						String time=String.valueOf(endTime-startTime)+"ms";
						executionTime.setText(time);
						description.setText(bestSol.toString()+"\n");
						totalValue.setText(String.valueOf(mkp.totalValue(bestSol))+" $");
						totalWeight.setText(String.valueOf(mkp.totalWeight(bestSol))+" KG");
						
					 
					}
				}
				else {
					//BSO
					
					
				}
				
				tablesContainer.add(solutionDescription);
				selectBtn.setBackground(topPanelBgColor);
				selectBtn.setEnabled(true);
				this.revalidate();
			
			}
		});
		//sidePanel.add(nbObjectsLabel);
		//sidePanel.add(objectsSlider);
		//sidePanel.add(nbSacsLabel);
		//sidePanel.add(sacsSlider);
		//sidePanel.add(minValueLabel);
		//sidePanel.add(valueInp);
		JPanel separator=new JPanel();
		separator.setPreferredSize(new Dimension(200,1));
		separator.setBackground(titleColor);
		//sidePanel.add(separator);
		
		sidePanel.add(b1);
		sidePanel.add(b2);
		ParamsPanel.setBackground(null);
		sidePanel.add(ParamsPanel);
		bottomPanel.add(selectBtn);
		bottomPanel.add(generateBtn);
		bottomPanel.add(searchBtn);
		topPanel.setLayout(new BorderLayout());
		
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(titleColor);
		topPanel.add(titleLabel);
	
		

		
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
	  JLabel crossoverLabel=new JLabel("Crossover");
	  JRadioButton b1=new JRadioButton("Single Point");
	  JRadioButton b2=new JRadioButton("Bi-point");
	  ButtonGroup group=new ButtonGroup();
	  JPanel radioBtnsPanel=new JPanel();
		JSlider point1Slider =new JSlider();
		JSlider point2Slider =new JSlider();
		JSlider mutationSlider =new JSlider();
		
		JLabel mutationRateLabel=new JLabel("Mutation Rate : 50%");
		JLabel initialPopulationLabel=new JLabel("Initial Population");
		JLabel maxIterationsLabel=new JLabel("Max Iterations");
		JTextField initPopulationInp=new JTextField();
		JTextField maxIterInp=new JTextField();
		

		public void GAParamsPanel() {
			
		  	if(ParamsPanel.getComponents().length>0) {
		  		for(Component c:ParamsPanel.getComponents())ParamsPanel.remove(c);
		  	}
		  	ParamsPanel.setBackground(null);
			ParamsPanel.setLayout(new GridLayout(11,1,0,0));
			//ParamsPanel.setBorder(BorderFactory.createLineBorder(titleColor,1,true));
			crossoverLabel.setForeground(sidePanelTextColor);
			crossoverLabel.setFont(labelsFont);
			crossoverLabel.setPreferredSize(new Dimension(180,5));
			crossoverLabel.setHorizontalAlignment(JLabel.CENTER);
			
			//Radio buttons
			
			b1.setFont(new Font("Roboto",Font.BOLD,12));
			b2.setFont(new Font("Roboto",Font.BOLD,12));
		
			b1.setBackground(null);
			b2.setBackground(null);
			ButtonGroup group=new ButtonGroup();
			

			group.add(b1);
			group.add(b2);
			b1.setSelected(true);
			point2Slider.setEnabled(false);	
			point2Slider.setValue(-1);
			
			b1.addActionListener(e->{
				point2Slider.setValue(-1);
				point2Slider.setEnabled(false);	
				revalidate();
			});
			b2.addActionListener(e->{
				point2Slider.setEnabled(true);
				point2Slider.setValue(point1Slider.getValue()+1);
	
				revalidate();
			});
			
			
			
			radioBtnsPanel.add(b1);
			radioBtnsPanel.add(b2);

			radioBtnsPanel.setBackground(null);
		
		
			
	
			
	
			point1Slider.setMaximum(nbObj);
			point1Slider.setMinimum(1);
			point1Slider.setValue(nbObj/2);
			point1Slider.setPreferredSize(new Dimension(100,40));
			point1Slider.setPaintTicks(true);
			point1Slider.setMajorTickSpacing(1);
			point1Slider.setPaintLabels(true);
			point1Slider.setBackground(null);
			point1Slider.setFont(new Font("Roboto",Font.PLAIN,10));
			
			point2Slider.setMaximum(nbObj);
			point2Slider.setMinimum(1);
			
			point2Slider.setPreferredSize(new Dimension(100,40));
			point2Slider.setPaintTicks(true);
			point2Slider.setMajorTickSpacing(1);
			point2Slider.setPaintLabels(true);
			point2Slider.setBackground(null);
			point2Slider.setFont(new Font("Roboto",Font.PLAIN,10));
			
			mutationRateLabel.setForeground(sidePanelTextColor);
			mutationRateLabel.setFont(labelsFont);
			mutationRateLabel.setPreferredSize(new Dimension(180,5));
			mutationRateLabel.setHorizontalAlignment(JLabel.CENTER);
			
			mutationSlider.setMaximum(100);
			mutationSlider.setMinimum(0);
			mutationSlider.setValue(50);
			mutationSlider.setPreferredSize(new Dimension(100,40));
			mutationSlider.setPaintTicks(true);
			mutationSlider.setMajorTickSpacing(25);
			mutationSlider.setPaintLabels(true);
			mutationSlider.setBackground(null);
			mutationSlider.setFont(new Font("Roboto",Font.PLAIN,10));
			mutationSlider.addChangeListener(e->{
				mutationRateLabel.setText("Mutation Rate : "+mutationSlider.getValue()+"%");
			});
			initialPopulationLabel.setForeground(sidePanelTextColor);
			initialPopulationLabel.setFont(labelsFont);
			initialPopulationLabel.setPreferredSize(new Dimension(180,5));
			initialPopulationLabel.setHorizontalAlignment(JLabel.CENTER);
			
			initPopulationInp.setFont(labelsFont);
			//initPopulationInp.setPreferredSize(new Dimension(100,20));
			initPopulationInp.setBorder(BorderFactory.createLoweredBevelBorder());
			initPopulationInp.setHorizontalAlignment(JTextField.CENTER);;
			initPopulationInp.setText(String.valueOf(3));
			
			maxIterationsLabel.setForeground(sidePanelTextColor);
			maxIterationsLabel.setFont(labelsFont);
			maxIterationsLabel.setPreferredSize(new Dimension(180,5));
			maxIterationsLabel.setHorizontalAlignment(JLabel.CENTER);
			
			maxIterInp.setFont(labelsFont);
			//maxIterInp.setPreferredSize(new Dimension(100,20));
			maxIterInp.setBorder(BorderFactory.createLoweredBevelBorder());
			maxIterInp.setHorizontalAlignment(JTextField.CENTER);;
			maxIterInp.setText(String.valueOf(5));
			
			checkbox.setBackground(null);
			checkbox.setFont(new Font("Roboto Black",Font.BOLD,11));
			ParamsPanel.add(initialPopulationLabel);
			ParamsPanel.add(initPopulationInp);
			ParamsPanel.add(maxIterationsLabel);
			ParamsPanel.add(maxIterInp);
			ParamsPanel.add(crossoverLabel);
			ParamsPanel.add(radioBtnsPanel);
			ParamsPanel.add(point1Slider);
			ParamsPanel.add(point2Slider);
			ParamsPanel.add(mutationRateLabel);
			ParamsPanel.add(mutationSlider);
			ParamsPanel.add(checkbox);
			
	
			this.revalidate();
	  }
	  public void BSOParamsPanel() {
		  	if(ParamsPanel.getComponents().length>0) {
		  		for(Component c:ParamsPanel.getComponents())ParamsPanel.remove(c);
		  	}
		  	ParamsPanel.setBackground(null);
			
		
	  }
	  
	
}
