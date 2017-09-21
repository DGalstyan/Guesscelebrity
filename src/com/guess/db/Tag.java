package com.guess.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.util.Log;

public class Tag {
	public final static int GAME_STAR_0=0;
	public final static int GAME_STAR_1=1;
	public final static int GAME_STAR_2=2;
	public final static int GAME_STAR_3=3;
	
	public final static int MALE=1;
	public final static int FEMALE=2;
	private String name = null;
	private String image = null;
	private int id;
	private int type;
	private int status;
	private int sex;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the sex
	 */
	public int getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public List<String> getQuestions() {
		List<String> shuffle = new ArrayList<String>();
		shuffle.add(getName());
		List<Tag> list = Util.db.getAllTagBySex(this);
		for (int i = 0; i < 3; i++) {
			int index = new Random().nextInt(list.size());
			Tag value = list.get(index);
			if(value.getId() == this.getId()){			
				shuffle.add(list.get(new Random().nextInt(list.size())).getName());
			}else{
				shuffle.add(value.getName());
			}
		}
		Collections.shuffle(shuffle);
		return shuffle;
	}
	
	public static void updateTable() {
		Util.db.insertTagsEasy("Nicolas Cage", "cel_1.jpg", 0, MALE);
		Util.db.insertTagsEasy("Brad Pitt", "cel_2.jpg", 0, MALE);
		Util.db.insertTagsEasy("Sylvester Stallone", "cel_3.jpg", 0, MALE);
		Util.db.insertTagsEasy("Leonardo DiCaprio", "cel_4.jpg", 0, MALE);
		Util.db.insertTagsEasy("Jean-Claude Van Damme", "cel_5.jpg", 0, MALE);
		Util.db.insertTagsEasy("Robert De Niro", "cel_6.jpg", 0, MALE);
		Util.db.insertTagsEasy("Jackie Chan", "cel_7.jpg", 0, MALE);
		Util.db.insertTagsEasy("Angelina Jolie", "cel_8.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Jennifer Lopez", "cel_9.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("David Beckham", "cel_10.jpg", 0, MALE);
		Util.db.insertTagsEasy("Adam Sandler", "cel_11.jpg", 0, MALE);
		Util.db.insertTagsEasy("Bruce Willis", "cel_12.jpg", 0, MALE);
		Util.db.insertTagsEasy("Kim Kardashian", "cel_13.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Beyonce", "cel_14.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Al Pacino", "cel_15.jpg", 0, MALE);
		Util.db.insertTagsEasy("Charles Chaplin", "cel_16.jpg", 0, MALE);
		Util.db.insertTagsEasy("Jessica Alba", "cel_17.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Will Smith", "cel_18.jpg", 0, MALE);
		Util.db.insertTagsEasy("Rihanna", "cel_19.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Taylor Swift", "cel_20.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Johnny Depp", "cel_21.jpg", 0, MALE);
		Util.db.insertTagsEasy("Denzel Washington", "cel_22.jpg", 0, MALE);
		Util.db.insertTagsEasy("Laurence Olivier", "cel_23.jpg", 0, MALE);
		Util.db.insertTagsEasy("Tom Cruise", "cel_24.jpg", 0, MALE);
		Util.db.insertTagsEasy("Morgan Freeman", "cel_25.jpg", 0, MALE);
		Util.db.insertTagsEasy("Sean Penn", "cel_26.jpg", 0, MALE);
		Util.db.insertTagsEasy("Russell Crowe", "cel_27.jpg", 0, MALE);
		Util.db.insertTagsEasy("Judi Dench", "cel_28.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Julia Roberts", "cel_29.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Jack Nicholson", "cel_30.jpg", 0, MALE);
		Util.db.insertTagsEasy("Jack Lemmon", "cel_31.jpg", 0, MALE);
		Util.db.insertTagsEasy("Robin Williams", "cel_32.jpg", 0, MALE);
		Util.db.insertTagsEasy("Nicole Kidman", "cel_33.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Anne Hathaway", "cel_34.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Ingrid Bergman", "cel_35.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Sandra Bullock", "cel_36.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Emma Thompson", "cel_37.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Diane Keaton", "cel_38.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Natalie Portman", "cel_39.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Jodie Foster", "cel_40.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Marion Cotillard", "cel_41.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Reese Witherspoon", "cel_42.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Charlize Theron", "cel_43.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Chris Pratt", "cel_44.jpg", 0, MALE);
		Util.db.insertTagsEasy("Marilyn Monroe", "cel_45.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Abraham Lincoln", "cel_46.jpg", 0, MALE);
		Util.db.insertTagsEasy("Adolf Hitler", "cel_47.jpg", 0, MALE);
		Util.db.insertTagsEasy("Jason London", "cel_48.jpg", 0, MALE);
		Util.db.insertTagsEasy("Bill Gates", "cel_49.jpg", 0, MALE);
		Util.db.insertTagsEasy("Muhammad Ali", "cel_50.jpg", 0, MALE);
		Util.db.insertTagsEasy("Gabriel Macht", "cel_51.jpg", 0, MALE);
		Util.db.insertTagsEasy("Roman Coppola", "cel_52.jpg", 0, MALE);
		Util.db.insertTagsEasy("Ron Bottitta", "cel_53.jpg", 0, MALE);
		Util.db.insertTagsEasy("Christina Aguilera", "cel_54.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Paul Walker", "cel_55.jpg", 0, MALE);
		Util.db.insertTagsEasy("Wentworth Miller", "cel_56.jpg", 0, MALE);
		Util.db.insertTagsEasy("Gemma Arterton", "cel_57.jpg", 0, FEMALE);
		Util.db.insertTagsEasy("Jimmie Walker", "cel_58.jpg", 0, MALE);
		Util.db.insertTagsEasy("Lady Gaga", "cel_59.jpg", 0, Tag.FEMALE);
		Util.db.insertTagsEasy("Steve Jobs", "cel_60.jpg", 0, Tag.MALE);
		
		Util.db.insertTags("Rosie Huntington", "cel_61.jpg", 0, FEMALE, 2);
		Util.db.insertTags("Jennifer Love", "cel_62.jpg", 0, FEMALE, 2);
		Util.db.insertTags("Benedict Cumberbatch", "cel_63.jpg", 0, MALE, 2);
		Util.db.insertTags("Kylie Jenner", "cel_64.jpg", 0,  FEMALE, 2);
		Util.db.insertTags("Donald Faison", "cel_65.jpg", 0, MALE, 2);
		Util.db.insertTags("Janet Jackson", "cel_66.jpg", 0, MALE, 2);
		Util.db.insertTags("Jenifer", "cel_67.jpg", 0, FEMALE, 2);
		Util.db.insertTags("Jimmy Fallons", "cel_68.jpg", 0, MALE, 2);
		Util.db.insertTags("Kate Upton", "cel_69.jpg", 0, FEMALE, 2);
		Util.db.insertTags("Frank Darabont", "cel_70.jpg", 0, MALE, 2);
		Util.db.insertTags("Jon Bernthal", "cel_71.jpg", 0, MALE, 2);
		Util.db.insertTags("Logan Lerman", "cel_72.jpg", 0, MALE, 2);
		Util.db.insertTags("Matthew Macfadyen", "cel_73.jpg", 0, MALE, 2);
		Util.db.insertTags("Bruno Ganz", "cel_74.jpg", 0, MALE, 2);
		Util.db.insertTags("Sigourney Weaver", "cel_75.jpg", 0, FEMALE, 2);
		Util.db.insertTags("Cate Blanchett", "cel_76.jpg", 0, FEMALE, 2);
		Util.db.insertTags("Monica Bellucci", "cel_77.jpg", 0, FEMALE, 2);
		Util.db.insertTags("Charlize Theron", "cel_78.jpg", 0, FEMALE, 2);
		
		Util.db.insertTags("Julia Roberts", "cel_79.jpg", 0, FEMALE, 2);
		Util.db.insertTags("Will Ferrell", "cel_80.jpg", 0, MALE, 2);
		Util.db.insertTags("Bradley Cooper", "cel_81.jpg", 0, MALE, 2);		
		Util.db.insertTags("George Lopez", "cel_82.jpg", 0, Tag.MALE, 2);
		Util.db.insertTags("Gary Oldman",  "cel_83.jpg", 0, Tag.MALE, 2);
		Util.db.insertTags("Jennifer Jason Leigh", "cel_84.jpg", 0, Tag.FEMALE, 2);		
		Util.db.insertTags("Tracy Pollan", "cel_85.jpg", 0, Tag.FEMALE, 2);
		Util.db.insertTags("Antonio Banderas", "cel_86.jpg", 0, MALE, 2);
		Util.db.insertTags("Marc Maron", "cel_87.jpg", 0, Tag.MALE, 2);
		Util.db.insertTags("James Spader", "cel_88.jpg", 0, Tag.MALE, 2);		
		Util.db.insertTags("Rob Lowe", "cel_89.jpg", 0, MALE, 2);
		Util.db.insertTags("Woody Harrelson", "cel_90.jpg", 0, MALE, 2);
		Util.db.insertTags("Steven Weber", "cel_91.jpg", 0, MALE, 2);
		Util.db.insertTags("Clark Gregg", "cel_92.jpg", 0, MALE, 2);		
		Util.db.insertTags("Fred Stoller", "cel_93.jpg", 0, Tag.MALE, 2);
		Util.db.insertTags("Heather Langenkamp", "cel_94.jpg", 0, Tag.FEMALE, 2);
		Util.db.insertTags("Arnold Schwarzenegger", "cel_95.jpg", 0, MALE, 2);
		Util.db.insertTags("Jim Carrey", "cel_96.jpg", 0, MALE, 2);		
		Util.db.insertTags("Scarlett Johansson", "cel_97.jpg", 0, FEMALE, 2);
		Util.db.insertTags("Heath Ledger", "cel_98.jpg", 0, MALE, 2);
		Util.db.insertTags("Orlando Bloom", "cel_99.jpg", 0, MALE, 2);
		Util.db.insertTags("Emma Watson",    "cel_100.jpg", 0, FEMALE, 2);
		Util.db.insertTags("Dwayne Johnson", "cel_101.jpg", 0, MALE, 2);
		Util.db.insertTags("Daniel Radcliffe", "cel_102.jpg", 0, MALE, 2);
		Util.db.insertTags("Anne Hathaway", "cel_103.jpg", 0, FEMALE, 2);
		Util.db.insertTags("Edward Norton", "cel_104.jpg", 0, MALE, 2);
		Util.db.insertTags("Keira Knightley", "cel_105.jpg", 0, FEMALE, 2);
		Util.db.insertTags("Hugh Jackman", "cel_106.jpg", 0, Tag.FEMALE, 2);
		Util.db.insertTags("Matt Damon", "cel_107.jpg", 0, MALE, 2);		
		Util.db.insertTags("Cameron Diaz", "cel_108.jpg", 0, FEMALE, 2);
		Util.db.insertTags("George Clooney", "cel_109.jpg", 0, MALE, 2);
		Util.db.insertTags("Steven Spielberg", "cel_110.jpg", 0, MALE, 2);
		Util.db.insertTags("Harrison Ford", "cel_111.jpg", 0, MALE, 2);
		Util.db.insertTags("Al Pacino", "cel_112.jpg", 0, MALE, 2);
		Util.db.insertTags("Robert Downey Jr.", "cel_113.jpg", 0, MALE, 2);
		Util.db.insertTags("Russell Crowe", "cel_114.jpg", 0, MALE, 2);
		Util.db.insertTags("Liam Neeson", "cel_115.jpg", 0, MALE, 2);
		Util.db.insertTags("Kate Winslet", "cel_116.jpg", 0, FEMALE, 2);
		Util.db.insertTags("Mark Wahlberg", "cel_117.jpg", 0, MALE, 2);
		Util.db.insertTags("Natalie Portman", "cel_118.jpg", 0, FEMALE, 2);
		Util.db.insertTags("Pierce Brosnan", "cel_119.jpg", 0, MALE, 2);
		Util.db.insertTags("Sean Connery", "cel_110.jpg", 0, MALE, 2);
		
		Util.db.insertTags("Sara Jay", "cel_121.jpg", 0, FEMALE, 3);
		Util.db.insertTags("Debra Winger",      "cel_122.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Kim Cattrall",      "cel_123.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Carrie Fisher",     "cel_124.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Melanie Griffith",  "cel_125.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Darrell Hammond",   "cel_126.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Gilbert Gottfried", "cel_127.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Khandi Alexander",  "cel_128.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Dennis Christopher","cel_129.jpg", 0, Tag.MALE, 3);		
		Util.db.insertTags("Fran Drescher",     "cel_130.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Sharon Stone",      "cel_131.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Jennifer Saunders", "cel_132.jpg", 0, Tag.FEMALE, 3);		
		Util.db.insertTags("Tim Daly",          "cel_133.jpg", 0, Tag.MALE, 3);		
		Util.db.insertTags("Marlon Jackson",    "cel_134.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Jon Lovitz",        "cel_135.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Andrew Dice Clay",  "cel_136.jpg", 0, Tag.MALE, 3);		
		Util.db.insertTags("Rosanna Arquette",  "cel_137.jpg", 0, Tag.FEMALE, 3);		
		Util.db.insertTags("Kyle MacLachlan",   "cel_138.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Clancy Brown",      "cel_139.jpg", 0, Tag.MALE, 3);		
		Util.db.insertTags("Catherine Keener",  "cel_140.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Aidan Quinn",       "cel_141.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Emma Thompson",     "cel_142.jpg", 0, Tag.FEMALE, 3);		
		Util.db.insertTags("Julia Sweeney",     "cel_143.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Tracey Ullman",     "cel_144.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Matthew Modine",    "cel_145.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Jennifer Rubin",    "cel_146.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Gina Gershon",      "cel_147.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Amy Yasbeck",       "cel_148.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Edie Falco",        "cel_149.jpg", 0, Tag.FEMALE, 3);		
		Util.db.insertTags("Mike Myers",        "cel_150.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Weird Al Yankovic", "cel_151.jpg", 0, Tag.MALE, 3);		
		Util.db.insertTags("Phoebe Cates",      "cel_152.jpg", 0, Tag.FEMALE, 3);		
		Util.db.insertTags("Jason Alexander",   "cel_153.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Scott Thompson",    "cel_154.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Wanda Sykes",       "cel_155.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Bridget Fonda",     "cel_156.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Laura Linney",      "cel_157.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Darlene Vogel",     "cel_158.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Elisabeth Shue",    "cel_159.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Dave Foley",        "cel_160.jpg", 0, Tag.MALE, 3);
		
		Util.db.insertTags("Harold Perrineau", "cel_161.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Michael Chiklis", "cel_162.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Vincent Spano", "cel_163.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Hank Azaria", "cel_164.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Andrew McCarthy", "cel_165.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Eamonn Walker", "cel_166.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Crispin Glover", "cel_167.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Christopher Meloni", "cel_168.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Ricky Gervais", "cel_169.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Chi McBride", "cel_170.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Eric Stoltz", "cel_171.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Tim Roth", "cel_172.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Michael J. Fox", "cel_173.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Oliver Platt", "cel_174.jpg", 0, Tag.MALE, 3);
		Util.db.insertTags("Michael T. Weiss", "cel_175.jpg", 0, Tag.MALE, 3);		
		Util.db.insertTags("Julia Louis-Dreyfus", "cel_176.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Amy Sedaris", "cel_177.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Julianne Moore", "cel_178.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Jennifer Grey", "cel_179.jpg", 0, Tag.FEMALE, 3);
		Util.db.insertTags("Lea Thompson", "cel_180.jpg", 0, Tag.FEMALE, 3);
		
		
		Util.db.insertTags("50 Cent", "cel_181.jpg", 0, MALE, 3);
		Util.db.insertTags("Steven Seagal", "cel_182.jpg", 0, MALE, 3);
		Util.db.insertTags("Jenny McCarthy", "cel_183.jpg", 0, FEMALE, 3);		
		Util.db.insertTags("Carmen Electra", "cel_184.jpg", 0, FEMALE, 3);
		Util.db.insertTags("Connie Britton", "cel_185.jpg", 0, FEMALE, 3);
		Util.db.insertTags("Dina Meyer", "cel_186.jpg", 0, FEMALE, 3);
		Util.db.insertTags("Amber Heard", "cel_187.jpg", 0, FEMALE, 3);
		Util.db.insertTags("Pamela Anderson", "cel_188.jpg", 0, FEMALE, 3);
	}
	
	

}
