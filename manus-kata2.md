# Kata 2

Som nämndes i del ett. Det är helt ok att gå tillbaka i inspelningen
och titta igen hur uppgiften är tänkt att lösa, men gör gärna ett
försök själv först.

Vi får reda på att butiken kommer att vilja ha fler typer av filmer.
De kommer också att vilja ha möjlighet att byta prissättning på filmer.
Vi vet inte så mycket mer än så just nu. Vilka typer av filmer eller
hur de har tänkt sig med prissättningen är inte helt klart än.

Kan vi förändra koden för att underlätta det som komma skall?

Om vi tittar på koden så kan vi se att om vi ska lägga till ytterligare
en typ av film kommer vi att behöva ändra i båda Movie, men också i
rental. I varje case-del av switch-satsen verkar det kunna finnas en
liten klass som skulle vilja komma ut. Det skulle göra det enklare
att lägga till en ny priskategori.

Vi behöver nog göra lite förarbete, men vi sätter det som någon
form av långsiktigt mål för denna Kata

Del 2.A

Om vi tittar i `charge()` ser vi i switch-satsen att informationen
vi använder för att räkna ut kostnaden baseras dels på data från
Rental (där vi befinner oss) i form av `daysRented` dels från Movie
if form av vilken typ av film det är. Då logiken beror på vilken
typ av film det är, verkar Movie vara en bättre plats för logiken.
Låt oss göra ett försöka att flytta den.

_Gör `Move` igen på `charge()`. Det verkar inte gå så bra._

Vad är det som händer? Varför fungerar det inte? Vi har ju
fortfarande ett beroende till `Rental` i form av `daysRented` och
det behöver vi lösa. Vi försöker tydliggöra detta beroende.

_Gör `Extract Variable` på `getDaysRented()` och skapa en variabel
`int daysRented` och lägg överst i `charge()`_

```java
public double charge(){
        int daysRented=getDaysRented();
        double thisAmount=0;
        // determine amount for each line
        switch(getMovie().getPriceCode()){
        case Movie.REGULAR:
        thisAmount+=2;
        if(daysRented>2)
        thisAmount+=(daysRented-2)*1.5;
        break;
        case Movie.NEW_RELEASE:
        thisAmount+=daysRented*3;
        break;
        case Movie.CHILDRENS:
        thisAmount+=1.5;
        if(daysRented>3)
        thisAmount+=(daysRented-3)*1.5;
        break;
        }
        return thisAmount;
        }
```

Vi har nu tydliggjort vårt beroende till hur många dagar filmen
hyrs genom att samla det på ett ställe. Vi kan nu skapa en hjälp-
metod och injicera beroendet istället.

_Gör `Extract Method` på allt utom översta raden i metoden._

Vi får nu två metoder, en där vi har ett beroende till klassen
metoden ligger i, och en i vilket beroendet är injicerat.

```java
public double charge(){
        return chargeForDaysRented(getDaysRented());
        }

private double chargeForDaysRented(int daysRented1){
        double thisAmount=0;
        //switch statement
        return thisAmount;
        }
```

Vi pratade om att det verkade vara kunna vara en liten klass i
varje del av switch-satsen. Skulle vi kunna bryta ut dem?

_Testa att använda extract method på en switch-sats._

Vi får ett problem vi stött på tidigare, vi tar en och returnerar
samma variabel. Skapa en tmp-amount i början på varje case-del.

```java
case CHILDRENS:
        double tmpAmount=0;
        tmpAmount+=1.5;
        if(daysRented>3)
        tmpAmount+=(daysRented-3)*1.5;
        thisAmount+=tmpAmount;
        break;
```

Vi kan sedan göra extract method på allt ovanför sista raden. Vi
skapar en `chargeForRegularDaysRented`, `chargeForNewReleaseRented`
och `chargeForChidrensDaysRented`. Notera att dessa blir static.
Varför blir de det? IntelliJ känner själv av att de saknar beroenden
utanför det som injiceras till metoden, de kan därför vara static,
de varken påverkar eller påverkas av klassens state. Det är bra
för oss att veta, för det vi har något som skulle kunna brytas ut
då det saknar externa beroenden.

## Del 2.B

Vi skulle vilja få bort switch-satsen. Vi skulle kunna lösa det
med polymorfi. Det kan vi uppnå med arv eller interface. Men vi
kommer att lösa det med strategi istället. Vad har det för fördelar?

_Avbrott för design patterns_

Så nu vet vi att vi ska använda något vi har istället för något
vi är. Så det ska finnas något vi har i Movie istället för något
vi är. Låt oss kalla det för en priskalkylator. Det borde vara en
priskalkylator och det finns flera olika priskalkylatorer
beroende på hur priset ska beräknas. Låter som ett interface
med ett par olika implementationer. Vi har en riktning, men låt
oss ta ett steg i taget. Första steget blir att byta ut "är" mot
"har".

Vi tittar igen på `chargeForDaysRented` och ser att vi har
ytterligare ett beroende till klassen vi befinner oss is, nämligen
`getPriceCode`. Vi behöver bryta ut det. Så vi upprepar knepet
från tidigare.

_Bryt ut getPriceCode till variabel och extrahera sedan övrigt_

```java
    private static double chargeForDaysRentedAndPriceCode(int daysRented,int priceCode){
        double thisAmount=0;
        // determine amount for each line
        switch(priceCode){
        case REGULAR:
        thisAmount+=chargeForRegularDaysRented(daysRented);
        break;
        case NEW_RELEASE:
        thisAmount+=chargetForNewReleaseDaysRented(daysRented);
        break;
        case CHILDRENS:
        thisAmount+=chargeForChildrensDaysRented(daysRented);
        break;
        }
        return thisAmount;
        }
```

Vi ser igen att metoden blir statisk. Våde enda beroenden kommer
alltså in som parametrar till metoden. Det borde vara helt säkert
att bryta ut den.

_Testa att bryta ut den när den är static med hjälp av Refactoring
-> ._

Det här var inte riktigt det vi ville ha. Vi vill ju ha någon form
av priskalkylator i Movie som vi kunde injicera. Nu fick vi en
statisk referens istället. Vi går tillbaka och gör om till instans-
metod istället.

_Lägg till default-konstruktor. Använd Refactor -> Convert to
Instance. Ta sedan bort default-konstruktor._

Vi gör ett nytt försök med Extract Delegate. Vi lägger till metoderna
för varje enskild filmtyp också, de används ju av
`chargeForDaysRentedAndPriceCode.`Nu gick det bättre.

_Rensa upp icke använda metoder i Movie._

_DISKUSSION: Är detta refactoring eller redesign?_

## Del 2.C

Om vi tittar i Movie så ser vi att vi nu **har** något som beräknar
priset. Men det är fortfarande så att det skapas i klassen. Det
vore bättre om vi kunde injicera beroendet.

_Flytta initiering till konstruktorn. Därefter extrahera som
parameter._

```java
    public Movie(String title,int priceCode,PriceCalculatorImpl priceCalculatorImpl){
        this.title=title;
        this.priceCode=priceCode;
        this.priceCalculatorImpl=priceCalculatorImpl;
        }
```

Hur ser det nu ut i testet? Vi testar att köra det också. Vi ser
att om vi kan skapa ett interface för `PriceCalculatorImpl` kan vi
lätt byta ut beräkningsmetod genom att skicka in en annan
priskalkylator. Att möjliggöra det borde bli vårt näta steg.

_Gör chargeForDaysRentedAndPriceCode publik. Gör sedan Refactor ->
Extract Interface och markera chargeForDaysRentedAndPriceCode_

Vi vill nu skapa en PriceCalculator för varje uträkning. Låt oss
börja med Regular. Vi kopierar `PriceCalculatorImpl` med hjälp av
vår IDE och skapar `RegularPriceCalculator`. Vi ser att den
automatiskt implementerar vårt interface.

Eftersom detta endast ska vara för regular vill vi tydligt visa
att vi inte använder något annat, så vi ser till att kasta ett
exception om vi skulle råka ta oss in i den delen.

```java
switch(priceCode){
        case Movie.REGULAR:
        thisAmount+=chargeForRegularDaysRented(daysRented);
        break;
        case Movie.NEW_RELEASE:
        throw new IllegalStateException("En film kan inte både vara REGULAR och NEW_RELEASE");
        case Movie.CHILDRENS:
        throw new IllegalStateException("En film kan inte både vara REGULAR och CHILDRENS");
        }
```

Vi behöver nu byta ut så vi använder oss av vår nya
`RegularPriceCalculator` i vårt test.

_Byt ut i testet och notera att det blir fel_

Det blir fel. Och det är för att vi fortfarande kräver explicit
en PriceCalculatorImpl. Men det ska ju gå lika bra med en
`RegularPriceCalulator` istället så vi ser till att byta ut i
Movie så att vi kan skicka in valfri priskalylator som
implementerar samma interface istället.

```java
private final PriceCalculator priceCalculator;

private String title;
private int priceCode;

public Movie(String title,int priceCode,PriceCalculator priceCalculator){
        this.title=title;
        this.priceCode=priceCode;
        this.priceCalculator=priceCalculator;
        }
```

_Kontrollera att det ser bra ut i testet och kör sedan testet._

Nu gick det bättre och vi kastade inget exception.

## Del 2.D

Nu vill vi göra detsamma även för `NEW_RELEASE` och `CHILDRENS`.
Vi kopierar `PriceCalculatorImpl` igen och byter i switchsatsen
de delar som inte behövs.

Vi byter ut i `CustomerTest` och ser till att skapa instanser av
våra nya priskalylatorer istället.

```java
tolvan.addRental(new Rental(new Movie("Fast and Furious 4711",Movie.NEW_RELEASE,new NewReleasePriceCalculator()),2));
        tolvan.addRental(new Rental(new Movie("Toy Story 17",Movie.CHILDRENS,new ChildrensPriceCalculator()),5));
        tolvan.addRental(new Rental(new Movie("Casa Blanca",Movie.REGULAR,new RegularPriceCalculator()),1));
```

```java
Customer tolvan=new Customer("Tolvan");
final Movie dieHard=new Movie("Die Hard",Movie.CHILDRENS,new ChildrensPriceCalculator());
```

_Ignorera att vi byter priskod senare i det andra testet_

_DISKUSSION: Vad var det som hände, varför får vi fel i testet?_

## Del 2.E

Vi ser att vi måste lägga till möjligheten att ändra priskalylator.
Hur ska vi göra detta? En ny metod för att ändra kalkylator eller
se till att `setPriceCode` nu också tar in en `PriceCalculator`?

_DISKUSSION: Var ska vi lägga till den?_

Det finns inget tydligt rätt svar. Välja att göra den ändring du
tycker verkar bäst. Här väljer vi att lägga till en ny metod.
Nackdelen är att vi kommer att behöva göra två anrop för att byta
från en typ av film till en annan. Fördelen är att vi kan byta
den ena utan att behöva byta den andra. Det fanns ju en antydan
att butiken hade en önskan om att kunna byta prissättning (och då
kanske utan att behöva byta typ av film). En film kanske skulle
kunna ha fler typer i framtiden? Hur blir det när en barnfilm
är precis nyutgiven?

```java
dieHard.setPriceCode(Movie.NEW_RELEASE);
        dieHard.setPriceCalculator(new NewReleasePriceCalculator());
```

_Ta bort final från PriceCalculator-fältet i Movie och lägg till
setter för PriceCalculator_

```java
public void setPriceCalculator(PriceCalculator priceCalculator){
        this.priceCalculator=priceCalculator;
        }
```

Vi har fortfaranade en hel del kod kvar som inte längre borde
köras. Låt oss städa upp den också. Vi kan börja i våra nya
PriceCalculators där vi helt kan ta bort switch-satsen.

```java
@Override
public double chargeForDaysRentedAndPriceCode(int daysRented,int priceCode){
        double thisAmount=0;
        // determine amount for each line
        thisAmount+=chargeForRegularDaysRented(daysRented);
        return thisAmount;
        }
```

Vi ser att vi även kan städa upp extra metoder i dessa klasser
som inte används.

_Gör safe delete på de extra metoder som finns i respektive klass_

Vi se nu att vi kan göra inline på den extra metoden som finns
i varje priskalkylator.

_Gör `Refactor` -> `Inline method` på extra-metoden och ta bort_

Vi kan sedan göra ytterligare förenklingar i den kvarvarande
logiken i klassen. I `RegularPriceCalculator` kan vi ta bort
extra tillfällig variabel.

```java
@Override
public double chargeForDaysRentedAndPriceCode(int daysRented,int priceCode){
    double tmpAmount=0;     
    tmpAmount+=2;
    if(daysRented>2){
        tmpAmount+=(daysRented-2)*1.5;
    }
    return tmpAmount;
}
```

Om vi tittar i alla våra nya PriceCalculators ser vi att vi inte
längre använder oss av priceCode. Vi får dock ingen varning om
att den inte längre används? Det är för att den är deklarerad i
PriceCode. Vi flyttar oss till PriceCode och metoden
`chargeForDaysRentedAndPriceCode` och väljer `Refactor` ->
`Change Signature` och tar sedan bort priceCode. Vi får upp en
varning om att den fortfarande används i vår gamla
PriceCodeCalculatorImpl. Vi kan se att den inte längre används,
så vi gör en `Safe Delete` på den. Vi upprepar därefter vårt
försök att ta bort priceCode.

```java
public interface PriceCalculator {
    double chargeForDaysRentedAndPriceCode(int daysRented);
}
```

Vi ser även till att byta namn på metoden till
`chargeForDaysRented` då vi inte längre är beroende av priskoden.

Kan vi helt ta bort `priceCategory` nu? Det verkar inte riktigt 
som det. Den verkar fortfarande användas i poängberäkningen. 
Ska vi bryta ut den också?

_DISKUSSION: Ovan fråga_

_DISKUSSION: Är vi klara med vår refactoring?_

_DISKUSSION: Har vi bara gjort en refactoring eller har vi nu
gjort en redesign också?_

_DISKUSSION: Finns det saker vi inte tagit hänsyn till här?
Hur fungerar det nu om vi vill spara ett state för en film i 
en databas?_