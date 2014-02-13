/**
 *
 * Oppgave11_4.java ST/EL/HRS 2005-04-20/2010-02-19
 *
 * Denne filen inneholder et testprogram.
 * Menystyrt klient fins på filen Oppgave11_4_meny.java.
 *
 * Denne oppgaven beskriver en egen immutabel klasse som tilbyr enkle
 * metoder for å håndtere datoer. Tidspunktobjektene
 * inneholder også informasjon om klokkeslett ned til millisekunder,
 * dette tar vi ikke hensyn til her, unntatt i metoden toString()
 * som henter ut både dato og klokkeslett.
 *
 * Oppgaven er nok relativt krevende, men den ferdige klassen bør være
 * langt enklere å bruke enn de klassene som Java API'et tilbyr.
 *
 * Litt om datoer og kalendere i Java API:
 *      Klassen Date beskriver et tidspunkt (dato og klokkeslett) relativt
 *      til GMT. Dette er ikke alltid det mest hensiktsmessige.
 *      Java SDK lar oss ta hensyn til tidssone, sommer- og vintertid samt
 *      kalendertype (gregoriansk, muslimsk, jødisk, ..., foreløpig er bare
 *      den gregorianske kalenderen implementert).Vi bruker kalenderen som
 *      er beskrevet i klassen GregorianCalendar. Det er en subklasse til Calendar.
 *      Vi bruker klassen SimpleDateFormat til å sette og senere sjekke gyldigheten av en datostreng.
 *      Denne klassen bruker vi også til å lage utskrift. Til dette siste kan vi også bruke
 *      metoden printf(), se kapittel 8.12.
 */
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Klassen Dato
 *
 */
class Dato implements Comparable<Dato> {  // Comparable, se kapittel 12
  public static final String DATOSTRENG = "ddMMyyyy";
  private static final int ANT_TIMER_PR_DØGN = 24;
  private static final int ANT_MIN_PR_TIME = 60;
  private static final int ANT_SEK_PR_MIN = 60;
  private static final int ANT_MS_PR_SEK = 1000;
  private static final long ANT_MS_PR_DØGN = ANT_TIMER_PR_DØGN * ANT_MIN_PR_TIME
                                                                    * ANT_SEK_PR_MIN * ANT_MS_PR_SEK;

  /* Standardverdien er dagens dato basert på regionale innstillinger */
  private GregorianCalendar kalender = new GregorianCalendar();

  /**
   * Lar datoen være dagens dato, dvs at objektvariabelen kalender har verdien satt et par linjer over!
   */
  public Dato() {
  }

  /**
   * Setter datoen til å være lik parameteren 'dato'
   */
  public Dato(Date dato) {
    kalender.setTime(dato);
  }

  /**
   * Setter datoen lik datoverdien av parameteren 'dato' hvis strengen kan
   * omformes til en gyldig dato i henhold til det klassens oppgitte formatet.
   * Hvis ikke kastes ParseException.
   */
  public Dato(String dato) throws java.text.ParseException {
    java.text.SimpleDateFormat datoformat = new java.text.SimpleDateFormat(DATOSTRENG);
    datoformat.setLenient(false); // krever en 'gyldig' dato, se API doc.
    Date date = datoformat.parse(dato);
    kalender.setTime(date);
  }


 /**
  * Returnerer årstallet.
  */
  public int finnÅr() {
    return kalender.get(Calendar.YEAR);
  }

 /**
  * Returnerer månedens nummer (1-12).
  */
  public int finnMnd() {
    return kalender.get(Calendar.MONTH) + 1;  // MONTH er i intervallet [0,11]
  }

  /**
   * Returnerer dagen i måneden (1-31).
   */
  public int finnDagIMnd() {
    return kalender.get(Calendar.DAY_OF_MONTH);
  }

  /**
   * Returnerer en dato som er lik this + et antall dager gitt som parameter.
   */
  public Dato nyDato(int dager) {
    long antMs = kalender.getTime().getTime() + (long) dager * (long) ANT_MS_PR_DØGN;
    Date dato = new Date(antMs);
    return new Dato(dato);
  }

  /**
   * Metode som sammenligner to datoer.
   * Returnerer negativ verdi hvis this ligger foran parameteren dato i tid,
   * positiv verdi hvis this ligger etter parameteren dato i tid,
   * og 0 hvis de to datoene er like.
   */
  public int compareTo(Dato dato) {
    if (kalender.before(dato.kalender)) {
      return -1;
    } else if (kalender.equals(dato.kalender)) {
      return 0;
    } else {
      return 1;
    }
  }

  /**
   * Returnerer antall dager mellom this og parameteren denAndreDatoen.
   * Verdien er positiv hvis denAndreDatoen ligger lenger fram i tid enn this.
   */
  public int dagerForskjell(Dato denAndreDatoen) {
    long ms = denAndreDatoen.kalender.getTime().getTime() - kalender.getTime().getTime();
    return (int)(ms / (ANT_MS_PR_DØGN));
  }

 /**
  * Returnerer antall _hele_ år i forskjell mellom this og parameteren denAndreDatoen.
  * Verdien er positiv hvis denAndreDatoen ligger lenger fram i tid enn this.
   */
  public int antHeleÅrForskjell(Dato denAndreDatoen) {
    int år1 = finnÅr();
    int år2 = denAndreDatoen.finnÅr();
    int antÅrForskjell = år2 - år1;

    int dagnr1 = kalender.get(Calendar.DAY_OF_YEAR); // dag nr i året
    int dagnr2 = denAndreDatoen.kalender.get(Calendar.DAY_OF_YEAR);
    if (antÅrForskjell > 0) {
      if (dagnr1 > dagnr2) {
        antÅrForskjell--;
      }
    } else if (antÅrForskjell < 0) {
      if (dagnr2 > dagnr1) {
        antÅrForskjell++;
      }
    }
    return antÅrForskjell;
  }

  /**
   * Formaterer datoen for utskrift.
   */
  public String format() {
    java.text.SimpleDateFormat datoformat = new java.text.SimpleDateFormat(DATOSTRENG);
    return datoformat.format(kalender.getTime());
  }

  /**
   * Denne metoden returnerer en beskrivende tekststreng for dato og klokkeslett.
   */
  public String toString() {
    return kalender.getTime().toString();
  }
}