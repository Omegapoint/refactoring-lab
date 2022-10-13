# Agenda (avsett för läraren)

(Stegtid / Total tid vid avslutat steg)

## Presentera er! (5 min/5 min)

## Förväntningar (10 min/15 min)

(dela ut post-it-lappar)
Vad har du för förväntningar? (en per lapp)
Vad betyder det för dig?

## Vad betyder refaktorisering? (10 min/25 min)

* Funktionellt säkra förändringar av kod.
* Vad en code smell är (Vagheten)
* I kursen kommer vi använda oss av: Långa metoder (long methods), funktions-avundsjuka (feature envy), switch
  statements.

## Fowler-katan (5 min/30 min)

En lätt moderniserad version av övningen från Fowlers bok.
Vad är en kata?

"Kata är en rad förutbestämda rörelser, som utförs mot "osynliga" motståndare i luften.
Ordets betydelse innebär att i träningens rörelsemönster eftersträva perfektion.
Poängen med kata är att så grundligt lära in de tekniker som finns i katan,
att de sedan snabbt och korrekt kan användas i en uppkommen nödvärns-situation eller tävling."

Det är också ett släkte plattmaskar. Och ett vattendrag i Centralafrikanska republiken.

---

## Genomgång (55 min/1h 25 min)

Denna kata består av 4 olika små delar och upplägget är sådant att:

* Läraren gör uppgiften
* Studenterna härmar
* Diskutera varför. Till varje del så finns en diskussionsfråga.

Se till att alla har hittat repot: https://github.com/omegapoint/refactor-lab

## Kata 1

### Dela upp och organisera om koden i statement-metoden

### Del 1.A _(10 min)_

(demo/labb 10 min, disussion, demo/labb 10 min, diskussion, demo/labb 10 min, diskussion)

_Förklara lite snabbt (visa) vad statement metoden gör_

switch priceCode => extract method "int amountFor(Rental)", för hand.
"Råka" göra fel och orsaka ett avrundningsfel => Rätta genom att göra om variabeln till en double.
Gör om refaktorisering men från menyn.
Snygga upp parameter (namnet) och "result"-variabel.

**Disskusion:** Vad är skillnaden att använda sig av använda sig av inbyggda verktyg? Lättheten att göra fel även vid
små refaktoriseringar.

### Del 1.B _(10 min)_

data envy i amountFor.
Flytta den nya metoden till Rental - manuell, klipp ur method-body.
behåll delegerande metod i Customer.
Byt namn till t.ex. charge().

```java
double charge(){
    double thisAmount=0;
    // determine amount for each line
    switch(getMovie().getPriceCode()){
        case Movie.REGULAR:
            thisAmount+=2;
            if(getDaysRented()>2)
                thisAmount+=(getDaysRented()-2)*1.5;
            break;
        case Movie.NEW_RELEASE:
            thisAmount+=getDaysRented()*3;
            break;
        case Movie.CHILDRENS:
            thisAmount+=1.5;
            if(getDaysRented()>3)
                thisAmount+=(getDaysRented()-3)*1.5;
            break;
        }
    return thisAmount;
}
```

**Diskussion:** varför gjorde vi denna rekatorinseringen?

### Del 1.C _(15 min)_

I statement - "replace temp with query", dvs inline på "thisAmount"
(behövs egentligen inte göras än)
kan motiveras av att man vill bryta ut metod "statementDetailsForRental"

```java
// show figures for this rental
result+="\t"+each.getMovie().getTitle()+"\t"+String.valueOf(each.getThisAmount())+"\n";
totalAmount+=each.getThisAmount();
```

Argument för:

* Minskar beroendet. Behöver inte vara beroende av this amount
* ska man spendera tid på mikroptimeringar? Ibland under en refaktorisering eller under en fas kan det vara bra att
  faktiskt inte bry sig om performance ut att det gör man sedan när man anser att man är någorlunda klar med programmet.
  Då mäter man vart ens största minnes och tidskrävande operationer är någonstans och optimerar på plats.

Byt ut ++ mot += för att göra algoritmen tydligare.
i Customer.statement - frequentRenterPoint
int thisFrequentRenterPoint

Vi bryter ut tmpPoints för att göra (o)beroendet till frequentrenterpoint tydligare både för vertyget och oss själva.
...
frequentRenterPoint += thisFrequentRenterPoint

```java
// add frequent renter points
int tmpPoints=0;
tmpPoints+=1;
// add bonus for two days new release rental
if((each.getMovie().getPriceCode()==Movie.NEW_RELEASE)&&each.getDaysRented()>1){
    tmpPoints+=1;
}
frequentRenterPoints+=tmpPoints;

```

Returnera olika beroende på villkor istället för att summera ihop och returnera resultatet. Börja
med att bryta det till en if-else istället för ternary. Låt dem se om de kan förända det till ternary.

extract method på fyra rader om thisFrequentRenterPoint
move method till Rental

```java
int getFrequentRentalPoints(){
    return getMovie().getPriceCode()==Movie.NEW_RELEASE&&getDaysRented()>1?2:1;
}
```

**Diskussion:** Vad är skillnaden mellan enkelt/lätt gentemot att vara van vid.

### Del 1.D _(15 min)_

Pilla isär loopen och pilla ut totalAmount-beräkningen till egen loop.

När man får en metod som tar in en totalAmount och skickar tillbaka en totalamount. för att undvika skapa en tmpvariabel
tmpAmount för totalamount och sedan tilldela den efter loopen från värdet av tmpAmount. Då när du tar extract method så
kommer du inte få att den tar in samma sak som den returnerar.

Ska denna då flyttas till Rentals? Nej för den handhar flera olika rentals.

Pilla ut "totalFrequentRenterPoints" på samma sätt som ovanstånde.

Överkurs: Bygg om looparna till stream().mapToInt().sum(). (Inbyggd refaktorisering).

Extrahera beskrivningsraderna och konvertera till stream. Det får de göra själva. Så elaka är vi.

Nu kan man även göra replace tmpvariable with query instead. med andra ord kan vi ersätta totalAmount och Frequentpoints
med ett metodanrop.

### Sammanfattning _(5 min)_

Skriv ner tre saker du skulle vilja komma ihåg.

### Paus (15 min/1h 40 min)

### Repetition: en sak du ville komma ihåg

Kodlukter: Långa metoder, dataavundsjuka.
Refaktoriseringar: ??

___

## Genomgång (30 min/2h 10 min)

Mål: Isolera prisberäkningen.

Vi får reda på att butiken kommer att vilja ha fler typer av priser
och kunna byta prissättning på filmerna allt eftersom. Kan vi förändra
koden så att det blir lättare att stödja en sådan funktion?

Inuti varje switch-sats finns några små klasser som vill komma ut. Open-Closed principle.
Enklare att lägga till en ny priskategori utan att modifiera i Rental eller Customer.

## Kata 2

### Isolera prisberäkningen

### Del 2.A

Nu vill vi flytta charge/amount/price från Rental till Movie. Vi skulle kunna flytta
den direkt (visa eventuellt) men vi kan se att det blir lite konstigt med flera beroenden.
Vi väljer att först se till att ta in beroendet till metoden.

* Bryt ut daysRented till lokal variabel med introduce variable (lägg ovanför thisAmount)

```java
public double charge(){
    int daysRented1=getDaysRented();
    double thisAmount=0;
    // determine amount for each line
    switch(getMovie().getPriceCode()){
        case Movie.REGULAR:
            thisAmount+=2;
            if(daysRented1>2)
                thisAmount+=(daysRented1-2)*1.5;
            break;
        case Movie.NEW_RELEASE:
            thisAmount+=daysRented1*3;
            break;
        case Movie.CHILDRENS:
            thisAmount+=1.5;
            if(daysRented1>3)
                thisAmount+=(daysRented1-3)*1.5;
            break;
        }
    return thisAmount;
}

```

* Bryt ut tillfällig hjälpmetod chargeForDaysRented
* Flytta chargeForDaysRented(daysRented) till Movie. Vi får med hela Customer-objektet nu, men vi använder movie från
  Customer-objektet istället för direkt. Använd priceCode direkt. Städa bort Customer ur parameterlistan.

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

* Extrahera tillfällig amount-metod i switch-sats i Movie.
  (a) skapa en variabel som håller tmpamount = 0
  (b) lägg thisAmount += tmpamount
  (c) byt ut så att det är tmpamount som används istället
  (d) nu kan du använda extraxt method

```java
double tmpAmount=0;
tmpAmount+=2;
if(getDaysRented()>2)
    tmpAmount+=(getDaysRented()-2)*1.5;
thisAmount+=tmpAmount;
``` 

Fortsätt med resterande. I Movie finns nu metoderna charge (publik), childrensCharge() (privat),
newReleaseCharge() (privat) samt regularCharge().

**Diskussion:** Blev det egentligen bättre nu? Nu skickar vi med ett rental-data in i Movie?

### Del 2.B - Design patterns

Nu skulle vi vilja få bort switchsatsen som vi kan lösa med hjälp av polymorfi. Detta kan upnås med hjälp av
arv eller interface. Vi kommer att välja att göra med strategi. Alltså en egenskap istället för något man är.
(a) Med arv så har vi ett är beroende.
(b) Med interface blir det ett har eller kan beroende.

**Design patterns**

Kort genomgång av Design Patterns, vad är det? _(det finns en powerpoint presentation i repot)_

* Visa två-tre typer av patterns
* Strategipattern
* (Visa ett kort exempel på tavlan om composition over inheritance)

Koda live:

* Extrahera anropet till getPriceCode till en lokal variabel
* Extrahera en tillfällig metod chargeForDaysAndPriceCode(int daysRented, int priceCode)

```java
double chargeForDaysRented(int daysRented){
    int priceCode=getPriceCode();
    return chargeForDaysAndPriceCode(daysRented,priceCode); 
}

private double chargeForDaysAndPriceCode(int daysRented,int priceCode){
    double thisAmount=0;
    // determine amount for each line
    // switch
    return thisAmount;
}
```

* Extrahera metoden till delegate Refactorings -> Extract -> Delegate. PriceCalculatorImpl
* Städa upp i chargeForDaysRented(int daysRented)

**Diskussion:** Är detta steg refaktorisering? Eller är detta en redesign?

### Del 2.C

* Flytta initieringen av PriceCalculatorImpl till konstruktorn i Movie. Ta in den som parameter i konstruktorn.
* Kontrollera hur det ser ut i testet (kör testet)
* Extract Interface
* Kopiera klassen PriceCalculatorImpl (F5) och skapa RegularPriceCalculator
* Inaktivera de delar av switchsatsen som inte har med Regular att göra.
* Försök byta ut PriceCalculatorImpl mot RegularPriceCalculator i testet (går inte)
* Gör `chargeForDaysRentedAndPriceCode` i PriceCalculatorImpl publik
* Extrahera Interface (och ta med `chargeForDaysRentedAndPriceCode`)
* Låt även RegularPriceCalculator implementera interfacet

### Del 2.D
* Ändra så att Movie tar in interface i konstruktorn istället
* Byt ut PriceCalculatorImpl till RegularPriceCalculator i testet (och kör testet)
* Gör så att endast Regular-fallet är implementerat i RegularPriceCalculator::chargeForDaysRentedAndPriceCode. De andra kastar exception.
* Upprepa för Childrens och New Release (i det senare fallet kan PriceCalculatorImpl döpas om)
* Byt ut skapandet i testet (även för setPriceCode-testet)

* Testfel!

**Diskussion:** Ett test failar och det är för att vi nu inte använder pricecode längre. Varför?

### Del 2.E

* Lägg till metod för att ändra calculator
* Ta bort switch statement i alla PriceCalculator.
* Safe delete på priceCode i PriceCalculator::amountForCategory

**Diskussion:** Ta bort priceCategory? Är vi klara nu?

**Med arv:**

Skapa subklass ChildrenMovie.
Kopiera hela price-metoden från Movie till ChildrenMovie.
Visa att det är ChildrenMovie.price som körs genom att ändra gränsen för rabatt.
Eller lägg till en exception för typen CHILDREN.
Rensa ChildrenMovie.price från de andra casen.
Rensa Movie.price från Children-case.
(notera likheten med "pilla isär loop")
Kopiera nu-reducerade pricemetoden från Movie till ny klass NewReleaseMovie.
Rensa.
Kopiera väldigt-reducerade pricemetoden från Movie till ny klass RegularMovie.
Rensa.
Nu kan vi göra Movie abstrakt - vi bör inte skapa objekt av den.
Nu kan vi ta bort price-metodens implmentation (som ändå är bisarr, gratis) - abstract.

Begränsning -> film kan inte byta kategori under sin livstid.
Kan illustreras genom att ta bort setPriceCategory.
getPriceCategory kan returnera konstant; constructor parameter försvinner.

### Övning (60 min/3h 10 min)

Gör själva parvis.

___

## Sammanfattning (20 min/3h 30 min)

Kodlukter: Switch-sats. Överkomplicerade beräkningar med mutationer.
Refaktoriseringar: ??

En punkt från varje deltagare.

### Extra material

- Thread-safe refactoring
- Reflection safe refactoring
