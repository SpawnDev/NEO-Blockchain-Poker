package pkg;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.Timer;
    
public class Program extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JLabel lblHash;
	private static JLabel lblLastBlock;
	private static JLabel lblBestBlock;
	
	public static String node1 = "http://seed2.ngd.network:10332";
	public static String node2 = "http://seed7.ngd.network:10332";
	public static String node3 = "http://seed8.cityofzion.io:443";
	public static String node4 = "http://seed6.cityofzion.io:443";
	public static String node5 = "http://seed.neoeconomy.io:10332";
	public static String node6 = "http://seed9.ngd.network:10332";
	public static String node7 = "http://seed8.ngd.network:10332";
	public static String workingnode1 = "";
	public static String workingnode2 = "";
	public static String workingnode3 = "";
	
	public static String req1 = "?jsonrpc=2.0&method=getbestblockhash&params=[]&id=1";
	public static String req2 = "?jsonrpc=2.0&method=getblockcount&params=[]&id=1";
	public static String req3 = "?jsonrpc=2.0&method=getconnectioncount&params=[]&id=1";
	public static String req4 = "?jsonrpc=2.0&method=getrawmempool&params=[]&id=1";
	public static String req5 = "?jsonrpc=2.0&method=getblockhash&params=[";
	
	public static int lastblock = 0;
	public static String lasthash = "";
	public static String lastpokerhash = "";
	public static int spil[] = new int[52];
	public static String spilstr[] = new String[52];
	public static String spiltxt = "";
	private static Timer globaltimer;
	
	private JTextField fieldSalt1;
	private JTextField fieldSalt2;
	private JTextField fieldSalt3;
	private JTextField fieldSalt4;
	private JTextField fieldSalt5;
	private Timer pokertimer;

	TekstOut out;
	
	public static String PitajNode(String nodeURL, String request)
	{
		String res = "";
		try 
		{
			URL url = new URL(nodeURL + request);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			res = in.readLine().toString();
			in.close();	
			Gson gson = new Gson();
			res = gson.fromJson(res,JsonObject.class).get("result").toString();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return res;

	}
	public void FisherShuffle(int[]deck, String hash)
	{
	    int index;
	    SecureRandom random = new SecureRandom();
	    random.setSeed(hash.getBytes());
	    for (int i = deck.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        if (index != i)
	        {
	            deck[index] ^= deck[i];
	            deck[i] ^= deck[index];
	            deck[index] ^= deck[i];
	        }
	    }
	    
		upisiSpil();
	}
	public static void upisiSpil()
	{
		String mod[] = new String[3];
		mod[0] = "J";
		mod[1] = "D";
		mod[2] = "K";
		
		spiltxt = "";
		for (int i=0; i<52; i++)
		{
			int karta = (spil[i]%13 + 1);
			if (spil[i] == -1)
			{
				spilstr[i] = "XX";
				spiltxt = spiltxt + spilstr[i] + ", ";
				continue;
			}
			
			String znak = "\u2660";
			if (spil[i] > 12)
			{
				znak = "\u2666";
				if (spil[i] > 25)
				{
					znak = "\u2663";
					if (spil[i] > 38)
					{
						znak = "\u2665";
					}
				}
			}
			String vrijednost	= "" + karta;
			if (karta > 10)
			{
				vrijednost = mod[karta-11];
			}
			if (karta == 1)
			{
				vrijednost = "A";
			}
			
			spilstr[i] = vrijednost + znak;
			spiltxt = spiltxt + spil[i] + ", ";
		}
		spiltxt = spiltxt.substring(0, spiltxt.length() - 2);
	}
	public static String sha256(String base) 
	{
	    try
	    {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } 
	    catch(Exception ex)
	    {
	       throw new RuntimeException(ex);
	    }
	}
	public static void main(String[] args) throws IOException
	{
		EventQueue.invokeLater(new Runnable()
		    {
		      public void run()
		      {
		        try
		        {
		        	Program frame = new Program();
		        	frame.setVisible(true);
		        	init();
		        }
		        catch (Exception e){e.printStackTrace();}

		      }
		    });
	}
	public static void init()
	{
		TekstOut.dodajTekst("Program started!", Color.WHITE);
		
		int[] blockheight = new int[7];
			
		try 
		{
			blockheight[0] = Integer.valueOf(PitajNode(node1, req2));
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		try 
		{
			blockheight[1] = Integer.valueOf(PitajNode(node2, req2));
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		try 
		{
			blockheight[2] = Integer.valueOf(PitajNode(node3, req2));
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		try 
		{
			blockheight[3] = Integer.valueOf(PitajNode(node4, req2));
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		try 
		{
			blockheight[4] = Integer.valueOf(PitajNode(node5, req2));
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		try 
		{
			blockheight[5] = Integer.valueOf(PitajNode(node6, req2));
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		try 
		{
			blockheight[6] = Integer.valueOf(PitajNode(node7, req2));
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
		System.out.println("block1 - " + blockheight[0]);
		System.out.println("block2 - " + blockheight[1]);
		System.out.println("block3 - " + blockheight[2]);
		System.out.println("block4 - " + blockheight[3]);
		System.out.println("block5 - " + blockheight[4]);
		System.out.println("block6 - " + blockheight[5]);
		System.out.println("block7 - " + blockheight[6]);
		
		int largest = blockheight[0];
		for(int i=1;i<blockheight.length;i++)
		{
			largest = Math.max(blockheight[i], largest);
		}
		
		if (largest == blockheight[0])
		{
			workingnode1 = node1;
		}
		else if (largest == blockheight[1])
		{
			workingnode1 = node2;
		}
		else if (largest == blockheight[2])
		{
			workingnode1 = node3;
		}
		
		lasthash = PitajNode(workingnode1, req1).substring(3, 67);
		lastblock = Integer.valueOf(PitajNode(workingnode1, req2));
		
		lblHash.setText("Hash - 0x" + lasthash);
		lblBestBlock.setText("Best Block - #" + lastblock);

		
		ActionListener taskPerformer = new ActionListener() 
		{
            public void actionPerformed(ActionEvent evt) 
            {
            	String newblock = PitajNode(workingnode1, req1).substring(3, 67);
            	System.out.println(newblock);
            	if (!lasthash.equals(newblock))
            	{
            		TekstOut.dodajTekst("Block found!", Color.WHITE);
            		lasthash = newblock;
            		lastblock = Integer.valueOf(PitajNode(workingnode1, req2));
            		
            		lblHash.setText("Hash - 0x" + lasthash);
            		lblBestBlock.setText("Best Block - #" + lastblock);
            	}
            }
		};
        globaltimer = new Timer( 5000 , taskPerformer);
        globaltimer.setRepeats(true);
        globaltimer.start();
	}
	public Program()
	{
	    setBackground(new Color(50, 50, 50));
	    setBounds(100, 100, 620, 359);
	    contentPane = new JPanel();
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setContentPane(contentPane);
	    contentPane.setLayout(null);
	    out = new TekstOut(contentPane);
	    
	    JLabel lblNeoHodlemPoker = new JLabel("NEO Blockchain Poker");
	    lblNeoHodlemPoker.setFont(new Font("Arial", Font.PLAIN, 24));
	    lblNeoHodlemPoker.setBounds(145, 10, 260, 29);
	    contentPane.add(lblNeoHodlemPoker);
	    
	    JLabel lblIgrac = new JLabel("Player 1");
	    lblIgrac.setFont(new Font("Arial", Font.PLAIN, 18));
	    lblIgrac.setBounds(10, 105, 100, 29);
	    contentPane.add(lblIgrac);
	    
	    JLabel lblIgrac_1 = new JLabel("Player 2");
	    lblIgrac_1.setFont(new Font("Arial", Font.PLAIN, 18));
	    lblIgrac_1.setBounds(120, 105, 74, 29);
	    contentPane.add(lblIgrac_1);
	    
	    JLabel lblIgrac_2 = new JLabel("Player 3");
	    lblIgrac_2.setFont(new Font("Arial", Font.PLAIN, 18));
	    lblIgrac_2.setBounds(230, 105, 74, 29);
	    contentPane.add(lblIgrac_2);
	    
	    JLabel lblIgrac_3 = new JLabel("Player 4");
	    lblIgrac_3.setFont(new Font("Arial", Font.PLAIN, 18));
	    lblIgrac_3.setBounds(340, 105, 74, 29);
	    contentPane.add(lblIgrac_3);
	    
	    JLabel lblIgrac_4 = new JLabel("Player 5");
	    lblIgrac_4.setFont(new Font("Arial", Font.PLAIN, 18));
	    lblIgrac_4.setBounds(450, 105, 74, 29);
	    contentPane.add(lblIgrac_4);
	    
	    final JLabel lblRuka = new JLabel("P1- ");
	    lblRuka.setFont(new Font("Arial", Font.PLAIN, 18));
	    lblRuka.setBounds(10, 170, 100, 29);
	    contentPane.add(lblRuka);
	    
	    final JLabel lblRuka_1 = new JLabel("P2- ");
	    lblRuka_1.setFont(new Font("Arial", Font.PLAIN, 18));
	    lblRuka_1.setBounds(120, 170, 100, 29);
	    contentPane.add(lblRuka_1);
	    
	    final JLabel lblRuka_2 = new JLabel("P3- ");
	    lblRuka_2.setFont(new Font("Arial", Font.PLAIN, 18));
	    lblRuka_2.setBounds(230, 170, 100, 29);
	    contentPane.add(lblRuka_2);
	    
	    final JLabel lblRuka_3 = new JLabel("P4- ");
	    lblRuka_3.setFont(new Font("Arial", Font.PLAIN, 18));
	    lblRuka_3.setBounds(340, 170, 100, 29);
	    contentPane.add(lblRuka_3);
	    
	    final JLabel lblRuka_4 = new JLabel("P5- ");
	    lblRuka_4.setFont(new Font("Arial", Font.PLAIN, 18));
	    lblRuka_4.setBounds(450, 170, 100, 29);
	    contentPane.add(lblRuka_4);
	    
	    final JLabel lblJavne = new JLabel("Table - ");
	    lblJavne.setFont(new Font("Arial", Font.PLAIN, 18));
	    lblJavne.setBounds(10, 205, 540, 29);
	    contentPane.add(lblJavne);
	    
	    lblBestBlock = new JLabel("Best Block - #");
	    lblBestBlock.setFont(new Font("Arial", Font.PLAIN, 14));
	    lblBestBlock.setBounds(10, 40, 194, 22);
	    contentPane.add(lblBestBlock);
	    
	    JButton btnPodijeliKarte = new JButton("Start Poker");
	    btnPodijeliKarte.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    btnPodijeliKarte.setBounds(10, 245, 110, 34);
	    contentPane.add(btnPodijeliKarte);
	    btnPodijeliKarte.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("XXX - NOVA IGRA - XXX");
				
				for (int i=0; i<52; i++)
				{
					spil[i] = i;
				}
				upisiSpil();
				lblRuka.setText("P1- ");
				lblRuka_1.setText("P2- ");
				lblRuka_2.setText("P3- ");
				lblRuka_3.setText("P4- ");
				lblRuka_4.setText("P5- ");
				lblJavne.setText("Table - ");
				
				String hash = PitajNode(workingnode1, req1).substring(3, 67);
				lasthash = hash;
				TekstOut.dodajTekst("Waiting new block...", Color.WHITE);
				
				ActionListener taskPerformer = new ActionListener() 
				{
		            public void actionPerformed(ActionEvent evt) 
		            {
		            	String newblock = PitajNode(workingnode1, req1).substring(3, 67);
		            	System.out.println(newblock);
		            	if (!lasthash.equals(newblock))
		            	{
		            		TekstOut.dodajTekst("Block found!", Color.WHITE);
		            		lasthash = newblock;
		            		lastpokerhash = lasthash + fieldSalt1.getText() + fieldSalt2.getText() + fieldSalt3.getText() + fieldSalt4.getText() + fieldSalt5.getText();
		            		System.out.println("Last hash - " + lasthash);
		            		System.out.println("Last poker seed - " + lastpokerhash);
		            		lastpokerhash = sha256(lastpokerhash);
		            		System.out.println("Last poker hash - " + lastpokerhash);
		            		FisherShuffle(spil, lastpokerhash);
		            		System.out.println(spiltxt);
		            		
		            		lblRuka.setText(lblRuka.getText() + spilstr[0] + " ");
		            		lblRuka_1.setText(lblRuka_1.getText() + spilstr[1] + " ");
		            		lblRuka_2.setText(lblRuka_2.getText() + spilstr[2] + " ");
		            		lblRuka_3.setText(lblRuka_3.getText() + spilstr[3] + " ");
		            		lblRuka_4.setText(lblRuka_4.getText() + spilstr[4] + " ");
		            		
		            		lblRuka.setText(lblRuka.getText() + spilstr[5] + " ");
		            		lblRuka_1.setText(lblRuka_1.getText() + spilstr[6] + " ");
		            		lblRuka_2.setText(lblRuka_2.getText() + spilstr[7] + " ");
		            		lblRuka_3.setText(lblRuka_3.getText() + spilstr[8] + " ");
		            		lblRuka_4.setText(lblRuka_4.getText() + spilstr[9] + " ");
		            		
		            		pokertimer.stop();
		    				System.out.println("XXX - NOVA IGRA - XXX");
		            	}
		            }
				};
		        pokertimer = new Timer( 5000 , taskPerformer);
		        pokertimer.setRepeats(true);
		        pokertimer.start();
			}
	    });
	    
	    JButton btnFlop = new JButton("Flop");
	    btnFlop.setBounds(150, 256, 80, 23);
	    contentPane.add(btnFlop);
	    btnFlop.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("XXX - FLOP - XXX");
				System.out.println(spiltxt);
				
				lblJavne.setText(lblJavne.getText() + spilstr[10] + " ");
				lblJavne.setText(lblJavne.getText() + spilstr[11] + " ");
				lblJavne.setText(lblJavne.getText() + spilstr[12] + "   ");
				
				System.out.println("XXX - FLOP - XXX");
			}
	    });
	    
	    JButton btnTurn = new JButton("Turn");
	    btnTurn.setBounds(240, 256, 80, 23);
	    contentPane.add(btnTurn);
	    btnTurn.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("XXX - TURN - XXX");
				System.out.println(spiltxt);
				
				lblJavne.setText(lblJavne.getText() + spilstr[13] + "   ");
				
				System.out.println("XXX - TURN - XXX");
			}
	    });
	    
	    JButton btnRiver = new JButton("River");
	    btnRiver.setBounds(330, 256, 80, 23);
	    contentPane.add(btnRiver);
	    btnRiver.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("XXX - RIVER - XXX");
				System.out.println(spiltxt);
				
				lblJavne.setText(lblJavne.getText() + spilstr[14] + " ");

				System.out.println("XXX - RIVER - XXX");
			}
	    });
	    
	    fieldSalt1 = new JTextField();
	    fieldSalt1.setText("salt1");
	    fieldSalt1.setBounds(10, 145, 100, 20);
	    contentPane.add(fieldSalt1);
	    fieldSalt1.setColumns(10);
	    
	    fieldSalt2 = new JTextField();
	    fieldSalt2.setText("salt2");
	    fieldSalt2.setColumns(10);
	    fieldSalt2.setBounds(120, 145, 100, 20);
	    contentPane.add(fieldSalt2);
	    
	    fieldSalt3 = new JTextField();
	    fieldSalt3.setText("salt3");
	    fieldSalt3.setColumns(10);
	    fieldSalt3.setBounds(230, 145, 100, 20);
	    contentPane.add(fieldSalt3);
	    
	    fieldSalt4 = new JTextField();
	    fieldSalt4.setText("salt4");
	    fieldSalt4.setColumns(10);
	    fieldSalt4.setBounds(340, 145, 100, 20);
	    contentPane.add(fieldSalt4);
	    
	    fieldSalt5 = new JTextField();
	    fieldSalt5.setText("salt5");
	    fieldSalt5.setColumns(10);
	    fieldSalt5.setBounds(450, 145, 100, 20);
	    contentPane.add(fieldSalt5);
	    
	    lblHash = new JLabel("Hash - 0x");
	    lblHash.setFont(new Font("Arial", Font.PLAIN, 14));
	    lblHash.setBounds(10, 65, 580, 22);
	    contentPane.add(lblHash);
	    
	    lblLastBlock = new JLabel("Last Block - ");
	    lblLastBlock.setFont(new Font("Arial", Font.PLAIN, 14));
	    lblLastBlock.setBounds(214, 40, 194, 22);
	    contentPane.add(lblLastBlock);

	    
	    WindowListener exitListener = new WindowAdapter()
	    {
	      public void windowClosing(WindowEvent e)
	      {
	    	  System.exit(0);
	      }
	    };
	    addWindowListener(exitListener);
	    setDefaultCloseOperation(0);
	}
}