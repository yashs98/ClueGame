package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DisproveSuggestionBox extends JDialog{
	private JComboBox<String> humanHand;
    private JPanel hand;
    private JLabel handLabel;
    private JButton submit;
    private static GameDriver driver;
    private Card disproveCard;
	private static Board board;
	public DisproveSuggestionBox() {
		setTitle("Disprove the suggestion");
		setLayout(new GridLayout(5,1));
		setSize(350, 300);
		hand = new JPanel();
		handLabel = new JLabel("Show a card to disprove");
		humanHand = new JComboBox<String>();
		board = Board.getInstance();
		driver = GameDriver.getInstance();
		submit = new JButton("Submit");
		submit.addActionListener(new SubmitCard());
		hand.add(handLabel);
		hand.add(showHumanHand());
	    disproveCard = null;
		
		add(hand);
		add(submit);
	}
	
	public JComboBox<String> showHumanHand() {
	       HumanPlayer human = (HumanPlayer) board.getHumanPlayer();
	       Vector<Card> humanCards = human.getHand();
	       
	       for(Card card: humanCards) {
	    	   humanHand.addItem(card.getCardName());
	       }
	       
	       return humanHand;
	       
	}
	
	public Card getDisproveCard() {
		while (true) {
			if(disproveCard != null) {
				return disproveCard;
			}
		}
	}

	
	private class SubmitCard implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			ComputerPlayer computer = (ComputerPlayer) driver.currentPlayer();
			HumanPlayer hPlayer = (HumanPlayer) board.getHumanPlayer();
			for(Card card: computer.getHand()) {
				if(hPlayer.getHand().contains(card)) {
					 ControlPanel control = new ControlPanel();
					 control.setSuggestDisplay(card.getCardName());
					 disproveCard = card;
					 
				}
			}
			
			dispose();
		}
		
	}

	
	

}
