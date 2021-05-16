package personnel;

public class ImpossibleChangerDate extends Exception {

	public ImpossibleChangerDate() {
		System.out.println("La date de départ doit être plus tard que la date d'arrivée");
	}
}
