import java.util.*;
import java.util.Scanner; //Needed for user input

class MovieReviews {
	public static void main(String[] args)
	{
		//Creates a movie review fetching object
		nytMovieReviews NYTimesReviews = new nytMovieReviews();
		
		//Movie name
		String movieName;
		Scanner input = new Scanner(System.in); //Initialize input reader from input stream
		System.out.print("Please enter movie name: ");
		movieName = input.nextLine(); //Read in the input
		
		//Access and prints out all search results for this 'movieName' in preformat
		NYTimesReviews.getMovieReviews(movieName);
	}
}
