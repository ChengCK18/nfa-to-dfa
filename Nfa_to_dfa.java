import java.util.*; //to use array list :D
import java.util.stream.Collectors;
import javax.swing.*;  
import java.awt.*;
import java.awt.event.*;  


public class Nfa_to_dfa{
	public static void main(String[] args){
		JFrame main_frame = new JFrame();
		
		JLabel[] frontLabel = new JLabel[4];
		for (int i = 0; i < 4; i++) {
			frontLabel[i] = new JLabel("");
			//you can add any listener for JTextField here
			frontLabel[i].setBounds(10,70+(i*30),40,30);  
			frontLabel[i].setBackground(Color.yellow);
			frontLabel[i].setBorder(BorderFactory.createLineBorder(Color.black));
			frontLabel[i].setHorizontalAlignment(SwingConstants.CENTER);  
			frontLabel[i].setOpaque(true);
			main_frame.add(frontLabel[i]);
		}
		
		
		frontLabel[0].setText("\u03A3");
		frontLabel[1].setText("V");
		frontLabel[2].setText("start");
		frontLabel[3].setText("Final");
		

		
		JTextField[] sigma_input = new JTextField[4];
		for (int i = 0; i < 4; i++) {
			sigma_input[i] = new JTextField("");
			//you can add any listener for JTextField here
			sigma_input[i].setBounds(52+(i*20),70, 20,30);  
			sigma_input[i].setHorizontalAlignment(SwingConstants.CENTER);    
			main_frame.add(sigma_input[i]);
		}
		
		JTextField[] state_input = new JTextField[4];
		for (int i = 0; i < 4; i++) {
			state_input[i] = new JTextField("");
			//you can add any listener for JTextField here
			state_input[i].setBounds(52+(i*20),100, 20,30);  
			state_input[i].setHorizontalAlignment(SwingConstants.CENTER);    
			main_frame.add(state_input[i]);
		}
		
		JTextField start_state_input = new JTextField("");
		start_state_input.setBounds(52,130, 20,30);
		start_state_input.setHorizontalAlignment(SwingConstants.CENTER);		
		main_frame.add(start_state_input);
		
		
		JTextField[] final_state_input = new JTextField[4];
		for (int i = 0; i < 4; i++) {
			final_state_input[i] = new JTextField("");
			//you can add any listener for JTextField here
			final_state_input[i].setBounds(52+(i*20),160, 20,30);  
			final_state_input[i].setHorizontalAlignment(SwingConstants.CENTER);    
			main_frame.add(final_state_input[i]);
		}
		
		JButton proceed_button = new JButton("Proceed");
		proceed_button.setBounds(150,100,100,30);
		proceed_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Alphabets
				String sigma_input_string = "";
				for(JTextField sigma_entry: sigma_input){
					if(!sigma_entry.getText().isEmpty()){					
						sigma_input_string+=sigma_entry.getText().charAt(0);
					}
				}
			
				char[] user_alphabet = new char[sigma_input_string.length()];
				for(int count=0;count<sigma_input_string.length();count++){
					user_alphabet[count]=sigma_input_string.charAt(count);
				}
				
				
				//States
				String state_input_string = "";
				for(JTextField state_entry: state_input){
					if(!state_entry.getText().isEmpty()){
						state_input_string+=state_entry.getText().charAt(0);
					}
				}
				
				
				char[] user_state = new char[state_input_string.length()];
				for(int count=0;count<state_input_string.length();count++){
					user_state[count]=state_input_string.charAt(count);
				}
				
				
				//Start State
				char user_start = ' ';
				if(!start_state_input.getText().isEmpty()){
					user_start = start_state_input.getText().charAt(0);
				}
				
				//Final States
				String final_state_input_string = "";
				for(JTextField final_state_entry: final_state_input){
					if(!final_state_entry.getText().isEmpty()){					
						final_state_input_string+=final_state_entry.getText().charAt(0);
					}
				}
			
				char[] user_final = new char[final_state_input_string.length()];
				for(int count=0;count<final_state_input_string.length();count++){
					user_final[count]=final_state_input_string.charAt(count);
				}
				
				generate_transition_table(user_alphabet,user_state,user_start,user_final,main_frame);
			
			}
			
		});
		main_frame.add(proceed_button);
		//remove all
		/*
		char[] user_alphabet = {'0','1','&'};
		char[] user_state = {'a','b','c'};
		char user_start = 'a';
		char[] user_final = {'b'};
		
		char[] user_alphabet = {'a','b','&'};
		char[] user_state = {'1','2','3'};
		char user_start = '1';
		char[] user_final = {'2'};
		String[] state_a = {"3","@","2"};
		String[] state_b = {"1","@","@"};
		String[] state_c = {"2","2,3","@"};
		
		
		
		ArrayList<String[]> user_transition = new ArrayList<String[]>();
		String[] state_a = {"b","c","@"};
		String[] state_b = {"@","@","@"};
		String[] state_c = {"a","@","b"};
		user_transition.add(state_a);
		user_transition.add(state_b);
		user_transition.add(state_c);
		
		initiate_ep_nfa_conversion(user_alphabet,user_state,user_start,user_final,user_transition,main_frame);
		*/
		//remove all ends
		
	
		//main_frame.add(entry_table);
		main_frame.setSize(800,800);
		main_frame.setLayout(null);
		main_frame.setVisible(true);
	}
	
	
	public static void generate_transition_table(char[] user_alphabet,char[] user_state,char user_start,char[] user_final,JFrame main_frame){
		//IMPORTANT,
		//@ = null
		//& = epsilon
		/*
		char[] user_alphabet = {'0','1','&'};
		char[] user_state = {'a','b','c'};
		char user_start = 'a';
		char[] user_final = {'b'};
		*/
		
		//for three states, for every letter there must be input including null
		//EXAMPLE 1 
		/*
		char[] user_alphabet = {'a','b','&'};
		char[] user_state = {'1','2','3'};
		char user_start = '1';
		char[] user_final = {'2'};
		String[] state_a = {"3","@","2"};
		String[] state_b = {"1","@","@"};
		String[] state_c = {"2","2,3","@"};
		*/
		
		/*
		//EXAMPLE 2
		char[] user_alphabet = {'0','1','&'};
		char[] user_state = {'a','b','c'};
		char user_start = 'a';
		char[] user_final = {'b'};
		String[] state_a = {"c","b","@"};
		String[] state_b = {"@","@","@"};
		String[] state_c = {"@","a","b"};
		*/
		
		/*
		//EXAMPLE 3
		char[] user_alphabet = {'0','1','&'};
		char[] user_state = {'a','b','c'};
		char user_start = 'a';
		char[] user_final = {'b'};
		String[] state_a = {"b","c","@"};
		String[] state_b = {"@","@","@"};
		String[] state_c = {"a","@","b"};
		*/
		
		JLabel[] state_label_header = new JLabel[user_alphabet.length];
		for (int i = 0; i < user_alphabet.length; i++) {
			state_label_header[i] = new JLabel(""+user_alphabet[i]);
			//you can add any listener for JTextField here
			state_label_header[i].setBounds(360+(i*50),40,50,30);  
			state_label_header[i].setBackground(Color.yellow);
			state_label_header[i].setBorder(BorderFactory.createLineBorder(Color.black));
			state_label_header[i].setHorizontalAlignment(SwingConstants.CENTER);  
			state_label_header[i].setOpaque(true);
			main_frame.add(state_label_header[i]);
		}
		
		JTextField[][] input_state_transition = new JTextField[user_state.length][user_alphabet.length];
		
		
		JLabel[] state_label = new JLabel[user_state.length];
		for (int i = 0; i < user_state.length; i++) {
			boolean user_final_stat = false;
			for(char c :user_final){
				if(user_state[i]==c){
					user_final_stat=true;
				}
			}
			
			if(user_start==user_state[i]){
				if(user_final_stat){
					state_label[i] = new JLabel("*>"+user_state[i]);
				}
				else{
					state_label[i] = new JLabel(">"+user_state[i]);
				}

			}
			else if(user_final_stat){
				state_label[i] = new JLabel("*"+user_state[i]);
			}
			else{
				state_label[i] = new JLabel(""+user_state[i]);
			}
			//you can add any listener for JTextField here
			state_label[i].setBounds(300,70+(i*30),60,30);  
			state_label[i].setBackground(Color.gray);
			state_label[i].setBorder(BorderFactory.createLineBorder(Color.black));
			state_label[i].setHorizontalAlignment(SwingConstants.CENTER);  
			state_label[i].setOpaque(true);
			main_frame.add(state_label[i]);
			for(int count=0;count<user_alphabet.length;count++){
				input_state_transition[i][count] = new JTextField("@");
				input_state_transition[i][count].setBounds(360+(count*50),70+(i*30),50,30);
				input_state_transition[i][count].setHorizontalAlignment(SwingConstants.CENTER);		
				main_frame.add(input_state_transition[i][count]);
				
			}
		}
		
		
		ArrayList<String[]> user_transition = new ArrayList<String[]>();
		JButton convert_button = new JButton("Convert");
		convert_button.setBounds(600,100,100,30);
		convert_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ArrayList<String[]> list_of_transition = new ArrayList<String[]>();
				for(int count=0;count<user_state.length;count++){
					String[] trans = new String[user_alphabet.length];
					for(int count2=0;count2<user_alphabet.length;count2++){
						trans[count2] = input_state_transition[count][count2].getText();
					}
					list_of_transition.add(trans);
				}
				for (int count=0;count<list_of_transition.size();count++){
					for(int count2=0;count2<user_alphabet.length;count2++){
						System.out.println(list_of_transition.get(count)[count2]);
						
					}
					user_transition.add(list_of_transition.get(count));
				}
				
				initiate_ep_nfa_conversion(user_alphabet,user_state,user_start,user_final,user_transition,main_frame);
			}
		});
		main_frame.add(convert_button);

		main_frame.revalidate();
		main_frame.repaint();
	}
	
	public static void initiate_ep_nfa_conversion(char[] user_alphabet,char[] user_state,char user_start,char[] user_final,ArrayList<String[]> user_transition,JFrame main_frame){
		
		
		ArrayList<State> states = new ArrayList<State>();
		
		//To create State object for each of the entered states
		for (int num=0;num<user_state.length;num++){
			states.add(new State(user_state[num],num));
					
			if(user_state[num] == user_start){
				states.get(num).set_start_state();
			}
			for (int num2=0;num2<user_final.length;num2++){
				if(user_state[num]==user_final[num2]){
					states.get(num).set_final_state();

				}
			}
		}
				
		HashMap<Character,State> map_state_char = new HashMap<Character,State>();
		for(int count=0;count<states.size();count++){
			map_state_char.put(states.get(count).get_name(),states.get(count));
			
		}
	
		
		//set the link between states
		for (int num=0;num<user_state.length;num++){
			states.get(num).set_transitions(user_alphabet,user_transition.get(num),map_state_char);
		}
		
		ArrayList<ArrayList<TransitionTo>> tempTransition = new ArrayList<ArrayList<TransitionTo>>();
		for (int num=0;num<user_state.length;num++){
			tempTransition.add(states.get(num).get_transitions());
			//states.get(num).set_transitions(user_alphabet,user_transition.get(num));
		}
		
		for(int num=0;num<tempTransition.size();num++){
			for(int num2=0;num2<tempTransition.get(num).size();num2++){
				//System.out.println(tempTransition.get(num).get(num2));
			
			}
			//System.out.println("==================");
		}
		System.out.println("oooooooo  Entered Values  oooooooo");
		for(int count=0;count<states.size();count++){
			ArrayList<TransitionTo> temp = states.get(count).get_transitions();
			System.out.println("The State: "+states.get(count).get_name());
			for(int count2=0;count2<temp.size();count2++){
				System.out.println("The Alphabet: "+ temp.get(count2).get_alphabet());
				ArrayList<State> temp_states2 = new ArrayList<State>(temp.get(count2).get_destination_state());
				if(temp_states2.size()==0){
					System.out.println("@");
				}
				for(int count3=0;count3< temp_states2.size();count3++){
					System.out.print(temp_states2.get(count3).get_name()+",");
				}
				System.out.println();
			}
			System.out.println("xxxxxxxxxxxxxxxxxx");
		}
		System.out.println("oooooooooooooooo");
		
	
		//conversion from &-NFA to NFA
		//To check if start state can reach end state with only epsilon. Make it final state as well if yes.
		for(int count=0;count<states.size();count++){
			if(states.get(count).is_start_state()){
				ArrayList<TransitionTo> temp  = new ArrayList<TransitionTo>(states.get(count).get_transitions());
				ArrayList<State> temp_trans_states =  new ArrayList<State>();
				temp_trans_states = temp.get(temp.size()-1).get_destination_state();//get all the epsilon states
				for(int count2=0;count2<temp_trans_states.size();count2++){
					ArrayList<State> destination_states_from_start = temp_trans_states.get(count2).traverse(temp_trans_states.get(count2));
					for(State the_state:destination_states_from_start){
						if(the_state.is_final_state()){
							states.get(count).set_final_state();					
							count2=temp_trans_states.size(); //to break out of loop
						}
					}
				}
			}
		}
		
		for (int num=0;num<user_state.length;num++){
			states.get(num).set_nfa_transitions(user_alphabet,map_state_char);
		}
		
		
		System.out.println("\n\noooooooo  &-NFA to NFA  oooooooo");
		for(int count=0;count<states.size();count++){
			System.out.println("The State: "+states.get(count).get_name());
			ArrayList<TransitionTo> temp = states.get(count).get_nfa_transitions();
			for(int count2=0;count2<temp.size();count2++){
				System.out.println("The Alphabet: "+ temp.get(count2).get_alphabet());
				ArrayList<State> temp_states2 = new ArrayList<State>(temp.get(count2).get_destination_state());
				if(temp_states2.size()==0){
					System.out.println("@");
				}
				for(int count3=0;count3< temp_states2.size();count3++){
					System.out.print(temp_states2.get(count3).get_name()+",");
				}
				System.out.println();
			}
			System.out.println("xxxxxxxxxxxxxxxxxx");
		}
		System.out.println("oooooooooooooooo");
		
		
	
		//conversion from NFA to DFA
		LinkedHashMap<String,Character> reduced_name = new LinkedHashMap<String,Character>();
		
		ArrayList<State> dfa_states = new ArrayList<State>();
		dfa_states.add(new State("@"));
		reduced_name.put(dfa_states.get(0).get_dfa_name(),'Z');
		char new_name = 'A'; //Starting alphabet for big letter
		
		
		int counter0=1;
		for(int count=0;count<states.size();count++){
			
			states.get(count).addDfaName();
			reduced_name.put(states.get(count).get_dfa_name(),new_name);
			new_name = (char)(new_name+1);
			dfa_states.add(states.get(count));
			dfa_states.get(counter0).set_dfa_transition(states.get(count).get_nfa_transitions());
			
			if(states.get(count).is_start_state()){
			
				dfa_states.get(counter0).set_start_state();
			}
			if(states.get(count).is_final_state()){
			
				dfa_states.get(counter0).set_final_state();
			}
			
			
			counter0++;
			
		}
		int counter = states.size()+1; //plus one because of new null state
		
		for(int count=0;count<states.size();count++){
			for(int count2=count+1;count2<states.size();count2++){
				String dfa_state_name = states.get(count).get_name() + "," +states.get(count2).get_name();
				
			
				ArrayList<TransitionTo> transition1 = new ArrayList<TransitionTo>();
				transition1= states.get(count).get_nfa_transitions();
				ArrayList<TransitionTo> transition2 = new ArrayList<TransitionTo>();
				transition2 = states.get(count2).get_nfa_transitions();
			
				dfa_states.add(new State(dfa_state_name));
				
				if(states.get(count).is_final_state()||states.get(count2).is_final_state()){
					//System.out.println("hey guysss "+states.get(count).get_name()+" "+ states.get(count).is_final_state());
					//System.out.println("hey gaiz "+states.get(count2).get_name()+" "+states.get(count2).is_final_state());
					dfa_states.get(counter).set_final_state();
				}
				
				for(int count3=0;count3<user_alphabet.length-1;count3++){ //-1 because epsilon not included
					ArrayList<State> transition1_states = transition1.get(count3).get_destination_state();
					ArrayList<State> transition2_states = transition2.get(count3).get_destination_state();
					ArrayList<State> combined_states = new ArrayList<State>();
					combined_states.addAll(transition1_states);
					for(State theState:transition2_states){
						if(!combined_states.contains(theState)){
							combined_states.add(theState);
						}
					}
					
					dfa_states.get(counter).set_dfa_transition(user_alphabet[count3],combined_states);
					
					
					
				}
				reduced_name.put(dfa_states.get(counter).get_dfa_name(),new_name);
				new_name = (char)(new_name+1);
				counter++;
			}
		}
		
		
		String dfa_state_name="";
	
		for(int count=0;count<user_state.length;count++){
			dfa_state_name+="," +states.get(count).get_name();
		}
		dfa_state_name=dfa_state_name.substring(1);
		dfa_states.add(new State(dfa_state_name));
		reduced_name.put(dfa_states.get(counter).get_dfa_name(),new_name);
		new_name = (char)(new_name+1);
		for(int count=0;count<user_alphabet.length-1;count++){
			ArrayList<State> combined_states = new ArrayList<State>();
			for(int count2=0;count2<user_state.length;count2++){
				TransitionTo transition_states = states.get(count2).get_nfa_transitions(count);
				ArrayList<State> last_state_transition_states = transition_states.get_destination_state();
				for(State theState:last_state_transition_states){
						if(!combined_states.contains(theState)){
							combined_states.add(theState);
						}
				}
			}
			dfa_states.get(counter).set_final_state();
			dfa_states.get(counter).set_dfa_transition(user_alphabet[count],combined_states);
			
		}
		

		JLabel[] dfa_table_header = new JLabel[user_alphabet.length-1];
		for(int count=0 ;count<user_alphabet.length-1;count++){
			dfa_table_header[count] = new JLabel(""+user_alphabet[count]);
			//you can add any listener for JTextField here
			dfa_table_header[count].setBounds(150+(count*50),300,50,30);  
			dfa_table_header[count].setBackground(Color.yellow);
			dfa_table_header[count].setBorder(BorderFactory.createLineBorder(Color.black));
			dfa_table_header[count].setHorizontalAlignment(SwingConstants.CENTER);  
			dfa_table_header[count].setOpaque(true);
			main_frame.add(dfa_table_header[count]);
		}
		
		main_frame.revalidate();
		main_frame.repaint();
		JLabel dfa_table_title = new JLabel("DFA");
		dfa_table_title.setBounds(150,270,50,30);  		
		dfa_table_title.setHorizontalAlignment(SwingConstants.CENTER);  
		dfa_table_title.setOpaque(true);
		main_frame.add(dfa_table_title);
		
		JLabel[][] dfa_table_content = new JLabel[dfa_states.size()][user_alphabet.length-1];
		JLabel[][] dfa_table_row_header = new JLabel[dfa_states.size()][2];
		
		System.out.println("\n\noooooooo  NFA to DFA  oooooooo");
		for(int count=0;count<dfa_states.size();count++){
			System.out.println("Big Letter: "+reduced_name.get(dfa_states.get(count).get_dfa_name()));
			System.out.println("The State: "+dfa_states.get(count).get_dfa_name());
			
			ArrayList<TransitionTo> temp = dfa_states.get(count).get_dfa_transitions();
			if(temp.size()==0){
				
				dfa_table_row_header[count][0] =  new JLabel("buzz"+reduced_name.get(dfa_states.get(count).get_dfa_name()));
				dfa_table_row_header[count][0].setBounds(50,330+(count*30),50,30);  
				dfa_table_row_header[count][0].setBorder(BorderFactory.createLineBorder(Color.black));			
				dfa_table_row_header[count][0].setHorizontalAlignment(SwingConstants.CENTER);  
				dfa_table_row_header[count][0].setOpaque(true);
				main_frame.add(dfa_table_row_header[count][0]);
				
				dfa_table_row_header[count][1] =  new JLabel("buzz"+dfa_states.get(count).get_dfa_name());
				dfa_table_row_header[count][1].setBounds(100,330+(count*30),50,30);  
				dfa_table_row_header[count][1].setBackground(Color.gray);
				dfa_table_row_header[count][1].setBorder(BorderFactory.createLineBorder(Color.black));
				dfa_table_row_header[count][1].setHorizontalAlignment(SwingConstants.CENTER);  
				dfa_table_row_header[count][1].setOpaque(true);
				main_frame.add(dfa_table_row_header[count][1]);
				
				//Epsilon transition
				for(int count2=0;count2<user_alphabet.length-1;count2++){
					dfa_table_content[count][count2] = new JLabel("@");
					dfa_table_content[count][count2].setBounds(150+(count2*50),330+(count*30),50,30);  
					dfa_table_content[count][count2].setBorder(BorderFactory.createLineBorder(Color.black));
					dfa_table_content[count][count2].setHorizontalAlignment(SwingConstants.CENTER);  
					dfa_table_content[count][count2].setOpaque(true);
					main_frame.add(dfa_table_content[count][count2]);
				}
				
	
				
			}
			for(int count2=0;count2<temp.size();count2++){
				System.out.println("The Alphabet: "+ temp.get(count2).get_alphabet());
				ArrayList<State> temp_states2 = new ArrayList<State>(temp.get(count2).get_destination_state());
				String temp_trans_string = "";
				if(temp_states2.size()==0){
					System.out.print("@");
					temp_trans_string+="@";
				}
				
				for(int count3=0;count3< temp_states2.size();count3++){
					System.out.print(temp_states2.get(count3).get_dfa_name()+',');
					if(count3!=0){
						temp_trans_string+=","+temp_states2.get(count3).get_dfa_name();
					}else{
						temp_trans_string+=temp_states2.get(count3).get_dfa_name();
					}
					
				}
				if(dfa_states.get(count).is_start_state()){
					if(dfa_states.get(count).is_final_state()){
						dfa_table_row_header[count][1] =  new JLabel("*>"+dfa_states.get(count).get_dfa_name());
					}
					else{
						dfa_table_row_header[count][1] =  new JLabel(">"+dfa_states.get(count).get_dfa_name());
					}
				}
				else if(dfa_states.get(count).is_final_state()){
					dfa_table_row_header[count][1] =  new JLabel("*"+dfa_states.get(count).get_dfa_name());
				}
				else{
					dfa_table_row_header[count][1] =  new JLabel(""+dfa_states.get(count).get_dfa_name());
				}
				dfa_table_row_header[count][0] =  new JLabel(""+reduced_name.get(dfa_states.get(count).get_dfa_name()));	
				dfa_table_row_header[count][0].setBounds(50,330+(count*30),50,30);  
				dfa_table_row_header[count][0].setBorder(BorderFactory.createLineBorder(Color.black));			
				dfa_table_row_header[count][0].setHorizontalAlignment(SwingConstants.CENTER);  
				dfa_table_row_header[count][0].setOpaque(true);
				main_frame.add(dfa_table_row_header[count][0]);
				
				
				dfa_table_row_header[count][1].setBounds(100,330+(count*30),50,30);  
				dfa_table_row_header[count][1].setBackground(Color.gray);
				dfa_table_row_header[count][1].setBorder(BorderFactory.createLineBorder(Color.black));
				dfa_table_row_header[count][1].setHorizontalAlignment(SwingConstants.CENTER);  
				dfa_table_row_header[count][1].setOpaque(true);
				main_frame.add(dfa_table_row_header[count][1]);
				

				
				dfa_table_content[count][count2] =  new JLabel(temp_trans_string);				
				dfa_table_content[count][count2].setBounds(150+(count2*50),330+(count*30),50,30);  
				dfa_table_content[count][count2].setBorder(BorderFactory.createLineBorder(Color.black));
				dfa_table_content[count][count2].setHorizontalAlignment(SwingConstants.CENTER);  
				dfa_table_content[count][count2].setOpaque(true);
				main_frame.add(dfa_table_content[count][count2]);
				
				System.out.println();
				
			}
			
			System.out.println(dfa_states.get(count).get_dfa_name()+" "+dfa_states.get(count).is_start_state()+ "  "+dfa_states.get(count).is_final_state());
			System.out.println("xxxxxxxxxxxxxxxxxx");
		}
		
		
		
		/*System.out.println("oooooooooooooooo");
		for(int count=0;count<dfa_states.size();count++){
			System.out.println("+++++++++++++++++++++++++++++++++");
			System.out.println("State: "+dfa_states.get(count).get_dfa_name());
			System.out.println(dfa_states.get(count).get_dfa_transitions());
			System.out.println("=+++++++++++++++++++++++++++++++++\n\n\n");
		}*/

		
		reduced_name.entrySet().forEach(entry->{
			//System.out.println(entry.getKey() + " > " + entry.getValue());  
		});
		

		
		//Converting DFA's state and transition with BIG letters
		//System.out.println("===========================");
		LinkedHashMap<String,State> assoc_string_state = new LinkedHashMap<String,State>();
		ArrayList<State> dfa_big_states = new ArrayList<State>();
		
		for(int count=0;count<dfa_states.size();count++){
			//System.out.println(reduced_name.get(dfa_states.get(count).get_dfa_name()));
			dfa_big_states.add(new State(reduced_name.get(dfa_states.get(count).get_dfa_name())));
			
			if(dfa_states.get(count).is_start_state()){
				dfa_big_states.get(count).set_start_state();
			}
			if(dfa_states.get(count).is_final_state()){
				dfa_big_states.get(count).set_final_state();
			}
			
			assoc_string_state.put(dfa_states.get(count).get_dfa_name(),dfa_big_states.get(count));
		}
		
	
		assoc_string_state.entrySet().forEach(entry->{
			//System.out.println(entry.getKey() + " > " + entry.getValue().get_big_dfa_name());  
		});
		

		//To add the transitions for the new Big letter state
		for(int count=0;count<dfa_states.size();count++){
			//System.out.println(reduced_name.get(dfa_states.get(count).get_dfa_name()));
			ArrayList<TransitionTo> previous_dfa_transitions = new ArrayList<TransitionTo>(dfa_states.get(count).get_dfa_transitions());
	
			for(int count2=0;count2<previous_dfa_transitions.size();count2++){

				ArrayList<State> old_states = new ArrayList<State>(previous_dfa_transitions.get(count2).get_destination_state());
				//Collections.sort(old_states);
				
				Collections.sort(old_states, new Comparator<State>() {
				@Override
				public int compare(State o1, State o2) {
					return o1.toString().compareTo(o2.toString());
				}
				});
				
				
				String old_states_combined_nfa_name = old_states.stream().map(Object::toString)
																.collect(Collectors.joining(","));
				
				if(old_states_combined_nfa_name.length()!=0){
					System.out.println(old_states_combined_nfa_name);
					ArrayList<State> temp_state = new ArrayList<State>();
					temp_state.add(assoc_string_state.get(old_states_combined_nfa_name));
				
					//System.out.println(assoc_string_state.get(old_states_combined_nfa_name)+" ur map");
					dfa_big_states.get(count).set_big_dfa_transition(user_alphabet[count2],temp_state);
				}
				else{
					ArrayList<State> temp_state =new ArrayList<State>();
					temp_state.add(assoc_string_state.get("@"));				
					//System.out.println(assoc_string_state.get("@")+" ur map");
					dfa_big_states.get(count).set_big_dfa_transition(user_alphabet[count2],temp_state);
				}
				
			
			}
		}
		
		
		//System.out.println(dfa_big_states.size());
		for(int count=0;count<dfa_big_states.size();count++){
			ArrayList<TransitionTo> temp = new ArrayList<TransitionTo> (dfa_big_states.get(count).get_big_dfa_transitions());
			
			//System.out.println("Temp here:" +temp.size());
			for(int count2=0;count2<temp.size();count2++){
				ArrayList<State> tempy = temp.get(count2).get_destination_state();
				
				for(int count3=0;count3<tempy.size();count3++){
					System.out.println(tempy.get(count3).get_big_dfa_name()+" aezz");
				}
				
			}
			
		}
		
		
		
		
		
		JLabel dfa_big_table_title = new JLabel("DFA(Transformed)");
		dfa_big_table_title.setBounds(480,270,150,30);  		
		dfa_big_table_title.setHorizontalAlignment(SwingConstants.CENTER);  
		dfa_big_table_title.setOpaque(true);
		main_frame.add(dfa_big_table_title);
		
		JLabel[][] dfa_big_table_content = new JLabel[dfa_big_states.size()][user_alphabet.length-1];
		JLabel[] dfa_big_table_row_header = new JLabel[dfa_big_states.size()];
		JLabel[] dfa_big_table_content_header = new JLabel[user_alphabet.length-1];
		for(int count=0 ;count<user_alphabet.length-1;count++){
			dfa_big_table_content_header[count] = new JLabel(""+user_alphabet[count]);
			//you can add any listener for JTextField here
			dfa_big_table_content_header[count].setBounds(500+(count*50),300,50,30);  
			dfa_big_table_content_header[count].setBackground(Color.yellow);
			dfa_big_table_content_header[count].setBorder(BorderFactory.createLineBorder(Color.black));
			dfa_big_table_content_header[count].setHorizontalAlignment(SwingConstants.CENTER);  
			dfa_big_table_content_header[count].setOpaque(true);
			main_frame.add(dfa_big_table_content_header[count]);
		}
		
		
		//Access big dfa state here
		System.out.println("\n\noooooooo  DFA to DFA(transformed)  oooooooo");
		
		for(int count=0;count<dfa_big_states.size();count++){
			if(dfa_big_states.get(count).is_start_state()){
				if(dfa_big_states.get(count).is_final_state()){
					dfa_big_table_row_header[count] = new JLabel("*>"+dfa_big_states.get(count).get_big_dfa_name());
				}
				else{
					dfa_big_table_row_header[count] = new JLabel(">"+dfa_big_states.get(count).get_big_dfa_name());
				}
			}
			else if(dfa_big_states.get(count).is_final_state()){
				dfa_big_table_row_header[count] = new JLabel("*"+dfa_big_states.get(count).get_big_dfa_name());
			}
			else{
				dfa_big_table_row_header[count] = new JLabel(""+dfa_big_states.get(count).get_big_dfa_name());
			}
				
			
			dfa_big_table_row_header[count].setBounds(450,330+(count*30),50,30);  
			dfa_big_table_row_header[count].setBackground(Color.gray);
			dfa_big_table_row_header[count].setBorder(BorderFactory.createLineBorder(Color.black));
			dfa_big_table_row_header[count].setHorizontalAlignment(SwingConstants.CENTER);  
			dfa_big_table_row_header[count].setOpaque(true);
			main_frame.add(dfa_big_table_row_header[count]);
			
			
			System.out.println("The State: "+dfa_big_states.get(count).get_big_dfa_name());
			ArrayList<TransitionTo> temp = dfa_big_states.get(count).get_big_dfa_transitions();
			
			
			if(temp.size()==0){
				for(int count2=0;count2<user_alphabet.length-1;count2++){
					dfa_big_table_content[count][count2] =  new JLabel(""+dfa_big_states.get(count).get_big_dfa_name());				
					dfa_big_table_content[count][count2].setBounds(500+(count2*50),330+(count*30),50,30);  
					dfa_big_table_content[count][count2].setBorder(BorderFactory.createLineBorder(Color.black));
					dfa_big_table_content[count][count2].setHorizontalAlignment(SwingConstants.CENTER);  
					dfa_big_table_content[count][count2].setOpaque(true);
					main_frame.add(dfa_big_table_content[count][count2]);
				}
				
			}
			for(int count2=0;count2<temp.size();count2++){
				System.out.println("The Alphabet: "+ temp.get(count2).get_alphabet());
				String temp_trans_string ="";
				ArrayList<State> temp_states2 = new ArrayList<State>(temp.get(count2).get_destination_state());
				for(int count3=0;count3< temp_states2.size();count3++){	
					//System.out.println(temp_states2.get(count3).get_big_dfa_name());
					temp_trans_string+=temp_states2.get(count3).get_big_dfa_name();
				}
				dfa_big_table_content[count][count2] =  new JLabel(temp_trans_string);				
				dfa_big_table_content[count][count2].setBounds(500+(count2*50),330+(count*30),50,30);  
				dfa_big_table_content[count][count2].setBorder(BorderFactory.createLineBorder(Color.black));
				dfa_big_table_content[count][count2].setHorizontalAlignment(SwingConstants.CENTER);  
				dfa_big_table_content[count][count2].setOpaque(true);
				main_frame.add(dfa_big_table_content[count][count2]);
			}
		
			System.out.println("xxxxxxxxxxxxxxxxxx");
		}
		System.out.println("oooooooooooooooo");
		
		
		
		
		
		//Removal of unreachable states
		for(int count=0;count<dfa_big_states.size();count++){
			System.out.println(dfa_big_states.get(count).get_big_dfa_name());
		}
		
		System.out.println("=========================\n");
		while(true){
			boolean removal=false;
			ArrayList<State> all_states_transition = new ArrayList<State>();
			for(int count=1;count<dfa_big_states.size();count++){ // states
				ArrayList<TransitionTo> temp = dfa_big_states.get(count).get_big_dfa_transitions();
				for(int count2=0;count2<temp.size();count2++){ //transition
					ArrayList<State> temp_states2 = new ArrayList<State>(temp.get(count2).get_destination_state());
					for(int count3=0;count3< temp_states2.size();count3++){
						if(!all_states_transition.contains(temp_states2.get(count3))){
							all_states_transition.add(temp_states2.get(count3));
						}		
					}
				}
			}
			for(int count=1;count<dfa_big_states.size();count++){
				if(!all_states_transition.contains(dfa_big_states.get(count))&&!dfa_big_states.get(count).is_start_state()){
					dfa_big_states.remove(count);
					removal = true;
				}
			} 
			
			if(!removal){//no states had been removed, don't have to check again
				break;
			}
		}
		
		
		// Access DFA without reachable states here
		System.out.println("=====================================");
		System.out.println("DFA Without Unreachable States");
		
		for(int count=0;count<user_alphabet.length-1;count++){ //To add transition for Z state which is null value
			ArrayList<State> temp_state_epsilon = new ArrayList<State>();
			temp_state_epsilon.add(dfa_big_states.get(0));
			dfa_big_states.get(0).set_big_dfa_transition(user_alphabet[count],temp_state_epsilon);
		}
		
	
		for(int count=0;count<dfa_big_states.size();count++){

			ArrayList<TransitionTo> temp = new ArrayList<TransitionTo>(dfa_big_states.get(count).get_big_dfa_transitions());		
			System.out.println("State buzz: "+dfa_big_states.get(count).get_big_dfa_name());
			for(int count2=0;count2<temp.size();count2++){
				ArrayList<State> temp_trans_states = new ArrayList<State>(temp.get(count2).get_destination_state());
				System.out.println("The Alphabet: "+temp.get(count2).get_alphabet());
				for(State the_state:temp_trans_states){
					System.out.print(the_state.get_big_dfa_name()+", ");
				}
				System.out.println();
			}
		}
		
		
		
		
		
		//Further minimization(Grouping)
		System.out.println("=================Before Grouping=================");
		for(State the_state:dfa_big_states){
			System.out.println("State: "+the_state.get_big_dfa_name()+" Group:"+the_state.get_minimization_group()+" Final?"+the_state.is_final_state());
		}
		
		System.out.println("=================Before Grouping ENDS=================");
		
		ArrayList<ArrayList<State>> non_final_group_of_states =new ArrayList<ArrayList<State>>();
		ArrayList<ArrayList<State>> final_group_of_states =new ArrayList<ArrayList<State>>();
		ArrayList<State>final_group_of_states_temp = new ArrayList<State>();
		ArrayList<State>non_final_group_of_states_temp = new ArrayList<State>();
		String group_index_final = "G0";
		String group_index_non_final = "G1";
		
		for(int count=0;count<dfa_big_states.size();count++){
			if(dfa_big_states.get(count).is_final_state()){
				final_group_of_states_temp.add(dfa_big_states.get(count));
				dfa_big_states.get(count).set_minimization_group(group_index_final);
			}
			else{
				non_final_group_of_states_temp.add(dfa_big_states.get(count));
				dfa_big_states.get(count).set_minimization_group(group_index_non_final);
			}
		}
		non_final_group_of_states.add(non_final_group_of_states_temp);
		final_group_of_states.add(final_group_of_states_temp);
		boolean non_final_group_stability = false;
		boolean final_group_stability = false;
		int times = 0;
		int group_index=3;
		while(true){

			//final states grouping
			ArrayList<Integer[]> final_group_to_remove= new ArrayList<Integer[]>();
			int f_initial_size = final_group_of_states.size();			
			for(int count=0;count<f_initial_size;count++){
				ArrayList<State> group_states = new ArrayList<State>(final_group_of_states.get(count));//get states from this group
				final_group_stability=true;
				if(group_states.size()>1){ //not atomic seperation yet
					State first_group_state = final_group_of_states.get(count).get(0);
					ArrayList<TransitionTo> first_group_state_trans = first_group_state.get_big_dfa_transitions();
					
					for(int count2=0;count2<group_states.size();count2++){ //traverse through states in this group
						ArrayList<TransitionTo> compared_state_trans = group_states.get(count2).get_big_dfa_transitions();
						boolean similar_behaviour=true;
						for(int count3=0;count3<compared_state_trans.size();count3++){ //check for each alphabet's state transition
							ArrayList<State> compared_state_states = new ArrayList<State>(compared_state_trans.get(count3).get_destination_state());
							ArrayList<State> first_group_state_states = new ArrayList<State>(first_group_state_trans.get(count3).get_destination_state());
							System.out.println(compared_state_states.get(0).get_big_dfa_name()+" Compare with "+first_group_state_states.get(0).get_big_dfa_name());
							System.out.println(compared_state_states.get(0).get_minimization_group()+" Compare with "+first_group_state_states.get(0).get_minimization_group());
							
							if(compared_state_states.get(0).get_minimization_group()!=first_group_state_states.get(0).get_minimization_group())//There is only one state for each transition, hence 0 straight
							{
								similar_behaviour=false;  
							}
							
						}
						
						if(!similar_behaviour){
							System.out.println("Size is =>"+final_group_of_states.get(count).size() +" and "+"Count="+count+" "+count2+" Group size= "+group_states.size());
							System.out.println("\nNot same bro: "+first_group_state.get_big_dfa_name()+"   "+group_states.get(count2).get_big_dfa_name());
							System.out.println("Odd ones out =>"+ final_group_of_states.get(count).get(count2).get_big_dfa_name());
							ArrayList<State> odd_ones = new ArrayList<State>();
							final_group_of_states.get(count).get(count2).set_minimization_group("G"+group_index);
							group_index++;
							odd_ones.add(final_group_of_states.get(count).get(count2));
							final_group_of_states.add(new ArrayList<State>(odd_ones));			
							final_group_to_remove.add(new Integer[]{count,count2});
							
							final_group_stability=false;
							
						}else{
							System.out.println("\nSame bro: "+first_group_state.get_big_dfa_name()+"   "+group_states.get(count2).get_big_dfa_name());
						}
					}
					for(int count2=final_group_to_remove.size()-1;count2>=0;count2--){
						System.out.println("B4 Removal: "+final_group_of_states.get(final_group_to_remove.get(count2)[0]).size());
						System.out.println(final_group_of_states.get(final_group_to_remove.get(count2)[0]).remove(final_group_of_states.get(final_group_to_remove.get(count2)[0]).get(final_group_to_remove.get(count2)[1])));
						//System.out.println(final_group_to_remove.get(count2)[0]).get(final_group_to_remove.get(count2)[1]);
						System.out.println("After Removal: "+final_group_of_states.get(final_group_to_remove.get(count2)[0]).size());
						System.out.println(final_group_to_remove.get(count2)[0]+" "+final_group_to_remove.get(count2)[1]);
					}
					
				}
				
			}
			
			
			System.out.println("\n\nNon Final------------------------");
			
			ArrayList<Integer[]> non_final_group_to_remove= new ArrayList<Integer[]>(); 
			int nf_initial_size = non_final_group_of_states.size();
			for(int count=0;count<nf_initial_size;count++){
				ArrayList<State> group_states = new ArrayList<State>(non_final_group_of_states.get(count));//get states from this group
				non_final_group_stability=true;
				if(group_states.size()>1){ //not atomic seperation yet
					State first_group_state = non_final_group_of_states.get(count).get(0);
					ArrayList<TransitionTo> first_group_state_trans = first_group_state.get_big_dfa_transitions();
					
					for(int count2=0;count2<group_states.size();count2++){ //traverse through states in this group
						ArrayList<TransitionTo> compared_state_trans = group_states.get(count2).get_big_dfa_transitions();
						boolean similar_behaviour=true;
						for(int count3=0;count3<compared_state_trans.size();count3++){ //check for each alphabet's state transition
							ArrayList<State> compared_state_states = new ArrayList<State>(compared_state_trans.get(count3).get_destination_state());
							ArrayList<State> first_group_state_states = new ArrayList<State>(first_group_state_trans.get(count3).get_destination_state());
							System.out.println(compared_state_states.get(0).get_big_dfa_name()+" Compare with "+first_group_state_states.get(0).get_big_dfa_name());
							if(compared_state_states.get(0).get_minimization_group()!=first_group_state_states.get(0).get_minimization_group())//There is only one state for each transition, hence 0 straight
							{
								similar_behaviour=false;  
							}
							
						}
						
						if(!similar_behaviour){
							System.out.println("Not same bro: "+first_group_state.get_big_dfa_name()+"   "+group_states.get(count2).get_big_dfa_name());
							System.out.println("Odd ones out =>"+ non_final_group_of_states.get(count).get(count2).get_big_dfa_name());
							ArrayList<State> odd_ones = new ArrayList<State>();
							non_final_group_of_states.get(count).get(count2).set_minimization_group("G"+group_index);
							group_index++;
							odd_ones.add(non_final_group_of_states.get(count).get(count2));
							non_final_group_to_remove.add(new Integer[]{count,count2});
							non_final_group_of_states.add(new ArrayList<State>(odd_ones));
							non_final_group_stability=false;
							
							//ArrayList<State> split_state = new ArrayList<State>();
							
						}else{
							System.out.println("Same bro: "+first_group_state.get_big_dfa_name()+"   "+group_states.get(count2).get_big_dfa_name());
						}
						
					}
					for(int count2=non_final_group_to_remove.size()-1;count2>=0;count2--){
						System.out.println("B4 Removal: "+non_final_group_of_states.get(non_final_group_to_remove.get(count2)[0]).size());
						System.out.println(non_final_group_of_states.get(non_final_group_to_remove.get(count2)[0]).remove(non_final_group_of_states.get(non_final_group_to_remove.get(count2)[0]).get(non_final_group_to_remove.get(count2)[1])));
						//System.out.println(non_final_group_to_remove.get(count2)[0]).get(non_final_group_to_remove.get(count2)[1]);
						System.out.println("After Removal: "+non_final_group_of_states.get(non_final_group_to_remove.get(count2)[0]).size());
						System.out.println(non_final_group_to_remove.get(count2)[0]+" "+non_final_group_to_remove.get(count2)[1]);
					}
				}
				
			}//end of non-final for loop
			times++;
			System.out.println("STABILITY F:"+final_group_stability+" NF:"+non_final_group_stability+"  "+times);
			if(final_group_stability&&non_final_group_stability){
				System.out.println(">>>>>>>>>>>>>>All Groups are stable<<<<<<<<<<<<<<");
				break;
			}

		}
		
		System.out.println("[[[[[[[[[[[[[[ FINAL ]]]]]]]]]]]]]]");
		for(int count=0;count<final_group_of_states.size();count++){
			//System.out.println("Group:"+count+" Num of states:"+final_group_of_states.get(count).size());
			if(final_group_of_states.get(count).size()>1){
				for(int count2=0;count2<final_group_of_states.get(count).size();count2++){
					if(count2!=0){
						dfa_big_states.remove(final_group_of_states.get(count).get(count2));//removal of repetative state
					}
					char new_big_dfa_name = final_group_of_states.get(count).get(count2).get_minimization_group().charAt(1);
					final_group_of_states.get(count).get(count2).set_big_dfa_name(new_big_dfa_name);
						
					//System.out.print(final_group_of_states.get(count).get(count2).get_big_dfa_name()+", ");
				}
			}
			System.out.println();
		}
		
		System.out.println("[[[[[[[[[[[[[[ NON-FINAL ]]]]]]]]]]]]]]");
		for(int count=0;count<non_final_group_of_states.size();count++){
			//System.out.println("Group:"+count+" Num of states:"+non_final_group_of_states.get(count).size());
			if(non_final_group_of_states.get(count).size()>1){
				for(int count2=0;count2<non_final_group_of_states.get(count).size();count2++){
					if(count2!=0){
						dfa_big_states.remove(non_final_group_of_states.get(count).get(count2));//removal of repetative state
					}
					char new_big_dfa_name = non_final_group_of_states.get(count).get(count2).get_minimization_group().charAt(1);
					non_final_group_of_states.get(count).get(count2).set_big_dfa_name(new_big_dfa_name);
					//System.out.print(non_final_group_of_states.get(count).get(count2).get_big_dfa_name()+", ");
				}
			}
			System.out.println();
		}
		
		
		
		System.out.println("=================After Grouping=================");
		for(State the_state:dfa_big_states){
			System.out.println("State: "+the_state.get_big_dfa_name()+" Group:"+the_state.get_minimization_group());
		}
		System.out.println("=================After Grouping ENDS=================");
		
		
		
		
		
		
		JLabel[][] dfa_minimized_table_content = new JLabel[dfa_big_states.size()][user_alphabet.length-1];
		JLabel[] dfa_minimized_table_row_header = new JLabel[dfa_big_states.size()];
		JLabel[] dfa_minimized_table_content_header = new JLabel[user_alphabet.length-1];
		JLabel dfa_minimized_table_title = new JLabel("Minimized DFA");
		dfa_minimized_table_title.setBounds(800,270,150,30);  		
		dfa_minimized_table_title.setHorizontalAlignment(SwingConstants.CENTER);  
		dfa_minimized_table_title.setOpaque(true);
		main_frame.add(dfa_minimized_table_title);
		
		
		
		for(int count=0 ;count<user_alphabet.length-1;count++){
			dfa_minimized_table_content_header[count] = new JLabel(""+user_alphabet[count]);
			
			//you can add any listener for JTextField here
			dfa_minimized_table_content_header[count].setBounds(850+(count*50),300,50,30);  
			dfa_minimized_table_content_header[count].setBackground(Color.yellow);
			dfa_minimized_table_content_header[count].setBorder(BorderFactory.createLineBorder(Color.black));
			dfa_minimized_table_content_header[count].setHorizontalAlignment(SwingConstants.CENTER);  
			dfa_minimized_table_content_header[count].setOpaque(true);
			main_frame.add(dfa_minimized_table_content_header[count]);
		}
		
		
		
		for(int count=0;count<dfa_big_states.size();count++){
			ArrayList<TransitionTo> temp_trans = dfa_big_states.get(count).get_big_dfa_transitions();
			if(dfa_big_states.get(count).is_start_state()){
				if(dfa_big_states.get(count).is_final_state()){
					dfa_minimized_table_row_header[count] = new JLabel("*>"+dfa_big_states.get(count).get_big_dfa_name());
				}
				else{
					dfa_minimized_table_row_header[count] = new JLabel(">"+dfa_big_states.get(count).get_big_dfa_name());
				}
			}
			else if(dfa_big_states.get(count).is_final_state()){
				dfa_minimized_table_row_header[count] = new JLabel("*"+dfa_big_states.get(count).get_big_dfa_name());
			}
			else{
				dfa_minimized_table_row_header[count] = new JLabel(""+dfa_big_states.get(count).get_big_dfa_name());
			}
		
			dfa_minimized_table_row_header[count].setBounds(800,330+(count*30),50,30);  
			dfa_minimized_table_row_header[count].setBackground(Color.gray);
			dfa_minimized_table_row_header[count].setBorder(BorderFactory.createLineBorder(Color.black));
			dfa_minimized_table_row_header[count].setHorizontalAlignment(SwingConstants.CENTER);  
			dfa_minimized_table_row_header[count].setOpaque(true);
			main_frame.add(dfa_minimized_table_row_header[count]);
			
			
			System.out.println("The State: "+dfa_big_states.get(count).get_big_dfa_name());
			for(int count2=0;count2<temp_trans.size();count2++){
				System.out.print(temp_trans.get(count2).get_alphabet()+"  ");
				String temp_trans_string = "";
				ArrayList<State> temp_state = new ArrayList<>(temp_trans.get(count2).get_destination_state());
				for(State the_state2:temp_state){
					System.out.println(the_state2.get_big_dfa_name());
					temp_trans_string+=the_state2.get_big_dfa_name();
					
				}
				dfa_minimized_table_content[count][count2] =  new JLabel(temp_trans_string);				
				dfa_minimized_table_content[count][count2].setBounds(850+(count2*50),330+(count*30),50,30);  
				dfa_minimized_table_content[count][count2].setBorder(BorderFactory.createLineBorder(Color.black));
				dfa_minimized_table_content[count][count2].setHorizontalAlignment(SwingConstants.CENTER);  
				dfa_minimized_table_content[count][count2].setOpaque(true);
				main_frame.add(dfa_minimized_table_content[count][count2]);
			}
			
		}
		
		
		
		
		
		
		/*
		System.out.println("=====================================");
		System.out.println("Non-Final States");
		for(int count=0;count<non_final_group_of_states.size();count++){

			for(State the_state:non_final_group_of_states.get(count)){
				System.out.print("Group: "+the_state.get_minimization_group()+" ");
				System.out.print("State: "+the_state.get_big_dfa_name());
				System.out.println();
			}
			System.out.println("------------");
			
		}
		
		System.out.println("Final States");
		for(int count=0;count<final_group_of_states.size();count++){
			for(State the_state:final_group_of_states.get(count)){
				System.out.print("Group: "+the_state.get_minimization_group()+" ");
				System.out.print("State: "+the_state.get_big_dfa_name());
				System.out.println();
			}
			System.out.println("------------");
			
		}
		*/
	}
	
	
	
	
}



class State{
	private char name;
	private String dfaName;
	private char big_dfa_name;
	private int index;
	private ArrayList<TransitionTo> transitions = new ArrayList<TransitionTo>();
	private ArrayList<TransitionTo> nfa_transitions = new ArrayList<TransitionTo>();
	private ArrayList<TransitionTo> dfa_transitions = new ArrayList<TransitionTo>();
	private ArrayList<TransitionTo> dfa_big_transitions = new ArrayList<TransitionTo>();
	private boolean start_state;
	private boolean final_state;
	
	private String minimization_group;
	
	public State(String entered_name){
		this.dfaName=entered_name;
	}
	public State(char big_dfa_name){
		this.big_dfa_name = big_dfa_name;
	}
	public State(char entered_name,int index){
		this.name=entered_name;
		this.index = index;
		start_state = false;
		final_state = false;
	}
	public void set_start_state(){
		start_state = true;
	}
	public void set_final_state(){
		final_state = true;
	}
	
	public boolean is_start_state(){
		return start_state;
	}
	public boolean is_final_state(){
		return final_state;
	}
	public char get_name(){
		return name;
	}
	
	public void set_transitions(char[] alphabet,String[] destination_state,HashMap<Character,State> map_state_char){
		
		for(int count=0;count<alphabet.length;count++){
			String[] splitted_dest_states = destination_state[count].split(",");
			transitions.add(new TransitionTo(alphabet[count],splitted_dest_states,map_state_char));
		}
		
		System.out.println();
		
	}
	
	public void set_nfa_transitions(char[] alphabet,HashMap<Character,State> map_state_char){
		
		for(int count=0;count<transitions.size()-1;count++){ //-1 not traversing to epsilon
			ArrayList<State> temp_states = transitions.get(count).get_destination_state();
			char current_alphabet = transitions.get(count).get_alphabet();
			nfa_transitions.add(new TransitionTo(alphabet[count],map_state_char));
			if(temp_states.size()!=0){
				for(int count2=0;count2<temp_states.size();count2++){
					nfa_transitions.get(count).add_destination_states(temp_states.get(count2));
				}
				
				
				//This section is to add states for each alphabet excluding the epsilon alphabet
				ArrayList<State> e_closure = new ArrayList<State>();
				for(int count2=0;count2<temp_states.size();count2++){ //Traverse for each existing state for each alphabet
					e_closure.addAll(traverse(temp_states.get(count2))); 
				}
				for(int count2=0;count2<e_closure.size();count2++){
					nfa_transitions.get(count).add_destination_states(e_closure.get(count2));
				}			
			}
			else{
			}
		}
		
		//This section is to add states for the epsilon alphabet
		ArrayList<State> temp_states = transitions.get(transitions.size()-1).get_destination_state();
		
		//nfa_transitions.add(new TransitionTo(alphabet[transitions.size()-1],map_state_char));
		if(temp_states.size()!=0){
			for(int count=0;count<alphabet.length-1;count++){
				ArrayList<State> e_closure_start_ep = new ArrayList<State>();
				for(int count2=0;count2<temp_states.size();count2++){
					e_closure_start_ep.addAll(ep_traverse(temp_states.get(count2),count));
				}
				//System.out.println("The Epsilon stuff here" + e_closure_start_ep);
				
				
				for(int count2=0;count2<e_closure_start_ep.size();count2++){
					nfa_transitions.get(count).add_destination_states(e_closure_start_ep.get(count2));
				}
			}
		
			
		}
		
		for(int count=0;count<nfa_transitions.size();count++){
			//System.out.println("===============================");
			nfa_transitions.get(count).sort_states(); // sort ascending order
			//System.out.println(nfa_transitions.get(count));
			//System.out.println("===============================");
			
		}
		//System.out.println("\n\n\n\n\n");
		
		
	}
	//traverse for non-epsilon alphabet
	public ArrayList<State> traverse(State pointer){
		TransitionTo temp_pointer_epsilon_transition = pointer.get_epsilon_transitions();
		ArrayList<State> temp_pointer_epsilon_states = new ArrayList<>(temp_pointer_epsilon_transition.get_destination_state());
		ArrayList<State> included_states = new ArrayList<State>();
			
		while(temp_pointer_epsilon_states.size()!=0){
			State temp = temp_pointer_epsilon_states.remove(0);
			if(temp == pointer){
				included_states.add(temp);
				return included_states;
			}
			else{
				included_states.addAll(traverse(temp));
		
			}
		}
		
		included_states.add(pointer);
		
		return included_states;

	}
	
	public void set_big_dfa_transition(char alphabet, ArrayList<State> state_list){
		dfa_big_transitions.add(new TransitionTo(alphabet,state_list));
	}
	public void set_dfa_transition(char alphabet, ArrayList<State> state_list){
		dfa_transitions.add(new TransitionTo(alphabet,state_list));
	}

	
	public void set_dfa_transition(ArrayList<TransitionTo> existingList){
		this.dfa_transitions = new ArrayList<TransitionTo>(existingList);
	}
	
	//traverse for epsilon alphabet
	public ArrayList<State> ep_traverse(State pointer,int alphabet_index){
		TransitionTo temp_pointer_alphabet_transition = pointer.get_transition(alphabet_index);
		TransitionTo temp_pointer_epsilon_transition = pointer.get_epsilon_transitions();
		ArrayList<State> temp_pointer_alphabet_states = new ArrayList<>(temp_pointer_alphabet_transition.get_destination_state());
		ArrayList<State> temp_pointer_epsilon_states = new ArrayList<>(temp_pointer_epsilon_transition.get_destination_state());
		ArrayList<State> included_states = new ArrayList<State>();
		
		if(temp_pointer_alphabet_states.size() == 0){
			for(int count=0;count<temp_pointer_epsilon_states.size();count++){
				ep_traverse(temp_pointer_epsilon_states.get(count),alphabet_index);
			}
		}
		else{
			for(int count=0;count<temp_pointer_alphabet_states.size();count++){
				State pointer_extend = temp_pointer_alphabet_states.remove(0);
				included_states.addAll(traverse(pointer_extend));
			}
		}
		return included_states;
	}
	
	public TransitionTo get_epsilon_transitions(){
		return transitions.get(transitions.size()-1);
	}
	public TransitionTo get_transition(int index){
		return transitions.get(index);
	}
	
	public ArrayList<TransitionTo> get_transitions(){
		return transitions;
	}
	public ArrayList<TransitionTo> get_nfa_transitions(){
		return nfa_transitions;
	}
	public TransitionTo get_nfa_transitions(int index){
		return nfa_transitions.get(index);
	}
	public ArrayList<TransitionTo> get_dfa_transitions(){
		return dfa_transitions;
	} 
	public ArrayList<TransitionTo> get_big_dfa_transitions(){
		return dfa_big_transitions;
	}
	
	public void addDfaName(){
		this.dfaName = Character.toString(name);
	}
	
	public String get_dfa_name(){
		return dfaName;
	}
	
	public char get_big_dfa_name(){
		return big_dfa_name;
	}
	public void set_big_dfa_name(char new_big_dfa_name){
		this.big_dfa_name = new_big_dfa_name;
	}
	public void set_minimization_group(String state_group){
		this.minimization_group = state_group;
	}
	
	public String get_minimization_group(){
		return minimization_group;
	}
	
	
	public String toString(){
		//return "Name:"+name +"\n start_state? "+start_state+"\n final_state? "+final_state;
		return name+"";
	}
	
	public String toDfaString(){
		return dfaName;
	}
	
	
}

class TransitionTo{
	private char alphabet;
	private ArrayList<State> destination_states=new ArrayList<State>();
	public TransitionTo(char alphabet,HashMap<Character,State> map_state_char){
		this.alphabet=alphabet;
		
	}
	public TransitionTo(char alphabet,String[] destination_state,HashMap<Character,State> map_state_char){
		this.alphabet=alphabet;
		//System.out.println(destination_state[0].charAt(0));
		
		for(int count=0;count<destination_state.length;count++){
			if(destination_state[count].charAt(0)!='@'){
				//System.out.println(map_state_char.get(destination_state[count]));
				destination_states.add(map_state_char.get(destination_state[count].charAt(0)));
			}
		}
	}
	
	public TransitionTo(char alphabet, ArrayList<State> state_list){
		this.alphabet = alphabet;
		destination_states.addAll(state_list);
	}
	public TransitionTo(ArrayList<State> add_states){
		this.destination_states = add_states;
		
	}
	
	public char get_alphabet(){
		return alphabet;
	}
	
	public void add_destination_states(State add_state){
		if(!destination_states.contains(add_state)){ //add if there is no existing state yet
			destination_states.add(add_state);
		}
	}
	
	public int get_destination_state_size(){
		return destination_states.size();
	}
	public ArrayList<State> get_destination_state(){
		return destination_states;
	}
	
	public void sort_states(){
		Collections.sort(destination_states, new Comparator <State>(){
			@Override
			public int compare(State s1,State s2){
				return s1.get_name() - s2.get_name();
			}
		});
	}
	public String toString(){
		return "alpha " +alphabet+": "+destination_states+"\n";
	}
	
}