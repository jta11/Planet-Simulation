import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class NBody {
	private static Clip audioClip;
	private static ArrayList<Body> planets = new ArrayList<Body>();
	private static double radius;
	//private static int T = (int) 1000000;
	private static int dt = 25000;
	private static String[] names = {"3body", "8star-rotation", "antimatter", "armageddon", "atom", "awesome", "binary", "binaryStars", "chaosblossom", "dance10", "entropy-universe", "fourellipses", "galaxy", "hypnosis", "illusion", "its-a-trap", "kaleidoscope", "kevin", "massive-squirrel-battle", "planets", "planets-elliptical", "planetsparty", "pluto", "quad-stars", "renegade", "sbh2", "sbh3", "soap-opera", "suninterference", "twin-binaries", "uniform3", "uniform8", "uniform100"};
	private static double[] fx1;
	private static double[] fy1;
	private static final double G = 6.67 * Math.pow(10, -11);

	public static void main(String[] args) {

		try {
			File audioFile = new File("data/Epic Cinematic Space Music  Ambient Background Music.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

			AudioFormat format = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);

			try {
				audioClip = (Clip) AudioSystem.getLine(info);
				audioClip.open(audioStream);
				audioClip.start();
				audioClip.loop(Clip.LOOP_CONTINUOUSLY);

			}

			catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
		}

		catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		}

		catch (IOException e1) {
			e1.printStackTrace();
		}

		init();

		StdDraw.setXscale(-radius, radius);
		StdDraw.setYscale(-radius, radius);

		// int currentTime = 0;
		while (true) {
			
			if(StdDraw.getChange())
			{
				planets.clear();
				init();
				fx1 = new double[planets.size()];
				fy1 = new double[planets.size()];
				StdDraw.setChange(false);
			}
			
			for (int count = 0; count < planets.size(); count++) {
				double fxSum = 0;
				double fySum = 0;
				for (int count2 = 0; count2 < planets.size(); count2++) {
					if (count == count2) {
						//
					}

					else {
						double dx = planets.get(count2).getX() - planets.get(count).getX();
						double dy = planets.get(count2).getY() - planets.get(count).getY();

						double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

						double force = (G * planets.get(count).getMass() * planets.get(count2).getMass())
								/ Math.pow(dist, 2);

						fxSum += (force * (dx / dist));
						fySum += (force * (dy / dist));
					}

					fx1[count] = fxSum;
					fy1[count] = fySum;
				}
			}

			for (int count3 = 0; count3 < planets.size(); count3++) {
				double ax = fx1[count3] / planets.get(count3).getMass();
				double ay = fy1[count3] / planets.get(count3).getMass();

				planets.get(count3).setXV(planets.get(count3).getXV() + (dt * ax));
				planets.get(count3).setYV(planets.get(count3).getYV() + (dt * ay));
			}

			for (int i = 0; i < planets.size(); i++) {
				planets.get(i).setX(planets.get(i).getX() + (planets.get(i).getXV() * dt));
				planets.get(i).setY(planets.get(i).getY() + (planets.get(i).getYV() * dt));
			}

			StdDraw.picture(0, 0, "data/starfield.jpg");

			for (Body b : planets) {
				StdDraw.picture(b.getX(), b.getY(), "data/" + b.getFileName());

			}

			// currentTime += dt;
			StdDraw.show(20);
		}
	}
	
	public static void init()
	{
		File f = new File("data/" + StdDraw.getName() + ".txt");
		try {
			Scanner input = new Scanner(f);
			getData(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		fx1 = new double[planets.size()];
		fy1 = new double[planets.size()];
	}

	public static void getData(Scanner input) {
		int indx = 0;
		int particles = input.nextInt();
		radius = input.nextDouble();
		input.nextLine();

		while (input.hasNext()) {
			String xs = input.next();
			
			if(xs.equals("Creator"))
			{
				break;
			}

			double x = Double.parseDouble(xs);

			String ys = input.next();
			double y = Double.parseDouble(ys);

			String xvs = input.next();
			double xv = Double.parseDouble(xvs);

			String yvs = input.next();
			double yv = Double.parseDouble(yvs);

			String ms = input.next();
			double mass = Double.parseDouble(ms);

			String fileName = input.next();

			planets.add(new Body(x, y, xv, yv, mass, fileName));
		}
	}
}