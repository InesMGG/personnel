package personnel;

public class ImpossibleChangerDate extends Exception {

	public ImpossibleChangerDate() {
		System.out.println("La date de d�part doit �tre plus tard que la date d'arriv�e");
	}
}
