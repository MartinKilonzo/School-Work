/**
 * Test Class for Feed and Reader
 * @author Remmy Martin Kilonzo, 250750759
 * @task Assignment 1
 *
 */
public class TestFeed{

	  public static void main(String[] args){

	    Feed UWO = new Feed("UWO");
	    Reader reader1 = new Reader(UWO);
	    Reader reader2 = new Reader(UWO);

	    UWO.add("Jan 8 - Toastmaster's Campus Communicators");
	    UWO.add("Jan 8 - The Department of Modern Languages and Literatures - La Tertulia");
	    UWO.add("Jan 9 - Nominations open for Board and Senate membership");

	    System.out.println("reader1 read:");
	    reader1.read();
	    System.out.println("reader1 read:");
	    reader1.read();

	    UWO.add("Jan 9 - Scott Conarroe: By Rail at McIntosh Gallery");

	    System.out.println("reader1 read:");
	    reader1.read();
	    System.out.println("reader2 read:");
	    reader2.read();

	    UWO.add("Jan 9 - RGE Murray Seminar Series");
	    UWO.add("Jan 9 - Physics and Astronomy Colloquium - Dr. Rachel Friesen, Dunlap Inst for Astronomy &amp; Astrophysics (UofT)");
	    UWO.add("Jan 10 - Don Wright Faculty of Music - pianist Yoko Hirota");
	    UWO.add("Jan 10 - Dr. Shelley McKellar, A Silent Pandemic? Says WHO? ...");
	    UWO.add("Jan 13 - Successful Time Management Presentation");
	    UWO.add("Jan 14 - Senior Alumni Program - Renovating the Human Joint");
	    UWO.add("Jan 15 - The Chinese Program at Huron - Tea and Conversation.");
	    UWO.add("Jan 15 - Preparing for Multiple-choice Tests Presentation");
	    UWO.add("Jan 15 - Toastmaster's Campus Communicators");
	    UWO.add("Jan 15 - Visiting Speaker in Chemistry - Prof. Gabriel Hanna");
	    UWO.add("Jan 15 - The Department of Modern Languages and Literatures - La Tertulia");
	    UWO.add("Jan 15 - Don Wright Faculty of Music - Ensemble Paramirabo");
	    UWO.add("Jan 16 - Bone; Joint Injury and Repair");
	    UWO.add("January 16-17 - London Convention Centre");
	    UWO.add("Jan 16 - Scott Conarroe: By Rail at McIntosh Gallery");
	    UWO.add("Jan 16 - RGE Murray 2013 Annual Lecture");
	    UWO.add("Jan 16 - Physics and Astronomy Colloquium - Dr. Emmanuel Jacquet, CITA");
	    UWO.add("Jan 16 - Optimize Lecture Learning Presentation");
	    UWO.add("Jan 16 - MER Colloquium: Dr. Victoria Esses");
	    UWO.add("Jan 16 - Don Wright Faculty of Music - Wintersong");
	    UWO.add("Jan 17 - Don Wright Faculty of Music - Selections from Wintersong");
	    UWO.add("Jan 17 - Department of Earth Sciences -  Barbara Sherwood-Lollar, University of Toronto");
	    UWO.add("Jan 17 - Don Wright Faculty of Music - Apparitions by Shulamit Ran and Ashfall by Mark Schultz");
	    UWO.add("Jan 19 - Don Wright Faculty of Music -  Brazilian pianist Mauricio Veloso");
	    UWO.add("Jan 20 - Reading Strategies for International Students Presentation");
	    UWO.add("Jan 20 - Department of Physiology and Pharmacology Seminar Series");
	    UWO.add("Jan 21 - Senior Alumni Program - Dr. R.O. Bot");
	    UWO.add("Jan 21 - Effective Textbook Strategies Presentation");
	    UWO.add("Jan 21 - Visiting Speaker in Chemistry - Dr. Lijia Liu will present a talk entitled:");
	    UWO.add("Jan 22 - The Chinese Program at Huron - Tea and Conversation.");
	    UWO.add("Jan 22 - United Way Announcement");
	    UWO.add("Jan 22 - Toastmaster's Campus Communicators");
	    UWO.add("Jan 22 - Centre for Transitional Justice and Post-Conflict Reconstruction Speaker Series");
	    UWO.add("Jan 22 - Stats &amp; Data Series: Introduction to Program Evaluation");
	    UWO.add("Jan 22 - Enhance Your Note-Taking Skills Presentation");
	    UWO.add("Jan 22 - ICLR Public Lecture Conron Hall UC 224");
	    UWO.add("Jan 22 - The Department of Modern Languages and Literatures - La Tertulia");
	    UWO.add("Jan 23 - Scott Conarroe: By Rail at McIntosh Gallery");
	    UWO.add("Jan 23 - RGE Murray Seminar Series");
	    UWO.add("Jan 23 - Physics and Astronomy Colloquium - Dr. Andrea Soddu, Western");
	    UWO.add("Jan 23 - Nominations close for Board and Senate membership (except At-Large Student Senate membership)");
	    UWO.add("Jan 23 - Don Wright Faculty of Music - Fred Pattison Piano Competition 2013 winner");
	    UWO.add("Jan 24 - Don Wright Faculty of Music - French pianist Olivier Chauzu");
	    UWO.add("Jan 24 - Senate meeting");
	    UWO.add("Jan 24 - International Research Connections");
	    UWO.add("Jan 24 - Faculty Mentor Program - International Research Connections");
	    UWO.add("Jan 24 - Don Wright Faculty of Music - Fred Pattison Piano Competition");
	    UWO.add("Jan 24 - Psychology Colloquium Series - Michael Anderson, Franklin &amp; Marshall College");
	    UWO.add("Jan 24 - Department of Earth Sciences - Lindy Elkins-Tanton, Carnegie Institute of Science");
	    UWO.add("Jan 25 - Don Wright Faculty of Music - Fred Pattison Masterclass");
	    UWO.add("Jan 27 - Department of Physiology and Pharmacology Seminar Series");
	    UWO.add("Jan 27 - Nominations close for At-Large Student Senate membership");
	    UWO.add("Jan 27 - Don Wright Faculty of Music - Contemporary Music Ensemble");
	    UWO.add("Jan 28 - Senior Alumni Program - What Does Short-Termism Mean for Sustainability?");
	    UWO.add("Jan 28 - Essay Exam Essentials Presentation");
	    UWO.add("Jan 28 - Time Management for Graduate Students Presentation");
	    UWO.add("Jan 29 - The Chinese Program at Huron - Tea and Conversation.");
	    UWO.add("Jan 29 - Toastmaster's Campus Communicators");
	    UWO.add("Jan 29 - International Students - Group Career Counselling Sessions");
	    UWO.add("Jan 29 - Writing Multiple-choice Tests Presentation");
	    UWO.add("Jan 29 - The Department of Modern Languages and Literatures - La Tertulia");
	    UWO.add("Jan 30 - Scott Conarroe: By Rail at McIntosh Gallery");
	    UWO.add("Jan 30 - RGE Murray Seminar Series");
	    UWO.add("Jan 30 - Board of Governors meeting");
	    UWO.add("Jan 30 - Physics and Astronomy Colloquium - Dr. Ken Ragan, McGill University");
	    UWO.add("Jan 30 - MER Colloquium: Dr. Sherin Hussein");
	    UWO.add("Jan 31 - Don Wright Faculty of Music - Faculty artists recital");
	    UWO.add("Jan 31 - Earth Sciences Colloquium - Adrian Fiege, University of Michigan");
	    UWO.add("Jan 31 - Don Wright Faculty of Music - La Cenerentola and Feb 1 at 8 p.m. Feb 2 at 2 p.m.");
	    UWO.add("Feb 2 - Don Wright Faculty of Music - Schumann concert");
	    UWO.add("Feb 3 - Putting off Procrastination Presentation");
	    UWO.add("Feb 3 - Department of Physiology and Pharmacology Seminar Series");
	    UWO.add("Feb 4 - Senior Alumni Program - No Laughing Matter");
	    UWO.add("Feb 4 - Preparing for Multiple-choice Tests Presentation");
	    UWO.add("Feb 5 - The Chinese Program at Huron - Tea and Conversation.");
	    UWO.add("Feb 5 - Toastmaster's Campus Communicators");
	    UWO.add("Feb 5 - Online Research Skills Presentation");
	    UWO.add("Feb 5 - The Department of Modern Languages and Literatures - La Tertulia");
	    UWO.add("Feb 6 - Physics and Astronomy Colloquium - Dr. Marc Dignam, Queen's University");
	    UWO.add("Feb 6 - Classes Without Quizzes - Big Brains, Big Data, Big Challenges");
	    UWO.add("Feb 7 - Don Wright Faculty of Music - Paul Marleyn");
	    UWO.add("Feb 7 - Psychology Colloquium Series - David White, Wilfrid Laurier University.");
	    UWO.add("Feb 7 - Earth Sciences Colloquium - Herb Helmstaedt");
	    UWO.add("Feb 7 - Don Wright Faculty of Music - La Cenerentola. Feb 7 and 8, 8 p.m. Feb 9 at 2 p.m.");

	    System.out.println("reader2 read:");
	    reader2.read();

	    System.out.println("reader1 find:");
	    reader1.find("Colloquium");
	  }
	}